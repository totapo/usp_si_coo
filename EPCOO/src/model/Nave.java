package model;

import java.util.*;

public abstract class Nave extends Elemento{
	private double velocidade,angulo;
	private Map<String,Arma> armas;
	
	public void addArma(Arma arma){
		armas.put(arma.getNome(),arma);
	}
	
	public void removeArma(Arma arma){
		armas.remove(arma.getNome());
	}
	
	public void selecionaArma(Arma arma){
		
	}
	
	public double getVel(){
		return velocidade;
	}
	
	public double getAngulo(){
		return angulo;
	}
	
	public void setVelocidade(double velocidade){
		this.velocidade = velocidade;
	}
	
	public void setAngulo(double angulo){
		this.angulo = angulo;
	}
	
	public abstract List<Projetil> atirar(Collection<Elemento> alvos);
	public abstract void mover();
	public abstract void explodir();
	
}
