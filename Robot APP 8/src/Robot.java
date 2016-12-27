import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * It's the class that represents the robot
 * <br><br>
 * Please note that the method's names are in French but a translation of
 * each name is normaly available in the comment before the method.
 * This is due to the fact that this class has been developed in France.
 * <br><br>
 * If you encounter any issue while using this java project because of this 
 * inconsistency, please send a e-mail to "juan.portillo@novel.com" which will 
 * transfer it to one of the developers.
 * 
 * @author TIC - ESIGELEC Department and Clément Fraitot
 * @version 1.2
 */

public class Robot {

	/**
	 * an image that represent the robot
	 */
	private BufferedImage image;
	/**
	 * number of the line where is the robot (starts at 0)
	 */
	private int ligne;
	/**
	 * number of the column where is the robot (starts at 0)
	 */
	private int colonne;
	/**
	 * orientation of the robot : "ouest" (west), "est" (east), "nord" (north) ou "sud" (south)
	 */
	private String direction;
	/**
	 * Ground on which the robot is
	 */
	private Terrain terrain;
	/**
	 * speed of the Robot (unit : cases per second, ie m/s)
	 */
	private double vitesse = 2;
	/**
	 * Indicates if the robot is destroyed or not. If yes, the robot cannot be moved
	 */
	private boolean robotDetruit = false;
	/**
	 * Indique la couleur de tracé
	 */
	private int trace = 0;
	
	/**
	 * represent the battery charge level (expressed in percent)
	 */
	private double charge=100;
	
	/*
	/**
	 * quit obvious no ?
	 *\/
	private double totalDistanceTravelled=0;
	*/
	
	/**
	 * it's the remaining distance that the robot can move to. This is calculated 
	 * with the current battery charge level. 
	 */
	private double remainigDistance;
	/**
	 * the number of obstacles that the robot has detected or it has run face-first into ;)
	 */
	
	@SuppressWarnings("unused")
	private int numberObstacles=0;
	
	/**
	 * We need something to count the number of collision. After 3, the robot is destroyed
	 * this attributes is used only in the "detruireRobot" method (in english, "destroyRobot")
	 */
	private int numberShocks=0;
	
	/**
	 * the following boolean controls if we print in the console the state of the battery charge
	 * in "calculRemainingDistance"
	 */
	private static boolean displayBattery=true;

	/**
	 * Robot's constructor
	 * 
	 * @param ligne
	 *            initial ligne where the robot is (starts at 0)
	 * @param colonne
	 *            initial robots column (starts at 0)
	 * @param direction
	 *            direction initiale du Robot (est, ouest, nord ou sud)
	 */
	public Robot(int ligne, int colonne, String direction) {
		// initialisation des attributs de la classe
		this.ligne = ligne;
		this.colonne = colonne;
		this.direction = direction;
		// initialisation de l'attribut image à partir d'un fichier de type
		// image représentant le Robot à l'écran

		try {
			image = ImageIO.read(new File("./data/robot.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// we now need to initialize the battery related attributes
		this.remainigDistance=this.caculRemainingDistance(this.charge);
	}

	/**
	 * Allows to draw the robot ("dessiner")
	 * 
	 * @param g
	 *            Graphics object on which the robot is drawn
	 * @param largeurCase
	 *            width of the case on which we will draw the robot (in pixels).the image will be automatically
	 *            resized
	 * @param hauteurCase
	 *            heigh of the case on which we will draw the robot (in pixels).the image will be automatically
	 *            resized
	 */
	public void dessiner(Graphics g, int largeurCase, int hauteurCase) {
		// on convertit l'objet Graphics en Graphics2D afin de pouvoir utiliser
		// des méthodes plus pratiques
		Graphics2D g2 = (Graphics2D) g;
		// on trouve l'angle de rotation de l'image en s'appuyant sur la
		// direction du Robot
		double angleRotation = 0;
		if ("est".equals(direction))
			angleRotation = Math.PI / 2;
		else if ("sud".equals(direction))
			angleRotation = Math.PI;
		else if ("ouest".equals(direction))
			angleRotation = 3 * Math.PI / 2;

		// on fait pivoter l'image de angleRotation, le centre de rotation étant
		// le centre de la case
		g2.rotate(angleRotation, largeurCase / 2, hauteurCase / 2);
		// on dessine l'image pivotée sur la case dont on connait le Graphics
		g2.drawImage(image, 0, 0, largeurCase, hauteurCase, null);
		// on remet la rotation à 0
		g2.rotate(-angleRotation, largeurCase / 2, hauteurCase / 2);

	}

	/**
	 * permet d'avancer le robot droit devant lui
	 * 
	 * @return 1 si le robot a pu avancer, -1 si le robot n'a pas pu avancer à
	 *         cause d'un obstacle ou des limites du terrain, -2 si le robot n'a
	 *         pas pu avancer car il est détruit, -3 si le robot n'a plus de
	 *         batterie
	 */
	public int avancer() {
		int retour = 1;

		// si le robot est détruit on ne peut pas avancer
		if (robotDetruit)
			retour = -2;

		else
		// si on avance alors qu'il y a un obstacle devant alors on détruit le
		// robot
		if (isObstacleDevant()) {

			detruireRobot();
			retour = -1;
		
		}
		else if (this.charge<=0.0){
			retour=-3;
		}
		else {
			//we use 2% of the battery
			useBattery(2.0);
			//and we restore 0.5% as the methode isObstacleDevant consume 0.5%
			this.charge+=0.5;
			
			// and we can move the robot according to its orientation ("direction")
			if ("nord".equals(direction)) {
				ligne--;

			} else if ("sud".equals(direction)) {
				ligne++;

			} else if ("ouest".equals(direction)) {
				colonne--;

			} else if ("est".equals(direction)) {
				colonne++;

			} else
				retour = -1;

			// on mémorise que la case est maintenant visitée
			if (trace == 1) {
				terrain.getGrille()[ligne][colonne].setBackground(Color.RED);
				terrain.getGrille()[ligne][colonne].setVisitee(true);
			}
			if (trace == 2) {
				terrain.getGrille()[ligne][colonne].setBackground(Color.BLUE);
				terrain.getGrille()[ligne][colonne].setVisitee(true);
			}

			// on redessine le terrain
			terrain.repaint();

			// on effectue une petite pause fonction de la vitesse du robot
			try {
				Thread.sleep((int) (1000 / vitesse));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		return retour;
	}

	/**
	 * Permet de faire reculer le robot
	 * 
	 * @return 1 si le robot a pu reculer, -1 si le robot n'a pas pu reculer à
	 *         cause d'un obstacle ou des limites du terrain, -2 si le robot n'a
	 *         pas pu reculer car il est détruit, -3 si le robot n'a plus de
	 *         batterie
	 */
	public int reculer() {
		int retour = 1;

		// si le robot est détruit on ne peut pas avancer
		if (robotDetruit)
			retour = -2;
		else
		// si on recule alors qu'il y a un obstacle derriere alors on détruit le
		// robot
		if (isObstacleDerriere()) {
			
			detruireRobot();
			retour = -1;
		}
		else if (this.charge<=0.0){
			retour=-3;
		}

		else {
			//we use 2% of the battery
			useBattery(2.0);
			//and we restore 0.5% as the methode isObstacleDerriere consume 0.5%
			this.charge+=0.5;
			
			// and we can move the robot according to its opposite orientation ("direction")
			if ("sud".equals(direction)) {
				ligne--;
			} else if ("nord".equals(direction)) {
				ligne++;
			} else if ("est".equals(direction)) {
				colonne--;
			} else if ("ouest".equals(direction)) {
				colonne++;
			} else
				retour = -1;

			if (trace == 1) {
				terrain.getGrille()[ligne][colonne].setBackground(Color.RED);
				terrain.getGrille()[ligne][colonne].setVisitee(true);
			}
			if (trace == 2) {
				terrain.getGrille()[ligne][colonne].setBackground(Color.BLUE);
				terrain.getGrille()[ligne][colonne].setVisitee(true);
			}
		}
		// on redessine le terrain
		terrain.repaint();
		// on effectue une petite pause fonction de la vitesse du robot
		try {
			Thread.sleep((int) (1000 / vitesse));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return retour;
	}

	/**
	 * Permet de faire pivoter le robot sur sa droite
	 * 
	 * @return 1 si le robot a pu tourner correctement, -2 si le robot n'a pas
	 *         pu tourner car il est détruit, -3 si le robot n'a plus de
	 *         batterie
	 */
	public int tournerDroite() {
		int retour = 1;

		if (robotDetruit){
			retour = -2;}
		else if (this.charge<=0.0){
			retour=-3;
		}
		else {
			//we use 1% of the battery
			useBattery(1.0);
			
			// and we can turn the robot by its right
			if ("sud".equals(direction)) {
				direction = "ouest";
			} else if ("nord".equals(direction)) {
				direction = "est";
			} else if ("est".equals(direction)) {
				direction = "sud";
			} else if ("ouest".equals(direction)) {
				direction = "nord";
			}
		}
		// on redessine le terrain
		terrain.repaint();
		// on effectue une petite pause fonction de la vitesse du robot
		try {
			Thread.sleep((int) (1000 / vitesse));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return retour;
	}

	/**
	 * Permet de faire pivoter le robot sur sa droite
	 * 
	 * @return 1 si le robot a pu  correctement, -2 si le robot n'a pas
	 *         pu tourner car il est détruit, -3 si le robot n'a plus de
	 *         batterie
	 */
	public int tournerGauche() {
		int retour = 1;

		if (robotDetruit){
			retour = -2;}
		else if (this.charge<=0.0){
			retour=-3;
		}
		else {
			//we use 1% of the battery
			useBattery(1.0);
			// and we can turn the robot by its left
			if ("sud".equals(direction)) {
				direction = "est";
			} else if ("nord".equals(direction)) {
				direction = "ouest";
			} else if ("est".equals(direction)) {
				direction = "nord";
			} else if ("ouest".equals(direction)) {
				direction = "sud";
			}
		}

		// on redessine le terrain
		terrain.repaint();
		// on effectue une petite pause fonction de la vitesse du robot
		try {
			Thread.sleep((int) (1000 / vitesse));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return retour;
	}

	/**
	 * Test if there is an obstacle in front of the robot. This consumes % of battery
	 * as required in the specification 
	 * 
	 * @return true sthere is an obstacle in front of the robot (the terrain edges counts
	 * 			as an obstacle); false otherwise.
	 */

	public boolean isObstacleDevant() {
		
		//with the following lines, we implement the battery comsumption
		useBattery(0.5);
		

		// en fonction de la direction du robot, on teste s'il ne sort pas des
		// limites du terrain, et si la case devant lui n'est pas de type
		// Obstacle
		if ("nord".equals(direction)
				&& ligne > 0
				&& !(terrain.getGrille()[ligne - 1][colonne] instanceof Obstacle))
			return false;
		else if ("sud".equals(direction)
				&& ligne < terrain.getNbLignes() - 1
				&& !(terrain.getGrille()[ligne + 1][colonne] instanceof Obstacle))
			return false;
		else if ("ouest".equals(direction)
				&& colonne > 0
				&& !(terrain.getGrille()[ligne][colonne - 1] instanceof Obstacle))
			return false;
		else if ("est".equals(direction)
				&& colonne < terrain.getNbColonnes() - 1
				&& !(terrain.getGrille()[ligne][colonne + 1] instanceof Obstacle))
			return false;
		else
			return true;

	}

	/**
	 * Teste la présence d'un obstacle à l'arrière le Robot
	 * 
	 * @return true si un obstacle est présent derrière le robot; false si aucun
	 *         obstacle n'est présent derrière le robot
	 */
	public boolean isObstacleDerriere() {
		//with the following lines, we implement the battery comsumption
		useBattery(0.5);

		// en fonction de la direction du robot, on teste s'il ne sort pas des
		// limites du terrain, et si la case derrière lui n'est pas de type
		// Obstacle
		if ("sud".equals(direction)
				&& ligne > 0
				&& !(terrain.getGrille()[ligne - 1][colonne] instanceof Obstacle))
			return false;
		else if ("nord".equals(direction)
				&& ligne < terrain.getNbLignes() - 1
				&& !(terrain.getGrille()[ligne + 1][colonne] instanceof Obstacle))
			return false;
		else if ("est".equals(direction)
				&& colonne > 0
				&& !(terrain.getGrille()[ligne][colonne - 1] instanceof Obstacle))
			return false;
		else if ("ouest".equals(direction)
				&& colonne < terrain.getNbColonnes() - 1
				&& !(terrain.getGrille()[ligne][colonne + 1] instanceof Obstacle))
			return false;

		else
			return true;

	}


	/**
	 * alows to destroy the robot after 3 shocks
	 */
	public void detruireRobot() {
		// on indique que le robot est détruit
		if (this.numberShocks==3){
		robotDetruit = true;
		// on modifie son image pour voir une image de robot détruit
		try {
			image = ImageIO.read(new File("./data/robot_detruit.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// on redessine le terrain
		terrain.repaint();
		System.out.println("Robot Détruit !");
		//GUI
		FrameGUI out =new FrameGUI("robot death",JFrame.EXIT_ON_CLOSE);
		out.addTxt("The robot encountered an obstacle and it was the third. <br>:_(",true);
		}
		else if (this.numberShocks<3){
			this.numberShocks+=1;
			System.out.println("------! WARNING !------");
			System.out.println("The robot has encountered an obstacle ("+this.numberShocks+"/3)");
			JOptionPane.showMessageDialog(null,"check ?");
		}
		
	}
	

	/**
	 * Allows to destroy the robot immediately
	 */
	public void detruireRobot(String mes) {
		// on indique que le robot est détruit
		robotDetruit = true;
		// on modifie son image pour voir une image de robot détruit
		try {
			image = ImageIO.read(new File("./data/robot_detruit.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// on redessine le terrain
		terrain.repaint();
		System.out.println("Robot Détruit !");
		//GUI
		//FrameGUI out =new FrameGUI("robot death",JFrame.EXIT_ON_CLOSE);
		//out.addTxt(mes,true);
		JOptionPane.showMessageDialog(null,mes);
		System.exit(0);

		}
		
	
	
	
	
	/**
	 * even if this is not a very useful method, its easier to use this to
	 * increment the attribute "numberObstaces"
	 */
	public void countObstacle(){
		this.numberObstacles+=1;
	}
	
	/**
	 * this allows (only if the robot is in (0,0)) to restore the full battery charge
	 */
	public void chargeBattery(){
		//we check if the robot is in the right place
		if (this.colonne==0 && this.ligne==0){
			this.charge=100.0;
		}
		else{
			System.out.print("It seems that you are trying to charge the robot but this last"
							 + "is not in the right place. ");
			checkLoss();
		}
	}
	/**
	 * if the battery charge level is 0.0 and the robot is not in (0,0), the robot is
	 * destroyed as it cannot be retrieve.
	 */
	private void checkLoss(){
		if (this.charge<=0.0 && (this.colonne!=0 && this.ligne!=0)){
			//GUI
			this.detruireRobot("RIP your robot is lost. <br>:_(");
			// the robot is useless
		// the other case is if the robot is too far away from the (0,0) point and it dosen't have enough battery
		// to go to this point
		}
		if (this.charge<(this.getColonne()+this.getLigne()+1)*2){
			// +1 because it starts at 0
			//GUI
			this.detruireRobot("RIP your robot is lost (too far away from (0,0)). \n:_(");
		}
		
	}
	
	private void useBattery(double amount){
		if(this.getColonne()!=0||this.getLigne()!=0){
			this.charge-=amount;
		}
		else{
			this.charge=100.0;
		}
		//the battery is used only if th e robot is not in (0,0)
		caculRemainingDistance(this.charge);
		checkLoss();
	}
	
	/**
	 * this methods calculates the remaining distance (in meters) that the robot
	 * can move to.
	 * 
	 * @param batteryCharge
	 * @return a distance (double) in meters 
	 */
	private double caculRemainingDistance(double batteryCharge){
		// print in the console what is ask in the specification
		if(displayBattery){
		System.out.println("Battery charge :"+this.charge+"\nWith it, the robot can move up to "+(this.charge*0.5)
				+" meters");
		}
		// return the value
		return (this.charge*0.5);
	}
	/**
	 * this methods allows to acces the value of remainigDistance
	 * @return the remaining distance
	 */
	public double getRemainigDistance(){
		return this.remainigDistance;
	}
	/**
	 * this methods allows to aces the value of the battery charge
	 * @return the battery charge
	 */
	public double getBatteryLevel(){
		return this.charge;
	}
	

	/**
	 * Permet de savoir si le roboto se trouve sur une case victime
	 * 
	 * @return true si le robot se trouve sur une case victime; false si le
	 *         robot ne se trouve pas sur une case victime
	 */
	public boolean isSurVictime() {
		// on teste le type de la case du robot
		if (terrain.getGrille()[ligne][colonne] instanceof CaseVictime) {
			
			//GUI
			JOptionPane.showMessageDialog(null,"The robot discovered a victime \n:)");			
			return true;
			
		} else
			return false;

	}

	/**
	 * Permet de sauver une victime, i.e. changer son état
	 * 
	 * @return 1 si la victime a été sauvée; -1 si aucune victime sur la case
	 *         courante du robot
	 */
	public int sauverVictime() {
		// si la case courante est de type victime
		if (terrain.getGrille()[ligne][colonne] instanceof CaseVictime) {
			// on sauve la victime
			((CaseVictime) terrain.getGrille()[ligne][colonne]).sauverVictime();
			return 1;
		} else
			return -1;

	}

	/**
	 * getter de l'attribut vitesse
	 * 
	 * @return l'attribut vitesse
	 */
	public double getVitesse() {
		return vitesse;
	}

	/**
	 * setter de l'attribut vitesse
	 * @param vitesse (speed), a double
	 */
	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}

	/**
	 * getter de l'attribut ligne
	 * 
	 * @return l'attribut ligne
	 */
	public int getLigne() {
		return ligne;
	}

	/**
	 * getter de l'attribut colonne
	 * 
	 * @return l'attribut colonne
	 */
	public int getColonne() {
		return colonne;
	}

	/**
	 * getter de l'attribut direction
	 * 
	 * @return l'attribut direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * getter de l'attribut terrain
	 * 
	 * @return l'attribut terrain
	 */
	public Terrain getTerrain() {
		return terrain;
	}

	/**
	 * setter de l'attribut terrain
	 * @param terrain (ground), a "Terrain" object
	 */
	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}

	/**
	 * Méthode permettant de modifier la couleur de tracer
	 * 
	 * @param trace
	 *            correspond au code de couleur : 1 pour le rouge, 2 pour le
	 *            bleu.
	 */
	public void tracer(int trace) {
		this.trace = trace;
		if (trace == 1) {
			terrain.getGrille()[ligne][colonne].setBackground(Color.RED);
			terrain.getGrille()[ligne][colonne].setVisitee(true);
		}
		if (trace == 2) {
			terrain.getGrille()[ligne][colonne].setBackground(Color.BLUE);
			terrain.getGrille()[ligne][colonne].setVisitee(true);
		}

	}

	/**
	 * Méthode permettant de détecter la gravité de l'état d'une victime.
	 * 
	 * @return la gravité de l'état de la victime entre 1 et 10 s'il y a une
	 *         victime sur la case; 0 s'il n'y a pas de victime.
	 */
	public int detecterGravite() {
		if (terrain.getGrille()[ligne][colonne].getClass().getName()
				.equals("CaseVictime"))
			return ((CaseVictime) terrain.getGrille()[ligne][colonne])
					.getGravite();
		else
			return 0;
	}

	/**
	 * Permet de récupérer l'état de la victime une fois que le robot est sur la
	 * victime
	 * 
	 * @return l'état de la victime ou un message indiquant que le robot n'a pas
	 *         encore atteint la victime
	 */
	public String getEtatVictime() {
		// on teste le type de la case du robot
		if (terrain.getGrille()[ligne][colonne] instanceof CaseVictime) {
			return ((CaseVictime) terrain.getGrille()[ligne][colonne])
					.getEtatVictime();
		} else
			return "Le robot n'a pas encore atteint la victime";
	}

	public static void displayBattery(boolean b) {
		displayBattery=b;
		
	}

}