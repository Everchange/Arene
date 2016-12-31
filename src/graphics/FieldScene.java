package graphics;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class FieldScene extends Scene {
	private static Group root=new Group();
	private boolean escapeOn=false;
	private static Menu menu=new Menu();
	
	public FieldScene(){
		
		super(root);
		
		Canvas canvas =new Canvas(735,600);
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        
     // load the image for the background
        Image bg = new Image("field.png");
        
        this.root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(bg, 0, 0);
        
        
        //set background
        this.setFill(Color.GREY);
        
        
        //key events 
        this.setOnKeyPressed(
        	new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    if(e.getCode()==KeyCode.ESCAPE && !FieldScene.this.escapeOn){
                    	//if menu off and escpe pressed
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
		
	}
	
	
	
	public void closeMenu(){
		this.root.getChildren().remove(FieldScene.this.menu.getMenuGroup());
        this.escapeOn=false;
	}
}
