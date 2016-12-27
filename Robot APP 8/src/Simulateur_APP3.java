//import de la classe JOptionPane et Array list
import java.util.ArrayList;
import javax.swing.JOptionPane; 

/**
 * Programme principal avec la méthode main
 * @author Département TIC - ESIGELEC and Clément Fraitot (variante 2)
 * @version 2.1
 */

public class Simulateur_APP3 {
	// variables dclarée ici pour un accès n'importe où dans le programme
	//creation de l'ArayList pour le deplacement
	private static ArrayList<Integer> returnPath= new ArrayList<Integer>();
	
	// creation de la variable de controle pour la decouverte de la victime
	private static boolean victimeTrouvee=false;
	
	// variable pour savoir si il faut enregistre le chemin
	private static boolean cheminADecouvrir=false;
	
	//creation de la matrice avec les coordonnée des portes
	int porte[][]=new int[4][2];
		
	// creation de la variable concernant l'etat de la victime
	private static String etat="";

	public static void main(String[] args) {
		//declaration des variables
		
		//creation de la matrice avec les coordonnée des portes
		int porte[][]=new int[4][2];
		
		//salle
		int salle;
		
		// création de l'environnement et récupération du terrain
		Terrain t = Environnement.creerEnvironnement(10, 10);

		// creation du robot
		Robot robot = new Robot(0, 0, "sud");
		
		// ajout du matos versus les cas
		String CorrespondanceCasMatos[][]={{"saignement","Trousse de soins"},{"asphyxie","Masque d'oxygénation artificielle"},{"fracture","Matelas immobilisateur"},{"arret cardiaque","Défibrillateur"}};
		
		// matos necesaire
		String matos="";

		// ajout du robot sur le terrain
		t.ajouterRobot(robot);
        
		//ajout des quatres salles 
		t.ajouterQuatreSalles();
		
		//ajout victime dans l'une des quatre salles
		t.ajouterVictimeQuatreSalles();

		// met à jour les composants graphiques
		t.updateIHM();
		
		//ajouter ici le code de déplacement du robot
		robot.setVitesse(50);
		//on change la direction du robot vers l'est
		dirigerRobot(1,robot);
		
		//on l'amène devant le premier le mur de la salle 0
		robot.avancer();
		robot.avancer();
		
		if(robot.isObstacleDevant()){
			//on cherche la porte et on la passe
			porte[0][0]=rechercherEst(robot);
		}
		else{
			robot.avancer();
			robot.avancer();
		}
		
		//on l'amène devant le premier mur
		deplacement_colonnes_centrales(0,5,robot);
		
		if(robot.isObstacleDevant()){
			dirigerRobot(2,robot);
			//on cherche la porte
			porte[1][0]=rechercherEst(robot);
		}
		else{
			robot.avancer();
			robot.avancer();
		}
		// le robot est dans la première salle
		
		salle=1;
		rechercherVicitmeDansSalleEst(robot);
		
		if (!victimeTrouvee){
			// si la victime n'est pas dans la 1er salle
			sortir_salle(robot, porte[1],6);
			deplacement_colonnes_centrales(7,5,robot);
			porte[2][0]=rechercherEst(robot);
			salle=2;
			rechercherVicitmeDansSalleEst(robot);
			
		}
		
		if (!victimeTrouvee){
			// si la victime n'est pas dans la 1er salle ni dans la deuxième
			sortir_salle(robot, porte[2],6);
			robot.avancer();
			deplacement_colonnes_centrales(7,4,robot);
			porte[3][0]=rechercherOuest(robot);
			salle=3;
			rechercherVicitmeDansSalleOuest(robot);
			testVictime(robot);
		}
		if(!victimeTrouvee){
			System.out.println("---! PROBLEME !---");
			System.out.println("victime non trouvée");
			System.exit(0);
		}
		else if(victimeTrouvee){
			System.out.println("---! INFO !---");
			System.out.println("Victime trouvée. Activation de l'enregistrement du chemin");
			System.out.println("état : "+etat);
			cheminADecouvrir=true;
		}
		
		// on gère Le matos ici
		for (int k=0;k<4;k++){
			if(CorrespondanceCasMatos[k][0]==etat){
				matos=CorrespondanceCasMatos[k][1];
			}
		}
		
		//le robot sort de la salle et revient vers la porte de la salle 0
		sortir_salle(robot,porte[salle],3);
		deplacement_colonnes_centrales(porte[0][0],4,robot);
		// il entre
		dirigerRobot(3,robot);
		for(int k=0;k<4;k++){
			robot.avancer();
			if(victimeTrouvee && cheminADecouvrir){
				returnPath.add(1);
			}
		}
		// le robot est en colonne 0
		
		dirigerRobot(0,robot);
		while(robot.getLigne()!=0){//pour la ligne 0
			robot.avancer();
			if(victimeTrouvee && cheminADecouvrir){
				returnPath.add(1);
			}
		}
		// on n'a plus besion d'enregistrer le chemin
		cheminADecouvrir=false;
		dirigerRobot(2,robot);
		//dirigé en 0 0 vers le sud
		
		//affichage du message pour le matériel
		JOptionPane.showMessageDialog(null,"Pour sauver la victime, veuillez placer le "
				+ "kit suivant sur le robot :\n"+matos);
		
		for (int j=0;j<3;j++){
			// on enlève des mouvements inutiles
			if ((returnPath.get(0)!=1)&&(returnPath.size()>1)){
				returnPath.remove(0);
			}
		}
		// retour du robot vers la victime
		for(int k=returnPath.size()-1; k>-1;k--){
			switch(returnPath.get(k)){
			case(2):
				robot.tournerGauche();
				break;
			case(3):
				robot.tournerDroite();
				break;
			case(1):
				robot.avancer();
				break;
			}
		}
		// la simulation est terminée
		JOptionPane.showMessageDialog(null,"Simulation terminée");
		System.exit(0);	
	}
	
	// definition des fonctions de déplacement et de recherche
	public static boolean testVictime(Robot robot){
		if (robot.isSurVictime()){
			victimeTrouvee=true;
			etat=robot.getEtatVictime();
			return true;
		}
		else{
			return false;
		}
	}
	
	public static void sortir_salle(Robot robot, int[] porte,int colonne){
		if (robot.getLigne()<porte[0]){
			dirigerRobot(2,robot);
		}
		if (robot.getLigne()>porte[0]){
			dirigerRobot(0,robot);

		}

		while(robot.getLigne()!=porte[0]){
			robot.avancer();
			if(victimeTrouvee && cheminADecouvrir){
				returnPath.add(1);
			}
		}
		// permet de correspondre à la colonne après la porte
		colonne++;
		if (robot.getColonne()<colonne){
			dirigerRobot(1,robot);
		}
		if (robot.getColonne()>colonne){
			dirigerRobot(3,robot);
		}
		
		while(robot.getColonne()!=colonne){
			robot.avancer();
			if(victimeTrouvee && cheminADecouvrir){
				returnPath.add(1);
			}
		}

	}
	
	public static void rechercherVicitmeDansSalleEst(Robot robot){
		dirigerRobot(0,robot);
		// on amène le robot en 0 0 de la salle
		if (testVictime(robot)){
			return;
		}
		while(!robot.isObstacleDevant()){
			if (testVictime(robot)){
				return;
			}
			robot.avancer();
		}
		
		if (testVictime(robot)){
			return;
		}
		
		dirigerRobot(1,robot);
		for (int k=0;k<3;k++){
			for(int l=0;l<2;l++){
				robot.avancer();
				if (testVictime(robot)){
					return;
				}
			}
			if(k==0){
				dirigerRobot(2,robot);
				robot.avancer();
				dirigerRobot(3,robot);
			}
			else if (k==1){
				dirigerRobot(2,robot);
				robot.avancer();
				dirigerRobot(1,robot);
			}
			if (testVictime(robot)){
				return;
			}
		}
		if (testVictime(robot)){
			return;
		}
	}
	public static void rechercherVicitmeDansSalleOuest(Robot robot){
		dirigerRobot(0,robot);
		// on amène le robot en 3 0 de la salle
		
		if (testVictime(robot)){
			return;
		}
		
		while(!robot.isObstacleDevant()){
			robot.avancer();
			if (testVictime(robot)){
				return;
			}
		}
		// amène en 0  0
		dirigerRobot(3,robot);
		robot.avancer();
		robot.avancer();
		
		//boucles de recherche
		dirigerRobot(1,robot);
		for (int k=0;k<3;k++){
			for(int l=0;l<2;l++){
				if (testVictime(robot)){
					return;
				}
				robot.avancer();
			}
			if (testVictime(robot)){
				return;
			}
			if(k==0){
				dirigerRobot(2,robot);
				robot.avancer();
				dirigerRobot(3,robot);
			}
			else if (k==1){
				dirigerRobot(2,robot);
				robot.avancer();
				dirigerRobot(1,robot);
			}
			if (testVictime(robot)){
				return;
			}
		}
		if (testVictime(robot)){
			return;
		}
	}
	
	
	public static void deplacement_colonnes_centrales(int ligne,int colonne,Robot robot){
		

		if (robot.getColonne()<colonne){
			dirigerRobot(1,robot);

			while(robot.getColonne()!=colonne){
				robot.avancer();
			}
			if(victimeTrouvee && cheminADecouvrir){
				returnPath.add(1);
			}
		}
		else if(robot.getColonne()>colonne){
			dirigerRobot(3,robot);
			while(robot.getColonne()!=colonne){
				robot.avancer();
			}
			if(victimeTrouvee && cheminADecouvrir){
				returnPath.add(1);
			}
		}
		
		if (robot.getLigne()<ligne){
			dirigerRobot(2,robot);
			while(robot.getLigne()!=ligne){
				robot.avancer();
				if(victimeTrouvee && cheminADecouvrir){
					returnPath.add(1);
				}
			}
		}
		else if (robot.getLigne()>ligne){
			dirigerRobot(0,robot);
			while(robot.getLigne()!=ligne){
				robot.avancer();
				if(victimeTrouvee && cheminADecouvrir){
					returnPath.add(1);
				}
			}
		}
	}
	
	public static int rechercherEst(Robot robot){
		
		int coord;
		
		switch(robot.getDirection()){
		case("nord"):
			robot.tournerDroite();
			robot.tournerDroite();

			break;
		case("est"):
			robot.tournerDroite();
			break;
		case("ouest"):
			robot.tournerGauche();
			break;
		}
		
		for (int k=0;k<3;k++){
			robot.tournerGauche();
			if(!robot.isObstacleDevant()){
				robot.avancer();
				// le robot est dans l'encadrement de la porte
				coord=robot.getLigne();
				// il passe dans la salle
				robot.avancer();
				return coord;
			}
			robot.tournerDroite();
			robot.avancer();
		}
		System.out.println("---! INCIDENT !---");
		System.out.println("Il semble qu'une porte soit manquante ou que le robot n'était");
		System.out.println("pas dans une configuration permetant la recherche de portes");
		return 99;
	}
	
	public static int rechercherOuest(Robot robot){
		
		int coord;
		
		dirigerRobot(2,robot);
		
		for (int k=0;k<3;k++){
			robot.tournerDroite();
			if(!robot.isObstacleDevant()){
				robot.avancer();
				// le robot est dans l'encadrement de la porte
				coord=robot.getLigne();
				// il passe dans la salle
				robot.avancer();
				return coord;
			}
			robot.tournerGauche();
			robot.avancer();
		}
		System.out.println("---! INCIDENT !---");
		System.out.println("Il semble qu'une porte soit manquante ou que le robot n'était");
		System.out.println("pas dans une configuration permetant la recherche de portes");
		return 99;
	}
	
	/**pour les directions : nord=0, est=1, sud=2, ouest=3*/
	public static void dirigerRobot(int direction,Robot robot){
		
		String directionsPossible[]={"nord","est","sud","ouest"};
		
		if(robot.getDirection()==directionsPossible[direction]){
			return;
		}
		
		if(robot.getDirection()==directionsPossible[Math.abs((direction+2)%4)]){
			robot.tournerDroite();
			robot.tournerDroite();
			if(victimeTrouvee && cheminADecouvrir){
				returnPath.add(2);
				returnPath.add(2);
			}
		}
		if(robot.getDirection()==directionsPossible[Math.abs((direction+1)%4)]){
			robot.tournerGauche();
			if(victimeTrouvee && cheminADecouvrir){
				returnPath.add(3);
			}
		}
		if(robot.getDirection()==directionsPossible[Math.abs((direction-1)%4)]){
			robot.tournerDroite();
			if(victimeTrouvee && cheminADecouvrir){
				returnPath.add(2);
			}
		}
		if(robot.getDirection()==directionsPossible[Math.abs((direction+3)%4)]){
			robot.tournerDroite();
			if(victimeTrouvee && cheminADecouvrir){
				returnPath.add(2);
			}
		}
		
		if(robot.getDirection()!=directionsPossible[(direction)]){
			//System.out.println("dirigerRobot n'a pas fonctionné");
		}
	}
}