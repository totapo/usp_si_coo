package controladores;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import elementos.LifeBar;
import pacote.GameLib;
import arquivo.TimerElemento;
import inimigos.Boss;
import inimigos.Factory;
import interfaces.Observer;
import interfaces.Subject;
import main.Timer;
import model.Elemento;
import model.Estado;
import model.Nave;

public class ControladorInimigo extends ControladorNave implements Subject{
	
	private List<Elemento> hud;
	
	public ControladorInimigo(Timer timer) {
		super(timer);
		hud = new LinkedList<Elemento>();
	}
	
	@Override
	public void limparMemoria() {
		this.disparos.clear();
		this.hud.clear();
		this.getNaves().clear();
	}

	@Override
	public void notifyObservers() {
		for(Observer o : obs){
			o.notify(this);
		}
	}
	
	public void drawHud(){
		for(Elemento e : hud){
			e.draw();
		}
	}

	@Override
	public void notify(Object s) {
		super.notify(s);
		if(s instanceof Boss){
			Boss b = (Boss)s;
			if(b.getEstado()==Estado.INACTIVE){
				this.hud.clear();
				this.notifyObservers();
			}
		} else if(s instanceof TimerElemento){
			TimerElemento a = (TimerElemento)s;
			if(a.isEnemy()){
				Nave aux;
				aux = Factory.instanciarInimigo(a, timer);
				aux.addObserver(this);
				if(aux instanceof Boss){
					Boss b = (Boss)aux;
					hud.add(new LifeBar(
							GameLib.WIDTH/2,
							30,
							0,
							b,
							GameLib.WIDTH*0.8,
							GameLib.HEIGHT*0.01,
							Color.RED, 
							Color.DARK_GRAY)
							);
				}
				this.getNaves().add(aux);
			} 
		}
	}
}
