package graphics;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Console extends Stage {
	
	private static int[] size={800,500};
	private TextArea output=new TextArea();
	private TextField entry=new TextField();
	private static final String[] command={"quit","clear"};
	
	public Console(){
		
		Group root = new Group();
		
		Text heading =new Text("Project arena (V"+Main.version+")");
		
		heading.relocate((int)(size[0]/2-heading.getBoundsInLocal().getWidth()/2), 10);
		
		root.getChildren().add(heading);
		
		
		this.output.setEditable(false);
		this.output.relocate(0, 40);
		
		this.output.setPrefSize(size[0]-16,size[1]-110);
		//border style
		this.output.setStyle("-fx-border-color: black; -fx-border-width: 0; "
                + "-fx-border-radius: 0; -fx-focus-color: transparent");
		this.output.setBorder(null);
		this.output.setFocusTraversable(false);
		//when F11 pressed we move the window to the back
		this.output.setOnKeyPressed(
	        	new EventHandler<KeyEvent>()
	            {
	                @Override
					public void handle(KeyEvent e)
	                {
	                	
		                    if(e.getCode()==KeyCode.F11){
		                    	Main.console.toBack();
		                    	Main.stage.toFront();
		                    }
		                    else{
		                    	//if the user try to type something
		                    	Console.this.entry.requestFocus();
		                    }
	                }
	            });
		
		root.getChildren().add(output);
		
		//command field and execution
		entry.requestFocus();
		entry.relocate(0, size[1]-67);
		entry.setPrefWidth(size[0]-16);
		//when key pressed
		entry.setOnKeyPressed(
	        	new EventHandler<KeyEvent>()
	            {
	                @Override
					public void handle(KeyEvent e)
	                {
	                	//when F11 pressed we move the window to the back
		                    if(e.getCode()==KeyCode.F11){
		                    	Main.console.toBack();
		                    	Main.stage.toFront();
		                    }
		                
		                  //when the ehter key is pressed we commit the command
	                    if(e.getCode()==KeyCode.ENTER){
	                    	String enteredCommand=Console.this.getEntry().getText().toString().toLowerCase();
	                    	switch(enteredCommand){
	                    	case("quit"):
	                    		System.exit(0);
	                    		break;
	                    	case("clear"):
	                    		Console.this.getOutput().clear();
	                    		break;
	                    	case("help"):
	                    		switch(enteredCommand){
	                    		
	                    		default:
	                    			//by default we print all the commands available
	                    			Console.this.println("Commands available :\n");
	                    			for (int k=0 ; k<Console.command.length;k++){
	                    				System.out.println(k);
	                    				Console.this.print(Console.command[k]);
	                    				if (k%7==0 && k>0){
	                    					Console.this.println("");
	                    				}
	                    				else if(k<Console.command.length-1){
	                    					Console.this.print(", ");
	                    				}
	                    			}
	                    		}
	                    		break;
	                    	default:
	                    		Console.this.println("Unknow command");
	                    	}
	                    	Console.this.getEntry().clear();
	                    }
	                    return;
	                }
	        });
		
		
		root.getChildren().add(this.entry);
		
		Scene optionScene =new Scene(root);
		
		
		
		optionScene.setFill(Color.WHITE);
		this.setScene(optionScene);
		this.setHeight(size[1]);
		this.setWidth(size[0]);
		
	}
	
	public void print(String txt){
		this.output.appendText(txt);
	}
	
	public void println(String txt){
		this.output.appendText("\n"+txt);
	}
	
	public TextArea getOutput(){
		return this.output;
	}
	
	public TextField getEntry(){
		return this.entry;
	}

}