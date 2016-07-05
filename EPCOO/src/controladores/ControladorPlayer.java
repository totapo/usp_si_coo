package controladores;

import interfaces.Destrutivel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import elementos.BolinhaOp;
import elementos.LifeBar;
import elementos.Player;
import elementos.Shield;
import armas.Arma;
import armas.ArmaPlayer;
import arquivo.TimerElemento;
import main.Timer;
import model.*;
import powerups.PowerUp;
import powerups.PowerUpFactory;

public class ControladorPlayer extends ControladorNave{
	private List<Elemento> hud;
	private List<Elemento> removerHud;
	private List<PowerUp> powerUps;
	private List<PowerUp> removerPowerUps;
	private List<Destrutivel> powerUpsAtivos;
	private List<Destrutivel> removerPowerUpsAtivos;
	
	public ControladorPlayer(Timer timer, int hp) {
		super(timer);
		hud = new LinkedList<Elemento>();
		removerHud = new ArrayList<Elemento>();
		powerUps = new LinkedList<PowerUp>();
		powerUpsAtivos = new ArrayList<Destrutivel>();
		removerPowerUps = new LinkedList<PowerUp>();
		removerPowerUpsAtivos = new LinkedList<Destrutivel>();
		Player player = new Player(
				GameLib.WIDTH / 2, 		//x
				GameLib.HEIGHT * 0.90, 	//y
				1,						//layer 1 - assumindo HUD no 0
				0.25,					// velocidade no eixo x
				0.25,					// velocidade no eixo y
				Estado.ACTIVE,			//estado
				timer,					//referencia pro Timer
				12.0,					//raio
				hp);
		Arma a = new ArmaPlayer("gun",100,timer);
		player.addArma(a);
		player.selecionaArma(a);
		player.addObserver(this);
		this.getNaves().add(player);
		hud.add(new LifeBar(
				GameLib.WIDTH/2,
				GameLib.HEIGHT-30,
				0,
				player,
				GameLib.WIDTH*0.9,
				GameLib.HEIGHT*0.01,
				Color.GREEN, 
				Color.DARK_GRAY)
				);
	}
	
	@Override
	public void execute() {
		super.execute();
		for(ElementoMutavel e :powerUps){
			e.mover(); 
		}
		for(Destrutivel e :powerUpsAtivos){
			if(e instanceof ElementoMutavel) ((ElementoMutavel)e).mover(); 
		}
	}

	public void resetLife(){
		if(this.getNaves().size()>0){
			Player player = (Player) this.getNaves().get(0);
			player.setHp(player.getTotalHp());
		}
	}
	
	public void drawHud(){
		for(Elemento e : hud){
			e.draw();
		}
	}
	
	@Override
	protected void limpar() {
		super.limpar();
		this.powerUpsAtivos.removeAll(removerPowerUpsAtivos);
		this.hud.removeAll(removerHud);
		this.powerUps.removeAll(removerPowerUps);
		removerPowerUpsAtivos.clear();
		removerPowerUps.clear();
		removerHud.clear();
	}

	@Override
	public void limparMemoria() {
		this.disparos.clear();
		this.powerUps.clear();
	}
	
	public Collection<Destrutivel> getPowerUpsAtivos(){
		return powerUpsAtivos;
	}
	
	public Collection<PowerUp> getPowerUps(){
		return powerUps;
	}
	
	@Override
	public void notify(Object s) {
		super.notify(s);
		if(s instanceof Player){
			Player p = (Player)s;
			if(p.getEstado() == Estado.INACTIVE){
				notifyObservers();
			} else if(p.getEstado() == Estado.FLASHING){ //se tomar tiro perde os powerups, no caso so a bolinha que orbita
				this.removerPowerUpsAtivos.addAll(this.getPowerUpsAtivos());
			}
		} if(s instanceof TimerElemento){
			TimerElemento a = (TimerElemento) s;
			if(!a.isEnemy()){
				PowerUp p = PowerUpFactory.instanciarPowerUp(a,timer);
				p.addObserver(this);
				this.powerUps.add(p);
			}
		} if(s instanceof PowerUp ){
			if(((PowerUp)s).getEstado()==Estado.INACTIVE){
				removerPowerUps.add((PowerUp)s);
				if(((PowerUp) s).wasHit()){
					Destrutivel b = PowerUpFactory.ativarPoder((PowerUp)s, timer, (Player)this.getNaves().get(0)); 
					int i = this.powerUpsAtivos.indexOf(b);
					if(i!=-1){
						if(b instanceof Shield){
							((Shield)this.powerUpsAtivos.get(i)).setHp(((Shield)this.powerUpsAtivos.get(i)).getTotalHp());
						} 
					} else if(b instanceof BolinhaOp){
						this.powerUpsAtivos.add(b);
						((BolinhaOp) b).addObserver(this);
					} else if(b instanceof Shield){
						this.powerUpsAtivos.add(b);//adiciona o novo shield
						this.hud.add(new LifeBar(
								GameLib.WIDTH/2,
								GameLib.HEIGHT-40,
								0,
								(Shield)b,
								GameLib.WIDTH*0.9,
								GameLib.HEIGHT*0.01,
								new Color(70,205,255), 
								Color.DARK_GRAY));
						((Shield) b).addObserver(this);
					} 
				} 
			}
		}else if(s instanceof Shield){
			Shield c = (Shield) s;
			if(c.getEstado() == Estado.INACTIVE){
				removerPowerUpsAtivos.add(c);
				this.removerHud.add(this.hud.get(this.hud.size()-1)); //remove a lifebar do shield 
			}
		} else if(s instanceof BolinhaOp){
			removerPowerUpsAtivos.add((BolinhaOp)s);
		}
	}
	

	@Override
	public void desenharObjetos() {
		super.desenharObjetos();
		for(Elemento a : powerUps){
			a.draw();
		}
		for(Destrutivel a : powerUpsAtivos){
			if(a instanceof Elemento) ((Elemento)a).draw();
		}
	}

	public void checarColisoes(Collection<? extends Destrutivel> objetos, boolean hit){
		if(this.getNaves().size()>0){
			Nave player = this.getNaves().get(0);
			if(player.getEstado() == Estado.ACTIVE){
				Iterator<? extends Destrutivel> it = objetos.iterator();
				Destrutivel i;
				while(it.hasNext()){
					i= it.next();
					if(i instanceof Nave){
						if(((Nave) i).getEstado()!=Estado.ACTIVE ||((Nave) i).getEstado()==Estado.FLASHING) continue;
					}
					double dx = i.getX() - player.getX();
					double dy = i.getY() - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < (player.getRaioColisao() + i.getRaioColisao()) * 0.8){
						if(hit)player.hit();
						i.hit();
						break;
					}
				}
			}
		}
		if(hit)
			for(Destrutivel a: powerUpsAtivos){
				if(a.getEstado() == Estado.ACTIVE){
					Iterator<? extends Destrutivel> it = objetos.iterator();
					Destrutivel i;
					while(it.hasNext()){
						i= it.next();
						
						if(i instanceof Nave){
							if(((Nave) i).getEstado()!=Estado.ACTIVE && ((Nave) i).getEstado()!=Estado.FLASHING) continue;
						}
						
						double dx = i.getX() - a.getX();
						double dy = i.getY() - a.getY();
						double dist = Math.sqrt(dx * dx + dy * dy);
						
						if(dist < (a.getRaioColisao() + i.getRaioColisao()) * 0.8){
							a.hit();
							i.hit();
							break;
						}
					}
				}
		}
	}

}
