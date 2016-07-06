package armas;

import java.util.ArrayList;
import java.util.List;

import main.Timer;
import model.Estado;
import projeteis.ProjetilInimigo;
import projeteis.Projetil;

public class ArmaInimigo2 extends Arma{
	//arma utilizada pelos inimigos tipo 2 (losangos) Tiro spread
	private Timer timer;
	
	public ArmaInimigo2(String nome, Timer timer) {
		super(nome);
		this.timer = timer;
	}
	@Override
	public List<Projetil> disparar(double x, double y,double angulo) {
		List<Projetil> resp = new ArrayList<Projetil>();
		ProjetilInimigo aux;
		double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };

		for(double d : angles){
			double a = d + Math.random() * Math.PI/6 - Math.PI/12;
			double vx = Math.cos(a);
			double vy = Math.sin(a);
			aux = new ProjetilInimigo(x,y,vx*0.30,vy*0.30,2.0,Estado.ACTIVE,timer);
			resp.add(aux);
		}
			
		return resp;
	}
}
