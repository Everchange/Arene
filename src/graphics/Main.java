package graphics;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class Main extends Application {
	
	
	public static int sizeW=800;
	public static int sizeH=600;
	public static String version="0.1.1.c";
	private static boolean dev=true;
	private static Menu menu=new Menu();
	private static Console console=new Console();
	private Group root = new Group();
	private static Stage stage; 
	
	
	
	
	public static void main(String[] args) {
        launch(args);
    }
	
	
	
	public static boolean escapeOn=false;
	
	@Override
	public void start(Stage stage) throws Exception {
		Main.stage=stage;
		//si l'application est en dev, on affiche la console
		if (dev){
			console.show();
		}
		console.print("hello");
		
		
		
		// définit la largeur et la hauteur de la fenêtre
        // en pixels, le (0, 0) se situe en haut à gauche de la fenêtre
        stage.setWidth(sizeW);
        stage.setHeight(sizeH);
        // met un titre dans la fenêtre
        stage.setTitle("Project Arena");

        // la racine du sceneGraph est le root
        
        
        Canvas canvas =new Canvas(735,600);
        for (int k=0; k<1000;k++){
        console.print("\ntest");}
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        
     // load the image for the background
        Image bg = new Image("field.png");
        
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(bg, 0, 0);
        
        
        //set the scene
        Scene scene = new Scene(root);
        scene.setFill(Color.GREY);
        stage.setScene(scene);
        
        
        //key events (menu)
        
        
        scene.setOnKeyPressed(
        	new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                	System.out.println(e.getCode());
                    if(e.getCode()==KeyCode.ESCAPE && !escapeOn){
                    	System.out.println("menu on");
                        root.getChildren().add(Main.menu.getMenuGroup());
                        Main.escapeOn=true;
                    }
                    else if(e.getCode()==KeyCode.ESCAPE && escapeOn){
                    	System.out.println("menu off");
                        root.getChildren().remove(Main.menu.getMenuGroup());
                        Main.escapeOn=false;
                    	
                    }
                    if(e.getCode()==KeyCode.F11){
                    	if (!dev){
                    		Main.getConsole().show();
                    	}
                    }
                    return;
                }
        });
        
        
        //action si la fenetre est ferm�e
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent ev) {
            	System.exit(0);
            	// if the main window is closed, the application stops
            }
        });
        
        // ouvrir le rideau
        stage.show();

	}
	
	
	public static Console getConsole(){
		return Main.console;
	}
	
	

}
