package model;


public abstract class Elemento {
	
	protected double x, y;
	
	public Elemento(double x, double y){
		this.x = x;
		this.y = y;
	}

	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public abstract void draw();
}
