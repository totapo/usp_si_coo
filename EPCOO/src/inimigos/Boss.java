package inimigos;

import interfaces.TemHp;

import java.util.List;

import projeteis.Projetil;
import main.Timer;
import model.Estado;
import model.Nave;

public abstract class Boss extends Nave implements TemHp {

	public Boss(double x, double y, int layer, Estado estado, Timer timer,
			double raio) {
		super(x, y, layer, estado, timer, raio);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void hit() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Projetil> atirar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mover() {
		// TODO Auto-generated method stub

	}

	@Override
	public void explodir() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

}
