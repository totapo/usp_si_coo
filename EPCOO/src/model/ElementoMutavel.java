package model;

import interfaces.Observer;
import interfaces.Subject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ElementoMutavel extends Elemento implements Subject {
	List<Observer> observers;
	public ElementoMutavel(double x, double y, int layer, Estado estado) {
		super(x, y, layer);
		this.estado = estado;
		observers = new ArrayList<Observer>();
	}
	
	private Estado estado;
	
	public Estado getEstado(){
		return estado;
	}
	
	public void setEstado(Estado est){
		this.estado = est;
		notifyObservers();
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void notifyObservers() {
		Iterator<Observer> i = this.observers.iterator();
		while(i.hasNext()){
			i.next().notify(this);
		}
	}

}
