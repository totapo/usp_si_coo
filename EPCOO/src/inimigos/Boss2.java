package inimigos;

import interfaces.Observer;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import armas.Arma;
import armas.ArmaInimigo;
import main.Timer;
import model.Estado;
import pacote.GameLib;
import projeteis.Projetil;

public class Boss2 extends Boss implements Observer {

	private List<Inimigo> inimigos;// guarda os inimigos dropados pelo boss
	private long lastDrop;
	private long dropCoolDown;
	private int maxInimigosDrop;// Quantidade máxima de inimigos que podem ser
								// dropados
	private int minInimigosDrop;// Quantidade mínima de inimigos que podem ser
								// dropados
	private long teleportCoolDown;
	private long lastTeleport;
	private List<Inimigo> removerI;

	public Boss2(double x, double y, int layer, Estado estado, Timer timer,
			double raio, int hpTotal, long flashTime, long flashCoolDown,
			double xLimite, double yLimite, long dropCoolDown,
			long teleportCoolDown, int maxInimigosDrop, int minInimigosDrop) {
		super(x, y, layer, estado, timer, raio, hpTotal, flashTime,
				flashCoolDown, xLimite, yLimite);
		inimigos = new LinkedList<Inimigo>();
		removerI = new LinkedList<Inimigo>();
		lastDrop = timer.getCurrentTime();
		lastTeleport = timer.getCurrentTime();
		this.dropCoolDown = dropCoolDown;
		this.teleportCoolDown = teleportCoolDown;
		this.maxInimigosDrop = maxInimigosDrop;
		this.minInimigosDrop = minInimigosDrop;
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
		List<Projetil> projeteisAux = getArma().disparar(this.x, this.y,
				(3 * Math.PI) / 2);
		if (projeteisAux != null) {
			projeteis.addAll(projeteisAux);
		}
		for (Inimigo inimigo : inimigos) {
			projeteisAux = inimigo.atirar();
			if (projeteisAux != null) {
				projeteis.addAll(projeteisAux);
			}
		}
		return projeteis;
	}

	@Override
	public void mover() {
		if (timer.getCurrentTime() - lastDrop > dropCoolDown) {
			dropInimigos();
			lastDrop = timer.getCurrentTime();
		}
		if (timer.getCurrentTime() - lastTeleport > teleportCoolDown) {
			teleport();
			lastTeleport = timer.getCurrentTime();
		}
		for (Inimigo inimigo : inimigos) {
			inimigo.mover();
		}
		limpar();
	}
	
	private void limpar(){
		inimigos.removeAll(removerI);
		removerI.clear();
	}

	private void teleport() {
		double x = Math.random() * (GameLib.WIDTH - 2 * xLimite + xLimite);
		double y = Math.random() * (GameLib.HEIGHT - 2 * yLimite + yLimite) / 2;// Boss
		// não
		// passa
		// do
		// metade
		// da
		// tela
		this.x = x;
		this.y = y;
	}

	private void dropInimigos() {
		Random rd = new Random();
		int qtdInimigos = rd.nextInt(maxInimigosDrop - minInimigosDrop) + minInimigosDrop;
		for (double i = -raio; i <= this.raio; i += 2 * raio / qtdInimigos) {

			Inimigo inimigo = new Inimigo1(this.x + i, this.y, this.layer, 9.0,
					Estado.ACTIVE, timer, 3 * Math.PI / 2,
					0.10 + Math.random() * 0.15, 0.0);

			Arma arma = new ArmaInimigo("def", 300, timer);
			inimigo.addArma(arma);
			inimigo.selecionaArma(arma);
			inimigo.addObserver(this);
			inimigos.add(inimigo);
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

			double alpha = (timer.getCurrentTime() - explosionStart)
					/ (explosionEnd - explosionStart);
			GameLib.drawExplosion(x, y, alpha);
		}

		if (estado == Estado.ACTIVE) {

			GameLib.setColor(Color.BLUE);
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
					GameLib.setColor(Color.BLUE);
				else
					GameLib.setColor(Color.WHITE);
				GameLib.drawCircle(x, y, raio);
				GameLib.drawDiamond(x, y, raio / 2.0);
			}
		}
		for (Inimigo inimigo : inimigos) {
			inimigo.draw();
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
	public void notify(Object s) {
		if (s instanceof Inimigo) {
			Inimigo i = (Inimigo) s;
			if (i.getEstado() == Estado.INACTIVE) {
				removerI.add(i);
			}
		}

	}

}
