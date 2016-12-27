
public class Puce {

	private Robot robot;

	/**
	 * default construtor
	 */
	public Puce(){
	}

	/**
	 * constructor with parameters
	 * @param robot
	 */
	public Puce(Robot robot){
		this.robot=robot;
	}	

	/**
	 * @param robot the robot to set
	 */
	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	/**
	 * affiche la position du robot
	 */
	public void afficherPosition(){
		System.out.println("Le robot est en ("+this.robot.getLigne()+","+this.robot.getColonne()+").");
	}
	/**
	 * affiche le % de charge de la batterie
	 */
	public void afficherBatterie(){
		System.out.println("La batterie du robot est chargée à : "+this.robot.getBatteryLevel()+"%");
	}

	/**
	 * affiche la direction du robot
	 */
	public void afficherDirection(){
		switch(this.robot.getDirection()){
		case("est"):
		case("ouest"):
			System.out.println("Le robot est dirigé vers l'"+this.robot.getDirection());
		break;
		default:
			System.out.println("Le robot est dirigé vers le "+this.robot.getDirection());
		}
	}
	
	public void afficherAll(){
		this.afficherBatterie();
		this.afficherDirection();
		this.afficherPosition();
	}

}
