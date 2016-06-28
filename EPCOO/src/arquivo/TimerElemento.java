package arquivo;

public class TimerElemento implements Comparable<TimerElemento>{
	private int spawn;
	private boolean isEnemy;
	private boolean isBoss;
	private int tipo,hp;
	private double x,y;
	
	public TimerElemento(int spawn, boolean isEnemy, boolean isBoss, int tipo, double x, double y, int hp){
		this.spawn = spawn;
		this.isBoss = isBoss;
		this.isEnemy = isEnemy;
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
	public boolean isEnemy() {
		return isEnemy;
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
	public int compareTo(TimerElemento o) {
		return  (this.spawn - o.getSpawnTime());
	}
	
	
}
