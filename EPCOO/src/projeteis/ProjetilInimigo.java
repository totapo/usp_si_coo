package projeteis;

import interfaces.Destrutivel;

import java.awt.Color;

import main.Timer;
import model.Estado;
import model.GameLib;

public class ProjetilInimigo extends Projetil {
	public ProjetilInimigo(double x, double y, double vX, double vY, double raio,
			Estado estado, Timer timer) {
		super(x, y, vX, vY, estado, timer,raio);
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
