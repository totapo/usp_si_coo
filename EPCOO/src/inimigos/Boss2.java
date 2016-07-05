package inimigos;


import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import main.Timer;
import model.Estado;
import model.GameLib;
import projeteis.Projetil;

public class Boss2 extends Boss{


	private long teleportCoolDown;
	private long lastTeleport;

	public Boss2(double x, double y, int layer, Estado estado, Timer timer,
			double raio, int hpTotal, long flashTime, long flashCoolDown,
			double xLimite, double yLimite, long teleportCoolDown,
			EnemyDropper eDropper) {
		super(x, y, layer, estado, timer, raio, hpTotal, flashTime,
				flashCoolDown, xLimite, yLimite);
		lastTeleport = timer.getCurrentTime();
		this.teleportCoolDown = teleportCoolDown;
		this.eDropper = eDropper;
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
		List<Projetil> projeteis = new LinkedList<Projetil>();
		if (this.getEstado() == Estado.ACTIVE) {
			List<Projetil> projeteisAux = getArma().disparar(this.x, this.y,
					(3 * Math.PI) / 2);
			if (projeteisAux != null) {
				projeteis.addAll(projeteisAux);
			}
		}
		return projeteis;
	}

	@Override
	public void mover() {
		if (this.getEstado() == Estado.ACTIVE
				|| this.getEstado() == Estado.FLASHING) {

			if (timer.getCurrentTime() - lastTeleport > teleportCoolDown) {
				teleport();
				lastTeleport = timer.getCurrentTime();
			}
		}else if(this.getEstado() == Estado.EXPLODING){
			if(timer.getCurrentTime() > explosionEnd){
				this.setEstado(Estado.INACTIVE);
			}
		}
	}

	private void teleport() {
		double x = Math.random() * (GameLib.WIDTH - 2 * xLimite + xLimite);
		double y = Math.random() * (GameLib.HEIGHT - 2 * yLimite + yLimite) / 2;// Boss

		this.x = x;
		this.y = y;
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

			double alpha = (timer.getCurrentTime() - explosionStart)
					/ (explosionEnd - explosionStart);
			GameLib.drawExplosion(x, y, alpha);
		}

		if (estado == Estado.ACTIVE) {

			GameLib.setColor(Color.YELLOW);
			GameLib.drawCircle(x, y, raio);
			GameLib.drawDiamond(x, y, raio / 2.0);
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
				GameLib.drawCircle(x, y, raio);
				GameLib.drawDiamond(x, y, raio / 2.0);
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
