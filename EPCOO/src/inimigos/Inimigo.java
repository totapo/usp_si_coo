package inimigos;

import main.Timer;
import model.Estado;
import model.Nave;

public abstract class Inimigo extends Nave {

	public Inimigo(double x, double y, int layer, double raio, Estado estado,
			Timer timer) {
		super(x, y, layer, estado, timer,raio);
	}
	
	//Não sei o que o inimigo tem que uma nave normal não hahahahahah, mas enfim...

}
