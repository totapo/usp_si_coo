package inimigos;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import arquivo.TimerElemento;
import main.Timer;
import model.Elemento;
import interfaces.ElementsDropper;

public class EnemyDropper implements ElementsDropper {

	private long lastDrop;
	private long dropCoolDown;
	private int maxInimigosDrop;// Quantidade máxima de inimigos que podem ser
								// dropados
	private int minInimigosDrop;// Quantidade mínima de inimigos que podem ser
								// dropados
	private Timer timer;
	private double dropRange;

	public EnemyDropper(Timer timer, int maxInimigosDrop, int minInimigosDrop,
			long dropCoolDown, double dropRange) {
		this.timer = timer;
		this.maxInimigosDrop = maxInimigosDrop;
		this.minInimigosDrop = minInimigosDrop;
		this.dropCoolDown = dropCoolDown;
		this.dropRange = dropRange;
	}

	@Override
	public List<Elemento> drop(int n, double x, double y, int layer) {
		List<Elemento> inimigos = new LinkedList<Elemento>();
		if (timer.getCurrentTime() - lastDrop > dropCoolDown) {
			
			Random rd = new Random();
			int qtdInimigos = rd.nextInt(maxInimigosDrop - minInimigosDrop)
					+ minInimigosDrop;
			for (double i = -dropRange; i <= this.dropRange; i += 2 * dropRange / qtdInimigos) {

				TimerElemento te = new TimerElemento(0, true, false, 1, x + i, y, 0);
				Inimigo inimigo = (Inimigo) Factory.instanciarInimigo(te, timer);
				inimigos.add(inimigo);
				lastDrop = timer.getCurrentTime();
			}
		}
		return inimigos;
	}

}
