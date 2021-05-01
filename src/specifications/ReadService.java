/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/ReadService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import tools.Position;
import java.util.ArrayList;
import data.Personnage;

public interface ReadService {
  public Position getSolPosition();
  public boolean getisrunningleft();
  public boolean getisrunningright();
  public int getStepNumber();
  /**********************************************************************************/
  public Personnage getLuffy();
  public ArrayList<Personnage> getSoldats();
  public ArrayList<Personnage> getTireurs();
  public ArrayList<Personnage> getSabreurs();
  public ArrayList<Position> getFond();
  public Position getFond2Position();
  public Position getFond3Position();
  public Position getFond4Position();
  public Position getFondPosition();
  public void showHitbox();
  public boolean getShowHitbox();
}
