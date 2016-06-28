package arquivo;

import java.util.PriorityQueue;

public class Fase {
	private PriorityQueue<TimerElemento> timers;
	private int id;
	private int playerHp;
	
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
