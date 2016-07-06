package armas;

import java.util.*;

import projeteis.Projetil;

public abstract class Arma {
	//classe que representa uma arma, a ideia era fazer com que cada arma disparasse os projeteis do seu jeito
	
	protected String nome;
	
	public Arma(String nome){
		this.nome = nome;
	}
	
	//Cada arma devera implementar seu proprio tipo de disparo
	//retorna uma lista pois as armas podem atirar apenas um projetil como varios de uma vez
	public abstract List<Projetil> disparar(double x, double y, double angle);
	
	public String getNome(){
		return nome;
	}

}
