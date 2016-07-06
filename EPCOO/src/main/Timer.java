package main;

public class Timer {
	//Classe que disponibiliza o delta e o currentTime para todas as outras
	//util para desenhar os objetos na tela principalmente
	//Implementa o padrão SINGLETON, ja que o timer deve ser unico para todos os elementos do jogo.
	private static Timer instance;
	
	static {
		instance = new Timer();
	}
	
	private long currentTime;
	private long delta;
	private long initFase;
	
	private Timer(){
		this.currentTime = System.currentTimeMillis();
	}
	
	public static Timer getInstance(){
		return instance;
	}
	
	public long getCurrentTime(){
		return currentTime;
	}
	
	public long getDelta(){
		return delta;
	}
	
	public long getIniFase(){
		return initFase;
	}
	
	//O update currentTime e o calcDelta são protegidos para que nem todas as classes que possuírem
	//referências a Timer possam recalcular esses valores, a única que pode fazer isso é a classe Main.
	protected void updateCurrentTime(){
		this.currentTime = System.currentTimeMillis();
	}
	
	protected void calcDelta(){
		this.delta = System.currentTimeMillis() - currentTime;
	}
	
	protected void marcarIniFase(){
		this.initFase = System.currentTimeMillis();
	}
	
}
