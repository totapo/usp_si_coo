package main;

import java.util.LinkedList;
import java.util.List;

import pacote.GameLib;
import controladores.ControladorBackground;
import controladores.ControladorInimigoDois;
import controladores.ControladorInimigoUm;
import controladores.ControladorNave;
import controladores.ControladorPlayer;

public class Game implements Runnable {

	private Timer timer;
	private List<ControladorNave> controladoresInimigos;
	private ControladorPlayer controladorPlayer;
	private ControladorBackground controladorBg;
	private boolean running;

	private static void busyWait(long time) {

		while (System.currentTimeMillis() < time)
			Thread.yield();
	}

	private void prepareResources() {
		/* Indica que o jogo está em execução */
		running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */

		timer = Timer.getInstance();
		updateTimer();

		initiateControllers();

		/* iniciado interface gráfica */

		GameLib.initGraphics();
	}
	
	private void initiateControllers(){
		controladoresInimigos = new LinkedList<ControladorNave>();
		controladoresInimigos.add(new ControladorInimigoUm(timer, 500, 2000));
		controladoresInimigos.add(new ControladorInimigoDois(timer,
				GameLib.WIDTH * 0.20, 7000, 10));

		controladorPlayer = new ControladorPlayer(timer);
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
		for (ControladorNave controlador : controladoresInimigos) {
			controlador.checarProjeteis(controladorPlayer.getNaves());
		}

		/* colisões player - inimigos */
		for (ControladorNave controlador : controladoresInimigos) {
			controladorPlayer.checarColisoes(controlador.getNaves());
		}

		/* colisões projeteis (player) - inimigos */
		for (ControladorNave controlador : controladoresInimigos) {
			controladorPlayer.checarProjeteis(controlador.getNaves());
		}
	}

	private void updateStates() {

		for (ControladorNave controlador : controladoresInimigos) {
			controlador.execute();
		}

		controladorPlayer.execute();
		controladorBg.execute();

		if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE))
			running = false;
	}

	private void drawScene() {
		/* desenhando plano fundo */
		controladorBg.desenharObjetos();

		for (ControladorNave controlador : controladoresInimigos) {
			controlador.desenharObjetos();
		}

		controladorPlayer.desenharObjetos();

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
}
