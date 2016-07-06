package projeteis;

import interfaces.Destrutivel;

import java.awt.Color;

import main.GameLib;
import model.Estado;

public class ProjetilInimigo extends Projetil {
	public ProjetilInimigo(double x, double y, double vX, double vY, double raio,
			Estado estado) {
		super(x, y, vX, vY, estado,raio);
	}

	@Override
	public void draw() {
		GameLib.setColor(Color.RED);
		GameLib.drawCircle(x, y, raio);
	}

	@Override
	public double criterioColisao(Destrutivel aux) {
		return (aux.getRaioColisao()+this.raio)*0.8;
	}


}
