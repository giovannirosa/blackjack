package main;

import java.awt.GraphicsEnvironment;
import java.io.IOException;

import controller.Actions;
import controller.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class BlackJack extends Application {

	static Actions act = new Actions();

	public static void main(String[] args) throws IOException {
		act.setNimbusLookAndFeel();
		new Game();

		String fonts[]
		        = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		for (int i = 0; i < fonts.length; i++) {
		    System.out.println(fonts[i]);
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

}
