package model;

public abstract class Elemento {
	protected double x, y,raio;
	protected int layer;
	protected Estado estado;
	
	public Elemento(double x, double y, double raio, int layer, Estado estado){
		this.x = x;
		this.y = y;
		this.layer = layer;
		this.raio = raio;
		this.estado = estado;
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
	
	public double getRaio(){
		return raio;
	}
	
	public Estado getEstado(){
		return estado;
	}
	
	public void setEstado(Estado est){
		this.estado = est;
	}
	
	public abstract void draw();
}
