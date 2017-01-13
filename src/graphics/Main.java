package graphics;


import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;


public class Main extends Application {


	static int sizeW=800;
	static int sizeH=600;
	static String version="0.3.1";
	static Stage stage; 
	static Scene[] scene=new Scene[4];
	static FieldScene fieldScene;
	static boolean dev=true;
	static Console console;
	static boolean escapeOn=false;
	static boolean fullScreen=false;

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
		//version number
		if (dev){
			Main.version+="/dev-"+System.getProperty("user.name");
		}
		
		Main.stage=stage;
		
		fieldScene = new FieldScene();
		Main.scene[0]=new HomeScene();
		Main.scene[1]=fieldScene;
		Main.scene[2]=new CharacterCreationScene();
		Main.scene[3]=new OptionScene();
		// even if the dev mode is not enable, we create a console
		Main.console=new Console();
		//si l'application est en dev, on affiche la console
		
		if (dev){
			console.show();
		}
		console.println("Started Project Arena v"+version);

		//console.print(.class.getName().replace(".", "/") + ".java");

		// définit la largeur et la hauteur de la fenêtre
		// en pixels, le (0, 0) se situe en haut à gauche de la fenêtre
		stage.setWidth(sizeW);
		stage.setHeight(sizeH);
		//the stage is not resizable for the moment.
		stage.setResizable(false);
		//ajout de la scene de base
		Main.setScene(0,false);
		// met un titre dans la fenêtre
		stage.setTitle("Project Arena "+version);
		//full screen mode
		stage.setFullScreen(Main.fullScreen);
		
		
		
		//action si la fenetre est ferm�e

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent ev) {
				System.exit(0);
				// if the main window is closed, the application stops
			}
		});

		// ouvrir le rideau
		stage.show();

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

}