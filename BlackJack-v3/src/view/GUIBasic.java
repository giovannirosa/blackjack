package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Blackjack;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIBasic extends Stage {

	GridPane geralPanel = new GridPane();

	MenuBar menuBar = new MenuBar();

	public MenuItem menuRestart = new MenuItem("Restart Game");
	public MenuItem menuReturn = new MenuItem("Return Menu");
	public MenuItem menuExit = new MenuItem("Exit");
	Menu menuOptions = new Menu("Options");

	public MenuItem menuRules = new MenuItem("Rules");
	public MenuItem menuStrat = new MenuItem("Strategy");
	Menu menuHelp = new Menu("Help");

	public MenuItem menuTrade = new MenuItem("Trade Chips");
	Menu menuTools = new Menu("Tools");

	/**
	 * Build basic window components.
	 * @param title
	 * @throws IOException
	 */
	public GUIBasic(String title) throws IOException {
		this.setTitle(title);
		this.setMaximized(true);
		this.setResizable(false);

		geralPanel.setAlignment(Pos.CENTER);
		geralPanel.setHgap(10);
		geralPanel.setVgap(30);
		geralPanel.setPadding(new Insets(25));
		geralPanel.setId("Pane");
		
		String os = System.getProperty("os.name");
		if (os.equals("Linux")) {
			geralPanel.setMinSize(Blackjack.width,Blackjack.height-80);
		} else {
			geralPanel.setMinSize(Blackjack.width,Blackjack.height-50);
		}

		menuTools.getItems().addAll(menuTrade);
		menuOptions.getItems().addAll(menuRestart, menuReturn, menuExit);
		menuHelp.getItems().addAll(menuRules, menuStrat);

		menuBar.getMenus().addAll(menuTools,menuOptions,menuHelp);

		Scene scene = new Scene(new VBox());
		((VBox)scene.getRoot()).getChildren().addAll(menuBar,geralPanel);
        this.setScene(scene);

        scene.getStylesheets().add
        (GUIMenu.class.getResource("GUIStyle.css").toExternalForm());

        BufferedImage icon = null;
		try {
			icon = ImageIO.read(new File("imgs/Nut.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        this.getIcons().add(SwingFXUtils.toFXImage(icon, null));
	}
}
