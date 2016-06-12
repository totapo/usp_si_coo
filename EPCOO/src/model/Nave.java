package model;

import java.util.*;

import main.Timer;
import armas.Arma;
import projeteis.Projetil;

public abstract class Nave extends Elemento{
	protected Map<String,Arma> armas;
	protected Arma atual;
	protected Timer timer;
	
	public Nave(double x, double y, int layer, double raio, Estado estado,Timer timer){
		super(x,y,raio,layer,estado);
		this.timer = timer;
	}
	
	public void addArma(Arma arma){
		armas.put(arma.getNome(),arma);
	}
	
	public void removeArma(Arma arma){
		armas.remove(arma.getNome());
	}
	
	public void selecionaArma(Arma arma){
		if(armas.containsKey(arma.getNome()))
			this.atual = armas.get(arma);
	}
	
	public abstract List<Projetil> atirar();
	public abstract void mover();
	public abstract void explodir();
	
}
