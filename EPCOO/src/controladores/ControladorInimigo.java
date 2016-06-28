package controladores;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import pacote.GameLib;
import powerups.PowerUp;
import elementos.LifeBar;
import arquivo.TimerElemento;
import inimigos.Boss;
import inimigos.Factory;
import interfaces.Subject;
import main.Timer;
import model.Elemento;
import model.Estado;
import model.Nave;

public class ControladorInimigo extends ControladorNave implements Subject {
	private PriorityQueue<TimerElemento> enemies;
	private List<Elemento> hud;
	public ControladorInimigo(Timer timer, PriorityQueue<TimerElemento> enemies) {
		super(timer);
		this.enemies = enemies;
		hud = new LinkedList<Elemento>();
	}
	
	public void setEnemies(PriorityQueue<TimerElemento> enemies){
		this.enemies = enemies;
	}

	@Override
	protected void spawnNave() {
		long init = timer.getIniFase();
		long current = timer.getCurrentTime();
		if(enemies!=null){
			while(enemies.peek()!=null && current > init+enemies.peek().getSpawnTime()){
				if(enemies.peek().isEnemy()){
					Nave aux;
					aux = Factory.instanciarInimigo(enemies.poll(), timer);
					aux.addObserver(this);
					if(aux instanceof Boss){
						Boss b = (Boss)aux;
						hud.add(new LifeBar(
								GameLib.WIDTH/2,
								30,
								0,
								b,
								GameLib.WIDTH*0.8,
								GameLib.HEIGHT*0.05,
								Color.RED, 
								Color.DARK_GRAY)
								);
					}
					this.getNaves().add(aux);
				} else {
					
				}
			}
		}
	}

	@Override
	public void notify(Subject s) {
		super.notify(s);
		if(s instanceof Boss){
			Boss b = (Boss)s;
			if(b.getEstado()==Estado.INACTIVE){
				this.hud.clear();
				this.notifyObservers();
			}
		} else if(s instanceof PowerUp){
			//TODO 
		}
	}
	
	public void drawHud(){
		for(Elemento e : hud){
			e.draw();
		}
	}
}
