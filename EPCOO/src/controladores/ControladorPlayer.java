package controladores;

import interfaces.Destrutivel;
import interfaces.Subject;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import elementos.LifeBar;
import armas.Arma;
import armas.ArmaDefault;
import main.Timer;
import model.*;
import pacote.GameLib;

public class ControladorPlayer extends ControladorNave{
	private int hp;
	private List<Elemento> hud;
	public ControladorPlayer(Timer timer, int hp) {
		super(timer);
		this.hp = hp;
		hud = new LinkedList<Elemento>();
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
					12.0,					//raio
					hp);
			Arma a = new ArmaDefault("gun",100,timer);
			player.addArma(a);
			player.selecionaArma(a);
			player.addObserver(this);
			this.getNaves().add(player);
			hud.add(new LifeBar(
					GameLib.WIDTH/2,
					GameLib.HEIGHT-30,
					0,
					player,
					GameLib.WIDTH*0.9,
					GameLib.HEIGHT*0.01,
					Color.GREEN, 
					Color.DARK_GRAY)
					);
		}	
	}
	
	public void resetLife(){
		if(this.getNaves().size()>0){
			Player player = (Player) this.getNaves().get(0);
			player.setHp(player.getTotalHp());
		}
	}
	
	public void drawHud(){
		for(Elemento e : hud){
			e.draw();
		}
	}
	
	
	
	@Override
	public void notify(Subject s) {
		super.notify(s);
		if(s instanceof Player){
			Player p = (Player)s;
			if(p.getEstado() == Estado.INACTIVE){
				notifyObservers();
			}
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
