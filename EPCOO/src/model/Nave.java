package model;

import interfaces.Destrutivel;

import java.util.*;

import main.Timer;
import armas.Arma;
import projeteis.Projetil;

public abstract class Nave extends ElementoMutavel implements Destrutivel{
	private Map<String,Arma> armas;
	protected double raio;
	protected Arma atual;
	protected Timer timer;
	
	public Nave(double x, double y, int layer, Estado estado,Timer timer, double raio){
		super(x,y,layer,estado);
		this.timer = timer;
		this.raio = raio;
		armas = new HashMap<String, Arma>();
	}
	
	public void addArma(Arma arma){
		armas.put(arma.getNome(),arma);
	}
	
	public void removeArma(String nome){
		armas.remove(nome);
	}
	
	public double getRaio(){
		return raio;
	}
	
	public void selecionaArma(Arma arma){
		if(armas.containsKey(arma.getNome()))
			this.atual = armas.get(arma);
	}
	
	public abstract List<Projetil> atirar();
	public abstract void mover();
	protected abstract void explodir();

	@Override
	public double getRaioColisao() {
		return raio;
	}
}
