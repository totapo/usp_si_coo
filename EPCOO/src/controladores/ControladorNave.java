package controladores;

import java.util.*;

import projeteis.Projetil;
import main.Timer;
import model.Estado;
import model.Nave;
import interfaces.Controlador;
import interfaces.Destrutivel;
import interfaces.Observer;
import interfaces.Subject;

public abstract class ControladorNave implements Observer,
		Subject,Controlador {
	protected List<Nave> naves;
	protected Set<Projetil> disparos;
	protected Timer timer;

	protected List<Observer> obs;

	private List<Nave> remover;
	private Set<Projetil> removerP;

	public ControladorNave(Timer timer) {
		naves = new ArrayList<Nave>();
		disparos = new HashSet<Projetil>();
		this.remover = new ArrayList<Nave>();
		this.removerP = new HashSet<Projetil>();
		this.timer = timer;
		obs = new LinkedList<Observer>();
	}

	public List<Nave> getNaves() {
		return naves;
	}

	public void execute() {
		Iterator<Nave> it = this.naves.iterator();
		Nave aux;
		Projetil p;
		List<Projetil> lista = null;
		while (it.hasNext()) {
			aux = it.next();
			lista = aux.atirar();
			aux.mover();
			if (lista != null) {
				Iterator<Projetil> itP = lista.iterator();
				while (itP.hasNext()) {
					p = itP.next();
					p.addObserver(this);
					this.disparos.add(p);
				}
			}
		}

		Iterator<Projetil> tp = this.disparos.iterator();
		while (tp.hasNext()) {
			tp.next().mover();
		}

		limpar();
	}

	protected void limpar() {
		this.naves.removeAll(remover);
		this.disparos.removeAll(removerP);
		remover.clear();
		removerP.clear();
	}

	@Override
	public void notify(Object s) {
		if (s instanceof Nave) {
			Nave aux = (Nave) s;
			if (aux.getEstado() == Estado.INACTIVE) {
				this.remover.add(aux);
			}
		} else if (s instanceof Projetil) {
			Projetil p = (Projetil) s;
			if (p.getEstado() == Estado.INACTIVE) {
				this.removerP.add(p);
			}
		}
	}

	public void desenharObjetos() {
		Iterator<Nave> it1 = naves.iterator();
		while (it1.hasNext()) {
			it1.next().draw();
		}

		Iterator<Projetil> it2 = disparos.iterator();
		while (it2.hasNext()) {
			it2.next().draw();
		}
	}

	public void checarProjeteis(Collection<? extends Destrutivel> col) {
		Iterator<Projetil> it = this.disparos.iterator();
		Projetil p;
		while (it.hasNext()) {
			p = it.next();
			p.checarColisoes(col);
		}
	}

	@Override
	public void addObserver(Observer o) {
		obs.add(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : obs) {
			o.notify(this);
		}
	}

}
