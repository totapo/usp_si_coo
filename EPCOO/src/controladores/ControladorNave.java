package controladores;

import java.util.*;

import projeteis.Projetil;
import model.Estado;
import model.Nave;
import interfaces.Controlador;
import interfaces.Destrutivel;
import interfaces.Observer;
import interfaces.Subject;

public abstract class ControladorNave implements Observer,
		Subject,Controlador {
	//classe abstrata que possui a implementacao basica de um controlador de Naves generico
	//implementa Observer, Subject e Controlador
	//Controlador pois eh um controlador
	//Observer para observar o estado dos projeteis e das naves que controla
	//Subject para avisar o Game caso algo tenha acontecido (Utilizado no ControladorPlayer e no ControladorBoss
	
	protected List<Nave> naves; //lista de naves sendo controladas
	protected List<Projetil> disparos; //conjunto de disparos feitos pelas naves controladas

	protected List<Observer> obs;

	private List<Nave> remover; //utilizada para limpar a lista de naves controladas quando algumas ficam inativas
	private List<Projetil> removerP; //idem para os disparos

	public ControladorNave() {
		naves = new LinkedList<Nave>();
		disparos = new LinkedList<Projetil>();
		this.remover = new LinkedList<Nave>();
		this.removerP = new LinkedList<Projetil>();
		obs = new LinkedList<Observer>();
	}

	public List<Nave> getNaves() {
		return naves;
	}

	//ciclo de execucao dos controladores de naves
	//para cada nave:
	// mover
	// atirar
	//para cada projetil:
	// mover
	//e, por fim, limpar a lista de projeteis e de naves caso seja necessario
	public void execute() {
		Iterator<Nave> it = this.naves.iterator();
		Nave aux;
		Projetil p;
		List<Projetil> lista = null;
		while (it.hasNext()) {
			aux = it.next();
			aux.mover();
			lista = aux.atirar();
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

	//remove os projeteis e naves que ficaram inativos
	protected void limpar() {
		this.naves.removeAll(remover);
		this.disparos.removeAll(removerP);
		remover.clear();
		removerP.clear();
	}

	@Override
	public void notify(Object s) {
		if (s instanceof Nave) { //se uma nave ficou inatica adiciona a nave na lista para remocao
			Nave aux = (Nave) s;
			if (aux.getEstado() == Estado.INACTIVE) {
				this.remover.add(aux);
			}
		} else if (s instanceof Projetil) { //se o projetil ficou inativo adiciona ele na lista para remocao
			Projetil p = (Projetil) s;
			if (p.getEstado() == Estado.INACTIVE) {
				this.removerP.add(p);
			}
		}
	}

	//desenha a lista de naves e a lista de projeteis
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

	//faz com que todos os projeteis verifiquem se atingiram algo Destrutivel da lista recebida como parametro
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
