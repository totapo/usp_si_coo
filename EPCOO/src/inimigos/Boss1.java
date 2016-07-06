package inimigos;

import interfaces.TemHp;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import armas.ArmaBoss1;
import main.GameLib;
import main.Timer;
import model.Estado;
import projeteis.Projetil;

public class Boss1 extends Boss implements TemHp {

	private int direcaoX;
	private int direcaoY;
	private double velocidade;
	private long movingStart;
	private long movingTime;
	

	public Boss1(double x, double y, Estado estado, Timer timer,
			double raio, int hp, long flashTime, long flashCoolDown,
			double velocidade, double xLimite, double yLimite) {
		super(x, y, estado, timer, raio, hp, flashTime, flashCoolDown, xLimite, yLimite);
		this.velocidade = velocidade;
		this.movingStart = timer.getCurrentTime();// Inicio do movimento atual do boss
		this.movingTime = 2000;// Tempo que o boss mantém o movimento na mesma direcao
		this.addArma(new ArmaBoss1("Arma Boss 1", 3000, timer));
		geraMovimento();
	}

	@Override
	public void hit() {
		if (this.getEstado() == Estado.ACTIVE
				|| this.getEstado() == Estado.FLASHING) {
			hpAtual--;
			flash();
			notifyObservers();

			if (hpAtual == 0)
				explodir();
		}
	}

	@Override
	public List<Projetil> atirar() {
		if (this.getEstado() == Estado.ACTIVE) {
			return this.getArma().disparar(this.x, this.y, 0);
		}
		return null;
	}

	@Override
	public void mover() {
		Estado estado = getEstado();
		if (estado == Estado.EXPLODING) {

			if (timer.getCurrentTime() > explosionEnd) {

				this.setEstado(Estado.INACTIVE);
			}
		}
		if (estado == Estado.ACTIVE || estado == Estado.FLASHING) {
			if (timer.getCurrentTime() - movingStart > movingTime 
					|| verificaLimites()) {
				movingStart = timer.getCurrentTime();
				geraMovimento();
			}
			this.x += direcaoX * velocidade * timer.getDelta();
			this.y += direcaoY * velocidade * timer.getDelta();
		}

	}

	private void geraMovimento() {
		Random rd = new Random();
		if (this.x - xLimite <= 0) {
			direcaoX = 1;
		} else if (this.x + xLimite >= GameLib.WIDTH) {
			direcaoX = -1;
		} else {
			direcaoX = rd.nextInt(3) - 1;
		}
		if (this.y - yLimite <= 0) {
			direcaoY = 1;
		} else if (this.y + yLimite >= GameLib.HEIGHT) {
			direcaoY = -1;
		} else {
			direcaoY = rd.nextInt(3) - 1;
		}
	}

	@Override
	public void explodir() {
		Estado estado = this.getEstado();
		if (estado == Estado.ACTIVE || estado == Estado.FLASHING) {
			this.setEstado(Estado.EXPLODING);
			explosionStart = timer.getCurrentTime();
			explosionEnd = explosionStart + 2000;
		}
	}

	@Override
	public void draw() {
		Estado estado = this.getEstado();
		if (estado == Estado.EXPLODING) {

			double alpha = (timer.getCurrentTime() - explosionStart) / (explosionEnd - explosionStart);
			GameLib.drawExplosion(x, y, alpha);
		}

		if (estado == Estado.ACTIVE) {

			GameLib.setColor(Color.GREEN);
			GameLib.drawDiamond(x, y, raio);
			GameLib.drawCircle(x, y, raio / 2.0);
		}
		if (estado == Estado.FLASHING) {
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
				GameLib.drawDiamond(x, y, raio);
				GameLib.drawCircle(x, y, raio / 2.0);
			}
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

}
