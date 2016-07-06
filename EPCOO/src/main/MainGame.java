package main;

//Classe de que contem o metodo main

public class MainGame {

	public static void main(String[] args) {
		Thread game = new Thread( new Game());
		game.start();
	}

}
