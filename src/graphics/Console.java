package graphics;


import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class Console extends Stage {
	
	private static int[] size={800,500};
	private TextFlow output=new TextFlow();
	private TextField entry=new TextField();
	private static final String[] command={"quit","clear","scene <scene number>"};
	private static final String[] commandLegend={"quit","clear","Set the scene number <scene number>"};
	private ScrollPane outputScroll=new ScrollPane(output);
	
	/*I use the ScrollPane as the text area cannot contain colored text
	 * So I used this ScrollPane with a TextFlow
	*/
	
	
	
	public Console(){
		
		Group root = new Group();
		
		Text heading =new Text("Project arena (V"+Main.version+")");
		
		heading.relocate((int)(size[0]/2-heading.getBoundsInLocal().getWidth()/2), 10);
		
		root.getChildren().add(heading);
		
		
		
		
		//we relocate the outputScroll
		this.outputScroll.relocate(0, 40);
		this.outputScroll.setPrefSize(size[0]-16,size[1]-110);
		
		//we resize the output textFlow
		this.output.setPrefSize(size[0]-33,size[1]-110);
		this.outputScroll.setFocusTraversable(false);
		//when F11 pressed we move the window to the back
		this.outputScroll.setOnKeyPressed(
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
		
		root.getChildren().add(outputScroll);
		
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
	                    	//we use the discriminant for the switch
	                    	String discriminant="none";
	                    	//we set the value of the discriminant
	                    	if (enteredCommand.startsWith("help")){
	                    		discriminant="help";
	                    	}
	                    	if(enteredCommand.startsWith("scene")){
	                    		try{
	                    			enteredCommand.substring(0, 6);
	                    			//we take into account only the first number
	                    		}
	                    		catch(StringIndexOutOfBoundsException exception){}
                    			finally{}
	                    		if (enteredCommand.length()==7){
	                    			//if the command is correct
	                    			discriminant="scene";
	                    		}
	                    		else{
	                    			//else we print that the syntax is incorrect
	                    			Console.this.println("Invalid syntax : "+Console.command[2],Color.RED);
	                    			discriminant="false";
	                    		}
	                    	}
	                    	if(enteredCommand.contentEquals("clear") || enteredCommand.contentEquals("quit")){
	                    		discriminant=enteredCommand;
	                    	}
	                    	
	                    	//switch that execute the command
	                    	switch(discriminant){
	                    	case("false"):
	                    		//we print nothing
	                    		break;
	                    	case("clear"):
	                    		//we clear the TextAea
	                    		Console.this.getOutput().getChildren().clear();
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
	                    	case("quit"):
	                    		System.exit(0);
	                    		break;
	                    	case("scene"):
	                    		int num=99; 
	                    		char numC='A';
	                    		//we try to get the probable scene number
	                    		numC=enteredCommand.charAt(6);
	                    		// we try to cast the probable scene number 
	                    		try{
	                    			num=Character.getNumericValue(numC);
	                    		}catch(ClassCastException exception){
	                    			// if the char is not a number
	                    			Console.this.println("Invalid scene number");
	                    		}
	                    		if (num<Main.scene.length){
	                    			Main.setScene(num,true);
	                    		}
	                    		else{
	                    			Console.this.println("Scene number to hight, must be between 0 and "+(Main.scene.length-1));
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
	
	public void print(String txt,Paint color){
		Text txtT=new Text(txt);
		txtT.setFill(color);
		if(this.output.getChildren().size()<1){
			this.output.getChildren().add(txtT);
			return;
		}
		Node end=this.output.getChildren().get(this.output.getChildren().size()-1);
		this.output.getChildren().remove(end);
		end.setAccessibleText(end.getAccessibleText()+txtT);
		//auto scroll
		this.outputScroll.setVvalue(1.0);
	}
	
	public void println(String txt,Paint color){
		Text txtT=new Text(txt+"\n");
		txtT.setFill(color);
		this.output.getChildren().add(txtT);
		//auto scroll
		this.outputScroll.setVvalue(1.0);
	}
	
	public void print(String txt){
		if(this.output.getChildren().size()<1){
			this.output.getChildren().add(new Text(txt));
			//auto scroll
			this.outputScroll.setVvalue(1.0);
			
			return;
		}
		Node end=this.output.getChildren().get(this.output.getChildren().size()-1);
		this.output.getChildren().remove(end);
		end.setAccessibleText(end.getAccessibleText()+txt);
		//auto scroll
		this.outputScroll.setVvalue(1.0);
	}
	
	public void println(String txt){
		this.output.getChildren().add(new Text(txt+"\n"));
		//auto scroll
		this.outputScroll.setVvalue(1.0);
	}
	
	public TextFlow getOutput(){
		return this.output;
	}
	
	public TextField getEntry(){
		return this.entry;
	}

}