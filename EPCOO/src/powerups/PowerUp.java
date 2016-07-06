package powerups;

import java.awt.Color;

import main.Timer;
import model.ElementoMutavel;
import model.Estado;
import model.GameLib;
import interfaces.Destrutivel;

public class PowerUp extends ElementoMutavel implements Destrutivel{
	//classe que representa um powerup na tela. Não o power up em si, a bolinha que, quando tocada pelo player
	//ativa um powerup
	private double raio;
	private int tipo;
	double velocidadeX,velocidadeY;
	private Timer timer;
	private boolean hit;

	public PowerUp(Timer timer,double x, double y, int tipo, double raio, Estado estado) {
		super(x, y, estado);
		this.raio = raio;
		this.tipo = tipo;
		velocidadeX = 0;
		velocidadeY = 0.40;
		this.timer = timer;
	}
	
	public int getTipo(){
		return tipo;
	}
	
	@Override
	public double getRaioColisao() {
		return raio;
	}

	public boolean wasHit(){
		return hit;
	}
	
	@Override
	public void hit() {
		hit = true;
		this.setEstado(Estado.INACTIVE);
	}

	@Override
	public void draw() {
		GameLib.setColor((tipo==1)?new Color(180, 230, 240):new Color(70,205,255));
		
		GameLib.drawCircle(x, y, raio);
		GameLib.drawCircle(x, y, raio-0.5);
		GameLib.drawCircle(x, y, raio-0.75);
	}

	@Override
	public void mover() {
		Estado estado = this.getEstado();
		if(estado == Estado.ACTIVE){
			
			if(y < 0 || y > GameLib.HEIGHT) {
				
				this.setEstado(Estado.INACTIVE);
			}
			else {
			
				x += velocidadeX * timer.getDelta();
				y += velocidadeY * timer.getDelta();
			}
		}
	}
	
	
	
}
