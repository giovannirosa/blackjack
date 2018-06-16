package model;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Chips {

	private int redChips;
	private int blueChips;
	private int greenChips;
	private int brownChips;
	private Button redChipBut;
	private Button blueChipBut;
	private Button greenChipBut;
	private Button brownChipBut;
	private Label redChipLab;
	private Label blueChipLab;
	private Label greenChipLab;
	private Label brownChipLab;

	/**
	 * Defines the quantity of each chip and link with each button.
	 * @param rc
	 * @param bc
	 * @param gc
	 * @param bnc
	 * @param cbox
	 */
	public Chips(int rc, int bc, int gc, int bnc) {
		redChips = rc;
		blueChips = bc;
		greenChips = gc;
		brownChips = bnc;
	}

	public void resetChips(int rc, int bc, int gc, int bnc) {
		redChips = rc;
		blueChips = bc;
		greenChips = gc;
		brownChips = bnc;
	}

	public void linkComponents(HBox cbox) {
		VBox vbox = (VBox) cbox.getChildren().get(0);
		redChipBut = (Button) vbox.getChildren().get(0);
		HBox hbox = (HBox) vbox.getChildren().get(1);
		redChipLab = (Label) hbox.getChildren().get(0);
		redChipLab.setId("ChipsLabels");

		vbox = (VBox) cbox.getChildren().get(1);
		blueChipBut = (Button) vbox.getChildren().get(0);
		hbox = (HBox) vbox.getChildren().get(1);
		blueChipLab = (Label) hbox.getChildren().get(0);
		blueChipLab.setId("ChipsLabels");

		vbox = (VBox) cbox.getChildren().get(2);
		greenChipBut = (Button) vbox.getChildren().get(0);
		hbox = (HBox) vbox.getChildren().get(1);
		greenChipLab = (Label) hbox.getChildren().get(0);
		greenChipLab.setId("ChipsLabels");

		vbox = (VBox) cbox.getChildren().get(3);
		brownChipBut = (Button) vbox.getChildren().get(0);
		hbox = (HBox) vbox.getChildren().get(1);
		brownChipLab = (Label) hbox.getChildren().get(0);
		brownChipLab.setId("ChipsLabels");
	}

	public int getRedChips() {
		return redChips;
	}

	public void setRedChips(int redChips) {
		this.redChips = redChips;
	}

	public int getBlueChips() {
		return blueChips;
	}

	public void setBlueChips(int blueChips) {
		this.blueChips = blueChips;
	}

	public int getGreenChips() {
		return greenChips;
	}

	public void setGreenChips(int greenChips) {
		this.greenChips = greenChips;
	}

	public int getBrownChips() {
		return brownChips;
	}

	public void setBrownChips(int brownChips) {
		this.brownChips = brownChips;
	}

	public Button getRedChipBut() {
		return redChipBut;
	}

	public void setRedChipBut(Button redChipBut) {
		this.redChipBut = redChipBut;
	}

	public Button getBlueChipBut() {
		return blueChipBut;
	}

	public void setBlueChipBut(Button blueChipBut) {
		this.blueChipBut = blueChipBut;
	}

	public Button getGreenChipBut() {
		return greenChipBut;
	}

	public void setGreenChipBut(Button greenChipBut) {
		this.greenChipBut = greenChipBut;
	}

	public Button getBrownChipBut() {
		return brownChipBut;
	}

	public void setBrownChipBut(Button brownChipBut) {
		this.brownChipBut = brownChipBut;
	}

	public Label getRedChipLab() {
		return redChipLab;
	}

	public void setRedChipLab(Label redChipLab) {
		this.redChipLab = redChipLab;
	}

	public Label getBlueChipLab() {
		return blueChipLab;
	}

	public void setBlueChipLab(Label blueChipLab) {
		this.blueChipLab = blueChipLab;
	}

	public Label getGreenChipLab() {
		return greenChipLab;
	}

	public void setGreenChipLab(Label greenChipLab) {
		this.greenChipLab = greenChipLab;
	}

	public Label getBrownChipLab() {
		return brownChipLab;
	}

	public void setBrownChipLab(Label brownChipLab) {
		this.brownChipLab = brownChipLab;
	}
}
