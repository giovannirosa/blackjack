package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIMenu extends Stage {

	public Button spButton = new Button("SinglePlayer");
	public Button mpButton = new Button("MultiPlayer");
	public Button customBut = new Button("Settings");
	public Button exitBut = new Button("Exit");

	GridPane geralPanel = new GridPane();

	Image iconImg = new Image(("file:Nut.png"));

	public GUIMenu() throws Exception {
		init();
	}

	public void init() throws Exception {
		this.setTitle("BlackJack gVersion 2.0");
		this.getIcons().add(new Image(("file:Nut.png")));
		this.setOnCloseRequest(e -> System.exit(0));
		this.setMaximized(true);

        geralPanel.setAlignment(Pos.CENTER);
        geralPanel.setHgap(10);
        geralPanel.setVgap(50);
        geralPanel.setPadding(new Insets(25, 25, 25, 25));
        geralPanel.setId("Pane");

        Text scenetitle = new Text("Blackjack");
        scenetitle.setId("welcome-text");
        HBox hbTxt = new HBox(10);
        hbTxt.setAlignment(Pos.CENTER);
        hbTxt.getChildren().add(scenetitle);
        geralPanel.add(hbTxt, 1, 0);

        VBox vbBtn = new VBox(10);
        vbBtn.setAlignment(Pos.CENTER);
        vbBtn.getChildren().add(spButton);
        vbBtn.getChildren().add(mpButton);
        vbBtn.getChildren().add(customBut);
        vbBtn.getChildren().add(exitBut);
        geralPanel.add(vbBtn, 1, 2);

//        grid.setGridLinesVisible(true);

        Scene scene = new Scene(geralPanel);
        this.setScene(scene);

        scene.getStylesheets().add
        (GUIMenu.class.getResource("GUIMenu.css").toExternalForm());

        BufferedImage icon = null;
		try {
			icon = ImageIO.read(new File("imgs/Nut.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

        this.getIcons().add(SwingFXUtils.toFXImage(icon, null));
	}
}
