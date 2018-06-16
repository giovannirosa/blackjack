package controller;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Actions {

	/**
	 * Simply mount a deck of cards from 1 to 13:
	 * A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K.
	 * <P>Also applies the suits (S,H,D,C):
	 * spades, hearts, diamonds, clubs.<P>
	 */
	public List<String> mountDeck() {
		List<String> dk = new ArrayList<String>();
		String[] suits = {"S","H","D","C"}; // spades, hearts, diamonds, clubs
		for (int i = 1; i < 14; i++) {
			for (int j = 0; j < 4; j++) {
				dk.add(suits[j]+Integer.toString(i));
			}
		}
		return dk;
	}

	/**
	 * Randomly shuffle the cards using collections.
	 * @param deck
	 */
	public void shuffleDeck(List<String> deck) {
		Collections.shuffle(deck);
	}

	/**
	 * Retrieve a random number based on max value.
	 * @param max
	 * @return <b>int random</b>
	 */
	public int getRandom(int max) {
		return (int) (Math.random()*max) + 1;
	}

	/**
	 * Retrieve the numberic value of the given card.
	 * @param card
	 * @return <b>int number</b>
	 */
	public int getValue(String card) {
		return Integer.parseInt(card.substring(1));
	}

	/**
	 * Retrieve the suit value of the given card.
	 * @param card
	 * @return <b>String suit</b>
	 */
	public String getSuit(String card) {
		return card.substring(0, 1);
	}

	/**
	 * Set Nimbus Look and Feel if available.
	 * <P>Otherwise it will set System Look and Feel.
	 * <P>
	 */
	public void setNimbusLookAndFeel() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
		}
		UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Serif", Font.PLAIN, 15));
		UIManager.put("OptionPane.cancelButtonText", "nope");
	}

	/**
	 * Plays applause sound.
	 * @throws IOException
	 */
	public void playApplause() throws IOException {
		InputStream app = new FileInputStream("sounds/Applause.wav");
		AudioStream applauseStream = new AudioStream(app);
		AudioPlayer.player.start(applauseStream);
	}

	/**
	 * Plays boo sound.
	 * @throws IOException
	 */
	public void playBoo() throws IOException {
		InputStream boo = new FileInputStream("sounds/Boo.wav");
		AudioStream booStream = new AudioStream(boo);
		AudioPlayer.player.start(booStream);
	}

	/**
	 * Cut the image to create each card of the deck.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public List<BufferedImage> cutDeck() throws FileNotFoundException, IOException {

		BufferedImage deck = ImageIO.read(new File("imgs/deck.png"));

		final int width = 79;
		final int height = 123;
		final int rows = 5;
		final int cols = 13;

		List<BufferedImage> sprites = new ArrayList<BufferedImage>();

		for (int i = 0; i < rows; i++) {
		    for (int j = 0; j < cols; j++) {
		        sprites.add(deck.getSubimage(
		            j * width,
		            i * height,
		            width,
		            height
		        ));
		    }
		}

		return sprites;
	}

	/**
	 * Create the image to represent the deck in a StackPane.
	 * @return
	 */
	public StackPane createDeckImg(BufferedImage sprite) {
		StackPane deck = new StackPane();

		Image card = SwingFXUtils.toFXImage(sprite, null);

		for (int i=0; i<52; i++) {
			deck.getChildren().add(new ImageView(card));
		}

		deck.setAlignment(Pos.CENTER);

		int i =0;
		for (Node cards: deck.getChildren()) {
			StackPane.setMargin(cards, new Insets(0, i, 0, 0));
			i+=3;
		}

		return deck;
	}

	/**
	 * It plays an animation representing the shuffle of the cards.
	 * @param deck
	 */
	public Timeline shuffleAnimation(StackPane deck) {
		Timeline timeline = new Timeline();
		String signal = "+";
		String signal2 = "-";
		String value = "200";
		String value2 = "100";
		String sigval = signal+value;
		String sigval2 = signal2+value2;
		boolean key = false;
		boolean key2 = true;
		Random r = new Random();

		for (Node card: deck.getChildren()) {
			if (key && key2) {
				signal = "+";
				signal2 = "-";
				sigval = signal+value;
				sigval2 = signal2+value2;
			} else if (key && !key2) {
				signal = "-";
				signal2 = "+";
				sigval = signal+value;
				sigval2 = signal2+value2;
			} else if (!key && key2) {
				signal = "+";
				signal2 = "+";
				sigval = signal+value;
				sigval2 = signal2+value2;
			} else if (!key && !key2) {
				signal = "-";
				signal2 = "-";
				sigval = signal+value;
				sigval2 = signal2+value2;
			}
			key = r.nextBoolean();
			key2 = r.nextBoolean();

		    timeline.getKeyFrames().addAll(
		        new KeyFrame(Duration.ZERO, // set start position at 0
		            new KeyValue(card.translateXProperty(), card.translateXProperty().doubleValue()),
		            new KeyValue(card.translateYProperty(), card.translateYProperty().doubleValue())
		        ),
		        new KeyFrame(new Duration(1000), // set mid position at 1s
			            new KeyValue(card.translateXProperty(), getRandom(Integer.parseInt(sigval))),
			            new KeyValue(card.translateYProperty(), getRandom(Integer.parseInt(sigval2)))
			    ),
		        new KeyFrame(new Duration(2000), // set mid position at 2s
			            new KeyValue(card.translateXProperty(), getRandom(Integer.parseInt(sigval))),
			            new KeyValue(card.translateYProperty(), getRandom(Integer.parseInt(sigval2)))
			    ),
		        new KeyFrame(new Duration(3000), // set mid position at 3s
			            new KeyValue(card.translateXProperty(), getRandom(Integer.parseInt(sigval))),
			            new KeyValue(card.translateYProperty(), getRandom(Integer.parseInt(sigval2)))
			    ),
		        new KeyFrame(new Duration(4000), // set end position at 4s
		            new KeyValue(card.translateXProperty(), 0),
		            new KeyValue(card.translateYProperty(), 0)
		        )
		    );
		}

		return timeline;
	}

	public Timeline distCardAnimation(Node card, HBox cardsPane) {
		Timeline timeline = new Timeline();

		timeline.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, // set start position at 0
						new KeyValue(card.translateXProperty(), card.translateXProperty().doubleValue()),
						new KeyValue(card.translateYProperty(), card.translateYProperty().doubleValue())
						),
				new KeyFrame(new Duration(2000), // set final position at 1s
						new KeyValue(card.translateXProperty(), 300),
						new KeyValue(card.translateYProperty(), 300)
						)
				);

		return timeline;
	}


	/**
	 * Creates a confirmation dialog.
	 * @param title
	 * @param message
	 * @return <b>Alert</b>
	 */
	public Alert confirmationMessage(String title, String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		ButtonType yesOpt = new ButtonType("Yes");
		ButtonType noOpt = new ButtonType("No");

		alert.getButtonTypes().setAll(yesOpt, noOpt);

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(
		   getClass().getResource("../view/myDialogs.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

		BufferedImage icon = null;
		try {
			icon = ImageIO.read(new File("imgs/question.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageView iconImg = new ImageView(SwingFXUtils.toFXImage(icon, null));
		iconImg.setFitHeight(80);
		iconImg.setFitWidth(80);

		alert.setGraphic(iconImg);

		stage.getIcons().add(SwingFXUtils.toFXImage(icon, null));

		return alert;
	}

	/**
	 * Creates info alert with image given.
	 * @param img
	 * @return
	 */
	private Alert createInfoAlert(String img) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText(null);

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(
		   getClass().getResource("../view/myDialogs.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

		BufferedImage icon = null;
		try {
			icon = ImageIO.read(new File(img));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageView iconImg = new ImageView(SwingFXUtils.toFXImage(icon, null));

		alert.setGraphic(iconImg);

		stage.getIcons().add(SwingFXUtils.toFXImage(icon, null));

		return alert;
	}

	/**
	 * Displays win message.
	 * @param message
	 */
	public void winMessage(String message) {
		Alert alert = createInfoAlert("imgs/congrats.png");
		alert.setContentText(message);

		alert.showAndWait();
	}

	/**
	 * Displays lose message for player who marks more than 21.
	 * @param message
	 */
	public void loseMessage(String message) {
		Alert alert = createInfoAlert("imgs/skull.png");
		alert.setContentText(message);

		alert.showAndWait();
	}

	/**
	 * Displays accurate message for player who marks 21.
	 * @param message
	 */
	public void accurateMessage(String message) {
		Alert alert = createInfoAlert("imgs/21.png");
		alert.setContentText(message);

		alert.showAndWait();
	}

	// ==================== Flip Card Animation ========================

    /*
     * Mmm... pie.
     */
    private static final Double PIE = Math.PI;

    private static final Double HALF_PIE = Math.PI / 2;

    private static final double ANIMATION_DURATION = 10000;

    private static final double ANIMATION_RATE = 10;

    // ====================== Instance Fields =============================

    private Timeline animation;

    private StackPane flipPane;

    private SimpleDoubleProperty angle = new SimpleDoubleProperty(HALF_PIE);

    private PerspectiveTransform transform = new PerspectiveTransform();

    private SimpleBooleanProperty flippedProperty = new SimpleBooleanProperty(true);

    // ==================== Creators ====================

    public StackPane createFlipPane(int i)
    {
        angle = createAngleProperty();

        flipPane = new StackPane();

        flipPane.setMinHeight(123);
        flipPane.setMinWidth(79);

        flipPane.getChildren().setAll(createBackNode(i), createFrontNode());

        flipPane.widthProperty().addListener(new ChangeListener<Number>() {

            @Override public void changed(final ObservableValue<? extends Number> arg0, final Number arg1, final Number arg2)
            {
                recalculateTransformation(angle.doubleValue());
            }
        });

        flipPane.heightProperty().addListener(new ChangeListener<Number>() {

            @Override public void changed(final ObservableValue<? extends Number> arg0, final Number arg1, final Number arg2)
            {
                recalculateTransformation(angle.doubleValue());
            }
        });

        return flipPane;
    }

    private StackPane createFrontNode()
    {
        final StackPane node = new StackPane();

        node.setEffect(transform);
        node.visibleProperty().bind(flippedProperty);

        node.getChildren().setAll(new ImageView(SwingFXUtils.toFXImage(Game.sprites.get(52), null))); //$NON-NLS-1$

        return node;
    }

    private StackPane createBackNode(int i)
    {
        final StackPane node = new StackPane();

        node.setEffect(transform);
        node.visibleProperty().bind(flippedProperty.not());

        node.getChildren().setAll(new ImageView(SwingFXUtils.toFXImage(Game.sprites.get(i), null))); //$NON-NLS-1$

        return node;
    }

    private SimpleDoubleProperty createAngleProperty()
    {
        // --------------------- <Angle> -----------------------

        final SimpleDoubleProperty angle = new SimpleDoubleProperty(HALF_PIE);

        angle.addListener(new ChangeListener<Number>() {

            @Override public void changed(final ObservableValue<? extends Number> obsValue, final Number oldValue, final Number newValue)
            {
                recalculateTransformation(newValue.doubleValue());
            }
        });

        return angle;
    }

    private Timeline createAnimation()
    {
        return new Timeline(

                new KeyFrame(Duration.millis(0),    new KeyValue(angle, HALF_PIE)),

                new KeyFrame(Duration.millis(ANIMATION_DURATION / 2),  new KeyValue(angle, 0, Interpolator.EASE_IN)),

                new KeyFrame(Duration.millis(ANIMATION_DURATION / 2),  new EventHandler<ActionEvent>() {

                    @Override public void handle(final ActionEvent arg0)
                    {
                        flippedProperty.set( flippedProperty.not().get() );
                    }
                }),

                new KeyFrame(Duration.millis(ANIMATION_DURATION / 2),  new KeyValue(angle, PIE)),

                new KeyFrame(Duration.millis(ANIMATION_DURATION), new KeyValue(angle, HALF_PIE, Interpolator.EASE_OUT))

                );
    }

    // ==================== Action Methods ====================

    public void flip()
    {
        if (animation == null)
            animation = createAnimation();

        animation.setRate( flippedProperty.get() ? ANIMATION_RATE : -ANIMATION_RATE );

        animation.play();
    }

    // ==================== Business Methods ====================

    private void recalculateTransformation(final double angle)
    {
        final double insetsTop = flipPane.getInsets().getTop() * 2;
        final double insetsLeft = flipPane.getInsets().getLeft() * 2;

        final double radius = flipPane.widthProperty().subtract(insetsLeft).divide(2).doubleValue();
        final double height = flipPane.heightProperty().subtract(insetsTop).doubleValue();
        final double back = height / 10;

        /*
         * Compute transform.
         *
         * Don't bother understanding these unless you're a math passionate.
         *
         * You may Google "Affine Transformation - Rotation"
         */
        transform.setUlx(radius - Math.sin(angle) * radius);
        transform.setUly(0 - Math.cos(angle) * back);
        transform.setUrx(radius + Math.sin(angle) * radius);
        transform.setUry(0 + Math.cos(angle) * back);
        transform.setLrx(radius + Math.sin(angle) * radius);
        transform.setLry(height - Math.cos(angle) * back);
        transform.setLlx(radius - Math.sin(angle) * radius);
        transform.setLly(height + Math.cos(angle) * back);
    }
}
