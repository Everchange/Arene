package graphics;
import java.util.ArrayList;
import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Menu {
	
	protected Group menuGroup=new Group();
	private static OptionStage optSta=new OptionStage();
	public static int buttonWidth=100;
	
	public Menu(){
		
		int mainWindowCenterH=(int)Main.sizeH/2;
		
		System.out.println("Main.sizeW = "+Main.sizeW);
		
		this.menuGroup.resizeRelocate(0,0,Main.sizeW,Main.sizeH);
		
		 Rectangle panel = new Rectangle(0,0,Main.sizeW,Main.sizeH);//Main.sizeH);
	     panel.setFill(Color.rgb(0,0,0,0.7));
	     this.menuGroup.getChildren().add(panel);   
		
		
		//(Rage)Quit button
        Button bQuit = new Button();
        bQuit.setText("Quit");
        bQuit.setPrefWidth(buttonWidth);
        // ajout des coordonnées pour que le bouton soit bien placé
        //NB: les coordonée sont fonction du groupe menuGroup
        
        
        
        bQuit.relocate((int)(Main.sizeW/2-buttonWidth/2), mainWindowCenterH-100);
        //defines the action when the button is pressed
        bQuit.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        this.menuGroup.getChildren().add(bQuit);
        
        
      //Option button
        Button bOption = new Button();
        bOption.setText("Option");
        bOption.setPrefWidth(buttonWidth);
        // ajout des coordonnées pour que le bouton soit en dessous du premier
        bOption.relocate((int)(Main.sizeW/2-(buttonWidth/2)), mainWindowCenterH-60);
        //defines the action when the button is pressed
        bOption.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                 Menu.optSta.show();
            }
        });
        
        this.menuGroup.getChildren().add(bOption);
        
        
	}
	
	public Group getMenuGroup(){
		return this.menuGroup;
	}
	
}
