package graphics;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import ressources.Beacon;

public class FieldScene extends Scene {
	private static Group root=new Group();
	private boolean escapeOn=false;
	private static Menu menu=new Menu();
	private static Group characterGp=new Group();
	
	
	/**
	 * Creation of the scene where the combat takes place
	 */
	public FieldScene(){
		
		super(root);
		
		Canvas canvas =new Canvas(735,600);
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        
        
     // load the image for the background
        Image bg =new Image(Beacon.class.getResourceAsStream("field.png"));
        System.out.println();
        
        this.root.getChildren().add(canvas);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(bg, 0, 0);
        
        
        Image test =new Image(Beacon.class.getResourceAsStream("defaultCharacter.png"));
        ImageView etsimg =new ImageView(test);
        etsimg.relocate(50, 50);
        etsimg.setOnMouseEntered(evt -> {System.out.println("ok");});
        
        //characterGp.getChildren().addAll(etsimg);
        this.root.getChildren().add(characterGp);
        characterGp.toFront();
        
        //set background
        this.setFill(Color.GREY);
        
        
        //key events 
        this.setOnKeyPressed(
        	new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    if(e.getCode()==KeyCode.ESCAPE && !FieldScene.this.escapeOn){
                    	//if menu off and escape pressed
                        FieldScene.this.root.getChildren().add(FieldScene.this.menu.getMenuGroup());
                        FieldScene.this.escapeOn=true;
                    }
                    else if(e.getCode()==KeyCode.ESCAPE && FieldScene.this.escapeOn){
                    	// if escape pressed and menu on
                        FieldScene.this.root.getChildren().remove(FieldScene.this.menu.getMenuGroup());
                        FieldScene.this.escapeOn=false;
                    	
                    }
                    if(e.getCode()==KeyCode.F11){
                    	// console
                    	if (!Main.dev){
                    		Main.console.show();
                    	}
                    	Main.console.toFront();
                    }
                    
                }
        });
        
        canvas.toBack();
        //just to be sure that canvas is the back ground
		
	}
	
	
	/**
	 * Removes the escape menu node of the scene
	 */
	public void closeMenu(){
		this.root.getChildren().remove(FieldScene.this.menu.getMenuGroup());
        this.escapeOn=false;
	}
}
