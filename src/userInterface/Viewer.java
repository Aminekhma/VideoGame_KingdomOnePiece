/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;

import tools.HardCodedParameters;
import tools.Position;
import specifications.ViewerService;
import specifications.ReadService;
import specifications.RequireReadService;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import data.Personnage;

public class Viewer implements ViewerService, RequireReadService {
	private final String imageFile = "file:src/images";
	private ReadService data;
	private ArrayList<ImageView> background;
	private ArrayList<Position> initial;
	private Rectangle barreVieTotal, barreStaminaTotal;

	public Viewer() {
		background = new ArrayList<ImageView>();

	}

	@Override
	public void bindReadService(ReadService service) {
		data = service;
	}

	@Override
	public void init() {
		initial = data.getFond();
		for (int i = 0; i < 4; i++) {
			ImageView add = new ImageView(new Image(imageFile + "/background/" + i + ".png"));
			add.setTranslateX(initial.get(i).x);
			add.setTranslateY(initial.get(i).y);
			background.add(add);

		}

	}

	@Override
	public Parent getPanel() {
		return loadView();

	}

	private Parent loadView() {
		Group panel = new Group();
		addFond(panel);
		addCharacter(panel);
		addLifeBar(panel);
		addStaminaBar(panel);
		addGameOver(panel);
		addScore(panel);
		return panel;
	}

	private void addCharacter(Group panel) {
		ImageView im = data.getLuffy().getRendu();
		panel.getChildren().add(im);
		boolean showHitbox = data.getShowHitbox();
		if (showHitbox)
			panel.getChildren().add(data.getLuffy().getHitBox());
		for (Personnage p : data.getSoldats()) {
			im = p.getRendu();
			im.setTranslateX(p.getPosition().x);
			im.setTranslateY(p.getPosition().y);
			panel.getChildren().add(im);
			if (showHitbox) { // ajout de l'envellope convexe
				Polygon add = p.getHitBox();
				add.setTranslateX(p.getPosition().x);
				add.setTranslateY(p.getPosition().y);
				panel.getChildren().add(add);
			}

		}
	}

	private void addFond(Group panel) {
		Rectangle ciel = new Rectangle(HardCodedParameters.defaultWidth, HardCodedParameters.defaultHeight / 2,
				new LinearGradient(1f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
						new Stop[] { new Stop(0, Color.web("#D98956")), new Stop(1, Color.web("#1B214B")), }));
		Image Fondsheet = new Image(imageFile + "/background/" + 1 + ".png");
		ImageView Fond = new ImageView(Fondsheet);
		Fond.setTranslateX(data.getFondPosition().x - 250);
		Fond.setTranslateY(data.getFondPosition().y);
		panel.getChildren().addAll(Fond);

		Fondsheet = new Image(imageFile + "/background/" + 0 + ".png");
		Fond = new ImageView(Fondsheet);
		Fond.setTranslateX(data.getFond2Position().x - 250);
		Fond.setTranslateY(data.getFond2Position().y);
		panel.getChildren().addAll(Fond);

		Fondsheet = new Image(imageFile + "/background/" + 2 + ".png");
		Fond = new ImageView(Fondsheet);
		Fond.setTranslateX(data.getFond3Position().x - 250);
		Fond.setTranslateY(data.getFond3Position().y);
		panel.getChildren().addAll(Fond);

		Fondsheet = new Image(imageFile + "/background/" + 3 + ".png");
		Fond = new ImageView(Fondsheet);
		Fond.setTranslateX(data.getFond4Position().x - 250);
		Fond.setTranslateY(data.getFond4Position().y);
		panel.getChildren().addAll(Fond);

		Image solsheet = new Image(imageFile + "/background/" + 4 + ".png");
		ImageView sol = new ImageView(solsheet);
		sol.setTranslateX(data.getSolPosition().x - 280);
		sol.setTranslateY(data.getSolPosition().y);
		panel.getChildren().addAll(sol);
		panel.getChildren().add(ciel);
		ciel.toBack();


	}

	private void addGameOver(Group panel) {
		if (data.getLuffy().getPdv() <= 0) {
			Image im = new Image("file:src/images/game_over.png");
			ImageView iv = new ImageView(im);
			iv.setFitWidth(HardCodedParameters.defaultWidth);
			iv.setFitHeight(HardCodedParameters.defaultHeight);
			panel.getChildren().add(iv);
		}

	}

	private void addLifeBar(Group panel) {
		barreVieTotal = new Rectangle(20, 20, 500, 10);
		Text t = new Text((int) data.getLuffy().getPdv() + "/" + HardCodedParameters.luffyMaxPdv);
		t.setX(270);
		t.setY(28);
		t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 8));
		t.setFill(Color.WHITE);
		// t.setTranslateX(270);
		// t.setTranslateX(20);
		barreVieTotal.setFill(Color.valueOf("ee2e31")); // rouge
		barreVieTotal.setStroke(Color.valueOf("071e22"));
		barreVieTotal.setStrokeWidth(3);
		panel.getChildren().add(barreVieTotal);
		Rectangle barreVieCourante = new Rectangle(21, 21.5, 498, 7);
		barreVieCourante.setFill(Color.valueOf("1d7874")); // vert
		barreVieCourante.setWidth(
				(data.getLuffy().getPdv() * 100 / HardCodedParameters.luffyMaxPdv) * barreVieTotal.getWidth() / 100);
		panel.getChildren().add(barreVieCourante);
		panel.getChildren().add(t);
	}

	private void addStaminaBar(Group panel) {
		barreStaminaTotal = new Rectangle(20, 35, 300, 10);
		Text t = new Text((int) data.getLuffy().getStamina() + "/" + HardCodedParameters.luffyMaxStamina);
		t.setX(170);
		t.setY(43);
		t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 8));
		t.setFill(Color.WHITE);
		barreStaminaTotal.setFill(Color.valueOf("03045e"));
		barreStaminaTotal.setStroke(Color.valueOf("0077b6"));
		barreStaminaTotal.setStrokeWidth(3);
		panel.getChildren().add(barreStaminaTotal);
		Rectangle barreStaminaCourante = new Rectangle(21, 36.5, 297, 7);
		barreStaminaCourante.setFill(Color.valueOf("00B4D8"));
		barreStaminaCourante.setWidth((data.getLuffy().getStamina() * 100 / HardCodedParameters.luffyMaxStamina)
				* barreStaminaTotal.getWidth() / 100);
		panel.getChildren().add(barreStaminaCourante);
		panel.getChildren().add(t);

	}
	
	private void addScore(Group panel) {

		Text score = new Text("Score: " + (int) data.getLuffy().getScore());
		score.setFill(Color.WHITE);
		score.setFont(new Font(0.05*HardCodedParameters.defaultHeight));
		score.setTranslateX(850);
		score.setTranslateY(45);
		panel.getChildren().add(score);
	}

}
