package controladores;

import arquivo.TimerElemento;
import inimigos.Factory;
import model.Nave;

public class ControladorInimigo extends ControladorNave {
	//controla todos os inimigos que aparecem na tela, exceto os Boss
	//tambem eh responsavel por instanciar os inimigos quando o ControladorSpawnElementos o notifica que 
	//um inimigo deve ser criado
	
	public ControladorInimigo() {
		super();
	}
	
	@Override
	public void limparMemoria() {
		this.disparos.clear();
		this.getNaves().clear();
	}

	@Override
	public void notify(Object s) {
		super.notify(s);
		 if(s instanceof TimerElemento){
			TimerElemento a = (TimerElemento)s;
			if(a.isEnemy() && !a.isBoss()){
				Nave aux;
				aux = Factory.instanciarInimigo(a);
				aux.addObserver(this);
				this.getNaves().add(aux);
			} 
		}
	}
}
