package graphics;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.image.*;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
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
        
        
        
        // add the image to the root
        //root.getChildren().add(imageView);
        
        //(Rage)Quit button
        Button btn = new Button();
        btn.setText("Quit");
        // ajout des coordonn�es pour que le bouton ne soit pas sur l'image
        btn.setLayoutX(735);
        btn.setLayoutY(10);
        //defines the action when the button is pressed
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);;
            }
        });
        
        root.getChildren().add(btn);
        
        //set the scene
        Scene scene = new Scene(root);
        scene.setFill(Color.GREY);
        stage.setScene(scene);
        // ouvrir le rideau
        stage.show();

	}

}
