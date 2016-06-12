package projeteis;


import java.util.Collection;

import main.Timer;
import model.Elemento;
import model.Estado;

public abstract class Projetil extends Elemento {
	protected Timer timer;
	public Projetil(double x, double y, double raio, int layer, Estado estado, Timer timer) {
		super(x, y, raio, layer, estado);
		this.timer = timer;
	}
	
	public abstract void mover();
	public abstract void explodir();
	public abstract void checarColisoes(Collection<Elemento> alvos);
	
}
