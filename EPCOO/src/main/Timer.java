package main;

public class Timer {
	//Classe que disponibiliza o delta e o currentTime para todas as outras
	//útil para desenhar os objetos na tela principalmente
	//Implementa o padrão SINGLETON, já que o timer deve ser único para todos os elementos do jogo.
	private static Timer instance;
	
	static {
		instance = new Timer();
	}
	
	private long currentTime;
	private long delta;
	
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
	
	//TODO
	//O update currentTime e o calcDelta são protegidos para que nem todas as classes que possuírem
	//referências a Timer possam recalcular esses valores, a única que pode fazer isso é a classe Main (por enquanto,
	//já que o Marcos vai fazer a classe Game).
	protected void updateCurrentTime(){
		this.currentTime = System.currentTimeMillis();
	}
	
	protected void calcDelta(){
		this.delta = System.currentTimeMillis() - currentTime;
	}
	
}
