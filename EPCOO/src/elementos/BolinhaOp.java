package elementos;

import java.awt.Color;

import interfaces.Destrutivel;
import main.GameLib;
import main.Timer;
import model.ElementoMutavel;
import model.Estado;

public class BolinhaOp extends ElementoMutavel implements Destrutivel{
	//Classe que representa o powerup que eh a bolinha que orbita a nave do player
	private double angulo,raio,distancia;
	private Timer timer;
	private Player p;
	private int tipo;
	public BolinhaOp(Estado estado, Player p, int tipo) {
		super(4*p.getRaio(), p.getY(), estado);
		this.timer = Timer.getInstance();
		this.p = p;
		raio = p.getRaio();
		distancia = p.getRaio() * 4;
		angulo=0;
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tipo;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BolinhaOp other = (BolinhaOp) obj;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public double getRaioColisao() {
		return raio;
	}

	@Override
	public void hit() {
		//indestrutivel hahahaha
	}

	@Override
	public void mover() {
		double delta = timer.getDelta();
		angulo += delta / 360;
		x= p.getX() + distancia * Math.cos(angulo);
		y= p.getY() + distancia * Math.sin(angulo);
	}

	@Override
	public void draw() {
		GameLib.setColor(new Color(180, 230, 240));
		GameLib.drawCircle(x, y, raio);
		GameLib.drawCircle(x, y, raio-0.25);
		GameLib.drawCircle(x, y, raio-0.5);
		GameLib.drawCircle(x, y, raio-0.75);
		GameLib.drawCircle(x, y, raio-1);
	}
	
}
