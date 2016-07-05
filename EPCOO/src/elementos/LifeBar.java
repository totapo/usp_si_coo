package elementos;

import interfaces.Observer;
import interfaces.Subject;
import interfaces.TemHp;

import java.awt.Color;

import model.Elemento;
import model.GameLib;

public class LifeBar extends Elemento implements Observer{
	
	private TemHp objeto;
	private double comprimento, compAtual, altura;
	private Color vida, fundo;
	
	public LifeBar(double x, double y, int layer, TemHp obj, double comprimento, double altura, Color vida, Color fundo) {
		super(x, y, layer);
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
