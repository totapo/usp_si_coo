package controladores;

import java.util.*;

import projeteis.Projetil;
import main.Timer;
import model.Estado;
import model.Nave;
import inimigos.Inimigo;
import interfaces.Destrutivel;
import interfaces.Observer;
import interfaces.Subject;

public abstract class Controlador implements Observer {
	private List<Nave> naves;
	private Set<Projetil> disparos;
	protected Timer timer;
	
	private List<Nave> remover;
	private Set<Projetil> removerP;
	
	public Controlador(Timer timer){
		naves = new ArrayList<Nave>();
		disparos = new HashSet<Projetil>();
		this.remover = new ArrayList<Nave>();
		this.removerP = new HashSet<Projetil>();
		this.timer = timer;
	}
	
	public List<Nave> getNaves(){
		return naves;
	}
	
	public void removeInimigo(Nave i){
		naves.remove(i);
	}
	
	//faz os naves se moverem, atirarem e spawna novos se for o caso
	public void execute(){
		Iterator<Nave> it = this.naves.iterator();
		Nave aux;
		Projetil p;
		List<Projetil> lista;
		while(it.hasNext()){
			aux = it.next();
			aux.mover();
			lista = aux.atirar();
			if(lista!=null){
				Iterator<Projetil> itP = lista.iterator();
				while(itP.hasNext()){
					p = itP.next();
					p.addObserver(this);
					this.disparos.add(p);
				}
			}
		}
		spawnNave();
		
		Iterator<Projetil> tp = this.disparos.iterator();
		while(tp.hasNext()){
			tp.next().mover();
		}
		
		limpar();
	} 
	
	private void limpar() {
		this.naves.removeAll(remover);
		this.disparos.removeAll(removerP);
		remover.clear();
		removerP.clear();
	}

	protected abstract void spawnNave();

	@Override
	public void notify(Subject s) {
		if(s instanceof Inimigo){
			Inimigo aux = (Inimigo)s;
			if(aux.getEstado() == Estado.INACTIVE){
				this.remover.add(aux);
			}
		} else if(s instanceof Projetil){
			Projetil p = (Projetil)s;
			if(p.getEstado() == Estado.INACTIVE){
				this.removerP.add(p);
			}
		}
	}
	
	public void desenharObjetos(){
		Iterator<Nave> it1 = naves.iterator();
		while(it1.hasNext()){
			it1.next().draw();
		}
		
		Iterator<Projetil> it2 = disparos.iterator();
		while(it2.hasNext()){
			it2.next().draw();
		}
	}
	
	public void checarProjeteis(Collection<? extends Destrutivel> col){
		Iterator<Projetil> it = this.disparos.iterator();
		Projetil p;
		while(it.hasNext()){
			p = it.next();
			p.checarColisoes(col);
		}
	}

}
