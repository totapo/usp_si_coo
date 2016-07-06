package projeteis;

import interfaces.Destrutivel;

import java.awt.Color;

import main.Timer;
import model.Estado;
import model.GameLib;

public class ProjetilPlayer extends Projetil {

	public ProjetilPlayer(double x, double y, double vX, double vY,
			Estado estado, Timer timer) {
		super(x, y, vX, vY, estado, timer,0.0);
	}
	
	@Override
	public void draw() {
		GameLib.setColor(Color.GREEN);
		GameLib.drawLine(x, y - 5, x, y + 5);
		GameLib.drawLine(x - 1, y - 3, x - 1, y + 3);
		GameLib.drawLine(x + 1, y - 3, x + 1, y + 3);
	}

	@Override
	public double criterioColisao(Destrutivel aux) {
		return aux.getRaioColisao();
	}

	
}
