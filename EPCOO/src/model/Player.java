package model;

import java.awt.Color;
import java.util.*;

import main.Timer;
import pacote.GameLib;
import projeteis.Projetil;

public class Player extends Nave {

	double velocidadeX, velocidadeY;
	double explosionStart, explosionEnd;
	
	public Player(double x, double y, int layer, double vX, double vY, Estado estado, Timer timer, double raio){
		super(x,y,layer,estado,timer,raio);
		
		this.velocidadeX = vX;
		this.velocidadeY = vY;
	}
	
	@Override
	public void mover() {
		Estado estado = this.getEstado();
		if(estado == Estado.ACTIVE){
			long delta = timer.getDelta();
			if(GameLib.iskeyPressed(GameLib.KEY_UP)) y -= delta * velocidadeY;
			if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) y += delta * velocidadeY;
			if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) x -= delta * velocidadeX;
			if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) x += delta * velocidadeX;
			

			if(x < 0.0) y = 0.0;
			if(x >= GameLib.WIDTH) x = GameLib.WIDTH - 1;
			if(y < 25.0) y = 25.0;
			if(y >= GameLib.HEIGHT) y = GameLib.HEIGHT - 1;
		} else if(estado == Estado.EXPLODING){
			if(timer.getCurrentTime() > explosionEnd){
				this.setEstado(Estado.ACTIVE);
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
	public List<Projetil> atirar() {
		List<Projetil> resp = null;
		Estado estado = this.getEstado();
		if(estado == Estado.ACTIVE){
			if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
				resp = this.getArma().disparar(x,y-2*raio,0);
			}
		}
		return resp;
	}

	@Override
	public void draw() {
		Estado estado = this.getEstado();
		if(estado == Estado.EXPLODING){
			
			double alpha = (timer.getCurrentTime() - explosionStart) / (explosionEnd - explosionStart);
			GameLib.drawExplosion(x, y, alpha);
		}
		else{
			
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(x, y, raio);
		}
	}

	@Override
	public void hit() {//TODO dá pra implementar vida com isso
		if(this.getEstado() == Estado.ACTIVE)
			explodir();
	}

}
