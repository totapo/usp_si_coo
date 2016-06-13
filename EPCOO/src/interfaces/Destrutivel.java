package interfaces;

import model.Estado;

public interface Destrutivel {
	public double getRaioColisao();
	public void hit();
	public double getX();
	public double getY();
	public Estado getEstado();
}
