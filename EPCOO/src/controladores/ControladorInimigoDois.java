package controladores;

import inimigos.Inimigo;
import inimigos.Inimigo2;
import pacote.GameLib;
import armas.Arma;
import armas.ArmaDefInimigo2;
import main.Timer;
import model.Estado;

public class ControladorInimigoDois extends ControladorNave{
	private double spawnX;
	private long nextEnemy;
	private int enemyCount, packSize;
	
	public ControladorInimigoDois(Timer timer, double spawnX, long firstSpawn, int packSize) {
		super(timer);
		this.spawnX = spawnX;
		this.nextEnemy = this.timer.getCurrentTime()+firstSpawn;
		this.packSize = packSize;
		enemyCount = 0;
	}
	
	public void setPackSize(int size){
		this.packSize = size;
	}

	@Override
	protected void spawnNave() {
		long currentTime = timer.getCurrentTime();
		if( currentTime > nextEnemy){
			Inimigo aux;
			aux = new Inimigo2(
					spawnX, //x
					-10.0, //y
					1, //layer
					12.0, //raio
					Estado.ACTIVE, //estado
					timer, //timer
					(3 * Math.PI) / 2, //angulo
					0.42, //velocidade
					0.0 //velocidadeRotacao
					);	
			Arma arma = new ArmaDefInimigo2("Def",timer);
			aux.addArma(arma);
			aux.selecionaArma(arma);
			aux.addObserver(this);
			this.getNaves().add(aux);	

			enemyCount++;
			
			if(enemyCount < packSize){
				nextEnemy = timer.getCurrentTime() + 120;
			} else {
				enemyCount = 0;
				spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
				nextEnemy = (long) (timer.getCurrentTime() + 3000 + Math.random() * 3000);
			}
			
		}
	}

}
