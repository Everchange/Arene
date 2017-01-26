package character;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * this class will implement the characeter's GUI when this last is selected
 * 
 * @author Clément
 *
 */
public class CharacterGUI extends Group {

	public CharacterGUI() {
		
	}
	public CharacterGUI(String name, int[] position) {
		
		this.relocate(position[0]+20, position[1]+20);
		
		Rectangle bg=new Rectangle(0,0,300,400);
		bg.setFill(Color.WHITE);
		this.getChildren().add(bg);
		
		Rectangle portrait=new Rectangle(10,10,70,100);
		//replace photo by an image
		Text photo=new Text("photo");
		photo.relocate(20, 40);
		portrait.setFill(Color.BROWN);
		this.getChildren().addAll(portrait,photo);
		
		Text nameTXT=new Text(name);
		Text fonction=new Text("classe du personnage");
		nameTXT.relocate(140, 25);
		fonction.relocate(132, 65);
		Rectangle nomBg=new Rectangle(100,20,180,30);
		nomBg.setFill(Color.AQUAMARINE);
		Rectangle fctBg=new Rectangle(100,60,180,30);
		fctBg.setFill(Color.AQUAMARINE);
		this.getChildren().addAll(nomBg,fctBg,fonction,nameTXT);
		
		Text carac=new Text("Rappel des caractéristiques du perso,\nde ses point de vie, ect.");
		Rectangle caracBg=new Rectangle(20,120,260,120);
		carac.relocate(50, 160);
		caracBg.setFill(Color.AQUA);
		this.getChildren().addAll(caracBg,carac);
		
		Group actions=new Group();
		actions.relocate(20, 260);
		Rectangle actionBg=new Rectangle(0,0,260,120);
		actionBg.setFill(Color.BISQUE);
		Text action=new Text("Choix de l'action du personnage \n(attaque,parade,deplacement)");
		action.relocate(50, 40);
		actions.getChildren().addAll(actionBg,action);
		this.getChildren().add(actions);
		
	}

}
