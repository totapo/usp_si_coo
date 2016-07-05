package controladores;

import java.awt.Color;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import projeteis.Projetil;
import projeteis.ProjetilBoss;
import projeteis.ProjetilInimigo;
import arquivo.TimerElemento;
import elementos.LifeBar;
import inimigos.Boss;
import inimigos.Factory;
import inimigos.Inimigo;
import main.Timer;
import model.Elemento;
import model.Estado;
import model.GameLib;
import model.Nave;

public class ControladorBoss extends ControladorNave {

	private Boss boss;
	protected List<Elemento> hud;
	protected Set<ProjetilInimigo> adicionaP;

	public ControladorBoss(Timer timer) {
		super(timer);
		hud = new LinkedList<Elemento>();
		adicionaP = new HashSet<ProjetilInimigo>();
	}

	@Override
	public void execute() {
		if (boss != null && (boss.getEstado()==Estado.ACTIVE || boss.getEstado()==Estado.FLASHING)) {
			List<Elemento> inimigos = boss.dropEnemies();
			if (inimigos != null) {
				for (Elemento i : inimigos) {
					naves.add((Inimigo) i);
				}
			}
			Set<Projetil> projeteis = disparos;
			if (projeteis != null) {
				for (Projetil p : projeteis) {
					if (p instanceof ProjetilBoss) {
						List<ProjetilInimigo> explosao = ((ProjetilBoss) p)
								.explodir();
						if (explosao != null) {
							for(ProjetilInimigo pi:explosao){
								pi.addObserver(this);
								adicionaP.add(pi);
							}
						}
					}
				}
			}
			disparos.addAll(adicionaP);
			adicionaP.clear();
		}

		super.execute();
	}

	@Override
	public void notify(Object s) {
		if (s instanceof TimerElemento) {
			TimerElemento a = (TimerElemento) s;
			if (a.isBoss()) {
				Nave aux;
				aux = Factory.instanciarInimigo(a, timer);
				aux.addObserver(this);
				Boss b = (Boss) aux;
				hud.add(new LifeBar(GameLib.WIDTH / 2, 30, 0, b,
						GameLib.WIDTH * 0.9, GameLib.HEIGHT * 0.01, Color.RED,
						Color.DARK_GRAY));
				this.getNaves().add(aux);
				this.boss = b;
			}
		}
		if (s instanceof Boss) {
			Boss b = (Boss) s;
			if (b.getEstado() == Estado.INACTIVE) {
				this.hud.clear();
				this.notifyObservers();
			}
		}
		super.notify(s);
	}

	public void drawHud() {
		for (Elemento e : hud) {
			e.draw();
		}
	}

	@Override
	public void limparMemoria() {
		this.disparos.clear();
		this.hud.clear();
		this.getNaves().clear();
	}

}
