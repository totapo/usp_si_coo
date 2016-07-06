package powerups;

import elementos.BolinhaOp;
import elementos.Player;
import elementos.Shield;
import interfaces.Destrutivel;
import arquivo.TimerElemento;
import model.Estado;

public class PowerUpFactory {
	public static Destrutivel ativarPoder(PowerUp t, Player p){
		Destrutivel n = null;
		if(t.getTipo()==1){ //instanciar bolinha que gira
			n = new BolinhaOp(Estado.ACTIVE,p,t.getTipo());
		} else if(t.getTipo()==2){ //instanciar Shiled
			n = new Shield(Estado.ACTIVE,p,t.getTipo());
		}
		return n;
	}
	
	public static PowerUp instanciarPowerUp(TimerElemento t){
		PowerUp n = new PowerUp(t.getX(), t.getY(),t.getTipo(),7.0,Estado.ACTIVE);
		return n;
	}
}
