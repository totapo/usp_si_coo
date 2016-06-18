package arquivo;

public class TimerInimigo implements Comparable<TimerInimigo>{
	private int spawn;
	private boolean isBoss;
	private int tipo,hp;
	private double x,y;
	
	public TimerInimigo(int spawn, boolean isBoss, int tipo, double x, double y, int hp){
		this.spawn = spawn;
		this.isBoss = isBoss;
		this.tipo = tipo;
		this.x = x;
		this.y = y;
		this.hp = hp;
	}
	
	public int getSpawnTime() {
		return spawn;
	}
	public boolean isBoss() {
		return isBoss;
	}
	public int getTipo() {
		return tipo;
	}
	public int getHp(){
		return hp;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	@Override
	public int compareTo(TimerInimigo o) {
		return  (this.spawn - o.getSpawnTime());
	}
	
	
}
