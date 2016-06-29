package controladores;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import elementos.Estrela;
import main.Timer;

public class ControladorBackground extends Controlador {
	private Timer timer;
	private List<Estrela> estrelas;
	
	public ControladorBackground(Timer timer, int qtdEstrelas1, int qtdEstrelas2){
		this.timer = timer;
		this.estrelas = new LinkedList<Estrela>();
		
		int i;
		for(i=0; i<qtdEstrelas2; i++){
			estrelas.add(new Estrela(
					0.045,
					3,
					2, Color.DARK_GRAY,this.timer
					));
		}
		for(i=0; i<qtdEstrelas1; i++){
			estrelas.add(new Estrela(
					0.070,
					2,
					3, Color.gray,this.timer
					));
		}
		
	}

	@Override
	public void execute() {
		for(Estrela e:estrelas){
			e.mover();
		}
	}

	@Override
	public void desenharObjetos() {
		Iterator<Estrela> it = estrelas.iterator();
		while(it.hasNext()){
			it.next().draw();
		}
	}

	@Override
	public void limparMemoria() {
		this.estrelas.clear();
	}
}
