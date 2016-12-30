package graphics;


import javafx.application.*;
import javafx.stage.*;

import javafx.scene.*;

import javafx.event.EventHandler;


public class Main extends Application {


	static final int sizeW=800;
	static int sizeH=600;
	static String version="0.2.1";
	static Stage stage; 
	static Scene[] scene=new Scene[4];
	static boolean dev=true;
	static Console console=new Console();





	public static void main(String[] args) {
		launch(args);
		

	}



	public static boolean escapeOn=false;

	@Override
	public void start(Stage stage) throws Exception {
		
		//version number
		if (dev){
			Main.version+="/dev-"+System.getProperty("user.name");
		}
		
		Main.stage=stage;
		Main.scene[1]=new FieldScene().getScene();
		//si l'application est en dev, on affiche la console
		if (dev){
			console.show();
		}
		console.print("Started Project Arena v"+version);

		//console.print(.class.getName().replace(".", "/") + ".java");

		// définit la largeur et la hauteur de la fenêtre
		// en pixels, le (0, 0) se situe en haut à gauche de la fenêtre
		stage.setWidth(sizeW);
		stage.setHeight(sizeH);
		//ajout de la scene de base
		stage.setScene(Main.scene[1]);
		// met un titre dans la fenêtre
		stage.setTitle("Project Arena");

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

}