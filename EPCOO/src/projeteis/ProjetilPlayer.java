package projeteis;

import java.awt.Color;

import pacote.GameLib;
import main.Timer;
import model.Estado;

public class ProjetilPlayer extends Projetil {

	public ProjetilPlayer(double x, double y, double vX, double vY, int layer,
			Estado estado, Timer timer) {
		super(x, y, vX, vY, layer, estado, timer);
	}

	@Override
	public void draw() {
		GameLib.setColor(Color.GREEN);
		GameLib.drawLine(x, y - 5, x, y + 5);
		GameLib.drawLine(x - 1, y - 3, x - 1, y + 3);
		GameLib.drawLine(x + 1, y - 3, x + 1, y + 3);
	}

	
}
