package main;
import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import controladores.Controlador;
import controladores.ControladorInimigoDois;
import controladores.ControladorInimigoUm;
import controladores.ControladorPlayer;
import armas.Arma;
import armas.Default;
import model.Estado;
import model.Player;
import pacote.GameLib;

public class Main {
	
	/* Constantes relacionadas aos estados que os elementos   */
	/* do jogo (player, projeteis ou inimigos) podem assumir. */
	
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
	

	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */
	
	public static void busyWait(long time){
		
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	/* Método principal */
	
	public static void main(String [] args){

		/* Indica que o jogo está em execução */
		boolean running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		
		Timer timer = new Timer();
		timer.updateCurrentTime();
		/* variáveis do player */
		
		Player player = new Player(
				GameLib.WIDTH / 2, 		//x
				GameLib.HEIGHT * 0.90, 	//y
				1,						//layer 1 - assumindo HUD no 0
				0.25,					// velocidade no eixo x
				0.25,					// velocidade no eixo y
				Estado.ACTIVE,
				timer,
				12.0					//raio
				);
		Arma a = new Default("gun",100,timer);
		player.addArma(a);
		player.selecionaArma(a);
		
		List<Controlador> controladoresInimigos = new LinkedList<Controlador>();
		controladoresInimigos.add(new ControladorInimigoUm(
				timer,
				2000));
		controladoresInimigos.add(new ControladorInimigoDois(
				timer,GameLib.WIDTH * 0.20,7000,10
				));
		
		ControladorPlayer ctrl = new ControladorPlayer(timer, player);
		
		/* estrelas que formam o fundo de primeiro plano */
		
		double [] background1_X = new double[20];
		double [] background1_Y = new double[20];
		double background1_speed = 0.070;
		double background1_count = 0.0;
		
		/* estrelas que formam o fundo de segundo plano */
		
		double [] background2_X = new double[50];
		double [] background2_Y = new double[50];
		double background2_speed = 0.045;
		double background2_count = 0.0;
		
		/* inicializações */
		
		for(int i = 0; i < background1_X.length; i++){
			
			background1_X[i] = Math.random() * GameLib.WIDTH;
			background1_Y[i] = Math.random() * GameLib.HEIGHT;
		}
		
		for(int i = 0; i < background2_X.length; i++){
			
			background2_X[i] = Math.random() * GameLib.WIDTH;
			background2_Y[i] = Math.random() * GameLib.HEIGHT;
		}
						
		/* iniciado interface gráfica */
		
		GameLib.initGraphics();
		
		/*************************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo                                                                             */
		/*                                                                                               */
		/* O main loop do jogo possui executa as seguintes operações:                                    */
		/*                                                                                               */
		/* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
		/*                                                                                               */
		/* 2) Atualiza estados dos elementos baseados no tempo que correu desde a última atualização     */
		/*    e no timestamp atual: posição e orientação, execução de disparos de projéteis, etc.        */
		/*                                                                                               */
		/* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
		/*                                                                                               */
		/* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
		/*                                                                                               */
		/*************************************************************************************************/
		long delta;

		while(running){
			/* Usada para atualizar o estado dos elementos do jogo    */
			/* (player, projéteis e inimigos) "delta" indica quantos  */
			/* ms se passaram desde a última atualização.             */
			timer.calcDelta();
			delta = timer.getDelta();
			/* Já a variável "timer.getCurrentTime()" nos dá o timestamp atual.  */
			
			timer.updateCurrentTime();
			
			/***************************/
			/* Verificação de colisões */
			/***************************/
			
			/* colisões player - projeteis (inimigo) */
			Iterator<Controlador> it = controladoresInimigos.iterator();
			while(it.hasNext()){
				it.next().checarProjeteis(ctrl.getNaves());
			}
			
			/* colisões player - inimigos */
			it = controladoresInimigos.iterator();
			while(it.hasNext()){
				ctrl.checarColisoes(it.next().getNaves());
			}
			
			/* colisões projeteis (player) - inimigos */
			it = controladoresInimigos.iterator();
			while(it.hasNext()){
				ctrl.checarProjeteis(it.next().getNaves());
			}
				
			/***************************/
			/* Atualizações de estados */
			/***************************/
			it = controladoresInimigos.iterator();
			while(it.hasNext()){
				it.next().execute();
			}
			
			ctrl.execute();
			
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;

			/*******************/
			/* Desenho da cena */
			/*******************/
			
			/* desenhando plano fundo distante */
			
			GameLib.setColor(Color.DARK_GRAY);
			background2_count += background2_speed * delta;
			
			for(int i = 0; i < background2_X.length; i++){
				
				GameLib.fillRect(background2_X[i], (background2_Y[i] + background2_count) % GameLib.HEIGHT, 2, 2);
			}
			
			/* desenhando plano de fundo próximo */
			
			GameLib.setColor(Color.GRAY);
			background1_count += background1_speed * delta;
			
			for(int i = 0; i < background1_X.length; i++){
				
				GameLib.fillRect(background1_X[i], (background1_Y[i] + background1_count) % GameLib.HEIGHT, 3, 3);
			}
						
			it = controladoresInimigos.iterator();
			while(it.hasNext()){
				it.next().desenharObjetos();
			}
			
			ctrl.desenharObjetos();
			
			/* chamama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */
			
			busyWait(timer.getCurrentTime() + 5);
		}
		
		System.exit(0);
	}
}

/*
int [] enemy1_states = new int[10];						// estados
double [] enemy1_X = new double[10];					// coordenadas x
double [] enemy1_Y = new double[10];					// coordenadas y
double [] enemy1_V = new double[10];					// velocidades
double [] enemy1_angle = new double[10];				// ângulos (indicam direção do movimento)
double [] enemy1_RV = new double[10];					// velocidades de rotação
double [] enemy1_explosion_start = new double[10];		// instantes dos inícios das explosões
double [] enemy1_explosion_end = new double[10];		// instantes dos finais da explosões

//varaveis controlador
long [] enemy1_nextShoot = new long[10];				// instantes do próximo tiro
double enemy1_radius = 9.0;								// raio (tamanho do inimigo 1)
long nextEnemy1 = timer.getCurrentTime() + 2000;					// instante em que um novo inimigo 1 deve aparecer

/* variáveis dos inimigos tipo 2 

int [] enemy2_states = new int[10];						// estados
double [] enemy2_X = new double[10];					// coordenadas x
double [] enemy2_Y = new double[10];					// coordenadas y
double [] enemy2_V = new double[10];					// velocidades
double [] enemy2_angle = new double[10];				// ângulos (indicam direção do movimento)
double [] enemy2_RV = new double[10];					// velocidades de rotação
double [] enemy2_explosion_start = new double[10];		// instantes dos inícios das explosões
double [] enemy2_explosion_end = new double[10];		// instantes dos finais das explosões

//variaveis controlador
double enemy2_spawnX = GameLib.WIDTH * 0.20;			// coordenada x do próximo inimigo tipo 2 a aparecer
int enemy2_count = 0;									// contagem de inimigos tipo 2 (usada na "formação de voo")
double enemy2_radius = 12.0;							// raio (tamanho aproximado do inimigo 2)
long nextEnemy2 = timer.getCurrentTime() + 7000;					// instante em que um novo inimigo 2 deve aparecer

/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2) 

int [] e_projectile_states = new int[200];				// estados
double [] e_projectile_X = new double[200];				// coordenadas x
double [] e_projectile_Y = new double[200];				// coordenadas y
double [] e_projectile_VX = new double[200];			// velocidade no eixo x
double [] e_projectile_VY = new double[200];			// velocidade no eixo y
double e_projectile_radius = 2.0;						// raio (tamanho dos projéteis inimigos)
*/
