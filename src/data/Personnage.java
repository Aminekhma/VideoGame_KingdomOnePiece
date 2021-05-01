/**
 * 
 */
package data;

import java.util.ArrayList;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import tools.HardCodedParameters;
import tools.Position;
import tools.User.COMMAND;

public class Personnage {
	/********************************************************************************/
	/* Variable d'instance */
	/********************************************************************************/
	private int score = 0;
	private int stamina;
	private double pdv;
	private Sprite up, down, idle, right;
	private ArrayList<Sprite> sprite;
	private Sprite upTransformation, downTransformation, idleTransformation, rightTransformation;
	private ImageView rendu;
	private Position position;
	private Boolean isTransforme;
	private Polygon hitbox;
	private int pdvMAX, staminaMAX;
	private boolean isRunning = false;
	private boolean attak = false;

	/********************************************************************************/
	/* Constructeur */
	/********************************************************************************/
	public Personnage(double x, double y, int pdvMax, int staminaMax) {
		this.pdvMAX = pdvMax;
		this.staminaMAX = staminaMax;
		position = new Position(x, y);
		isTransforme = false;
		sprite = new ArrayList<>();
		pdv = pdvMax;
		stamina = staminaMax;
	}

	public Personnage(double x, double y) {
		this(x, y, HardCodedParameters.SoldatPdv, HardCodedParameters.SoldatStamina);
	}

	/********************************************************************************/
	/* Methode */
	/********************************************************************************/
	public void addSprite(String file, COMMAND commande, Boolean transformation) {
		Sprite add = new Sprite(file);
		switch (commande) {
		case LEFT:
			if (transformation)
				idleTransformation = add;
			else
				idle = add;
			break;
		case RIGHT:
			if (transformation)
				rightTransformation = add;
			else
				right = add;
			break;
		case UP:
			if (transformation)
				upTransformation = add;
			else
				up = add;
			break;
		case DOWN:
			if (transformation)
				downTransformation = add;
			down = add;
			break;
		default:
			break;
		}
		sprite.add(add);
	}

	public void addSprite(String file, COMMAND commande) {
		addSprite(file, commande, false);
	}

	public ImageView getRendu() {
		return rendu;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position p) {
		position = p;
	}

	public void setPosition(double x, double y) {
		position.x = x;
		position.x = y;

	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void init() {
		rendu = idle.getImageView();
		hitbox = idle.getPolynome();
		updatePosition();
		hitBoxColor();
	}

	public void updatePosition() {
		up.getImageView().setTranslateX(position.x);
		up.getImageView().setTranslateY(position.y);
		down.getImageView().setTranslateX(position.x);
		down.getImageView().setTranslateY(position.y);
		idle.getImageView().setTranslateX(position.x);
		idle.getImageView().setTranslateY(position.y);
		right.getImageView().setTranslateX(position.x);
		right.getImageView().setTranslateY(position.y);
		if (rightTransformation != null && upTransformation != null && downTransformation != null
				&& idleTransformation != null) {
			if (isTransforme && stamina < 10)
				rendu = idle.getImageView();
			upTransformation.getImageView().setTranslateX(position.x);
			upTransformation.getImageView().setTranslateY(position.y);
			downTransformation.getImageView().setTranslateX(position.x);
			downTransformation.getImageView().setTranslateY(position.y);
			idleTransformation.getImageView().setTranslateX(position.x);
			idleTransformation.getImageView().setTranslateY(position.y);
			rightTransformation.getImageView().setTranslateX(position.x);
			rightTransformation.getImageView().setTranslateY(position.y);
		}
	}

	public void moveLeft() {
		if (isTransforme) {
			rendu = rightTransformation.getImageView();
			hitbox = rightTransformation.getPolynome();
		} else {
			rendu = right.getImageView();
			hitbox = right.getPolynome();
		}
		if (rightTransformation != null && upTransformation != null && downTransformation != null
				&& idleTransformation != null) {
			rightTransformation.getImageView().setScaleX(-1);
			upTransformation.getImageView().setScaleX(-1);
			downTransformation.getImageView().setScaleX(-1);
			idleTransformation.getImageView().setScaleX(-1);
		}
		right.getImageView().setScaleX(-1);
		up.getImageView().setScaleX(-1);
		down.getImageView().setScaleX(-1);
		idle.getImageView().setScaleX(-1);
		updateHitBoxDirection();

	}

	public void moveRight() {
		if (isTransforme) {
			rendu = rightTransformation.getImageView();
			hitbox = rightTransformation.getPolynome();
		} else {
			rendu = right.getImageView();
			hitbox = right.getPolynome();
		}
		if (rightTransformation != null && upTransformation != null && downTransformation != null
				&& idleTransformation != null) {
			rightTransformation.getImageView().setScaleX(1);
			upTransformation.getImageView().setScaleX(1);
			downTransformation.getImageView().setScaleX(1);
			idleTransformation.getImageView().setScaleX(1);
		}
		right.getImageView().setScaleX(1);
		up.getImageView().setScaleX(1);
		down.getImageView().setScaleX(1);
		idle.getImageView().setScaleX(1);
		updateHitBoxDirection();
	}

	public void moveUP() {
		if (rightTransformation == null && upTransformation == null && downTransformation == null
				&& idleTransformation == null) {
			rendu = up.getImageView();
			hitbox = up.getPolynome();
			return;
		}
		if (stamina > staminaMAX / 10) {
			stamina -= staminaMAX / 10;
			if (isTransforme) {
				rendu = upTransformation.getImageView();
				hitbox = upTransformation.getPolynome();
			} else {
				rendu = up.getImageView();
				hitbox = up.getPolynome();
			}
		}
		updateHitBoxDirection();

	}

	public void moveDown() {
		if (isTransforme) {
			return;
		}
		if (stamina == staminaMAX) {
			rendu = down.getImageView();
			hitbox = down.getPolynome();
			isTransforme = true;
			rendu = idleTransformation.getImageView();
			hitbox = idleTransformation.getPolynome();
		}
		updateHitBoxDirection();
	}

	public void relache() {
		rendu = idle.getImageView();
		hitbox = idle.getPolynome();
		if (isTransforme) {
			rendu = idleTransformation.getImageView();
			hitbox = idleTransformation.getPolynome();
		}
		updateHitBoxDirection();
	}

	public double getPdv() {
		return pdv;
	}

	public void setPdv(double d) {
		this.pdv = d;
	}

	public int getPdvMAX() {
		return pdvMAX;
	}

	public void useStamina() {
		int decharge = 5;
		if (stamina - decharge >= 0)
			stamina -= decharge;
		else
			stamina = 0;
	}

	public void staminaCharge() {
		int charge = 3;
		if (isTransforme && stamina <= 0) {
			stamina = 0;
			rendu = idle.getImageView();
			hitbox = idle.getPolynome();
			isTransforme = false;
		}
		if (stamina + charge < staminaMAX)
			stamina += charge;
		else
			stamina = staminaMAX;
	}

	public void setRunning(boolean running) {
		isRunning = running;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public boolean Attak() {
		return attak;
	}

	public void setAttak(boolean b) {
		this.attak = b;
	}

	public ArrayList<Sprite> getSprite() {
		return sprite;
	}

	public int getStamina() {
		return stamina;
	}

	public Polygon getHitBox() {
		return hitbox;
	}

	public void hitBoxColor() {
		int r, g, b, min, max;
		min = 0;
		max = 255;
		r = (int) (Math.random() * ((max - min) + 1)) + min;
		g = (int) (Math.random() * ((max - min) + 1)) + min;
		b = (int) (Math.random() * ((max - min) + 1)) + min;
		for (Sprite s : sprite) {
			for (Frame f : s.getFrame()) {
				f.setColorHitbox(r, g, b);
				f.getEnvloppeConvexe().setTranslateX(position.x);
				f.getEnvloppeConvexe().setTranslateY(position.y);
			}
		}
	}

	public void updateHitBoxDirection() {
		if (rendu.getScaleX() == -1)
			hitbox.setScaleX(-1);
		else {
			hitbox.setScaleX(1);
		}
	}

}
