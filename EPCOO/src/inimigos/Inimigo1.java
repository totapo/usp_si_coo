package inimigos;

import java.awt.Color;
import java.util.List;

import projeteis.Projetil;
import main.GameLib;
import model.Estado;
import model.Nave;

public class Inimigo1 extends Nave{
	//classe que representa um inimigo do tipo 1
	private double velocidadeRotacao, velocidade, angulo;
	
	public Inimigo1(double x, double y, double raio, Estado estado, double angulo,double velocidade, double velocidadeRotacao) {
		super(x, y, estado,raio);
		this.velocidadeRotacao = velocidadeRotacao;
		this.velocidade = velocidade;
		this.angulo = angulo;
	}

	@Override
	public List<Projetil> atirar() {
		List<Projetil> resp = null;
		Estado estado = this.getEstado();
		if(estado == Estado.ACTIVE){
			resp = this.getArma().disparar(x,y,angulo);
		}
		return resp;
	}

	@Override
	public void mover() {
		Estado estado = this.getEstado();
		if(estado == Estado.EXPLODING){
			
			if(timer.getCurrentTime() > explosionEnd){
				
				this.setEstado(Estado.INACTIVE);
			}
		}
		
		if(estado == Estado.ACTIVE){
			
			/* verificando se inimigo saiu da tela */
			if(y > GameLib.HEIGHT + 10) {
				this.setEstado(Estado.INACTIVE);
			}
			else {
				long delta = timer.getDelta();
				x += velocidade * Math.cos(angulo) * delta;
				y += velocidade * Math.sin(angulo) * delta * (-1.0);
				angulo += velocidadeRotacao * delta;
				
			}
		}
	}

	@Override
	public void explodir() {
		Estado estado = this.getEstado();
		if(estado == Estado.ACTIVE){
			this.setEstado(Estado.EXPLODING);
			explosionStart = timer.getCurrentTime();
			explosionEnd = explosionStart + 2000;
		}
	}

	@Override
	public void draw() {
		Estado estado = this.getEstado();
		if(estado == Estado.EXPLODING){
			
			double alpha = (timer.getCurrentTime() - explosionStart) / (explosionEnd - explosionStart);
			GameLib.drawExplosion(x, y, alpha);
		}
		
		if(estado == Estado.ACTIVE){
	
			GameLib.setColor(Color.CYAN);
			GameLib.drawCircle(x, y, raio);
		}
	}

	@Override
	public void hit() {
		if(this.getEstado() == Estado.ACTIVE)
			explodir();
	}
	
}
