package elementos;

import interfaces.Observer;
import interfaces.Subject;
import interfaces.TemHp;

import java.awt.Color;

import main.GameLib;
import model.Elemento;

public class LifeBar extends Elemento implements Observer{
	//classe que representa uma barra de vida, observa o elemento cuja vida deve representar
	private TemHp objeto;
	private double comprimento, compAtual, altura;
	private Color vida, fundo;
	
	public LifeBar(double x, double y, TemHp obj, double comprimento, double altura, Color vida, Color fundo) {
		super(x, y);
		this.objeto = obj;
		compAtual = this.comprimento = comprimento;
		this.altura = altura;
		this.vida = vida;
		this.fundo = fundo;
		if(objeto instanceof Subject) {
			Subject s = (Subject) objeto;
			s.addObserver(this);
		}
	}

	@Override
	public void draw() {
		GameLib.setColor(fundo);
		GameLib.fillRect(x, y, comprimento, altura);
		
		GameLib.setColor(vida);
		GameLib.fillRect(x, y, compAtual, altura);
	}

	@Override
	public void notify(Object s) {
		if(s instanceof TemHp){
			compAtual = (objeto.getHpAtual() * comprimento)/objeto.getTotalHp();
		}
	}

}
