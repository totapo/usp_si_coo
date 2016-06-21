package elementos;

import java.awt.Color;

import pacote.GameLib;
import model.Elemento;

public class Estrela extends Elemento{
	private static long contador, contador2;
	private double size;
	private Color cor;
	
	public static void count(double vel, double vel2, long delta){
		contador += vel * delta;
		contador2+=vel2*delta;
	}
	
	public Estrela(double x, double y, int layer, double size, Color cor) {
		super(x, y, layer);
		this.size = size;
		this.cor = cor;
	}

	@Override
	public void draw() {
		GameLib.setColor(cor);
		GameLib.fillRect(x, (Math.abs(y + ((layer==2)?contador:contador2))) % GameLib.HEIGHT, size, size);
	}

}
