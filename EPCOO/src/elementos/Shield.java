package elementos;

import java.awt.Color;

import interfaces.Destrutivel;
import interfaces.TemHp;
import main.GameLib;
import model.ElementoMutavel;
import model.Estado;

public class Shield extends ElementoMutavel implements TemHp, Destrutivel{
	//Classe que representa um escudo centralizado no player
	private Player p;
	private int maxHp,hpAtual,tipo;
	private double raio;
	
	public Shield(Estado estado, Player p, int tipo) {
		super(p.getX(), p.getY(), estado);
		this.p = p;
		hpAtual = maxHp = 10;
		raio = 2*p.getRaio();
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
		Shield other = (Shield) obj;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public void mover() {
		x = p.getX();
		y = p.getY();
	}

	@Override
	public void draw() {
		if(this.getEstado()==Estado.ACTIVE){
			GameLib.setColor(new Color(70,205,255));
			GameLib.drawCircle(x, y, raio);

			GameLib.drawCircle(x, y, raio-0.25);
			GameLib.drawCircle(x, y, raio-0.5);
			GameLib.drawCircle(x, y, raio-0.75);
			GameLib.drawCircle(x, y, raio-1);
		}
	}

	@Override
	public double getRaioColisao() {
		return raio;
	}

	@Override
	public void hit() {
		if(this.getEstado() == Estado.ACTIVE){ 
			hpAtual-=1;
			notifyObservers(); //para avisar a barra de vida que o hp mudou
			
			if(hpAtual==0)
				this.setEstado(Estado.INACTIVE);;
			
		}
	}

	@Override
	public int getTotalHp() {
		return maxHp;
	}

	@Override
	public int getHpAtual() {
		return hpAtual;
	}

	@Override
	public void setHp(int hp) {
		hpAtual = hp;
		notifyObservers(); 
	}

}
