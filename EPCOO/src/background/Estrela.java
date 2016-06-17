package background;

import pacote.GameLib;
import main.Drawer;
import main.Timer;
import model.Elemento;

public class Estrela extends Elemento{
	private double velocidade;
	private Timer timer;
	private long contador;
	private double size;
	
	public Estrela(double x, double y, int layer, double velocidade, Timer timer, double size) {
		super(x, y, layer);
		this.velocidade = velocidade;
		this.timer = timer;
		this.size = size;
	}

	@Override
	public void draw() {
		GameLib.setColor(Drawer.getColor(layer));
		contador += velocidade * timer.getDelta();
		GameLib.fillRect(x, (Math.abs(y + contador)) % GameLib.HEIGHT, size, size);
	}

}
