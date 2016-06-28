package inimigos;

import interfaces.TemHp;

import main.Timer;
import model.Estado;
import model.Nave;

public abstract class Boss extends Nave implements TemHp {

	public Boss(double x, double y, int layer, Estado estado, Timer timer,
			double raio) {
		super(x, y, layer, estado, timer, raio);
	}

}
