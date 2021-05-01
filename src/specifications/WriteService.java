/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/WriteService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import tools.Position;

import java.util.ArrayList;

import data.Personnage;

public interface WriteService {
	public void setSolPosition(Position p);

	public void setisrunningleft(boolean b);

	public void setisrunningright(boolean b);

	public void setStepNumber(int n);

	public void setFondPosition(Position p);

	public void setFond2Position(Position p);

	public void setFond3Position(Position p);

	public void setFond4Position(Position p);

	public void DeathSoldat(Personnage p);

	public void addSabreur(Personnage p);

	public void addTireur(Personnage p);

	public void setTireurs(ArrayList<Personnage> tireurs);

	public void setSabreurs(ArrayList<Personnage> sabreurs);

}
