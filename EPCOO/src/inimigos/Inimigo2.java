package inimigos;

import java.awt.Color;
import java.util.List;

import projeteis.Projetil;
import main.GameLib;
import model.Estado;
import model.Nave;

public class Inimigo2 extends Nave{
	//classe que representa um inimigo do tipo 2
	double angulo, velocidade, velocidadeRotacao;
	boolean shootNow;
	
	public Inimigo2(double x, double y, double raio, Estado estado, double angulo,double velocidade, double velocidadeRotacao) {
		super(x, y, estado,raio);
		this.velocidadeRotacao = velocidadeRotacao;
		this.velocidade = velocidade;
		this.angulo = angulo;
		this.shootNow = false;
	}

	@Override
	public void hit() {
		if(this.getEstado() == Estado.ACTIVE)
			explodir();
	}

	@Override
	public List<Projetil> atirar() {
		List<Projetil> resp = null;
		if(shootNow){
			resp = this.getArma().disparar(x, y,angulo);
			shootNow = false;
		}
		return resp;
	}

	@Override
	public void mover() {
		Estado estado = this.getEstado();
		long currentTime = timer.getCurrentTime();
		long delta = timer.getDelta();
		if(estado == Estado.EXPLODING){
			
			if( currentTime > explosionEnd){
				
				this.setEstado(Estado.INACTIVE);
			}
		}
		
		if(estado == Estado.ACTIVE){
			
			/* verificando se inimigo saiu da tela */
			if(	x < -10 || x > GameLib.WIDTH + 10 ) {
				
				this.setEstado(Estado.INACTIVE);
			}
			else {
				
				shootNow = false;
				double previousY = y;
										
				x += velocidade * Math.cos(angulo) * delta;
				y+= velocidade * Math.sin(angulo) * delta * (-1.0);
				angulo += velocidadeRotacao * delta;
				
				double threshold = GameLib.HEIGHT * 0.30;
				
				if(previousY < threshold && y >= threshold) {
					
					if(x < GameLib.WIDTH / 2) velocidadeRotacao = 0.003;
					else velocidadeRotacao = -0.003;
				}
				
				if(velocidadeRotacao > 0 && Math.abs(angulo - 3 * Math.PI) < 0.05){
					
					velocidadeRotacao = 0.0;
					angulo = 3 * Math.PI;
					shootNow = true;
				}
				
				if(velocidadeRotacao < 0 && Math.abs(angulo) < 0.05){
					
					velocidadeRotacao = 0.0;
					angulo = 0.0;
					shootNow = true;
				}
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
	
			GameLib.setColor(Color.MAGENTA);
			GameLib.drawDiamond(x, y, raio);
		}
	}

}
