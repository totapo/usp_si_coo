package elementos;

import java.awt.Color;

import main.Timer;
import pacote.GameLib;
import model.ElementoMutavel;
import model.Estado;

public class Estrela extends ElementoMutavel{
	private double size;
	private Color cor;
	private double vel;
	private Timer t;
	
	public Estrela(double x, double y, double vel, int layer, double size, Color cor, Timer t) {
		super(x, y, layer,Estado.ACTIVE);
		this.size = size;
		this.cor = cor;
		this.vel = vel;
		this.t = t;
	}

	@Override
	public void draw() {
		GameLib.setColor(cor);
		GameLib.fillRect(x, y, size, size);
	}

	@Override
	public void mover() {
		y += vel*t.getDelta(); 
		y = y%GameLib.HEIGHT;
	}

}
