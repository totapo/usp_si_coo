package controladores;

import java.util.*;

import inimigos.Inimigo;
import interfaces.Observer;

public abstract class Controlador implements Observer {
	private List<Inimigo> inimigos;
	
	public Controlador(){
		inimigos = new ArrayList<Inimigo>();
	}
	
	public List<Inimigo> getInimigos(){
		return inimigos;
	}
	
	public void removeInimigo(Inimigo i){
		inimigos.remove(i);
	}
	
	public abstract void execute(); //faz os inimigos se moverem, atirarem e spawna novos se for o caso

}
