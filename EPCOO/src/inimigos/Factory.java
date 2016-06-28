package inimigos;

import armas.Arma;
import armas.ArmaDefInimigo;
import armas.ArmaDefInimigo2;
import arquivo.TimerElemento;
import main.Timer;
import model.Estado;
import model.Nave;

public class Factory {

	public static Nave instanciarInimigo(TimerElemento t, Timer timer){
		Nave n = null;
		if(t.isBoss()){
			if(t.getTipo()==1){
				
			} else if(t.getTipo()==2){
				
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
				Arma arma = new ArmaDefInimigo("Def",500,timer);
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
				Arma arma = new ArmaDefInimigo2("Def",timer);
				n.addArma(arma);
				n.selecionaArma(arma);
			}
		}
		return n;
	}
	
}
