package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
		this.output.setStyle("-fx-border-color: black; -fx-border-width: 0; "
                + "-fx-border-radius: 0; -fx-focus-color: transparent");
		this.output.setBorder(null);
		
		root.getChildren().add(output);
		
		//command field and execution
		entry.relocate(0, size[1]-67);
		entry.setPrefWidth(size[0]-16);
		//when the ehter key is pressed we commit the command
		entry.setOnKeyPressed(
	        	new EventHandler<KeyEvent>()
	            {
	                public void handle(KeyEvent e)
	                {
	                    if(e.getCode()==KeyCode.ENTER){
	                    	switch(Console.this.getEntry().getText().toString().toLowerCase()){
	                    	
	                    	case("quit"):
	                    		System.exit(0);
	                    		break;
	                    	case("clear"):
	                    		Console.this.getOutput().clear();
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