package interfaces;

import model.Estado;

public interface Destrutivel {
	//interface implementada por elementos destrutiveis
	public double getRaioColisao();
	public void hit();
	public double getX();
	public double getY();
	public Estado getEstado();
}
