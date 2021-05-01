/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package engine;

import tools.HardCodedParameters;
import tools.User;
import tools.User.COMMAND;
import tools.Position;
import specifications.EngineService;
import specifications.DataService;
import specifications.RequireDataService;

import java.util.Timer;
import java.util.TimerTask;

import data.Personnage;
import data.Sprite;

import java.util.Random;
import java.util.ArrayList;

public class Engine implements EngineService, RequireDataService {
	private static final double friction = HardCodedParameters.friction, SolStep = HardCodedParameters.SolStep,
			FondStep = HardCodedParameters.FondStep, Fond2Step = HardCodedParameters.Fond2Step,
			Fond3Step = HardCodedParameters.Fond3Step, Fond4Step = HardCodedParameters.Fond4Step;

	private Timer engineClock;
	private DataService data;
	private boolean moveLeft, moveRight, moveUp, moveDown;
	private double solVX, solVY;
	private double FondVX, FondVY, Fond2VX, Fond2VY, Fond3VX, Fond3VY, Fond4VX, Fond4VY;
	private ArrayList<Personnage> personnage;

	public Engine() {
	}

	@Override
	public void bindDataService(DataService service) {
		data = service;
	}

	@Override
	public void init() {
		engineClock = new Timer();
		moveLeft = false;
		moveRight = false;
		moveUp = false;
		moveDown = false;
		solVX = 0;
		solVY = 0;
		FondVX = 0;
		FondVY = 0;
		Fond2VX = 0;
		Fond4VY = 0;
		Fond3VX = 0;
		Fond4VY = 0;
		Fond4VX = 0;
		Fond4VY = 0;
		personnage = new ArrayList<>();
		personnage.add(data.getLuffy());

	}

	@Override
	public void start() {
		engineClock.schedule(new TimerTask() {
			public void run() {

				spawnSoldat();
				updateSpeedSol();
				updateCommandSol();
				updatePositionSol();
				updateSpeedFond();
				updateCommandFond();
				updatePositionFond();
				updateSpeedFond2();
				updateCommandFond2();
				updatePositionFond2();
				updateSpeedFond3();
				updateCommandFond3();
				updatePositionFond3();
				updateSpeedFond4();
				updateCommandFond4();
				updatePositionFond4();
				updateStamina();
				updateSprite();
				updateAnimation();
				updateSoldat();
				updateDeathSabreurs();
				updateDeathTireurs();

			}

		}, 0, HardCodedParameters.enginePaceMillis);
	}

	protected void updateStamina() {
		if (data.getLuffy().getStamina() > 0 && data.getisrunningleft() || data.getisrunningright()) {
			data.getLuffy().useStamina();
		}
		data.getLuffy().staminaCharge();
	}

	@Override
	public void stop() {
		engineClock.cancel();
	}

	private void updateDeathSabreurs() {

		ArrayList<Personnage> sabreursvivants = new ArrayList<Personnage>();

		for (Personnage p : data.getSabreurs()) {
			if (p.getPdv() == 0) {
				data.getLuffy().setScore(data.getLuffy().getScore() + 1);
			} else {
				sabreursvivants.add(p);
			}
		}
		data.setSabreurs(sabreursvivants);
	}

	private void updateDeathTireurs() {

		ArrayList<Personnage> tireursvivants = new ArrayList<Personnage>();

		for (Personnage p : data.getTireurs()) {
			if (p.getPdv() == 0) {
				data.getLuffy().setScore(data.getLuffy().getScore() + 1);

			} else {
				tireursvivants.add(p);
			}
		}
		data.setTireurs(tireursvivants);
	}

	private void updateSoldat() {
		final double xtarget = data.getLuffy().getPosition().x;
		int max = 215;
		int min = 185;
		int posDroite = (int) (Math.random() * ((max - min) + 1)) + min;

		max = -85;
		min = -115;
		int posGauche = (int) (Math.random() * ((max - min) + 1)) + min;

		for (Personnage p : data.getTireurs()) {
			if (p.getPosition().x <= xtarget) {
				p.moveRight();
			}
			if (p.getPosition().x >= xtarget) {
				p.moveLeft();
			}

			if (p.getPosition().x < xtarget + 200) {///// avance vers la droite

				if (data.getisrunningleft())
					p.setPosition(new Position(p.getPosition().x + 1 + 12, p.getPosition().y));
				if (data.getisrunningright())
					p.setPosition(new Position(p.getPosition().x - 1 - 20, p.getPosition().y));
				else
					p.setPosition(new Position(p.getPosition().x + 5, p.getPosition().y));

			}

			if (p.getPosition().x > xtarget - 100) { // avance vers la gauche
				if (data.getisrunningleft())
					p.setPosition(new Position(p.getPosition().x - 1 + 20, p.getPosition().y));

				if (data.getisrunningright())
					p.setPosition(new Position(p.getPosition().x + 1 - 20, p.getPosition().y));
				else
					p.setPosition(new Position(p.getPosition().x - 5, p.getPosition().y));
			}
			////////////////////////////////////////// COMBAT ELOIGNER /////////////////////////////////////////////////////////////

			if (p.getPosition().x >= xtarget + 50 && p.getPosition().x <= xtarget + 215) {///// vers la droite DISTANCE
				//
				p.moveUP();

				data.getLuffy().setPdv((data.getLuffy().getPdv() - 0.5));
				if (data.getLuffy().Attak() && p.getPosition().x >= xtarget && p.getPosition().x <= xtarget + 100) {
					if (data.getLuffy().Attak()) {
						p.setPdv(p.getPdv() - 20);
						if (data.getLuffy().getPdv() >= 95) {
							data.getLuffy().setPdv((data.getLuffy().getPdvMAX()));
						} else {
							data.getLuffy().setPdv((data.getLuffy().getPdv() + 0.1));
						}
					}
				}
			}
			if (p.getPosition().x >= xtarget - 150 && p.getPosition().x <= xtarget + 7) {///// vers la gauche
				p.moveUP();

				data.getLuffy().setPdv((data.getLuffy().getPdv() - 0.5));

				if (data.getLuffy().Attak() && p.getPosition().x >= xtarget - 70 && p.getPosition().x <= xtarget) {
					if (data.getLuffy().Attak()) {
						p.setPdv(p.getPdv() - 20);
						if (data.getLuffy().getPdv() >= 95) {
							data.getLuffy().setPdv((data.getLuffy().getPdvMAX()));
						} else {
							data.getLuffy().setPdv((data.getLuffy().getPdv() + 5));

						}
					}
				}

			}
		}
		for (Personnage p : data.getSabreurs()) {
			if (p.getPosition().x <= xtarget) {
				p.moveRight();
			}
			if (p.getPosition().x >= xtarget) {
				p.moveLeft();
			}

			if (p.getPosition().x < xtarget + 63) {///// avance vers la droite

				if (data.getisrunningleft())
					p.setPosition(new Position(p.getPosition().x + 1 + 12, p.getPosition().y));
				if (data.getisrunningright())
					p.setPosition(new Position(p.getPosition().x - 1 - 20, p.getPosition().y));
				else
					p.setPosition(new Position(p.getPosition().x + 5, p.getPosition().y));
			}

			if (p.getPosition().x > xtarget - 6) { // avance vers la gauche
				if (data.getisrunningleft())
					p.setPosition(new Position(p.getPosition().x - 1 + 20, p.getPosition().y));

				if (data.getisrunningright())
					p.setPosition(new Position(p.getPosition().x + 1 - 20, p.getPosition().y));
				else
					p.setPosition(new Position(p.getPosition().x - 5, p.getPosition().y));
			}
			///////////////////////////////////////// COMBAT RAPPROCHER  //////////////////////////////////////////////////////////////

			if (p.getPosition().x >= xtarget + 55 && p.getPosition().x <= xtarget + 75) {///// vers la droite DISTANCE
				data.getLuffy().setPdv((data.getLuffy().getPdv() - 0.2));

				if (data.getLuffy().Attak()) {
					p.setPdv(p.getPdv() - 20);
					if (data.getLuffy().getPdv() >= 95) {
						data.getLuffy().setPdv((data.getLuffy().getPdvMAX()));
					} else {
						data.getLuffy().setPdv((data.getLuffy().getPdv() + 5));

					}
				}

				p.moveUP();
			}
			if (p.getPosition().x >= xtarget - 12 && p.getPosition().x <= xtarget + 7) {///// vers la droite DISTANCE
				data.getLuffy().setPdv((data.getLuffy().getPdv() - 0.2));

				if (data.getLuffy().Attak()) {
					p.setPdv(p.getPdv() - 20);
					if (data.getLuffy().getPdv() >= 95) {
						data.getLuffy().setPdv((data.getLuffy().getPdvMAX()));
					} else {
						data.getLuffy().setPdv((data.getLuffy().getPdv() + 5));

					}
				}

				p.moveUP();
			}

		}

	}

	public void setLuffyCommand(User.COMMAND c) {
		if (c == User.COMMAND.LEFT)
			moveLeft = true;
		if (c == User.COMMAND.RIGHT)
			moveRight = true;
		if (c == User.COMMAND.UP)
			moveUp = true;
		if (c == User.COMMAND.DOWN)
			moveDown = true;
	}

	@Override
	public void releaseLuffyCommand(User.COMMAND c) {
		if (c == User.COMMAND.LEFT)
			moveLeft = false;
		if (c == User.COMMAND.RIGHT)
			moveRight = false;
		if (c == User.COMMAND.UP)
			moveUp = false;
		if (c == User.COMMAND.DOWN)
			moveDown = false;
	}

	private void spawnSoldat() {
		int max = 100;
		int min = 0;
		int random = (int) (Math.random() * ((max - min) + 1)) + min;
		if (random <= 3) { // 3%des soldats qui spawn
			max = 1;
			min = 0;
			int x = (int) (Math.random() * ((max - min) + 1)) + min;
			x = x == 0 ? -50 : HardCodedParameters.defaultWidth; // spawn a droite ou spawn a
			// gauche
			// x = x == 0 ? 0 : HardCodedParameters.defaultWidth - 50;
			Personnage soldat;
			soldat = new Personnage(x, HardCodedParameters.defaultHeight - 133);
			max = 100;
			random = (int) (Math.random() * ((max - min) + 1)) + min;
			int poucentageSabreur = 70;
			if (random <= poucentageSabreur) { // 70%de sabreur
				soldat.addSprite("file:src/images/marine/sabreur/run", COMMAND.RIGHT);
				soldat.addSprite("file:src/images/marine/sabreur/attaque", COMMAND.UP);
				soldat.addSprite("file:src/images/marine/sabreur/meurt", COMMAND.DOWN);
			} else {
				soldat.addSprite("file:src/images/marine/tireur/run", COMMAND.RIGHT);
				soldat.addSprite("file:src/images/marine/tireur/attaque", COMMAND.UP);
				soldat.addSprite("file:src/images/marine/tireur/meurt", COMMAND.DOWN);
			}
			soldat.addSprite("file:src/images/marine/sabreur/idle", COMMAND.LEFT);
			soldat.init();
			if (random <= poucentageSabreur)
				data.addSabreur(soldat);
			else
				data.addTireur(soldat);
		}

	}

	private void updateSpeedSol() {
		solVX *= 0.2;
		solVY *= 0.2;
	}

	private void updateCommandSol() {
		if (moveLeft)
			solVX = solVX + SolStep;
		if (moveRight)
			solVX = solVX - SolStep;

	}

	private void updatePositionSol() {
		data.setSolPosition(new Position((data.getSolPosition().x + solVX) % 260, data.getSolPosition().y + solVY));
	}

	private void updateSpeedFond2() {
		Fond2VX *= friction;
		Fond2VY *= friction;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////
	private void updateSpeedFond() {
		FondVX *= 0;
		FondVY *= 0;
	}

	private void updateCommandFond() {
		if (moveLeft)
			FondVX = FondVX + FondStep;
		if (moveRight)
			FondVX = FondVX - FondStep;
	}

	private void updatePositionFond() {
		data.setFondPosition(
				new Position((data.getFondPosition().x + FondVX) % 270, data.getFondPosition().y + FondVY));
	}
	////////////////////////////////////////////////////////////////////////////////////////////

	private void updateCommandFond2() {
		if (moveLeft)
			Fond2VX = Fond2VX + Fond2Step;
		if (moveRight)
			Fond2VX = Fond2VX - Fond2Step;

	}

	private void updatePositionFond2() {
		data.setFond2Position(
				new Position((data.getFond2Position().x + Fond2VX) % 270, data.getFondPosition().y + Fond2VY));
	}

	///////////////////////////////////////////////////////////////////////////
	private void updateSpeedFond3() {
		Fond3VX *= 0;
		Fond3VY *= 0;
	}

	private void updateCommandFond3() {
		if (moveLeft)
			Fond3VX = Fond3VX + Fond3Step;
		if (moveRight)
			Fond3VX = Fond3VX - Fond3Step;
	}

	private void updatePositionFond3() {
		data.setFond3Position(
				new Position((data.getFond3Position().x + Fond3VX) % 270, data.getFond3Position().y + Fond3VY));
	}

	////////////////////////////////////////////////////////////////////////////
	private void updateSpeedFond4() {
		Fond4VX *= 0;
		Fond4VY *= 0;
	}

	private void updateCommandFond4() {
		if (moveLeft)
			Fond4VX = Fond4VX + Fond4Step;
		if (moveRight)
			Fond4VX = Fond4VX - Fond4Step;
	}

	private void updatePositionFond4() {
		data.setFond4Position(
				new Position((data.getFond4Position().x + Fond4VX) % 260, data.getFond4Position().y + Fond4VY));
	}

	private void updateSprite() {
		for (Personnage p : personnage) {
			if (moveLeft) {
				p.moveLeft();
				return;
			}
			if (moveRight) {
				p.moveRight();
				return;
			}
			if (moveUp) {
				p.moveUP();
				return;
			}
			if (moveDown) {
				p.moveDown();
				return;
			}
			p.relache();
		}

	}

	private void updateAnimation() {
		for (Sprite s : data.getLuffy().getSprite()) {
			s.updateAnimation();
		}
		for (Personnage p : data.getSoldats()) {

			for (Sprite s : p.getSprite()) {
				s.updateAnimation();
			}

		}
	}
}
