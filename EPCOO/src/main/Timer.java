package main;

public class Timer {
	
	private long currentTime;
	
	public Timer(){
		this.currentTime = System.currentTimeMillis();
	}
	
	public long getCurrentTime(){
		return currentTime;
	}
	
	public long getDelta(){
		return System.currentTimeMillis() - currentTime;
	}
	
	protected void updateCurrentTime(){
		this.currentTime = System.currentTimeMillis();
	}
	
}
