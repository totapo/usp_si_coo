package elementos;

import java.awt.Color;

import main.Timer;
import model.ElementoMutavel;
import model.Estado;
import model.GameLib;

public class Estrela extends ElementoMutavel{
	private double size;
	private Color cor;
	private double vel;
	private Timer t;
	
	public Estrela(double vel, int layer, double size, Color cor, Timer t) {
		super(0, 0, layer,Estado.ACTIVE);
		x = Math.random() * GameLib.WIDTH;
		y = Math.random() * GameLib.HEIGHT;
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
		if(y > GameLib.HEIGHT) x = Math.random() * GameLib.WIDTH;
		y = y%GameLib.HEIGHT;
	}

}
