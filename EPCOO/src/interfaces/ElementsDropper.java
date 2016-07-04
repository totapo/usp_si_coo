package interfaces;

import java.util.List;

import model.Elemento;

public abstract interface ElementsDropper {
	
	public abstract List<Elemento> drop(int n, double x, double y, int layer);
}
