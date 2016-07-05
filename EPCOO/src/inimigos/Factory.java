package inimigos;

import armas.Arma;
import armas.ArmaBoss1;
import armas.ArmaBoss2;
import armas.ArmaInimigo;
import armas.ArmaInimigo2;
import arquivo.TimerElemento;
import main.Timer;
import model.Estado;
import model.Nave;

public class Factory {

	public static Nave instanciarInimigo(TimerElemento t, Timer timer){
		Nave n = null;
		if(t.isBoss()){
			if(t.getTipo()==1){
				n = new Boss1(t.getX(),	//x
						t.getY(),		//y
						1,				//layer
						Estado.ACTIVE,	//Estado
						timer,			//timer
						12.0,			//Raio
						t.getHp(),		//HP
						500,			//FlashTime
						50,				//FlashCoolDownTime
						0.1,			//velocidade
						10,				//xLimite
						50);			//yLimite
						
				Arma arma = new ArmaBoss1("Avenger", 1000, timer);
				n.addArma(arma);
				n.selecionaArma(arma);
			} else if(t.getTipo()==2){
				n = new Boss2(
						t.getX(),//x
						t.getY(),//y
						1,//layer
						Estado.ACTIVE,//estado
						timer,//timer
						80.0,//raio
						t.getHp(),//hp
						500,//flashTime
						50,//FlasCoolDown
						45.0,//xLimite
						50.0,//yLimite
						4000,//teleportCoolDown
						new EnemyDropper(timer, 15, 10, 2000, 80.0));
				Arma arma = new ArmaBoss2("Destroyer", 2000, timer);
				n.addArma(arma);
				n.selecionaArma(arma);
			}
		} else {
			if(t.getTipo()==1){
				n = new Inimigo1(
						t.getX(), //x
						t.getY(), //y
						1, //layer
						9.0, //raio
						Estado.ACTIVE, //estado
						timer, //timer
						3 * Math.PI / 2, //angulo
						0.20 + Math.random() * 0.15, //velocidade
						0.0 //velocidadeRotacao
						);	
				Arma arma = new ArmaInimigo("Def",500,timer);
				n.addArma(arma);
				n.selecionaArma(arma);
			} else if(t.getTipo()==2){
				n = new Inimigo2(
						t.getX(), //x
						t.getY(), //y
						1, //layer
						12.0, //raio
						Estado.ACTIVE, //estado
						timer, //timer
						(3 * Math.PI) / 2, //angulo
						0.42, //velocidade
						0.0 //velocidadeRotacao
						);	
				Arma arma = new ArmaInimigo2("Def",timer);
				n.addArma(arma);
				n.selecionaArma(arma);
			}
		}
		return n;
	}
	
}
