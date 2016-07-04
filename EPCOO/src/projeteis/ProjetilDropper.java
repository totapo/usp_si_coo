package projeteis;

import java.util.LinkedList;
import java.util.List;

import main.Timer;
import model.Elemento;
import model.Estado;
import interfaces.ElementsDropper;


public class ProjetilDropper implements ElementsDropper{
	
	private double velocidadeProjeteis;
	private Timer timer;
	
	public ProjetilDropper(double velocidadeProjeteis, Timer timer){
		this.velocidadeProjeteis = velocidadeProjeteis;
		this.timer = timer;
	}

	@Override
	public List<Elemento> drop(int n, double x, double y, int layer) {
		List<Elemento> projeteis = new LinkedList<Elemento>();
		double vx, vy;
		ProjetilInimigo projetil;
		for (vx = -1; vx < 1; vx += 1.0 / (n/4)) {
			vy = 1;
			projetil = new ProjetilInimigo(x, y, vx*velocidadeProjeteis, vy*velocidadeProjeteis, 2.0, 1,
					Estado.ACTIVE, timer);
			projeteis.add(projetil);
		}
		for (vx = -1; vx < 1; vx += 1.0 / (n/4)) {
			vy = -1;
			projetil = new ProjetilInimigo(x, y, vx*velocidadeProjeteis, vy*velocidadeProjeteis, 2.0, 1,
					Estado.ACTIVE, timer);
			projeteis.add(projetil);
		}
		for (vy = -1; vy < 1; vy += 1.0 / (n/4)) {
			vx = 1;
			projetil = new ProjetilInimigo(x, y, vx*velocidadeProjeteis, vy*velocidadeProjeteis, 2.0, 1,
					Estado.ACTIVE, timer);
			projeteis.add(projetil);
		}
		for (vy = -1; vy < 1; vy += 1.0 / (n/4)) {
			vx = -1;
			projetil = new ProjetilInimigo(x, y, vx*velocidadeProjeteis, vy*velocidadeProjeteis, 2.0, 1,
					Estado.ACTIVE, timer);
			projeteis.add(projetil);
		}
		return projeteis;
	}

}
