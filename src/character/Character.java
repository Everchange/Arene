package character;

import java.io.IOException;

import javafx.scene.image.Image;
import ressources.Beacon;
import utilities.CS;

public class Character extends CS {

	
	private Image img;
	private String name;
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
	public Character(int[] att, int armr, int raceIndex,String name,String imgPath) {
		super(att, armr, raceIndex);
		this.name=name;
		try{
			this.img=new Image(imgPath,utilities.Race.getRaceSize(raceIndex));
		}catch(NullPointerException e){
			//if the specified image doesn't exist
			this.img=new Image(Beacon.class.getResourceAsStream("field.png"));	
		}
	}

}
