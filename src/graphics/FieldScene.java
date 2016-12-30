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

public class FieldScene {
	private Scene scene;
	private Group root=new Group();
	private boolean escapeOn=false;
	private static Menu menu=new Menu();
	
	public FieldScene(){
		
		
		Canvas canvas =new Canvas(735,600);
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        
     // load the image for the background
        Image bg = new Image("field.png");
        
        this.root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(bg, 0, 0);
        
        
        //set the scene
        this.scene = new Scene(this.root);
        scene.setFill(Color.GREY);
        
        
        //key events (menu)

        scene.setOnKeyPressed(
        	new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                	System.out.println(e.getCode());
                    if(e.getCode()==KeyCode.ESCAPE && !FieldScene.this.escapeOn){
                    	System.out.println("menu on");
                        FieldScene.this.root.getChildren().add(FieldScene.this.menu.getMenuGroup());
                        FieldScene.this.escapeOn=true;
                    }
                    else if(e.getCode()==KeyCode.ESCAPE && FieldScene.this.escapeOn){
                    	System.out.println("menu off");
                        FieldScene.this.root.getChildren().remove(FieldScene.this.menu.getMenuGroup());
                        FieldScene.this.escapeOn=false;
                    	
                    }
                    if(e.getCode()==KeyCode.F11){
                    	if (!Main.dev){
                    		Main.console.show();
                    	}
                    	Main.console.toFront();
                    }
                    
                }
        });
		
	}
	
	
	public Scene getScene(){
		return this.scene;
	}
}
