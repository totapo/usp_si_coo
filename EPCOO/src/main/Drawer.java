package main;

import java.awt.Color;

public class Drawer {
	//Só pro background, ainda vou decidir se farei o esquema do drawer chamar o draw pra tudo com um maxHeap definido pelo Layer
	
	public static Color getColor(int layer){
		if(layer==2)
			return Color.GRAY;
		if(layer==3) 
			return Color.DARK_GRAY;
		return Color.BLACK;
	}
}
