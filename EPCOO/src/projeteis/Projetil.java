package projeteis;

import pacote.GameLib;

import java.util.Collection;
import java.util.Iterator;

import interfaces.Destrutivel;
import main.Timer;
import model.ElementoMutavel;
import model.Estado;


public abstract class Projetil extends ElementoMutavel{
	private double velocidadeY, velocidadeX;
	protected Timer timer;
	public Projetil(double x, double y, double vX, double vY, int layer, Estado estado, Timer timer) {
		
		super(x, y, layer,estado);
		this.velocidadeX = vX;
		this.velocidadeY = vY;
		this.timer = timer;
	}

	
	public void mover() {
		Estado estado = this.getEstado();
		if(estado == Estado.ACTIVE){
			
			/* verificando se projétil saiu da tela TODO
			 * nos outros tiros isso tem que ser diferente*/
			if(y < 0 || y > GameLib.HEIGHT) {
				
				this.setEstado(Estado.INACTIVE);
			}
			else {
			
				x += velocidadeX * timer.getDelta();
				y += velocidadeY * timer.getDelta();
			}
		}
	}


	
	public void checarColisoes(Collection<? extends Destrutivel> alvos) {
		if(this.getEstado()==Estado.ACTIVE){
			Iterator<? extends Destrutivel>it = alvos.iterator();
			double dx,dy,dist;
			Object obj;
			Destrutivel aux;
			while(it.hasNext()){
				obj = it.next();
				aux = (Destrutivel)obj;
				if(aux.getEstado() == Estado.ACTIVE){
					dx = x - aux.getX();
					dy = y - aux.getY();
					dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < aux.getRaioColisao()){
						aux.hit();
						this.setEstado(Estado.INACTIVE);
						break;
					}
				}
			}
		}
	}

}
