package graphics;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// d�finit la largeur et la hauteur de la fen�tre
        // en pixels, le (0, 0) se situe en haut � gauche de la fen�tre
        stage.setWidth(800);
        stage.setHeight(600);
        // met un titre dans la fen�tre
        stage.setTitle("Truc");

        // la racine du sceneGraph est le root
        Group root = new Group();
        
        // load the image for the background
        Image image = new Image("file:///C:/Users/Merlin/workspace/Arene/src/field.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        // add the image to the root
        root.getChildren().add(imageView);
        
        //(Rage)Quit button
        Button btn = new Button();
        btn.setText("Quit");
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
