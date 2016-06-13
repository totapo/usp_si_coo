package controladores;

import interfaces.Destrutivel;

import java.util.Collection;
import java.util.Iterator;

import main.Timer;
import model.*;

public class ControladorPlayer extends Controlador{

	public ControladorPlayer(Timer timer, Player player) {
		super(timer);
		this.getNaves().add(player);
		player.addObserver(this);
	}

	@Override
	protected void spawnNave() {
		// DO Nothing
	}
	
	public void checarColisoes(Collection<? extends Destrutivel> objetos){
		Nave player = this.getNaves().get(0);
		if(player.getEstado() == Estado.ACTIVE){
			Iterator<? extends Destrutivel> it = objetos.iterator();
			Destrutivel i;
			while(it.hasNext()){
				i= it.next();
				double dx = i.getX() - player.getX();
				double dy = i.getY() - player.getY();
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if(dist < (player.getRaioColisao() + i.getRaioColisao()) * 0.8){
					player.hit();
					break;
				}
			}
		}
	}

}
