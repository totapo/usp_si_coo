package controladores;

import arquivo.TimerElemento;
import inimigos.Factory;
import interfaces.Observer;
import interfaces.Subject;
import main.Timer;
import model.Nave;

public class ControladorInimigo extends ControladorNave implements Subject{
	
	public ControladorInimigo(Timer timer) {
		super(timer);
	}
	
	@Override
	public void limparMemoria() {
		this.disparos.clear();
		this.getNaves().clear();
	}

	@Override
	public void notifyObservers() {
		for(Observer o : obs){
			o.notify(this);
		}
	}

	@Override
	public void notify(Object s) {
		super.notify(s);
		 if(s instanceof TimerElemento){
			TimerElemento a = (TimerElemento)s;
			if(a.isEnemy() && !a.isBoss()){
				Nave aux;
				aux = Factory.instanciarInimigo(a, timer);
				aux.addObserver(this);
				this.getNaves().add(aux);
			} 
		}
	}
}
