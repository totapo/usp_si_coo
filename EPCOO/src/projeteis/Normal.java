package projeteis;

import pacote.GameLib;
import main.Timer;
import model.Estado;

public abstract class Normal extends Projetil{
	private double velocidadeY, velocidadeX;
	
	public Normal(double x, double y, double vX, double vY, int layer, Estado estado, Timer timer) {
		super(x, y, layer, estado, timer);
		this.velocidadeX = vX;
		this.velocidadeY = vY;
	}

	@Override
	public void mover() {
		Estado estado = this.getEstado();
		if(estado == Estado.ACTIVE){
			
			/* verificando se projétil saiu da tela TODO
			 * nos outros tiros isso tem que ser diferente*/
			if(y < 0 || y > GameLib.HEIGHT) {
				
				this.setEstado(Estado.INACTIVE);
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
		this.setEstado(Estado.INACTIVE);
	}

}
