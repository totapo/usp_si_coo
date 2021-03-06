package controladores;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import arquivo.TimerElemento;
import main.Timer;
import interfaces.Controlador;
import interfaces.Observer;
import interfaces.Subject;

public class ControladorSpawnElementos implements Subject,Controlador {
	//controla a PriorityQueue de TimerElemento, que indica quando e onde as coisas (inimigos/powerups) devem aparecer
	private Timer timer;
	private PriorityQueue<TimerElemento> elems;
	private List<Observer> observers;
	
	public ControladorSpawnElementos(Timer timer, PriorityQueue<TimerElemento> enemies) {
		this.timer = timer;
		this.elems = enemies;
		this.observers = new LinkedList<Observer>();
	}
	
	//muda a PriorityQueue, usado nas trocas de fase
	public void setEnemies(PriorityQueue<TimerElemento> enemies){
		this.elems = enemies;
	}
	
	@Override
	public void execute() {
		long init = timer.getIniFase();
		long current = timer.getCurrentTime();
		if(elems!=null){
			while(elems.peek()!=null && current > init+elems.peek().getSpawnTime()){ 
				//enquanto o spawntime do elemento do topo for menor que o tempo atual
				notifyObservers(); //notifica os observadores que um elemento deve ser criado
			}
		}
	}

	@Override
	public void desenharObjetos() {
		//DO NOTHING
	}

	@Override
	public void limparMemoria() {
		this.elems.clear();
	}

	@Override
	public void addObserver(Observer o) {
		this.observers.add(o);
	}

	@Override
	public void notifyObservers() {
		TimerElemento e = elems.poll();
		for(Observer o : observers){
			o.notify(e);
		}
	}

}
