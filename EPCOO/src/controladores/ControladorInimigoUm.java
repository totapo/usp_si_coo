package controladores;

import armas.Arma;
import armas.DefInimigo;
import inimigos.Inimigo;
import inimigos.Inimigo1;
import main.Timer;
import model.Estado;
import pacote.GameLib;

public class ControladorInimigoUm extends Controlador {
	private long nextEnemy,cooldown;
	public ControladorInimigoUm(Timer timer, long spawnCooldown) {
		super(timer);
		this.cooldown = spawnCooldown;
		this.nextEnemy = timer.getCurrentTime()+spawnCooldown;
	}
	
	public void setCooldown(long cooldown){
		this.cooldown = cooldown;
	}

	@Override
	protected void spawnNave() {
		if(timer.getCurrentTime() > nextEnemy){
			Inimigo aux;
			aux = new Inimigo1(
					Math.random() * (GameLib.WIDTH - 20.0) + 10.0, //x
					-10.0, //y
					1, //layer
					9.0, //raio
					Estado.ACTIVE, //estado
					timer, //timer
					3 * Math.PI / 2, //angulo
					0.20 + Math.random() * 0.15, //velocidade
					0.0 //velocidadeRotacao
					);	
			Arma arma = new DefInimigo("Def",500,timer);
			aux.addArma(arma);
			aux.selecionaArma(arma);
			aux.addObserver(this);
			this.getNaves().add(aux);
			nextEnemy = timer.getCurrentTime()+cooldown;
		}
	}

}
