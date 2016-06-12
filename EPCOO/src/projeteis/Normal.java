package projeteis;

import java.awt.Color;
import java.util.Collection;

import pacote.GameLib;
import main.Timer;
import model.Elemento;
import model.Estado;

public class Normal extends Projetil{
	private double velocidadeY, velocidadeX;
	
	public Normal(double x, double y, double vX, double vY, int layer, Estado estado, Timer timer) {
		super(x, y, 0, layer, estado, timer);
		this.velocidadeX = vX;
		this.velocidadeY = vY;
	}

	@Override
	public void mover() {
		if(estado == Estado.ACTIVE){
			
			/* verificando se projétil saiu da tela TODO
			 * nos outros tiros isso tem que ser diferente*/
			if(y < 0) {
				
				estado = Estado.INACTIVE;
			}
			else {
			
				x += velocidadeX * timer.getDelta();
				y += velocidadeY * timer.getDelta();
			}
		}
	}

	@Override
	public void explodir() {
		//neste caso só some
		estado = Estado.INACTIVE;
	}

	@Override
	public void checarColisoes(Collection<Elemento> alvos) {
		
	}

	@Override
	public void draw() {
		if(estado == Estado.ACTIVE){
			
			GameLib.setColor(Color.GREEN);
			GameLib.drawLine(x, y - 5, x, y + 5);
			GameLib.drawLine(x - 1, y - 3, x - 1, y + 3);
			GameLib.drawLine(x + 1, y - 3, x + 1, y + 3);
		}
	}

}
