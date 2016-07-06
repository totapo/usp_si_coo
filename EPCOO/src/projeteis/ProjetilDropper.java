package projeteis;

import java.util.LinkedList;
import java.util.List;

import model.Elemento;
import model.Estado;
import interfaces.ElementsDropper;


public class ProjetilDropper implements ElementsDropper{
	
	private double velocidadeProjeteis;
	
	public ProjetilDropper(double velocidadeProjeteis){
		this.velocidadeProjeteis = velocidadeProjeteis;
	}

	@Override
	public List<Elemento> drop(int n, double x, double y) {
		List<Elemento> projeteis = new LinkedList<Elemento>();
		double vx, vy;
		ProjetilInimigo projetil;
		for (vx = -1; vx < 1; vx += 1.0 / (n/4)) {
			vy = 1;
			projetil = new ProjetilInimigo(x, y, vx*velocidadeProjeteis, vy*velocidadeProjeteis, 2.0, 
					Estado.ACTIVE);
			projeteis.add(projetil);
		}
		for (vx = -1; vx < 1; vx += 1.0 / (n/4)) {
			vy = -1;
			projetil = new ProjetilInimigo(x, y, vx*velocidadeProjeteis, vy*velocidadeProjeteis, 2.0, 
					Estado.ACTIVE);
			projeteis.add(projetil);
		}
		for (vy = -1; vy < 1; vy += 1.0 / (n/4)) {
			vx = 1;
			projetil = new ProjetilInimigo(x, y, vx*velocidadeProjeteis, vy*velocidadeProjeteis, 2.0, 
					Estado.ACTIVE);
			projeteis.add(projetil);
		}
		for (vy = -1; vy < 1; vy += 1.0 / (n/4)) {
			vx = -1;
			projetil = new ProjetilInimigo(x, y, vx*velocidadeProjeteis, vy*velocidadeProjeteis, 2.0, 
					Estado.ACTIVE);
			projeteis.add(projetil);
		}
		return projeteis;
	}

}
