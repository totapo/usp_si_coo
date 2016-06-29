package projeteis;

import interfaces.Destrutivel;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pacote.GameLib;
import main.Timer;
import model.Estado;

public class ProjetilBoss1 extends Projetil {

	private long dropTime;
	private long coolDownTime;
	private boolean explodiu;
	private List<ProjetilInimigo> projeteis;
	private List<ProjetilInimigo> removerP;
	private double raio;

	public ProjetilBoss1(double x, double y, double raio, int layer,
			Estado estado, Timer timer) {
		super(x, y, 0, 0, layer, estado, timer);
		this.dropTime = timer.getCurrentTime();
		this.raio = raio;
		explodiu = false;
	}

	@Override
	public void mover() {
		if (dropTime + coolDownTime > timer.getCurrentTime() && !explodiu) {
			explodiu = true;
			projeteis = espalharProjeteis();
		} else if (explodiu) {
			for (Projetil projetil : projeteis) {
				projetil.mover();
			}
		}
	}

	private List<ProjetilInimigo> espalharProjeteis() {
		List<ProjetilInimigo> projeteis = new LinkedList<ProjetilInimigo>();
		double vx, vy;
		ProjetilInimigo projetil;
		for (vx = -1; vx < 1; vx += 1.0 / 10.0) {
			vy = 1;
			projetil = new ProjetilInimigo(this.x, this.y, vx, vy, 2.0, 1,
					Estado.ACTIVE, timer);
			projeteis.add(projetil);
		}
		for (vx = -1; vx < 1; vx += 1.0 / 10.0) {
			vy = -1;
			projetil = new ProjetilInimigo(this.x, this.y, vx, vy, 2.0, 1,
					Estado.ACTIVE, timer);
			projeteis.add(projetil);
		}
		for (vy = -1; vy < 1; vy += 1.0 / 10.0) {
			vx = 1;
			projetil = new ProjetilInimigo(this.x, this.y, vx, vy, 2.0, 1,
					Estado.ACTIVE, timer);
			projeteis.add(projetil);
		}
		for (vy = -1; vy < 1; vy += 1.0 / 10.0) {
			vx = -1;
			projetil = new ProjetilInimigo(this.x, this.y, vx, vy, 2.0, 1,
					Estado.ACTIVE, timer);
			projeteis.add(projetil);
		}
		return projeteis;
	}

	@Override
	public void checarColisoes(Collection<? extends Destrutivel> alvos) {
		if (this.getEstado() == Estado.ACTIVE && !explodiu) {
			Iterator<? extends Destrutivel> it = alvos.iterator();
			double dx, dy, dist;
			Object obj;
			Destrutivel aux;
			while (it.hasNext()) {
				obj = it.next();
				aux = (Destrutivel) obj;
				dx = x - aux.getX();
				dy = y - aux.getY();
				dist = Math.sqrt(dx * dx + dy * dy);

				if (dist < (aux.getRaioColisao() + raio) * 0.8) {
					aux.hit();
					this.setEstado(Estado.INACTIVE);
					break;
				}
			}
		}
		if (explodiu && this.getEstado() == Estado.ACTIVE) {
			if (projeteis.isEmpty()) {
				setEstado(Estado.INACTIVE);
			} else {
				for (ProjetilInimigo projetil : projeteis) {
					projetil.checarColisoes(alvos);
					if (projetil.getEstado() == Estado.INACTIVE)
						removerP.add(projetil);
				}
				limpar();
			}

		}
	}

	private void limpar() {
		projeteis.removeAll(removerP);
		removerP.clear();
	}

	@Override
	public void draw() {
		if (!explodiu) {
			GameLib.setColor(Color.PINK);
			GameLib.drawCircle(x, y, raio);
		} else {
			for (Projetil projetil : projeteis) {
				projetil.draw();
			}
		}
	}

}
