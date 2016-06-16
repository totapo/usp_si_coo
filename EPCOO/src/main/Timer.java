package main;

public class Timer {
	
	private long currentTime;
	private long delta;
	
	public Timer(){
		this.currentTime = System.currentTimeMillis();
	}
	
	public long getCurrentTime(){
		return currentTime;
	}
	
	public long getDelta(){
		return delta;
	}
	
	protected void updateCurrentTime(){
		this.currentTime = System.currentTimeMillis();
	}
	
	protected void calcDelta(){
		this.delta = System.currentTimeMillis() - currentTime;
	}
	
}
