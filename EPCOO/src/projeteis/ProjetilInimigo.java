package projeteis;

import java.awt.Color;

import pacote.GameLib;
import main.Timer;
import model.Estado;

public class ProjetilInimigo extends Projetil {
	private double raio;
	public ProjetilInimigo(double x, double y, double vX, double vY, double raio, int layer,
			Estado estado, Timer timer) {
		super(x, y, vX, vY, layer, estado, timer);
		this.raio = raio;
	}

	@Override
	public void draw() {
		GameLib.setColor(Color.RED);
		GameLib.drawCircle(x, y, raio);
	}


}
