package armas;

import java.util.*;

import projeteis.Projetil;

public abstract class Arma {
	
	protected String nome;
	
	public Arma(String nome){
		this.nome = nome;
	}
	
	//Cada "nave" devera implementar seu proprio tipo de disparo
	public abstract List<Projetil> disparar(double x, double y, double angle);
	
	public String getNome(){
		return nome;
	}

}
