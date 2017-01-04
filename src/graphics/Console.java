package graphics;


import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import ressources.Beacon;

public class Console extends Stage {
	
	private static int[] size={800,500};
	private TextFlow output=new TextFlow();
	private TextField entry=new TextField();
	private static final String[] command={"quit","clear","scene","help"};
	// please note that the "help" name must always be at the end of this array !!!
	private static final String[] commandLegend={"Close the game","Clear the console (delete all the text (can't be undone))"
												,"change te scene of the main window (stage) to a specified number.\n Syntax : "
														+ "scene <number> or scene <name of the scene>",
														"display a help (could be use like this : help <command's first word>"};
	private ScrollPane outputScroll=new ScrollPane(output);
	
	/*I use the ScrollPane as the text area cannot contain colored text
	 * So I used this ScrollPane with a TextFlow
	*/
	
	
	
	public Console(){
		this.getIcons().add(new Image(Beacon.class.getResourceAsStream("console.png")));
		
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
	                    	if (enteredCommand.startsWith("export")){
	                    		discriminant="export";
	                    	}
	                    	if(enteredCommand.startsWith("scene")){
	                    		if (enteredCommand.length()>7){
	                    			if(enteredCommand.contains("option")){
	                    				enteredCommand="scene 3";
	                    				discriminant="scene";
	                    				
	                    			}
	                    			if(enteredCommand.contains("start")){
	                    				enteredCommand="scene 0";
	                    				discriminant="scene";
	                    			}
	                    			if(enteredCommand.contains("field")){
	                    				enteredCommand="scene 1";
	                    				discriminant="scene";
	                    			}
	                    			if(enteredCommand.contains("charcrea")){
	                    				enteredCommand="scene 2";
	                    				discriminant="scene";
	                    			}
	                    		}
	                    		if(discriminant.contentEquals("none")){
	                    			try{
	                    				enteredCommand=enteredCommand.substring(0, 7);
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
	                    				Console.this.println("Invalid syntax : "+Console.command[2]+
	                    						"\n Use : scene <number or name>",Color.RED);
	                    				discriminant="false";
	                    			}
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
	                    	case("export"):
	                    		
	                    		//Console.this.println("Console log exported",Color.GREEN);
	                    		break;
	                    	case("clear"):
	                    		//we clear the TextAea
	                    		Console.this.getOutput().getChildren().clear();
	                    		break;
	                    	
	                    	case("help"):
	                    		Console.this.println("---- Help ----");
	                    		boolean test=false;
	                    		for (int j=0 ; j<command.length ; j++){
	                    			if (enteredCommand.contains(command[j])&&enteredCommand.length()>4){
	                    				// >4 in provide a bug with "help help" command
	                    				test=true;
	                    				Console.this.println("Help on command \""+command[j]+"\" : "+commandLegend[j]);
	                    				Console.this.println("---- end of Help ----");
	                    				break;
	                    			}
	                    		}

	                    		//by default we print all the commands available
	                    		if (!test){
	                    			Console.this.println("Commands available :");
	                    			for (int k=0 ; k<Console.command.length;k++){
	                    				Console.this.println(Console.command[k]);
	                    			}
	                    			Console.this.println("---- end of Help ----");
	                    		}
	                    		break;
	                    	case("quit"):
	                    		if (Main.dev){
	                    			System.out.println("quit command from console");
	                    		}

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
	                    			Console.this.println("Invalid scene number",Color.RED);
	                    		}
	                    		if (num<Main.scene.length){
	                    			Main.setScene(num,true);
	                    		}
	                    		else{
	                    			Console.this.println("Scene number to hight, must be between 0 and "+(Main.scene.length-1),Color.RED);
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
		this.output.getChildren().add(txtT);
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
		this.output.getChildren().add(new Text(txt));
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
