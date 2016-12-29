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
	
	static Menu menu=new Menu();
	private static boolean dev=true;
	public static String version="0.2"; 
	
	public static void main(String[] args) {
        launch(args);
    }
	
	
	
	public static boolean escapeOn=false;
	
	
	@Override
	public void start(Stage stage) throws Exception {
		
		//si l'application est en dev, on affiche la console
		Console console=new Console();
		
		
		
		// définit la largeur et la hauteur de la fenêtre
        // en pixels, le (0, 0) se situe en haut à gauche de la fenêtre
        stage.setWidth(800);
        stage.setHeight(600);
        // met un titre dans la fenêtre
        stage.setTitle("Project Arena");

        // la racine du sceneGraph est le root
        Group root = new Group();
        
        Canvas canvas =new Canvas(735,600);
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
                    return;
                }
        });
        
        // ouvrir le rideau
        stage.show();

	}

}
