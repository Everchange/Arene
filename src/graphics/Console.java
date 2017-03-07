package graphics;

import java.util.ArrayList;
import java.util.Date;

import InputOutputFile.ExportText;
import character.ArenaCharacter;
import javafx.event.EventHandler;
import javafx.scene.Group;
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
import ressources.Config;

public class Console extends Stage {

	private static int[] size={800,500};
	private TextFlow output=new TextFlow();
	private TextField entry=new TextField();
	private static final String[] command={"clear","export","lang","quit","reset_size","scene","target","help"};
	// please note that the "help" name must always be at the end of this array !!!
	private static final String[] commandLegend={"Clear the console (delete all the text (can't be undone))",
					"Export the content of console into a .txt file",
					"Change the language \n\tSyntax : lang <language's name written in this language>",
					"Close the game",
					"Reset the size of the window to the default width and lenght",
					"change the scene of the main window (stage) to a specified number.\n\tSyntax : "
							+ "scene <number> or scene <name of the scene>",
					"Target the character with the given name.\n\tSyntax :"
							+"target <character's name>",
	"display a help (could be use like this : help <command's first word>)"};
	private ScrollPane outputScroll=new ScrollPane(output);
	private int tab=0;
	private static ArrayList<String> history = new ArrayList<String>();
	private int historyIndex=-1;
	private String[] sceneName={"option","start","field","charcrea"};

	/*I use the ScrollPane as the text area cannot contain colored text
	 * So I used this ScrollPane with a TextFlow
	 */


	/**
	 * Creates a new console
	 * <\br><\br>
	 * Please note that can be only one console per game 
	 */
	public Console(){
		this.getIcons().add(new Image(Beacon.class.getResourceAsStream("console.png")));

		//we don't want the user to resize the window
		this.setResizable(false);

		//just to prevent the loss of the console
		this.setOnCloseRequest(evt->{
			Main.getStage().toFront();
			evt.consume();
		});

		Group root = new Group();

		Text heading =new Text("Log");

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

						if(e.getCode()==Config.getDevControlCode(0)){
							Main.console.toBack();
							Main.getStage().toFront();
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
						if(e.getCode()==Config.getDevControlCode(0)){
							Main.console.toBack();
							Main.getStage().toFront();
						}

						//auto completion
						if(e.getCode()==KeyCode.TAB){
							e.consume();
							boolean stop=false;
							boolean autoC=false;
							
							if (!entry.getText().isEmpty() &&  !entry.getText().contains(" ")){
								// autocompletion
								for (int k = 0; k<command.length ; k++){
									if (command[k].contains(entry.getText()) && !command[k].equals(entry.getText().split(" ")[0])){
										entry.setText(command[k]);
										//if it's a success we don't want to overwrite the autocompletion
										autoC=true;
									}
								}
							}
							
							//if there is nothing or we are already cycling, we print all the command one after each
							if ((entry.getText().isEmpty() || entry.getText().charAt(entry.getText().length()-1)!=' ') &&  !entry.getText().contains(" ") && !autoC){
								// empty or no space in the command
								entry.setText(command[tab%command.length]);
								// we don't want to execute all the actions
								stop=true;
								
							}
							
							if (autoC){
								stop =true;
							}
							
							if (!entry.getText().isEmpty() &&  !entry.getText().contains(" ")){
								// autocompletion
								for (int k = 0; k<command.length ; k++){
									if (command[k].contains(entry.getText())){
										entry.setText(command[k]);
									}
								}
								
							}

							//we count the number of time the tab key have been pressed


							if (entry.getText().startsWith("target") && !stop){
								//target autocompletion
								if (((FieldScene)Main.getScene(1)).getNumberCharOnField()>0){
									entry.setText("target "+((FieldScene)Main.getScene(1)).getCharName(tab%((FieldScene)Main.getScene(1)).getNumberCharOnField()));
								}
								
							}
							
							if (entry.getText().startsWith("help") && !stop){
								//help autocompletion
								entry.setText("help "+command[tab%command.length]);
							}
							
							if (entry.getText().startsWith("scene") && !stop){
								//scene autocompletion
								entry.setText("scene "+sceneName[tab%4]);
							}


							tab++;

						}else{
							//reset
							tab=0;
							
						}

						//clear text field
						if (e.getCode()==KeyCode.DELETE){
							entry.clear();
						}

						//boolean to prevent loop when scrolling back or forward through commands
						boolean his =false;


						//get the last commands
						if (e.getCode()==KeyCode.UP){
							//if history index was null or from the down key
							if (historyIndex==-1 || historyIndex==-3){
								historyIndex=history.size()-1;
							}
							//if history index too hight
							if (historyIndex>=history.size()){
								historyIndex=history.size()-1;
							}
							//if ok
							if(historyIndex>-1){
								entry.setText(history.get(historyIndex));
								historyIndex--;
							}
							//if below 0 => null
							if (historyIndex==-1){
								historyIndex=-2;
							}
							his=true;
						}

						//scroll down to the latest command
						if (e.getCode()==KeyCode.DOWN && !his){
							if (historyIndex==-1 || historyIndex==-2){
								historyIndex=0;
							}
							if (historyIndex==history.size()){
								historyIndex=-3;
								entry.clear();
							}
							if(historyIndex<history.size() && historyIndex>-1){
								entry.setText(history.get(historyIndex));
								historyIndex++;
							}
							
							his=true;
						}
						
						//we set the cursor to the end of the line 
						entry.positionCaret(entry.getText().length());

						//when the enter key is pressed we commit the command
						if(e.getCode()==KeyCode.ENTER){
							boolean sucess=false;
							String enteredCommand=Console.this.getEntry().getText().toString().toLowerCase();
							//we use the discriminant for the switch
							String discriminant="false";
							//we set the value of the discriminant
							if (enteredCommand.startsWith("help")){
								discriminant="help";
							}
							if (enteredCommand.startsWith("target")){
								discriminant="target";
							}
							if (enteredCommand.startsWith("lang")){
								discriminant="lang";
							}
							if(enteredCommand.startsWith("scene")){
								if (enteredCommand.length()>7){
									if(enteredCommand.contains(sceneName[0])){
										enteredCommand="scene 3";
										discriminant="scene";

									}
									if(enteredCommand.contains(sceneName[1])){
										enteredCommand="scene 0";
										discriminant="scene";
									}
									if(enteredCommand.contains(sceneName[2])){
										enteredCommand="scene 1";
										discriminant="scene";
									}
									if(enteredCommand.contains(sceneName[3])){
										enteredCommand="scene 2";
										discriminant="scene";
									}
								}
								if(discriminant.contentEquals("false")){
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
							if(enteredCommand.contentEquals("clear") 
									|| enteredCommand.contentEquals("quit") 
									|| enteredCommand.contentEquals("reset_size")
									|| enteredCommand.contentEquals("export")){
								
								discriminant=enteredCommand;
								
							}

							//switch that execute the command
							switch(discriminant){
							case("false"):
								//the command doesn't match any known command
								Console.this.println("Unknow command",Color.RED);
							break;
							case("reset_size"):
								//we resize the Main stage
								Main.resetSize();
							sucess=true;
							break;
							
							case("export"):
								//export the console to a file 
								//the number is the  number of milliseconds since January 1, 1970, 00:00:00 GMT.
								if (new ExportText("Log "+Long.valueOf(new Date().getTime()).toString(),Console.this.output).getSuccess());{
									Console.this.println("Console log exported",Color.GREEN);
									sucess=true;}
								break;
								
							case("clear"):
								//we clear the TextAea
								Console.this.getOutput().getChildren().clear();
								sucess=true;
								break;

							case("help"):
								
								Console.this.println("---- Help ----");
							boolean test=false;
							for (int j=0 ; j<command.length ; j++){
								if (enteredCommand.contains(command[j])&&enteredCommand.length()>5){
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
								Console.this.println("type \"help <command's name>\" for further informations  ");
								Console.this.println("---- end of Help ----");
							}
							sucess=true;
							break;
							
							case("quit"):
								if (Config.inDev){
									System.out.println("quit command from console");
								}
							Main.quit();
							sucess=true;
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
							
							Main.setScene(num,true);
							

							sucess=true;
							break;
							
							case("target"):
								
								try{
									
									int index=((FieldScene)Main.getScene(1)).targetChar(enteredCommand.split(" ")[1]);
									
									Main.console.println("Index of the targeted character : "
											+index);
								}catch(Exception ex){
									ex.printStackTrace();
								}
							sucess=true;
							break;
							
							case("lang"):
								if (enteredCommand.contains(" ")){
								if(Config.updateLang(enteredCommand.split(" ")[1])){
									sucess=true;
								};
								}
								break;
							
							default:
								break;
							}
							if (sucess){
								history.add(enteredCommand);
							}
							Console.this.getEntry().clear();
						}


						if (!his){
							//if we've not used the up or down arrow, we reset the history count
							historyIndex=-1;
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

	/**
	 * Prints in the console a colored text. It will not add a line break at the end of the text printed
	 * 
	 * @param txt the text to print
	 * @param color the color of the text. This should be a color from the javafx.scene.paint.Color class
	 */
	public void print(Object o,Paint color){
		Text txtT=new Text(o.toString());
		txtT.setFill(color);
		this.output.getChildren().add(txtT);
		//auto scroll
		this.outputScroll.setVvalue(1.0);
	}
	/**
	 * 
	 * Prints a colored text inside the console and jump to the next line of it.
	 * 
	 * @param txt the text to print
	 * @param color the color of the text. This should be a color from the javafx.scene.paint.Color class
	 */
	public void println(Object o,Paint color){
		Text txtT=new Text(o.toString()+"\n");
		txtT.setFill(color);
		this.output.getChildren().add(txtT);
		//auto scroll
		this.outputScroll.setVvalue(1.0);
	}
	/**
	 * Prints a black text inside the console without jumping to the next line
	 * 
	 * @param txt the text to print
	 */
	public void print(Object o){
		this.output.getChildren().add(new Text(o.toString()));
		//auto scroll
		this.outputScroll.setVvalue(1.0);
	}
	/**
	 * Prints a black text inside the console and jump to the next line
	 * 
	 * @param txt the text to print
	 */
	public void println(Object o){
		this.output.getChildren().add(new Text(o.toString()+"\n"));
		//auto scroll
		this.outputScroll.setVvalue(1.0);
	}

	/**
	 * Returns the output text zone
	 * 
	 * @return the TextFlow (output) attribute
	 */
	public TextFlow getOutput(){
		return this.output;
	}
	/**
	 * Returns the entry text zone
	 * 
	 * @return the TextField (entry) attribute
	 */
	public TextField getEntry(){
		return this.entry;
	}
	
	public void targetAction(ArenaCharacter aC,String action){
		
	}
}
