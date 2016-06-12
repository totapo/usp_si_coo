package model;

import java.awt.Color;
import java.util.List;

import main.Timer;
import pacote.GameLib;
import projeteis.Projetil;

public class Player extends Nave {

	double velocidadeX, velocidadeY;
	long explosionStart, explosionEnd;
	
	public Player(double x, double y, int layer, double raio, double vX, double vY, Estado estado, Timer timer){
		super(x,y,layer,raio,estado,timer);
		
		this.velocidadeX = vX;
		this.velocidadeY = vY;
	}
	
	@Override
	public void mover() {
		if(estado == Estado.ACTIVE){
			long delta = timer.getDelta();
			if(GameLib.iskeyPressed(GameLib.KEY_UP)) y -= delta * velocidadeY;
			if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) y += delta * velocidadeY;
			if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) x -= delta * velocidadeX;
			if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) x += delta * velocidadeX;
		} else if(estado == Estado.EXPLODING){
			if(timer.getCurrentTime() > explosionEnd){
				estado = Estado.ACTIVE;
			}
		}
	}

	@Override
	public void explodir() {
		if(estado == Estado.ACTIVE){
			estado = Estado.EXPLODING;
			explosionStart = timer.getCurrentTime();
			explosionEnd = explosionStart + 2000;
		}
	}

	@Override
	public List<Projetil> atirar() {
		List<Projetil> resp = null;
		if(estado == Estado.ACTIVE){
			if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
				resp = atual.disparar(x,y-2*raio);
			}
		}
		return resp;
	}

	@Override
	public void draw() {
		if(estado == Estado.EXPLODING){
			
			double alpha = (timer.getCurrentTime() - explosionStart) / (explosionEnd - explosionStart);
			GameLib.drawExplosion(x, y, alpha);
		}
		else{
			
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(x, y, raio);
		}
	}

}
