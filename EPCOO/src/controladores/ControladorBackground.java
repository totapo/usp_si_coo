package controladores;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import elementos.Estrela;
import pacote.GameLib;
import main.Timer;

public class ControladorBackground extends Controlador {
	private int qtdEstrelas1, qtdEstrelas2;
	private Timer timer;
	private List<Estrela> estrelas;
	
	public ControladorBackground(Timer timer, int qtdEstrelas1, int qtdEstrelas2){
		this.timer = timer;
		this.qtdEstrelas1 = qtdEstrelas1;
		this.qtdEstrelas2 = qtdEstrelas2;
		this.estrelas = new LinkedList<Estrela>();
	}

	@Override
	public void execute() {
		if(estrelas.size()==0){
			int i;
			for(i=0; i<qtdEstrelas2; i++){
				estrelas.add(new Estrela(
						Math.random() * GameLib.WIDTH,
						Math.random() * GameLib.HEIGHT,
						3,
						//0.045,
						2
						));
			}
			for(i=0; i<qtdEstrelas1; i++){
				estrelas.add(new Estrela(
						Math.random() * GameLib.WIDTH,
						Math.random() * GameLib.HEIGHT,
						2,
						//0.070,
						3
						));
			}
		}
	}

	@Override
	public void desenharObjetos() {
		Estrela.count(0.070, 0.045, timer.getDelta());
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
