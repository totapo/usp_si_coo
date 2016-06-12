package armas;

import java.util.*;

import main.Timer;
import model.Estado;
import projeteis.Normal;
import projeteis.Projetil;

public class Default extends Arma {
	private long cooldown,lastShot;
	private Timer timer;
	
	public Default(String nome, long cooldown, Timer timer){
		super(nome);
		this.cooldown = cooldown;
		this.timer = timer;
	}
	
	@Override
	public List<Projetil> disparar(double x, double y) {
		List<Projetil> resp = null;
		if(timer.getCurrentTime()-lastShot > cooldown){
			resp = new LinkedList<Projetil>();
			resp.add(new Normal(
					x,y,
					0.0,-1.0,1,
					Estado.ACTIVE,
					timer
					));
			lastShot = timer.getCurrentTime();
		}
			
		return resp;
	}

}

