package armas;

import java.util.LinkedList;
import java.util.List;

import main.Timer;
import model.Estado;
import projeteis.*;

public class DefInimigo extends Arma {
	private double cooldown,lastShot;
	private Timer timer;
	
	public DefInimigo(String nome, double cooldown, Timer timer) {
		super(nome);
		this.cooldown = cooldown;
		this.timer = timer;
	}

	@Override
	public List<Projetil> disparar(double x, double y) {
		List<Projetil> resp = null;
		if(timer.getCurrentTime()-lastShot > cooldown){
			resp = new LinkedList<Projetil>();
			resp.add(new NormalInimigo(
					x,y,
					0.0, //vx
					-1.0, //vy
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
