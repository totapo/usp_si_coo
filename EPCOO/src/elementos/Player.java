package elementos;


import java.awt.Color;
import java.util.*;

import main.GameLib;
import main.Timer;
import model.Estado;
import model.NaveComVida;
import projeteis.Projetil;

public class Player extends NaveComVida {
	//classe que representa a nave do jogador
	
	double velocidadeX, velocidadeY;
	private int hp,hpMax;
	private long flashStart; //hora de inicio do flash
	private long flashEnd; //hora de termino do flash
	private long flashTime; //periodo de duracao do flash
	private long flashLastChange; //tempo da ultima mudanca de cor
	private boolean flash; //indica em qual cor o flash esta
	private long flashCoolDown; //representa o cooldown do flash
	
	public Player(double x, double y, double vX, double vY, Estado estado, Timer timer, double raio, int hp){
		super(x,y,estado,timer,raio,500,50);
		
		this.velocidadeX = vX;
		this.velocidadeY = vY;
		this.hp = this.hpMax = hp;
		flashCoolDown = 50;
		flashTime = 500;
	}
	
	@Override
	public void mover() {
		Estado estado = this.getEstado();
		if(estado == Estado.ACTIVE || estado == Estado.FLASHING){
			long delta = timer.getDelta();
			if(GameLib.iskeyPressed(GameLib.KEY_UP)) y -= delta * velocidadeY;
			if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) y += delta * velocidadeY;
			if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) x -= delta * velocidadeX;
			if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) x += delta * velocidadeX;
			

			if(x < 0.0) x = 0.0;
			if(x >= GameLib.WIDTH) x = GameLib.WIDTH - 1;
			if(y < 25.0) y = 25.0;
			if(y >= GameLib.HEIGHT) y = GameLib.HEIGHT - 1;
		} else if(estado == Estado.EXPLODING){
			if(timer.getCurrentTime() > explosionEnd){
				this.setEstado(Estado.INACTIVE);
			}
		}
	}

	@Override
	public void explodir() {
		Estado estado = this.getEstado();
		if(estado == Estado.ACTIVE || estado == Estado.FLASHING){
			this.setEstado(Estado.EXPLODING);
			explosionStart = timer.getCurrentTime();
			explosionEnd = explosionStart + 2000;
		}
	}

	@Override
	public List<Projetil> atirar() {
		List<Projetil> resp = null;
		Estado estado = this.getEstado();
		if(estado == Estado.ACTIVE || estado == Estado.FLASHING){
			if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
				resp = this.getArma().disparar(x,y-1*raio,0);
			}
		}
		return resp;
	}

	@Override
	public void draw() {
		Estado estado = this.getEstado();
		if(estado == Estado.EXPLODING){
			
			double alpha = (timer.getCurrentTime() - explosionStart) / (explosionEnd - explosionStart);
			GameLib.drawExplosion(x, y, alpha);
		} else if(estado == Estado.ACTIVE){
			
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(x, y, raio);
		} else {
			if (timer.getCurrentTime() > flashEnd)
				this.setEstado(Estado.ACTIVE);
			else {
				if (timer.getCurrentTime() - flashLastChange > this.flashCoolDown) {
					flash = !flash;
					flashLastChange = timer.getCurrentTime();
				}
				if (!flash)
					GameLib.setColor(Color.GRAY);
				else
					GameLib.setColor(Color.WHITE);

				GameLib.drawPlayer(x, y, raio);
			}
		}
	}

	@Override
	public void hit() {
		if(this.getEstado() == Estado.ACTIVE || this.getEstado() == Estado.FLASHING){
			hp-=1;
			notifyObservers(); 
			
			flash();
			
			if(hp==0)
				explodir();
			
		}
			
	}

	private void flash() {
		if (super.getEstado() == Estado.ACTIVE) {
			super.setEstado(Estado.FLASHING);
			this.flashStart = timer.getCurrentTime();
			this.flashEnd = flashStart + flashTime;
			this.flashLastChange = flashStart;
			this.flash = true;
		}
	}

	@Override
	public int getTotalHp() {
		return hpMax;
	}

	@Override
	public int getHpAtual() {
		return hp;
	}

	@Override
	public void setHp(int hp) {
		this.hp = hp;
		notifyObservers();
	}

}
