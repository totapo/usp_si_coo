package inimigos;

import pacote.GameLib;
import interfaces.TemHp;
import main.Timer;
import model.Estado;
import model.NaveTheBest;

public abstract class Boss extends NaveTheBest implements TemHp {

	protected int hpAtual;
	protected int hpTotal;

	public Boss(double x, double y, int layer, Estado estado, Timer timer,
			double raio, int hpTotal, long flashTime, long flashCoolDown) {
		super(x, y, layer, estado, timer, raio, flashTime, flashCoolDown);
		this.hpTotal = hpTotal;
		this.hpAtual = hpTotal;
		this.flashCoolDown = flashCoolDown;
		this.flashTime = flashTime;
	}

	public boolean verificaLimites() {//Verifica se o boss atingiu os limites da tela
		return this.x <= 0 || this.x >= GameLib.WIDTH 
				|| this.y <= 0|| this.y >= GameLib.HEIGHT;
	}

}
