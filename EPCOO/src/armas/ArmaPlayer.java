package armas;

import java.util.*;

import main.Timer;
import model.Estado;
import projeteis.ProjetilPlayer;
import projeteis.Projetil;

public class ArmaPlayer extends Arma {
	//representa a arma utilizada pelo player, que também depende de um cooldown
	private double cooldown,lastShot;
	private Timer timer;
	
	public ArmaPlayer(String nome,double cooldown){
		super(nome);
		this.cooldown = cooldown;
		this.timer = Timer.getInstance();
	}
	
	@Override
	public List<Projetil> disparar(double x, double y,double angulo) {
		List<Projetil> resp = null;
		if(timer.getCurrentTime()-lastShot > cooldown){
			resp = new LinkedList<Projetil>();
			resp.add(new ProjetilPlayer(
					x,y,
					0.0,-1.0,
					Estado.ACTIVE,
					timer
					));
			lastShot = timer.getCurrentTime();
		}
			
		return resp;
	}

}

