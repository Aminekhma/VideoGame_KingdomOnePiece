package data;

import javafx.scene.shape.Polygon;
import tools.Position;
import java.util.ArrayList;
import algorithm.Algorithm;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Frame {
	/********************************************************************************/
	/* Variable d'instance */
	/********************************************************************************/
	public static int cpt=0;
	private Image image;
	private Polygon envloppeConvexe;

	/********************************************************************************/
	/* Constructeur */
	/********************************************************************************/
	public Frame(Image image) {
		this.image = image;
		ArrayList<Double> points = new ArrayList<>();
		for (Position p : Algorithm.AlkToussaint(nuageDePoints())) {
			points.add(p.x);
			points.add(p.y);
		}
		double tab[] = new double[points.size()];
		for (int i = 0; i < points.size(); i++) {
			tab[i] = points.get(i);
		}
		envloppeConvexe = new Polygon(tab);
	}

	/********************************************************************************/
	/* Methode */
	/********************************************************************************/
	public ArrayList<Position> nuageDePoints() {
		cpt++;
		ArrayList<Position> retour = new ArrayList<>();
		PixelReader pr = image.getPixelReader();
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				if (pr.getArgb(i, j) != 0) {				// 0= pixel transparent 
					retour.add(new Position(i, j));
				}

			}
		}
		return retour;
	}

	public Image getImage() {
		return image;
	}

	public Polygon getEnvloppeConvexe() {
		return envloppeConvexe;
	}

	public void setColorHitbox(int r, int g, int b) {
		envloppeConvexe.setFill(Color.rgb(r, g, b));
		envloppeConvexe.setOpacity(0.5);
	}

}
