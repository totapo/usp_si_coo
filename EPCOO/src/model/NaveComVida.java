package model;


import interfaces.TemHp;
import main.Timer;

public abstract class NaveComVida extends Nave implements TemHp{
	
	
	protected long flashStart;
	protected long flashEnd;
	protected long flashTime;
	protected long flashCoolDown;
	protected long flashLastChange;
	protected boolean flash;//determina se o objeto está branco (flash) ou com sua cor normal

	public NaveComVida(double x, double y, int layer, Estado estado,
			Timer timer, double raio, long flashTime, long flashCoolDown) {
		super(x, y, layer, estado, timer, raio);
		this.flashTime = flashTime;
		this.flashCoolDown = flashCoolDown;
	}

	public long getFlashStart() {
		return flashStart;
	}

	public long getFlashEnd() {
		return flashEnd;
	}

	public long getFlashTime() {
		return flashTime;
	}

	public long getflashCoolDown() {
		return flashCoolDown;
	}

	
}