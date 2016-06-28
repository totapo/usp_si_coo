package powerups;

import java.awt.Color;

import pacote.GameLib;
import model.ElementoMutavel;
import model.Estado;
import interfaces.Destrutivel;
import controladores.ControladorPlayer;

public class PowerUp extends ElementoMutavel implements Destrutivel{
	//classe que representa um powerup na tela. Não o power up em si, a bolinha que, quando tocada pelo player
	//ativa um powerup
	private double raio;
	private int tipo;

	public PowerUp(double x, double y, int layer, int tipo, double raio, Estado estado) {
		super(x, y, layer, estado);
		this.raio = raio;
		this.tipo = tipo;
	}

	public void ativar(ControladorPlayer ctrl){
		
	};
	
	public int getTipo(){
		return tipo;
	}
	
	@Override
	public double getRaioColisao() {
		return raio;
	}

	@Override
	public void hit() {
		this.setEstado(Estado.INACTIVE);
	}

	@Override
	public void draw() {//TODO pensar num desenho pros powerups
		GameLib.setColor((tipo==1)?Color.ORANGE:Color.WHITE);
		GameLib.drawCircle(x, y, raio);
		//GameLib.drawCircle(x, y, raio+1);
		//GameLib.drawCircle(x, y, raio+2);
	}
	
	
	
}
