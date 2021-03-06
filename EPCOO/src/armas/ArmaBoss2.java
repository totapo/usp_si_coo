package armas;

import java.util.LinkedList;
import java.util.List;

import model.Estado;
import projeteis.Projetil;
import projeteis.ProjetilInimigo;

public class ArmaBoss2 extends ArmaInimigo{

	public ArmaBoss2(String nome, double cooldown) {
		super(nome, cooldown);
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
					60.0, //raio
					Estado.ACTIVE
					));
			lastShot = timer.getCurrentTime();
		}
			
		return resp;
	}

}
