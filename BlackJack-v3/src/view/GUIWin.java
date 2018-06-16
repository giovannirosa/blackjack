package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GUIWin extends Stage {

	public Label winGame = new Label();

	GridPane winningPanel = new GridPane();
	HBox w0Panel = new HBox();
	HBox w1Panel = new HBox();

	public GUIWin() {
		initGUI();
	}

	private void initGUI() {
		this.setTitle("Congratulations");
		this.setResizable(false);
		this.setOnCloseRequest(e -> this.close());

		BufferedImage gif = null;
		try {
			gif = ImageIO.read(new File("imgs/win.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageView winGif = new ImageView(SwingFXUtils.toFXImage(gif, null));

		w0Panel.setAlignment(Pos.CENTER);
		w1Panel.setAlignment(Pos.CENTER);
		winningPanel.setAlignment(Pos.CENTER);

		w0Panel.getChildren().add(winGame);
		w1Panel.getChildren().add(winGif);
		winningPanel.add(w0Panel, 0,0);
		winningPanel.add(w1Panel, 0,0);

		Scene scene = new Scene(winningPanel);
		this.setScene(scene);
	}
}
