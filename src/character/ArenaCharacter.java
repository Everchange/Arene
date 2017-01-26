package character;


import graphics.FieldScene;
import graphics.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import ressources.Beacon;
import ruleset.CS;

public class ArenaCharacter extends CS {

	
	private Image img;
	private String name;
	private int[] position;
	
	/**
	 * Create a new character that can be displayed on the field as an image is associated with him.
	 * 
	 * @param att
	 * @param armr
	 * @param raceIndex the index of the race of this character
	 * @param name name of the character (this is the one that will be displayed in the 
	 * 				character menu 
	 * @param imgName path to the character's image
	 */
	public ArenaCharacter(int[] att, int armr, int raceIndex,String name,String imgPath,int[] position) {
		super(att, armr, raceIndex);
		this.position=position;
		this.name=name;
		try{
			double[] size=ruleset.Race.getRaceSize(raceIndex);
			//path 
			this.img=new Image(imgPath,size[0],size[1],true,true);
		}catch(NullPointerException e){
			//if the specified image doesn't exist
			this.img=new Image(Beacon.class.getResourceAsStream("defaultCharacter.png"));
			System.out.println("set to default");
		}catch(IllegalArgumentException e){
			//if the specified path is incorrect
			this.img=new Image(Beacon.class.getResourceAsStream("defaultCharacter.png"));
		}
	}
	/**
	 * Returns a character image that can be displayed on the field
	 * 
	 * @return ImageView object
	 */
	public ImageView getRepresentationOnField(){
		ImageView ret=new ImageView(this.img);
		ret.setOnMouseEntered(evt -> {
			// implement quick view here
        	System.out.println("Name of the overflown character : "+this.name);
        });
		
		ret.setOnMouseExited(evt -> {
        	System.out.println("(mouse exited)");
        });
		
		ret.setOnMouseClicked(evt -> {
			//if this is the lrft button
			if(evt.getButton().equals(MouseButton.SECONDARY)){
				((FieldScene) Main.getScene(1)).displayCharacterGUI(new CharacterGUI(this.name,this.position));
			}
        	
        });
		
		return ret;
		
		
	}
	
	public int[] getPosition(){
		return this.position;
	}

}
