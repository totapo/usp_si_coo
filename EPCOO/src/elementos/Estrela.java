package elementos;

import pacote.GameLib;
import main.Drawer;
import model.Elemento;

public class Estrela extends Elemento{
	private static long contador, contador2;
	private double size;
	
	public static void count(double vel, double vel2, long delta){
		contador += vel * delta;
		contador2+=vel2*delta;
	}
	
	public Estrela(double x, double y, int layer, double size) {
		super(x, y, layer);
		this.size = size;
	}

	@Override
	public void draw() {
		GameLib.setColor(Drawer.getColor(layer));
		GameLib.fillRect(x, (Math.abs(y + ((layer==2)?contador:contador2))) % GameLib.HEIGHT, size, size);
	}

}
