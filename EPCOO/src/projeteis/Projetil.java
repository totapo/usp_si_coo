package projeteis;


import interfaces.Destrutivel;

import java.util.Collection;

import main.Timer;
import model.ElementoMutavel;
import model.Estado;

public abstract class Projetil extends ElementoMutavel {
	protected Timer timer;
	public Projetil(double x, double y, int layer, Estado estado, Timer timer) {
		super(x, y, layer,estado);
		this.timer = timer;
	}
	
	public abstract void mover();
	public abstract void explodir();
	public abstract void checarColisoes(Collection<? extends Destrutivel> alvos);
	
}
