package main;

import interfaces.Observer;
import interfaces.Subject;

import java.util.List;

import arquivo.Fase;
import arquivo.LeitorConfiguracoes;
import pacote.GameLib;
import controladores.ControladorBackground;
import controladores.ControladorInimigo;
import controladores.ControladorPlayer;

public class Game implements Runnable, Observer {

	private Timer timer;
	private ControladorInimigo controladoresInimigos;
	private ControladorPlayer controladorPlayer;
	private ControladorBackground controladorBg;
	private boolean running;
	private List<Fase> fases;
	private Fase faseAtual;
	private LeitorConfiguracoes leitor;

	private static void busyWait(long time) {

		while (System.currentTimeMillis() < time)
			Thread.yield();
	}

	private void prepareResources() {
		/* Indica que o jogo está em execução */
		running = true;
		
		//cria o leitor de fases, e já lê todos os arquivos
		leitor = new LeitorConfiguracoes("./fases/config.txt");
		
		//pega a lista de fases
		fases = leitor.getFases();
		
		/* variáveis usadas no controle de tempo efetuado no main loop */

		timer = Timer.getInstance();
		updateTimer();
		
		proximaFase();

		/* iniciado interface gráfica */

		GameLib.initGraphics();
	}
	
	private void proximaFase(){
		if(fases.size()>0){
			faseAtual = fases.remove(0); //TODO isso é meio feio mas por enquanto...
			timer.marcarIniFase();
			setControllers();
		}
	}
	
	private void setControllers(){
		if(controladoresInimigos==null){
			controladoresInimigos = new ControladorInimigo(timer, faseAtual.getEnemies());
			controladoresInimigos.addObserver(this);
		} else {
			controladoresInimigos.limparMemoria();
			controladoresInimigos.setEnemies(faseAtual.getEnemies());
		}
		
		if(controladorPlayer==null){
			controladorPlayer = new ControladorPlayer(timer, faseAtual.getPlayerHp());
			controladorPlayer.addObserver(this);
		}else 
			controladorPlayer.resetLife();
		
		if(controladorBg==null)
			controladorBg = new ControladorBackground(timer, 10, 50);
	}

	private void updateTimer() {
		/* Usada para atualizar o estado dos elementos do jogo */
		/* (player, projéteis e inimigos) "delta" indica quantos */
		/* ms se passaram desde a última atualização. */
		timer.calcDelta();
		/* Já a variável "timer.getCurrentTime()" nos dá o timestamp atual. */

		timer.updateCurrentTime();
	}

	private void verifyCollisions() {
		/* colisões player - projeteis (inimigo) */
		controladoresInimigos.checarProjeteis(controladorPlayer.getNaves());
		

		/* colisões player - inimigos */
		controladorPlayer.checarColisoes(controladoresInimigos.getNaves());
		

		/* colisões projeteis (player) - inimigos */
		controladorPlayer.checarProjeteis(controladoresInimigos.getNaves());
		
	}

	private void updateStates() {

		controladoresInimigos.execute();
		controladorPlayer.execute();
		controladorBg.execute();

		if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE))
			running = false;
	}

	private void drawScene() {
		/* desenhando plano fundo */
		controladorBg.desenharObjetos();

		controladoresInimigos.desenharObjetos();
		
		controladorPlayer.desenharObjetos();
		
		//desenhando HUD
		controladoresInimigos.drawHud();
		controladorPlayer.drawHud();

		/*
		 * chamada a display() da classe GameLib atualiza o desenho exibido pela
		 * interface do jogo.
		 */

		GameLib.display();
	}

	private void pause(long pauseTime) {
		busyWait(timer.getCurrentTime() + pauseTime);
	}

	@Override
	public void run() {

		prepareResources();

		/*************************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo */
		/*                                                                                               */
		/* O main loop do jogo possui executa as seguintes operações: */
		/*                                                                                               */
		/*
		 * 1) Verifica se há colisões e atualiza estados dos elementos conforme
		 * a necessidade.
		 */
		/*                                                                                               */
		/*
		 * 2) Atualiza estados dos elementos baseados no tempo que correu desde
		 * a última atualização
		 */
		/*
		 * e no timestamp atual: posição e orientação, execução de disparos de
		 * projéteis, etc.
		 */
		/*                                                                                               */
		/*
		 * 3) Processa entrada do usuário (teclado) e atualiza estados do player
		 * conforme a necessidade.
		 */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos. */
		/*                                                                                               */
		/*
		 * 5) Espera um período de tempo (de modo que delta seja aproximadamente
		 * sempre constante).
		 */
		/*                                                                                               */
		/*************************************************************************************************/

		while (running) {

			updateTimer();

			/***************************/
			/* Verificação de colisões */
			/***************************/

			verifyCollisions();

			/***************************/
			/* Atualizações de estados */
			/***************************/
			updateStates();

			/*******************/
			/* Desenho da cena */
			/*******************/

			drawScene();

			/*
			 * faz uma pausa de modo que cada execução do laço do main loop
			 * demore aproximadamente 5 ms.
			 */

			pause(5);

		}

		System.exit(0);
	}

	@Override
	public void notify(Subject s) {
		if(s instanceof ControladorInimigo){
			proximaFase();
		} else if(s instanceof ControladorPlayer){//TODO
			System.out.println("Game over, se fodeu");
			System.exit(0);
		}
	}
}
