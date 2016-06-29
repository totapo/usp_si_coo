package powerups;

import elementos.BolinhaOp;
import elementos.Shield;
import interfaces.Destrutivel;
import arquivo.TimerElemento;
import main.Timer;
import model.Estado;
import model.Player;

public class PowerUpFactory {
	public static Destrutivel ativarPoder(PowerUp t, Timer timer, Player p){
		Destrutivel n = null;
		if(t.getTipo()==1){ //instanciar bolinha que gira
			n = new BolinhaOp(1,Estado.ACTIVE,p,timer,t.getTipo());
		} else if(t.getTipo()==2){
			n = new Shield(1,Estado.ACTIVE,p,t.getTipo());
		}
		return n;
	}
	
	public static PowerUp instanciarPowerUp(TimerElemento t, Timer timer){
		PowerUp n = new PowerUp(timer,t.getX(), t.getY(),1,t.getTipo(),7.0,Estado.ACTIVE);
		return n;
	}
}
