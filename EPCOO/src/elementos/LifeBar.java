package elementos;

import interfaces.Observer;
import interfaces.Subject;
import interfaces.TemHp;

import java.awt.Color;

import pacote.GameLib;
import model.Elemento;

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
	public void notify(Subject s) {
		if(s instanceof TemHp){
			//total - comp
			//atual - x
			compAtual = (objeto.getHpAtual() * comprimento)/objeto.getTotalHp();
		}
	}

}
