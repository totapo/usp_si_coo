package armas;

import java.util.LinkedList;
import java.util.List;

import main.Timer;
import model.Estado;
import projeteis.*;

public class ArmaInimigo extends Arma {
	//arma utilizada pelos inimigos do tipo 1 (bolinhas azuis), cujo disparo depende do cooldown de terminado
	protected double cooldown,lastShot;
	protected Timer timer;
	
	public ArmaInimigo(String nome, double cooldown, Timer timer) {
		super(nome);
		this.cooldown = cooldown;
		this.timer = timer;
		this.lastShot = timer.getCurrentTime();
	}

	@Override
	public List<Projetil> disparar(double x, double y, double angulo) {
		List<Projetil> resp = null;
		if(timer.getCurrentTime()-lastShot > cooldown){ //verifica se ja ultrapassou o cooldown
			resp = new LinkedList<Projetil>();
			//adiciona o projetil na lista
			resp.add(new ProjetilInimigo(
					x,y,
					Math.cos(angulo) * 0.45, //vx
					Math.sin(angulo) * 0.45 * (-1.0), //vy
					2.0, //raio
					Estado.ACTIVE,
					timer
					));
			lastShot = timer.getCurrentTime();
		}
			
		return resp;
	}

}
