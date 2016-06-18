package main;

//Classe de Teste para a classe Game

public class MainGame {

	public static void main(String[] args) {
		Thread game = new Thread( new Game());
		game.start();
	}

}
