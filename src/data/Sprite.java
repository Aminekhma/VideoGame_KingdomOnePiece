package data;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
public class Sprite {
	/********************************************************************************/
	/*                               Variable d'instance                            */     
	/********************************************************************************/
	private ArrayList<Frame> frame;					//pouvoir les manipuler
	private  int count=0,nbframe;								//je l'ai mis ici par soucis de prote de variable
	private ImageView courant;
	private Boolean loop;
	private String path;

	/********************************************************************************/
	/*                                 Constructeur                                 */     
	/********************************************************************************/
	public Sprite(String directory,Boolean loop) {	
		frame=new ArrayList<Frame>();
		String[] parts = directory.split(":");
		path=directory;
		String part1 = parts[0]; //file:
		String part2 = parts[1]; // path
		File file= new File(part2);
		nbframe=file.list().length;
		this.loop=loop;
		for(int i=0;i<nbframe;i++) {
			Image add=new Image(directory+"/"+i+".png");
			frame.add(new Frame(add));
		}
		courant=new ImageView();
	}
	public Sprite(String directory) {	
		this(directory, true);
	}
	

	/********************************************************************************/
	/*                                    Methode                                   */     
	/********************************************************************************/
	public ImageView getImageView() {
		return courant;
	}
	public void updateAnimation() {
		courant.setImage(frame.get((count++)%nbframe).getImage());
	}
	public String getPath(){
		return path;
	}
	
	public Polygon getPolynome() {
		return frame.get((count)%nbframe).getEnvloppeConvexe();
	}
	public ArrayList<Frame> getFrame() {
		return frame;
	}
	
	
	
	
}
