package armas;

import java.util.LinkedList;
import java.util.List;

import main.Timer;
import model.Estado;
import projeteis.*;

public class ArmaDefInimigo extends Arma {
	private double cooldown,lastShot;
	private Timer timer;
	
	public ArmaDefInimigo(String nome, double cooldown, Timer timer) {
		super(nome);
		this.cooldown = cooldown;
		this.timer = timer;
		this.lastShot = timer.getCurrentTime();
	}

	@Override
	public List<Projetil> disparar(double x, double y, double angulo) {
		List<Projetil> resp = null;
		if(timer.getCurrentTime()-lastShot > cooldown){
			resp = new LinkedList<Projetil>();
			resp.add(new ProjetilInimigo(
					x,y,
					Math.cos(angulo) * 0.45, //vx
					Math.sin(angulo) * 0.45 * (-1.0), //vy
					2.0, //raio
					1,
					Estado.ACTIVE,
					timer
					));
			lastShot = timer.getCurrentTime();
		}
			
		return resp;
	}

}
