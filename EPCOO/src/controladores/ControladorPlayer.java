package controladores;

import interfaces.Destrutivel;

import java.util.Collection;
import java.util.Iterator;

import armas.Arma;
import armas.ArmaDefault;
import main.Timer;
import model.*;
import pacote.GameLib;

public class ControladorPlayer extends ControladorNave{

	public ControladorPlayer(Timer timer) {
		super(timer);
	}

	@Override
	protected void spawnNave() {
		if(this.getNaves().size()==0){
			Player player = new Player(
					GameLib.WIDTH / 2, 		//x
					GameLib.HEIGHT * 0.90, 	//y
					1,						//layer 1 - assumindo HUD no 0
					0.25,					// velocidade no eixo x
					0.25,					// velocidade no eixo y
					Estado.ACTIVE,
					timer,
					12.0					//raio
					);
			Arma a = new ArmaDefault("gun",100,timer);
			player.addArma(a);
			player.selecionaArma(a);
			player.addObserver(this);
			this.getNaves().add(player);
		}	
	}
	
	public void checarColisoes(Collection<? extends Destrutivel> objetos){
		if(this.getNaves().size()>0){
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

}
