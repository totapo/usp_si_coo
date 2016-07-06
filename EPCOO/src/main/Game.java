package main;

import interfaces.Observer;

import java.util.List;

import arquivo.Fase;
import arquivo.LeitorConfiguracoes;
import controladores.ControladorBackground;
import controladores.ControladorBoss;
import controladores.ControladorInimigo;
import controladores.ControladorPlayer;
import controladores.ControladorSpawnElementos;

public class Game implements Runnable, Observer {

	private Timer timer;
	private ControladorInimigo controladoresInimigos;
	private ControladorPlayer controladorPlayer;
	private ControladorBoss controladorBoss;
	private ControladorBackground controladorBg;
	private boolean running;
	private List<Fase> fases;
	private Fase faseAtual;
	private LeitorConfiguracoes leitor;
	private ControladorSpawnElementos controladorSpawnElementos;

	private static void busyWait(long time) {

		while (System.currentTimeMillis() < time)
			Thread.yield();
	}

	//metodo reponsavel por instanciar o leitor do arquivo de configuracoes e popular a lista de fases
	private void prepareResources() {
		/* Indica que o jogo está em execução */
		running = true;
		
		//cria o leitor de fases, e já lê todos os arquivos
		leitor = new LeitorConfiguracoes("./fases/config_game.txt");
		
		//pega a lista de fases
		fases = leitor.getFases();
		
		/* variáveis usadas no controle de tempo efetuado no main loop */

		

		/* iniciado interface gráfica */

		GameLib.initGraphics();
	}
	
	//metodo responsavel por encerrar o jogo caso não hajam mais fases ou por passar para a proxima caso um boss tenha sido derrotado
	private void proximaFase(){
		if(fases!=null && fases.size()>0){
			faseAtual = fases.remove(0);
			timer.marcarIniFase();
			setControllers();
		} else if(fases!=null){
			System.out.println("Congratulations!");
			System.exit(0);
		} else {
			System.out.println("Fases inexistentes!");
			System.exit(0);
		}
	}
	
	//inicializa os controladores ou, caso ja estejam criados, reseta suas variaveis caso necessario
	private void setControllers(){
		if(controladoresInimigos==null){
			controladoresInimigos = new ControladorInimigo();
		}
		if(controladorBoss == null){
			controladorBoss = new ControladorBoss();
			controladorBoss.addObserver(this);
		}
		if(controladorPlayer==null){
			controladorPlayer = new ControladorPlayer(faseAtual.getPlayerHp());
			controladorPlayer.addObserver(this);
		}else 
			controladorPlayer.resetLife();
		
		//controlador responsavel pela lista de elementos que devem aparecer durante a fase
		if(controladorSpawnElementos==null){
			controladorSpawnElementos = new ControladorSpawnElementos(timer, faseAtual.getEnemies());
			controladorSpawnElementos.addObserver(controladorPlayer);
			controladorSpawnElementos.addObserver(controladoresInimigos);
			controladorSpawnElementos.addObserver(controladorBoss);
		} else {
			controladorSpawnElementos.limparMemoria();
			controladorSpawnElementos.setEnemies(faseAtual.getEnemies());
		}
		
		
		
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
		controladoresInimigos.checarProjeteis(controladorPlayer.getPowerUpsAtivos());
		controladoresInimigos.checarProjeteis(controladorPlayer.getNaves());
		controladorBoss.checarProjeteis(controladorPlayer.getPowerUpsAtivos());
		controladorBoss.checarProjeteis(controladorPlayer.getNaves());

		/* colisões player - inimigos */
		controladorPlayer.checarColisoes(controladorPlayer.getPowerUps(),false);
		controladorPlayer.checarColisoes(controladoresInimigos.getNaves(),true);
		controladorPlayer.checarColisoes(controladorBoss.getNaves(), true);
		

		/* colisões projeteis (player) - inimigos */
		controladorPlayer.checarProjeteis(controladoresInimigos.getNaves());
		controladorPlayer.checarProjeteis(controladorBoss.getNaves());
		
	}

	private void updateStates() {
		controladorSpawnElementos.execute();
		controladoresInimigos.execute();
		controladorBoss.execute();
		controladorPlayer.execute();
		controladorBg.execute();

		if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE))
			running = false;
	}

	private void drawScene() {
		/* desenhando plano fundo */
		controladorBg.desenharObjetos();
		
		//desenhando naves e projeteis
		controladoresInimigos.desenharObjetos();

		controladorBoss.desenharObjetos();
		
		controladorPlayer.desenharObjetos();
		
		//desenhando HUD
		controladorBoss.drawHud();
		
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


		timer = Timer.getInstance();
		updateTimer();
		
		proximaFase();

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
	public void notify(Object s) {
		if(s instanceof ControladorBoss){ //significa que o player derrotou o Boss
			proximaFase();
		} else if(s instanceof ControladorPlayer){ //significa que o player morreu
			System.out.println("Game over");
			System.exit(0);
		}
	}
}
