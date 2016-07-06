package arquivo;

import java.util.PriorityQueue;

public class Fase {
	//classe que representa uma fase no jogo
	
	private PriorityQueue<TimerElemento> timers; //heap cujo topo possui o primeiro inimigo/powerup a ser criado no jogo
	private int id; //id da fase
	private int playerHp; //hp do player na fase
	
	public Fase(int id, int playerHp){
		timers = new PriorityQueue<TimerElemento>(1000); //chutei um numero decente de inimigos por fase...
		this.id = id;
		this.playerHp = playerHp;
	}
	
	public void addElemento(TimerElemento t){
		this.timers.add(t);
	}
	
	public int getPlayerHp(){
		return playerHp;
	}
	
	public int getId(){
		return id;
	}
	
	public PriorityQueue<TimerElemento> getEnemies(){
		return timers;
	}
}
