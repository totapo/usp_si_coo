package inimigos;

import pacote.GameLib;
import interfaces.TemHp;
import main.Timer;
import model.Estado;
import model.NaveTheBest;

public abstract class Boss extends NaveTheBest implements TemHp {

	protected int hpAtual;
	protected int hpTotal;
	protected int xLimite;//Inidica o quanto o boss pode se aproximar da tela pelo eixo x
	protected int yLimite;//Inidica o quanto o boss pode se aproximar da tela pelo eixo y
	
	public Boss(double x, double y, int layer, Estado estado, Timer timer,
			double raio, int hpTotal, long flashTime, long flashCoolDown, int xLimite, int yLimite) {
		super(x, y, layer, estado, timer, raio, flashTime, flashCoolDown);
		this.hpTotal = hpTotal;
		this.hpAtual = hpTotal;
		this.flashCoolDown = flashCoolDown;
		this.flashTime = flashTime;
		this.xLimite = xLimite;
		this.yLimite = yLimite;
	}

	public boolean verificaLimites() {//Verifica se o boss atingiu os limites da tela
		return this.x - xLimite <= 0 || this.x + xLimite >= GameLib.WIDTH 
				|| this.y - yLimite <= 0|| this.y + yLimite >= GameLib.HEIGHT;
	}

}
