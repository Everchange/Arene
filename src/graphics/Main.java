package graphics;


import java.io.BufferedReader;
import java.io.FileReader;

//import java.awt.Toolkit;

import character.ArenaCharacter;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;


public class Main extends Application {


	/*static double sizeW=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	static double sizeH=Toolkit.getDefaultToolkit().getScreenSize().getHeight();*/
	static double sizeW=800;
	static double sizeH=600;
	static String version="v unknowed";
	private static Stage stage; 
	static Scene[] scene=new Scene[4];
	static FieldScene fieldScene;
	static boolean dev=true;
	// even if the dev mode is not enable, we create a console
	public static final Console console=new Console();
	static boolean escapeOn=false;
	static boolean fullScreen=false;

	//the first one should be the "escape key"
	private static KeyCode[] controlsCodes={KeyCode.ESCAPE,KeyCode.F11};

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
		version=getVersion();
		if (dev){
			Main.version+="/dev-"+System.getProperty("user.name");
		}

		Main.stage=stage;


		fieldScene = new FieldScene();
		Main.scene[0]=new HomeScene();
		Main.scene[1]=fieldScene;
		Main.scene[2]=new CharacterCreationScene();
		Main.scene[3]=new OptionScene();

		//si l'application est en dev, on affiche la console

		if (dev){
			console.show();
		}
		console.println("Started Project Arena v"+version);

		//test pour un perso
		ArenaCharacter test=new ArenaCharacter(new int[]{10,10,10,10,10,10},4,3,"test","testpath",new int[] {50,50});
		ArenaCharacter testB=new ArenaCharacter(new int[]{10,10,10,10,10,10},4,3,"testbis","testpath",new int[] {300,50});

		((FieldScene) Main.scene[1]).addCharacterToField(test,test.getPosition());
		((FieldScene) Main.scene[1]).addCharacterToField(testB,testB.getPosition());

		// définit la largeur et la hauteur de la fenêtre
		// en pixels, le (0, 0) se situe en haut à gauche de la fenêtre
		stage.setWidth(sizeW);
		stage.setHeight(sizeH);
		//the stage is not resizable if it's a release
		if (!dev){
			stage.setResizable(false);
		}
		//ajout de la scene de base
		Main.setScene(0,false);
		// met un titre dans la fenêtre
		stage.setTitle("Project Arena "+version);
		//full screen mode
		stage.setFullScreen(Main.fullScreen);
		//stage.initStyle(StageStyle.UNDECORATED);



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

	public static Scene getScene(int k){
		return Main.scene[k];
	}

	public static double getH(){
		return Main.sizeH;
	}

	public static KeyCode getControlCode(int k){
		if (k<Main.controlsCodes.length){
			return Main.controlsCodes[k];
		}
		else{
			return Main.controlsCodes[0];
		}
	}

	public static int getControlCodeLength(){
		return Main.controlsCodes.length;
	}

	public static int setControlCode(int k, KeyCode x){
		int ret = 1;
		if (k<Main.controlsCodes.length){
			for (int l=0;l<Main.controlsCodes.length;l++){
				if (x==Main.controlsCodes[l]){
					ret=2;
				}
			}
			Main.controlsCodes[k]=x;
			Main.console.print("Key code "+k+" set to "+Main.controlsCodes[k].toString());
		}
		else{
			ret=1;
		}

		return ret;
	}
	/**
	 * reset the size of the window to the default size
	 */
	public static void resetSize(){
		Main.stage.setWidth(sizeW);
		Main.stage.setHeight(sizeH);
		Main.console.println("Stage size reseted");
	}
	
	public static Stage getStage(){
		return Main.stage;
	}
	/**
	 * the following method allows to synchronize the version number from README.md file
	 *
	 * @return versionNumber
	 */
	private static String getVersion(){
		//if enable to find the version number, here is the default value
		String v="v unknowed";
		//we prepare everything to read the file
		BufferedReader reader;
		FileReader fileReader ;
		try{
			//relative path
			fileReader =new FileReader("./README.md");
			reader =new BufferedReader(fileReader);

			//used to read the file
			String ligne;

			//we read the file
			while((ligne=reader.readLine())!=null){
				//will there is still something
				if (ligne.startsWith("**")){
					//if the line start match the one of the one of the line that contains the version number
					v=ligne.replace("**", "");
					//we remove the stars
				}
			}
		}catch(Exception e){
			Main.console.println("Unable to find the README.md file !");
		}
		return v;
	}


}