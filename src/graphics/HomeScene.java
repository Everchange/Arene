package graphics;

import character.ArenaCharacter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ressources.ArenaText;
import ressources.Config;
import ruleset.Weapon;

public class HomeScene extends ArenaScene{
	
	private Scene scene;
	private static Group root=new Group();
	private Button bStart = new Button();
	
	/**
	 * Creation of the welcome scene 
	 */
	public HomeScene(){
		super(root);
		
		bStart.setText(Config.arenaText.getText(ArenaText.startBt));
        bStart.setPrefWidth(100);
        // ajout des coordonnées pour que le bouton soit bien placé
        //NB: les coordonée sont fonction du groupe menuGroup
        
        
        
        bStart.relocate(0,10);
        //defines the action when the button is pressed
        bStart.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	if (Config.inDev){
            		HomeScene.createDevScene();
            	}
                Main.setScene(1, false);
            }
        });
		
		HomeScene.root.getChildren().add(this.bStart);
		
		// when the F11 key is pressed, the console pops at the foreground
				this.setOnKeyPressed(
			        	new EventHandler<KeyEvent>()
			            {
			                public void handle(KeyEvent e)
			                {
			                    if(e.getCode()==Config.getDevControlCode(0)){
			                    	if (!Config.inDev){
			                    		Main.console.show();
			                    	}
			                    	Main.console.toFront();
			                    }
			                    
			                }
			        });
				// When select and enter pressed
				bStart.setOnKeyPressed(new EventHandler<KeyEvent>()
		        {
		            public void handle(KeyEvent e)
		            {
		                if(e.getCode()==KeyCode.ENTER){
		                	bStart.fire();
		                }
		                
		            }
		        });
		
	}
	private static void createDevScene() {
		//test pour un perso
				ArenaCharacter test=new ArenaCharacter(new int[]{10,10,10,10,10,10},4,0,"test","testpath",new double[] {50,50},20);
				ArenaCharacter testB=new ArenaCharacter(new int[]{10,10,10,10,10,10},4,3,"testbis","testpath",new double[] {300,50},35);
				ArenaCharacter testT=new ArenaCharacter(new int[]{10,10,10,10,10,10},4,0,"testter","testpath",new double[] {350,250},10);
				
				for (int k=0; k<5;k++){
					//before : "k<test.getCS().getWeapon().length"
					test.getCS().getWeapon()[k]=new Weapon();
				}
				((FieldScene) Main.getScene(Main.FIELD_SCENE)).addCharacterToField(new ArenaCharacter[] {test,testB,testT});
				//((FieldScene) Main.scene[1]).addCharacterToField(testC);
		
	}
	/**
	 * Returns the scene
	 * 
	 * @return the Scene (scene) attribute
	 */
	public Scene getScene(){
		return this.scene;
	}
	
	@Override
	public void updateLang(){
		bStart.setText(Config.arenaText.getText(ArenaText.startBt));
	}
}
