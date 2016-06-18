package arquivo;

import java.util.PriorityQueue;

public class Fase {
	private PriorityQueue<TimerInimigo> timers;
	private int id;
	private int playerHp;
	
	public Fase(int id, int playerHp){
		timers = new PriorityQueue<TimerInimigo>(1000); //chutei um numero decente de inimigos por fase...
		this.id = id;
		this.playerHp = playerHp;
	}
	
	public void addEnemy(TimerInimigo t){
		this.timers.add(t);
	}
	
	public int getPlayerHp(){
		return playerHp;
	}
	
	public int getId(){
		return id;
	}
	
	public PriorityQueue<TimerInimigo> getEnemies(){
		return timers;
	}
}
