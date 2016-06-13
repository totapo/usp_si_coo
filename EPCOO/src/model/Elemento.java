package model;

import interfaces.Subject;

public abstract class Elemento implements Subject{
	protected double x, y;
	protected int layer;
	
	public Elemento(double x, double y, int layer){
		this.x = x;
		this.y = y;
		this.layer = layer;
	}

	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public int getLayer(){
		return layer;
	}
	
	public abstract void draw();
}
