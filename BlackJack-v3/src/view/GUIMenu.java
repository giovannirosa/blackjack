package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GUIMenu extends GUIBasic {

	public Button spButton = new Button("SinglePlayer");
	public Button mpButton = new Button("MultiPlayer");
	public Button customBut = new Button("Settings");
	public Button exitBut = new Button("Exit");

	public GUIMenu() throws Exception {
		super("BlackJack gVersion 3.0");
		init();
	}

	public void init() throws Exception {
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

        menuRestart.setDisable(true);
        menuReturn.setDisable(true);
        menuTools.setDisable(true);
//        geralPanel.setGridLinesVisible(true);
	}
}
