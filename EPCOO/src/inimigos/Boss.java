package inimigos;

import java.util.List;

import main.GameLib;
import model.Elemento;
import model.Estado;
import model.NaveComVida;

public abstract class Boss extends NaveComVida {

	protected int hpAtual;
	protected int hpTotal;
	protected double xLimite;// Inidica o quanto o boss pode se aproximar da
								// tela pelo eixo x
	protected double yLimite;// Inidica o quanto o boss pode se aproximar da
								// tela pelo eixo y
	protected EnemyDropper eDropper = null;


	public Boss(double x, double y, Estado estado,
			double raio, int hpTotal, long flashTime, long flashCoolDown,
			double xLimite, double yLimite) {
		super(x, y, estado, raio, flashTime, flashCoolDown);
		this.hpTotal = hpTotal;
		this.hpAtual = hpTotal;
		this.flashCoolDown = flashCoolDown;
		this.flashTime = flashTime;
		this.xLimite = xLimite;
		this.yLimite = yLimite;
	}

	public boolean verificaLimites() {// Verifica se o boss atingiu os limites
										// da tela
		return this.x - xLimite <= 0 || this.x + xLimite >= GameLib.WIDTH
				|| this.y - yLimite <= 0 || this.y + yLimite >= GameLib.HEIGHT;
	}

	@Override
	public int getTotalHp() {
		return hpTotal;
	}

	@Override
	public int getHpAtual() {
		return hpAtual;
	}

	@Override
	public void setHp(int hp) {
		this.hpAtual = hp;
	}
	
	public void setEnemyDropper(EnemyDropper ed){
		this.eDropper = ed;
	}
	
	public List<Elemento> dropEnemies(){
		if(eDropper != null) 
			return eDropper.drop(0, this.x, this.y);
		return null;
	}

}
