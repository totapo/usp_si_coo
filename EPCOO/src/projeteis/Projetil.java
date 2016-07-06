package projeteis;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import interfaces.Destrutivel;
import main.GameLib;
import main.Timer;
import model.Elemento;
import model.ElementoMutavel;
import model.Estado;


public abstract class Projetil extends ElementoMutavel{
	private double velocidadeY, velocidadeX;
	protected Timer timer;
	protected ProjetilDropper pDropper = null;
	protected double raio;

	public Projetil(double x, double y, double vX, double vY, Estado estado, double raio) {
		super(x, y,estado);
		this.velocidadeX = vX;
		this.velocidadeY = vY;
		this.timer = Timer.getInstance();
		this.raio = raio;
	}
	
	public void setpDropper(ProjetilDropper pDropper) {
		this.pDropper = pDropper;
	}

	public List<Elemento> dropProjeteis(int n, double x, double y){
		if(pDropper != null)
			return pDropper.drop(n, x, y);
		return null;
	}
	
	public void mover() {
		Estado estado = this.getEstado();
		if(estado == Estado.ACTIVE){
			
			if(y < 0 || y > GameLib.HEIGHT) {
				
				this.setEstado(Estado.INACTIVE);
			}
			else {
			
				x += velocidadeX * timer.getDelta();
				y += velocidadeY * timer.getDelta();
			}
		}
	}
	
	public abstract double criterioColisao(Destrutivel aux);
	
	public void checarColisoes(Collection<? extends Destrutivel> alvos) {
		if(this.getEstado()==Estado.ACTIVE){
			Iterator<? extends Destrutivel>it = alvos.iterator();
			double dx,dy,dist;
			Object obj;
			Destrutivel aux;
			while(it.hasNext()){
				obj = it.next();
				aux = (Destrutivel)obj;
				if(aux.getEstado() == Estado.ACTIVE || aux.getEstado() == Estado.FLASHING){
					dx = x - aux.getX();
					dy = y - aux.getY();
					dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < criterioColisao(aux)){
						aux.hit();
						this.setEstado(Estado.INACTIVE);
						break;
					}
				}
			}
		}
	}

}
