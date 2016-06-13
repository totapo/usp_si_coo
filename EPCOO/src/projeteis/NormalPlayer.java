package projeteis;

import interfaces.Destrutivel;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;

import pacote.GameLib;
import main.Timer;
import model.Estado;

public class NormalPlayer extends Normal {

	public NormalPlayer(double x, double y, double vX, double vY, int layer,
			Estado estado, Timer timer) {
		super(x, y, vX, vY, layer, estado, timer);
	}

	@Override
	public void draw() {
		GameLib.setColor(Color.GREEN);
		GameLib.drawLine(x, y - 5, x, y + 5);
		GameLib.drawLine(x - 1, y - 3, x - 1, y + 3);
		GameLib.drawLine(x + 1, y - 3, x + 1, y + 3);
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
				
				if(dist < aux.getRaioColisao()){
					aux.hit();
					this.setEstado(Estado.INACTIVE);
					break;
				}
			}
		}
	}

}
