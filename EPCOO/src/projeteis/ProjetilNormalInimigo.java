package projeteis;

import interfaces.Destrutivel;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;

import pacote.GameLib;
import main.Timer;
import model.Estado;

public class ProjetilNormalInimigo extends ProjetilNormal {
	private double raio;
	public ProjetilNormalInimigo(double x, double y, double vX, double vY, double raio, int layer,
			Estado estado, Timer timer) {
		super(x, y, vX, vY, layer, estado, timer);
		this.raio = raio;
	}

	@Override
	public void draw() {
		GameLib.setColor(Color.RED);
		GameLib.drawCircle(x, y, raio);
	}

	@Override
	public void checarColisoes(Collection<? extends Destrutivel> alvos) {
		if(this.getEstado()==Estado.ACTIVE){
			Iterator<? extends Destrutivel>it = alvos.iterator();
			double dx,dy,dist;
			Object obj;
			Destrutivel aux;
			while(it.hasNext()){
				obj = it.next();
				aux = (Destrutivel)obj;
				dx = x - aux.getX();
				dy = y - aux.getY();
				dist = Math.sqrt(dx * dx + dy * dy);
				
				if(dist < (aux.getRaioColisao() + raio) * 0.8){
					aux.hit();
					this.setEstado(Estado.INACTIVE);
					break;
				}
			}
		}
	}

}
