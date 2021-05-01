/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: alpha/Main.java 2015-03-11 buixuan.
 * ******************************************************/
package alpha;

import tools.HardCodedParameters;
import tools.User;
import specifications.DataService;
import specifications.EngineService;
import specifications.ViewerService;
import specifications.AlgorithmService;

import data.Data;
import data.Sprite;
import engine.Engine;
import userInterface.Viewer;
//import algorithm.RandomWalker;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Main extends Application{
  //---HARD-CODED-PARAMETERS---//
  private static String fileName = HardCodedParameters.defaultParamFileName;

  //---VARIABLES---//
  private static DataService data;
  private static EngineService engine;
  private static ViewerService viewer;
  private static AnimationTimer timer;

  //---EXECUTABLE---//
  public static void main(String[] args) {
    //readArguments(args);

    data = new Data();
    engine = new Engine();
    viewer = new Viewer();

    ((Engine)engine).bindDataService(data);
    ((Viewer)viewer).bindReadService(data);

    data.init();
    engine.init();
    viewer.init();
    
    launch(args);
  }

  public void start(Stage stage) {
	  Image icone=new Image("file:src/images/icon.png");			//icone du jeu
	  stage.getIcons().add(icone);									//application de l'icone
	  stage.setTitle("Kingdom");
	  stage.setResizable(false);									//empeche le redimentionnement
	  final Scene scene = new Scene(((Viewer)viewer).getPanel());	//
      //scene.setFill(Color.DEEPSKYBLUE);							//couleur de fond
      scene.setOnKeyPressed((event) ->{								//on ecoute sur notre scene utilisdation du lambda expression
    	  if(event.getCode()==KeyCode.LEFT) {
    		  engine.setLuffyCommand(User.COMMAND.LEFT);
    		  data.setisrunningleft(true);
    	  }
    	  else if(event.getCode()==KeyCode.RIGHT) {
    		  engine.setLuffyCommand(User.COMMAND.RIGHT);
    		  data.setisrunningright(true);    		  
    	  }
    	  else if(event.getCode()==KeyCode.UP) {
    		  engine.setLuffyCommand(User.COMMAND.UP);
		  	  data.getLuffy().setAttak(true);
    	  }
    	  else if(event.getCode()==KeyCode.DOWN) {
    		  engine.setLuffyCommand(User.COMMAND.DOWN);  
    	  }
    	  else if(event.getCode()==KeyCode.ESCAPE) {
    		  stage.close(); 
    		  engine.stop();
    	  }
    	  else if(event.getCode()==KeyCode.SPACE) {
    		  data.showHitbox();
    	  }
		  event.consume();
	       }
    );
      scene.setOnKeyReleased((event) ->{								//on ecoute sur notre scene utilisdation du lambda expression
		  switch (event.getCode()) {
		  	case LEFT:
				engine.releaseLuffyCommand(User.COMMAND.LEFT);
	        	 data.setisrunningleft(false);
	      	    break;
		  	case RIGHT:
		  		engine.releaseLuffyCommand(User.COMMAND.RIGHT);
	        	 data.setisrunningright(false);

		  		break;
		  	case UP:
		  		engine.releaseLuffyCommand(User.COMMAND.UP);	
		  		data.getLuffy().setAttak(false);
		  		break;
		  	case DOWN:
		  		engine.releaseLuffyCommand(User.COMMAND.DOWN);
		  		break;
		  default:
			break;}
		  event.consume();
	        }
    ); 
    stage.setScene(scene);
    stage.setWidth(HardCodedParameters.defaultWidth);
    stage.setHeight(HardCodedParameters.defaultHeight);
    stage.setOnShown(new EventHandler<WindowEvent>() {
      @Override public void handle(WindowEvent event) {
        engine.start();
      }
    });
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override public void handle(WindowEvent event) {
        engine.stop();
      }
    });
    stage.show();
    timer = new AnimationTimer() {
        @Override public void handle(long l) {
          scene.setRoot(((Viewer)viewer).getPanel());// MISE AJOUR DU PANEL
        }
      };
      timer.start();
  }

  //---ARGUMENTS---//
  private static void readArguments(String[] args){
    if (args.length>0 && args[0].charAt(0)!='-'){
      System.err.println("Syntax error: use option -h for help.");
      return;
    }
    for (int i=0;i<args.length;i++){
      if (args[i].charAt(0)=='-'){
	if (args[i+1].charAt(0)=='-'){
	  System.err.println("Option "+args[i]+" expects an argument but received none.");
	  return;
	}
	switch (args[i]){
	  case "-inFile":
	    fileName=args[i+1];
	    break;
	  case "-h":
	    System.out.println("Options:");
	    System.out.println(" -inFile FILENAME: (UNUSED AT THE MOMENT) set file name for input parameters. Default name is"+HardCodedParameters.defaultParamFileName+".");
	    break;
	  default:
	    System.err.println("Unknown option "+args[i]+".");
	    return;
	}
	i++;
      }
    }
  }
}
