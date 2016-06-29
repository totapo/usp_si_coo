package armas;

import main.Timer;
import model.Estado;
import projeteis.Projetil;
import projeteis.ProjetilBoss1;

import java.util.*;

public class ArmaBoss1 extends Arma {

	private Timer timer;
	private long cooldown;
	private long lastShot;

	public ArmaBoss1(String nome, long cooldown, Timer timer) {
		super(nome);
		this.cooldown = cooldown;
		this.timer = timer;
	}

	@Override
	public List<Projetil> disparar(double x, double y, double angle) {
		List<Projetil> retorno = null;
		if (timer.getCurrentTime() - lastShot > cooldown) {
			retorno = new LinkedList<Projetil>();
			Projetil projetil = new ProjetilBoss1(x, y, 2.0, 1, Estado.ACTIVE, timer);
			retorno.add(projetil);
		}
		return retorno;
	}

}
