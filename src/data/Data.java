/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-11 buixuan.
 * ******************************************************/
package data;

import tools.HardCodedParameters;
import tools.Position;
import tools.User.COMMAND;
import specifications.DataService;

import java.util.ArrayList;

public class Data implements DataService {
	private Position sol, Fond, Fond2, Fond3, Fond4;
	private ArrayList<Position> fond;
	private ArrayList<Personnage> sabreurs,tireurs;
	private int stepNumber;
	private Personnage luffy;
	private boolean isrunningleft, isrunningright,showhitbox;

	public Data() {
		
	}

	@Override
	public void init() {
		fond = new ArrayList<Position>();
		sol = new Position(-5, HardCodedParameters.defaultHeight - 122);
		Fond = new Position(0, 100);
		Fond2 = new Position(0, 200);
		Fond3 = new Position(0, 200);
		Fond4 = new Position(0, 310);

		luffy = new Personnage((HardCodedParameters.defaultWidth - 300 / 2) / 2,
				HardCodedParameters.defaultHeight - 175, HardCodedParameters.luffyMaxPdv,
				HardCodedParameters.luffyMaxStamina);
		luffy.addSprite("file:src/images/luffy/idle", COMMAND.LEFT);
		luffy.addSprite("file:src/images/luffy/run", COMMAND.RIGHT);
		luffy.addSprite("file:src/images/luffy/haki", COMMAND.UP);
		luffy.addSprite("file:src/images/luffy/transformation", COMMAND.DOWN);
		luffy.addSprite("file:src/images/luffy/gearidle", COMMAND.LEFT, true);
		luffy.addSprite("file:src/images/luffy/gearrun", COMMAND.RIGHT, true);
		luffy.addSprite("file:src/images/luffy/gearattaque", COMMAND.UP, true);
		luffy.addSprite("file:src/images/luffy/transformation", COMMAND.DOWN, true);
		luffy.init();

		sabreurs = new ArrayList<Personnage>();
		tireurs = new ArrayList<Personnage>();

		fond.add(new Position(0, 0));
		fond.add(new Position(0, 100));
		fond.add(new Position(0, 200));
		fond.add(new Position(0, 310));
		fond.add(new Position(-5, HardCodedParameters.defaultHeight - 122));
		stepNumber = 0;
	}

	public void setSabreurs(ArrayList<Personnage> sabreurs) {
		this.sabreurs = sabreurs;
	}

	public void setTireurs(ArrayList<Personnage> tireurs) {
		this.tireurs = tireurs;
	}

	@Override
	public int getStepNumber() {
		return stepNumber;
	}


	public void setluffyPosition(Position p) {
		sol = p;
	}

	@Override
	public void setStepNumber(int n) {
		stepNumber = n;
	}



	@Override
	public void setSolPosition(Position p) {
		sol = p;
	}

	@Override
	public Position getSolPosition() {
		return sol;
	}

	@Override
	public void setisrunningleft(boolean b) {
		isrunningleft = b;
	}

	public void setisrunningright(boolean b) {
		isrunningright = b;
	}

	@Override
	public boolean getisrunningleft() {
		return isrunningleft;
	}

	@Override
	public boolean getisrunningright() {
		return isrunningright;
	}

	@Override
	public ArrayList<Position> getFond() {
		return this.fond;
	}

	@Override
	public Personnage getLuffy() {
		return luffy;
	}

	public Position getFondPosition() {
		// TODO Auto-generated method stub
		return Fond;
	}

	@Override
	public void setFondPosition(Position p) {
		Fond = p;
	}

	@Override
	public Position getFond2Position() {
		// TODO Auto-generated method stub
		return Fond2;
	}

	@Override
	public void setFond2Position(Position p) {
		Fond2 = p;
	}

	@Override
	public Position getFond3Position() {
		// TODO Auto-generated method stub
		return Fond3;
	}

	@Override
	public Position getFond4Position() {
		// TODO Auto-generated method stub
		return Fond4;
	}

	@Override
	public void setFond3Position(Position p) {
		Fond3 = p;
	}

	@Override
	public void setFond4Position(Position p) {
		Fond4 = p;
	}

	@Override
	public ArrayList<Personnage> getSoldats() {
		ArrayList<Personnage> retour = new ArrayList<>();
		retour.addAll(sabreurs);
		retour.addAll(tireurs);
		return retour;
	}

	public void DeathSoldat(Personnage p) {
		if (!tireurs.remove(p))
			sabreurs.remove(p);

	}

	@Override
	public void addSabreur(Personnage p) {
		sabreurs.add(p);
	}

	@Override
	public void addTireur(Personnage p) {
		tireurs.add(p);
	}

	@Override
	public ArrayList<Personnage> getTireurs() {
		return tireurs;
	}

	@Override
	public ArrayList<Personnage> getSabreurs() {
		return sabreurs;
	}

	@Override
	public void showHitbox() {
		if(showhitbox) {
			showhitbox=false;
		}
		else {
			showhitbox=true;
		}
		
	}

	@Override
	public boolean getShowHitbox() {
		return showhitbox;
	}

}
