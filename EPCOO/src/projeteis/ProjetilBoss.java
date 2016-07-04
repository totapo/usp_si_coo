package projeteis;

import interfaces.Destrutivel;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import pacote.GameLib;
import main.Timer;
import model.Elemento;
import model.Estado;

public class ProjetilBoss extends Projetil {

	private long dropTime;
	private long coolDownTime;
	private boolean explodiu;
	private int qtdProjeteis;

	public ProjetilBoss(double x, double y, double raio, int layer,
			long coolDownTime, int qtdProjeteis, Estado estado, Timer timer,
			ProjetilDropper pDropper) {
		super(x, y, 0, 0, layer, estado, timer,raio);
		this.coolDownTime = coolDownTime;
		this.dropTime = timer.getCurrentTime();
		explodiu = false;
		this.qtdProjeteis = qtdProjeteis;
		this.pDropper = pDropper;
	}

	@Override
	public void mover() {
		
	}

	public List<ProjetilInimigo> explodir() {
		if (timer.getCurrentTime() - dropTime > coolDownTime && this.getEstado() == Estado.ACTIVE) {
			List<Elemento> elementos = dropProjeteis(qtdProjeteis, x, y, layer);
			List<ProjetilInimigo> projeteis = new LinkedList<ProjetilInimigo>();
			for (Elemento e : elementos) {
				projeteis.add((ProjetilInimigo) e);
			}
			this.setEstado(Estado.INACTIVE);
			return projeteis;
		}
		return null;

	}

	@Override
	public void draw() {
		if (!explodiu) {
			GameLib.setColor(Color.PINK);
			GameLib.drawCircle(x, y, raio);
		}
	}

	@Override
	public double criterioColisao(Destrutivel aux) {
		return (aux.getRaioColisao()+this.raio)*0.8;
	}

}
