package armas;

import java.util.ArrayList;
import java.util.List;

import main.Timer;
import model.Estado;
import projeteis.NormalInimigo;
import projeteis.Projetil;

public class DefInimigo2 extends Arma{
	private Timer timer;
	
	public DefInimigo2(String nome, Timer timer) {
		super(nome);
		this.timer = timer;
	}

	@Override
	public List<Projetil> disparar(double x, double y,double angulo) {
		List<Projetil> resp = new ArrayList<Projetil>();
		Projetil aux;
		double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };

		for(double d : angles){
			double a = d + Math.random() * Math.PI/6 - Math.PI/12;
			double vx = Math.cos(a);
			double vy = Math.sin(a);
			aux = new NormalInimigo(x,y,vx*0.30,vy*0.30,2.0,1,Estado.ACTIVE,timer);
			resp.add(aux);
		}
			
		return resp;
	}
}
