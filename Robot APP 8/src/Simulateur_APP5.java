
//TODO recoder tout le simulateur 


import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;



public class Simulateur_APP5 {
	private static char map[][]=new char[10][10];
	//line column (for "map")
	
	private static double batteryUsedToMove=0.0;
	// we keep track of the amount of battery we have used
	
	
	static ArrayList<Integer> path=new ArrayList<Integer>();
	//we keep track of the path traveled
	
	/*forward : 0
	* left : 1
	* right : 2
	*/
	
	
	
	
	
	public static void main(String[] args) {
		
		Robot.displayBattery(false);
		
		//we initialize the map
		for(int k=0;k<10;k++){
			for(int j=0;j<10;j++){
				map[k][j]='?';
			}
		}
		
		map[0][0]='O';
		
		printMap(map);
		
		
		//declaration des variales
		//taille du terrain
		int tailleTerrain=10;
		//robot
		RobotConnecte robot=new RobotConnecte(0,0,"sud");
		// création de l'environnement 
		Terrain t = Environnement.creerEnvironnement(tailleTerrain, tailleTerrain);
		t.ajouterObstaclesAleatoiresIsolesAvecBords(25);
		t.ajouterRobot(robot);
		//
		t.updateIHM();
		robot.setVitesse(200);
		
		//from the (0,0) position we can explore the position (0,1) and (1,0)
		//(1,0)
		testForObstacles(robot);
		//(0,1)
		dirigerRobot(1,robot);
		testForObstacles(robot);
		dirigerRobot(2,robot);
		//we are back in the original position
		
		
		while(!checkMapCompletion()){
			//if there is still some '?'
			while(robot.getBatteryLevel()-15>batteryUsedToMove){
				searchVoidZonne(robot);
			}
			goHome(robot);
		}
		
		printMap(map);
		JOptionPane.showMessageDialog(null,"end (main)"+displayMap());
		System.exit(0);
		
	}
	
	public static String displayMap(){
		String mes="\n\n";
		int compteur=0;
		compteur=map[0].length-1;	
			
		for(char ssTab[]:map){
			compteur=map[0].length-1;
			mes+="[";
			for (char str:ssTab){
				
				if (compteur>0){
					mes+=str+" ";
				}
				else{
					mes+=str;
				}
				
				compteur--;
			}
			mes+="]\n";
		}
		
		return mes;
	}
	
	public static boolean checkMapCompletion(){
		for (char[] sstab:map){
			for(char character:sstab){
				if (character=='?'){
					return false;
				}
			}
		}
		return true;
	}
	
	
	/**
	 * this function drives the robot back to (0,0) and charge it 
	 * once it's there
	 * @param Ppath
	 * @param robot
	 * 
	 * Please note that this function does not chooses the optimal way.
	 */
	public static void goHome(Robot robot){
		
			//the robot have to turn back
		robot.tournerDroite();
		robot.tournerDroite();
		
		for (int k = path.size()-1;k>=0;k--){
			switch(path.get(k)){
			case(0):
				// we don't call the avancer method because it will not work
				if(!robotInFrontObstacle(robot)){
					robot.avancer();
				}
				else{
					System.out.println("bug obstacle devant");
					System.out.println(0/0);
				}
				break;
			case(1):
				robot.tournerDroite();
				break;
			case(2):
				robot.tournerGauche();
				break;
			}
			path.remove(k);
			boolean test=false;
			// we test if the robot still have to move forward
			for (int j=path.size()-1;j>=0;j--){
				if (path.get(j)==0){
					test=true;
				}
			}
			if (test==false){
				// if the robot will never use the "avancer" methode, it means that it is 
				// in (0,0).
				path.clear();
				break;
			}
			
		}
		
		//Please notes that is useless to use the  variable now
		// so we don't use it
		
		if(robot.getLigne()!=0 && robot.getColonne()!=0){
			System.out.println("?????????????????????");
		}
		
		//th e robot is now in 0,0
		robot.chargeBattery();
		dirigerRobot(2,robot);
		robot.chargeBattery();
		System.out.println("I'm home ;) and charged at 100%");
		// we reinitialize the variables
		path.clear();
		batteryUsedToMove=0.0;
		printMap(map);
	}
	/**
	 * Add an obstacle on the map at the position line column
	 * @param line
	 * @param column
	 */
	
	public static void addObstacleOnMap(int line, int column){
		for (int k=-1;k<2;k++){
			if(column+k>=0&&line+1<10&&column+k<10){
				map[line+1][column+k]='O';
				//inferior line
			}
			if(line>=0&&column+k>=0&&line<10&&column+k<10){
				map[line][column+k]='O';
				//the line
			}
			if(line-1>=0&&column+k>=0&&column+k<10){
				map[line-1][column+k]='O';
				//the superior line
			}
			// we know that an obstacle is surounded with empty spot
		}
		map[line][column]='X';
	}
	public static boolean robotInFrontBorder(Robot robot){
		switch(robot.getDirection()){
		case("est"):
			if(robot.getColonne()==9){
				return true;
			}
			break;
		case("sud"):
			if(robot.getLigne()==9){
				return true;
			}
			break;
		case("ouest"):
			if(robot.getColonne()==0){
				return true;
			}
			break;
		case("nord"):
			if(robot.getLigne()==0){
				return true;
			}
			break;
		}
		return false;
	}
	
	public static boolean robotInFrontObstacle(Robot robot){
		if(!robotInFrontBorder(robot)){
			switch(robot.getDirection()){
			case("est"):
				if(map[robot.getLigne()][robot.getColonne()+1]=='X'){
					return true;
				}
			break;
			case("sud"):
				if(map[robot.getLigne()+1][robot.getColonne()]=='X'){
					return true;
				}
			break;
			case("ouest"):
				if(map[robot.getLigne()][robot.getColonne()-1]=='X'){
					return true;
				}
			break;
			case("nord"):
				if(map[robot.getLigne()-1][robot.getColonne()]=='X'){
					return true;
				}
			break;
			}
			return false;
		}
		else{
			return true;
		}
	}
	/**
	 * this function update the map
	 * @param robot
	 */
	public static void testForObstacles(Robot robot){
		if (robot.isObstacleDevant()&&!robotInFrontBorder(robot)){
			switch(robot.getDirection()){
			case("est"):
				addObstacleOnMap(robot.getLigne(),robot.getColonne()+1);
				break;
			case("sud"):
				addObstacleOnMap(robot.getLigne()+1,robot.getColonne());
				break;
			case("ouest"):
				addObstacleOnMap(robot.getLigne(),robot.getColonne()-1);
				break;
			case("nord"):
				addObstacleOnMap(robot.getLigne()-1,robot.getColonne());
				break;
			}
		}
		else if (!robotInFrontBorder(robot)){
			switch(robot.getDirection()){
			case("est"):
					map[robot.getLigne()][robot.getColonne()+1]='O';
				break;
			case("sud"):
					map[robot.getLigne()+1][robot.getColonne()]='O';
				break;
			case("ouest"):
					map[robot.getLigne()][robot.getColonne()-1]='O';
				break;
			case("nord"):
					map[robot.getLigne()-1][robot.getColonne()]='O';
				break;
			}
		}
		
		
	}
	/**
	 * this function define the spot where the robot haves to go to update the map
	 * @param robot
	 */
	public static void searchVoidZonne(Robot robot){
		
		
		int goal[]=new int[2];
		//{line,column}
		
		goal[0]=999;
		//used to check if a position was found
		
		

		for (int k=0;k<10;k++){
			for(int j=0;j<=k;j++){
				if( map[k][j]=='?'){
					goal[0]=k;
					goal[1]=j;
					//minus one because the robot will have to inspect this case
					break;
					//we are looking for an unexplored region
				}
				if( map[j][k]=='?'){
					goal[0]=j;
					goal[1]=k;
					//minus one because the robot will have to inspect this case
					break;
					//we are looking for an unexplored region
				}
			
			}
			if(goal[0]!=999){
				break;
			}
		}
		if (goal[0]==999&&checkMapCompletion()){
			JOptionPane.showMessageDialog(null,"end (searchFor)"+displayMap());
			
			System.exit(0);
		}
		System.out.println("The targeted position is : ("+goal[0]+","+goal[1]+")");
		
		goTo(robot,goal);
				
		if((robot.getBatteryLevel()-16>batteryUsedToMove)){
			System.out.println("test at :"+robot.getLigne()+" "+robot.getColonne());
			testForObstacles(robot);
			robot.tournerDroite();
			testForObstacles(robot);
			robot.tournerDroite();
			testForObstacles(robot);
			robot.tournerDroite();
			testForObstacles(robot);
			robot.tournerDroite();
		}
		
	}
	
	
	public static boolean testIndexMapLine(int j,Robot robot){
		if((robot.getLigne()+j>9)||(robot.getLigne()+j<0)){
			//if the coord are not in the map
			System.out.println("bug Map");
			return false;
		}
		else{
			return true;
		}
	}
	
	public static void avancer(Robot robot,String name){
		
		if (robotInFrontBorder(robot) || robotInFrontObstacle(robot)){
			return;
		}
		
		if(robot.getBatteryLevel()-15>batteryUsedToMove){
			robot.setVitesse(200);
			robot.avancer();
			path.add(0);
			batteryUsedToMove+=2;
			return;
		}
		else{
			System.out.println("back to the base");
			goHome(robot);
			return;
		}
	}
	
	
	/**
	 * this function leads the robot to a point (only if the path is free of obstacles)
	 * @param robot
	 * @param goal
	 */
	public static void goTo(Robot robot,int[] goal){
		
		if (map[goal[0]][goal[1]]=='X'){
			System.out.println("impossible de se placer sur un obstacle");
			goHome(robot);
			return;
		}
		
		int j=0;
		
		while (robot.getLigne()!=goal[0]){
			if (map[goal[0]][goal[1]]=='X'){
				//if the targeted position is an obstacle
				System.out.println("impossible de se placer sur un obstacle");
				return;
			}
			
			if(robot.getLigne()<goal[0]){
				dirigerRobot(2,robot);
				// direction = sud
				j=1;
			}
			else if(robot.getLigne()>goal[0]){
				dirigerRobot(0,robot);
				//direction = north
				j=-1;
			}
			if(robotInFrontBorder(robot)){
				System.out.println("border in front !!!");
				return;
			}
			
			switch(robot.getDirection()){
			case("sud"):
				j=1;
				break;
			case("nord"):
				j=-1;
				break;
			}
			
			if (testIndexMapLine(j,robot)==false){
				goHome(robot);
				return;
			}
			
			switch(map[robot.getLigne()+j][robot.getColonne()]){
			case ('?'):
				testForObstacles(robot);
				break;
			case('O'):
				avancer(robot,"goTo");
				break;
			case('X'):
				avoidObstacleCOL(robot);
				break;
			}
		}
		
		while (robot.getColonne()!=goal[1]){
			if (map[goal[0]][goal[1]]=='X'){
				System.out.println("impossible de se placer sur un obstacle");
				return;
			}
			if(robot.getColonne()<goal[1]){
				dirigerRobot(1,robot);
				j=1;
			}
			else if(robot.getColonne()>goal[1]){
				dirigerRobot(3,robot);
				j=-1;
			}
			if(robotInFrontBorder(robot)){
				System.out.println("border in front !!!");
				return;
			}
			
			/*if (testIndexMap(j,robot)==false){
				return;
			}*/
			switch(robot.getDirection()){
			case("est"):
				j=1;
				break;
			case("ouest"):
				j=-1;
				break;
			}
			
			switch(map[robot.getLigne()][robot.getColonne()+j]){
			case ('?'):
				testForObstacles(robot);
				break;
			case('O'):
				avancer(robot,"goTo");
				break;
			case('X'):
				avoidObstacleCOL(robot);
				break;
			}
			
		}
		
		
		if ((robot.getLigne()!=goal[0]||robot.getColonne()!=goal[1])&&(map[goal[0]][goal[1]]!='X')){
			goTo(robot,goal);
		}
		
		System.out.println("Position OK");
	}
	
	public static void avoidObstacleCOL(Robot robot){
		Random r = new Random();
		int test = r.nextInt();
		System.out.println(robot.getColonne()+robot.getLigne()+robot.getDirection());
		if (robotInFrontBorder(robot)){
			return;
		}
		
		if (test%2==0){
			if (robot.getLigne()!=9 && robot.getDirection()=="est"){
				dirigerRobot(2,robot);
				avancer(robot,"avoidCOL 510");

				// we go one line to the south

				dirigerRobot(1,robot);
				avancer(robot,"avoidCOL 515");
				return;
				// we are back to the original line+1 but we've passed the obstacle
			}

			if (robot.getColonne()!=9 && robot.getDirection()=="sud"){
				dirigerRobot(1,robot);
				avancer(robot,"avoidCOL 522");

				// we go one line to the south

				//optionel mais utile 
				testForObstacles(robot);

				dirigerRobot(2,robot);
				avancer(robot,"avoidCOL 530");
				return;
			}

			if (robot.getColonne()!=9 && robot.getDirection()=="nord" && robot.getLigne()!=0){
				dirigerRobot(1,robot);
				avancer(robot,"avoidCOL 536");
				// we go one line to the south

				//optionel mais utile 
				testForObstacles(robot);
				dirigerRobot(0,robot);
				avancer(robot,"avoidCOL 542");

				// we are back to the original line+1 but we've passed the obstacle
				testForObstacles(robot);
				return;
			}

			if (robot.getLigne()!=0 && robot.getDirection()=="ouest" && robot.getColonne()!=0 ){
				dirigerRobot(0,robot);
				avancer(robot,"avoidCOL 511");
				// we go one line to the north

				//optionel mais utile 
				testForObstacles(robot);
				dirigerRobot(3,robot);
				avancer(robot,"avoidCOL 557");

				// we are back to the original line+1 but we've passed the obstacle
				testForObstacles(robot);
				return;
			}
		
		}
		else{
			if (robot.getLigne()!=0 && robot.getDirection()=="est"){
				dirigerRobot(0,robot);
				avancer(robot,"avoidCOL 568");
				
				// we go one line to the south
				
				dirigerRobot(1,robot);
				avancer(robot,"avoidCOL 573");
				return;
				// we are back to the original line+1 but we've passed the obstacle
			}
			
			if (robot.getColonne()!=0 && robot.getDirection()=="sud"){
				dirigerRobot(3,robot);
				avancer(robot,"avoidCOL 580");

				// we go one line to the south

				//optionel mais utile 
				testForObstacles(robot);
				
				dirigerRobot(2,robot);
				avancer(robot,"avoidCOL 588");
				return;
			}
			
			if (robot.getColonne()!=0 && robot.getDirection()=="nord" && robot.getLigne()!=0){
				dirigerRobot(3,robot);
				avancer(robot,"avoidCOL 594");
				// we go one line to the south

				//optionel mais utile 
				testForObstacles(robot);
				dirigerRobot(0,robot);
				avancer(robot,"avoidCOL 600");

				// we are back to the original line+1 but we've passed the obstacle
				testForObstacles(robot);
				return;
			}
			
			if (robot.getLigne()!=9 && robot.getDirection()=="ouest" && robot.getColonne()!=0){
				dirigerRobot(2,robot);
				avancer(robot,"avoidCOL 609");
				// we go one line to the north
				
				//optionel mais utile 
				testForObstacles(robot);
				dirigerRobot(3,robot);
				avancer(robot,"avoidCOL 615");
				
				// we are back to the original line+1 but we've passed the obstacle
				testForObstacles(robot);
				return;
			}
		}
		//end of odd or even test
		if (robot.getLigne()==0 && robot.getDirection()=="ouest" && robot.getColonne()!=0){
			dirigerRobot(2,robot);
			avancer(robot,"avoidCOL 625");
			// we go one line to the north

			//optionel mais utile 
			testForObstacles(robot);
			dirigerRobot(3,robot);
			avancer(robot,"avoidCOL 631");

			// we are back to the original line+1 but we've passed the obstacle
			testForObstacles(robot);
			return;
		}
		if (robot.getColonne()==9 && robot.getDirection()=="nord"){
			dirigerRobot(3,robot);
			avancer(robot,"avoidCOL 639");
			// we go one line to the south

			//optionel mais utile 
			testForObstacles(robot);
			dirigerRobot(0,robot);
			avancer(robot,"avoidCOL 645");

			// we are back to the original line+1 but we've passed the obstacle
			testForObstacles(robot);
			return;
		}
		if((robot.getColonne()==9) && robot.getDirection()=="sud") {
			dirigerRobot(3,robot);
			avancer(robot,"avoidCOL");

			// we go one line to the south

			//optionel mais utile 
			testForObstacles(robot);
			dirigerRobot(1,robot);
			avancer(robot,"avoidCOL");
			// we are back to the original line+1 but we've passed the obstacle
			testForObstacles(robot);
			return;
		}
		if (robot.getLigne()==9 && robot.getDirection()=="est"&&robot.getColonne()!=9){
			dirigerRobot(0,robot);
			avancer(robot,"avoidCOL");
			
			// we go one line to the south
			
			dirigerRobot(1,robot);
			avancer(robot,"avoidCOL");
			return;
			// we are back to the original line+1 but we've passed the obstacle
		}
	}
	
	
	/**
	 * With this function, the robot circumvents the obstacle
	 * @param robot
	 */
	public static void avoidObstacle(Robot robot){
		
		if (robot.getLigne()!=9 && robot.getDirection()=="est"){
			dirigerRobot(2,robot);
			avancer(robot,"avoid");
			;
			// we go one line to the south
			;
			dirigerRobot(1,robot);
			avancer(robot,"avoid");
			;
			;
			// we are back to the original line+1 but we've passed the obstacle
			if (robot.getColonne()!=9){
				avancer(robot,"avoid");
				;
				;
				dirigerRobot(0,robot);
				avancer(robot,"avoid");
				;
				;
				dirigerRobot(1,robot);
				
			}
		}
		if (robot.getColonne()!=9 && robot.getDirection()=="sud"){
			dirigerRobot(1,robot);
			avancer(robot,"avoid");
			;
			// we go one line to the south
			
			//optionel mais utile 
			testForObstacles(robot);
			
			dirigerRobot(2,robot);
			avancer(robot,"avoid");
			;
			;
			// we are back to the original line+1 but we've passed the obstacle
			testForObstacles(robot);
			if (robot.getLigne()<8){
				//si le robot avance deux fois sans rencontrer de mur
				avancer(robot,"avoid");
				testForObstacles(robot);
				dirigerRobot(3,robot);
				avancer(robot,"avoid");
				;
				testForObstacles(robot);
				dirigerRobot(2,robot);

			}
		}
		if (robot.getColonne()!=9 && robot.getDirection()=="nord"){
			dirigerRobot(1,robot);
			avancer(robot,"avoid");
			;
			// we go one line to the south
			//optionel mais utile 
			testForObstacles(robot);
			
			dirigerRobot(0,robot);
			avancer(robot,"avoid");
			;
			// we are back to the original line+1 but we've passed the obstacle
			testForObstacles(robot);
			if (robot.getLigne()<8){
				//si le robot avance deux fois sans rencontrer de mur
				avancer(robot,"avoid");
				;
				testForObstacles(robot);
				dirigerRobot(3,robot);
				avancer(robot,"avoid");
				avancer(robot,"avoid");;
				testForObstacles(robot);
				dirigerRobot(0,robot);

			}
		}
	}
	
	/**
	 * turns the robot to a specified direction 
	 * @param direction
	 * @param robot
	 */
	public static void dirigerRobot(int direction,Robot robot){
		
		String directionsPossible[]={"nord","est","sud","ouest"};
		
		if(robot.getDirection()==directionsPossible[direction]){
			batteryUsedToMove+=0.0;
		}
		
		if(robot.getDirection()==directionsPossible[Math.abs((direction+2)%4)]){
			robot.tournerDroite();
			robot.tournerDroite();
			path.add(2);
			path.add(2);
			batteryUsedToMove+=2.0;
		}
		if(robot.getDirection()==directionsPossible[Math.abs((direction+1)%4)]){
			robot.tournerGauche();
			path.add(1);
			batteryUsedToMove+=1;
		}
		if(robot.getDirection()==directionsPossible[Math.abs((direction-1)%4)]){
			robot.tournerDroite();
			path.add(2);
			batteryUsedToMove+=1;
		}
		if(robot.getDirection()==directionsPossible[Math.abs((direction+3)%4)]){
			robot.tournerDroite();
			path.add(2);
			batteryUsedToMove+=1;
		}
		
		if(robot.getDirection()!=directionsPossible[(direction)]){
			System.out.println("dirigerRobot n'a pas fonctionné");
		}
		
		//if we are in (0,0) we charge the battery in order to have the full power available
		if((robot.getLigne()==0)&&(robot.getColonne()==0)&&(robot.getBatteryLevel()<100)){
			robot.chargeBattery();
		}
	}
	/**
	 * print the map in the console
	 * @param pMatrice
	 */
	public static void printMap(char pMatrice[][]){
		int compteur=0;
		compteur=pMatrice[0].length-1;
		System.out.println("This is the map :");		
			
		for(char ssTab[]:pMatrice){
			compteur=pMatrice[0].length-1;
			System.out.print("[");
			for (char str:ssTab){
				
				if (compteur>0){
					System.out.print(str+" ");
				}
				else{
					System.out.print(str);
				}
				
				compteur--;
			}
			System.out.println("]");
		}
	}

}
