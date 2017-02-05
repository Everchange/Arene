package graphics;

//import java.awt.Toolkit;

import character.ArenaCharacter;
import javafx.application.*;
import javafx.stage.*;
import ressources.Config;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;


public class Main extends Application {


	/*static double sizeW=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	static double sizeH=Toolkit.getDefaultToolkit().getScreenSize().getHeight();*/
	private static Stage stage; 
	static Scene[] scene=new Scene[4];
	static FieldScene fieldScene;
	// even if the dev mode is not enable, we create a console
	public static final Console console=new Console();
	static boolean escapeOn=false;
	

	/**
	 * Just use to launch the application
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
		
	}


	/**
	 * Creation of the main stage
	 * 
	 * @param stage a new Stage not initialized
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		Config.set();
		//version number
		if (Config.inDev){
			Config.updateVersion("-"+System.getProperty("user.name"));
		}
		

		Main.stage=stage;
		

		fieldScene = new FieldScene();
		Main.scene[0]=new HomeScene();
		Main.scene[1]=fieldScene;
		Main.scene[2]=new CharacterCreationScene();
		Main.scene[3]=new OptionScene();

		//si l'application est en dev, on affiche la console

		if (Config.inDev){
			console.show();
		}

		//test pour un perso
		ArenaCharacter test=new ArenaCharacter(new int[]{10,10,10,10,10,10},4,3,"test","testpath",new int[] {50,50});
		ArenaCharacter testB=new ArenaCharacter(new int[]{10,10,10,10,10,10},4,3,"testbis","testpath",new int[] {300,50});

		((FieldScene) Main.scene[1]).addCharacterToField(test,test.getPosition());
		((FieldScene) Main.scene[1]).addCharacterToField(testB,testB.getPosition());

		// définit la largeur et la hauteur de la fenêtre
		// en pixels, le (0, 0) se situe en haut à gauche de la fenêtre
		stage.setWidth(Config.getSizeW());
		stage.setHeight(Config.getSizeH());
		
		//the stage is not resizable if it's a release
		if (!Config.inDev){
			stage.setResizable(false);
		}
		//ajout de la scene de base
		Main.setScene(0,false);
		// met un titre dans la fenêtre
		stage.setTitle("Project Arena "+Config.getVersion());
		//full screen mode
		stage.setFullScreen(Config.isFullScreen());
		//stage.initStyle(StageStyle.UNDECORATED);



		//action si la fenetre est ferm�e

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent ev) {
				Main.quit();
				// if the main window is closed, the application stops
			}
		});

		// ouvrir le rideau
		stage.show();
		
		//We inform the dev that the application is launched
		Main.console.println("Started Project Arena "+Config.getVersion());
		

	}
	/**
	 * change the scene of the main stage
	 * 
	 * @param k the number of the scene
	 * @param print if this change has to be printed in the console or not
	 */
	public static void setScene(int k,boolean print){
		if (k<Main.scene.length){
			Main.stage.setScene(Main.scene[k]);
			if (print){
				Main.console.println("Set scene number "+k);
			}
		}
		else {
			Main.console.println("Incorect scene number (get : "+k+", expected : between 0 and"
					+(Main.scene.length-1),Color.RED);
		}
	}

	public static Scene getScene(int k){
		return Main.scene[k];
	}

	public static double getH(){
		return Config.getSizeH();
	}

	
	/**
	 * reset the size of the window to the default size
	 */
	public static void resetSize(){
		Main.stage.setWidth(Config.getSizeW());
		Main.stage.setHeight(Config.getSizeH());
		Main.console.println("Stage size reseted");
	}

	public static Stage getStage(){
		return Main.stage;
	}
	
	public static void quit(){
		Config.save();
		System.exit(0);
	}

}