/**
 * 
 */
package InputOutputFile;

import java.io.Serializable;

import ressources.Config;
import utilitiesOption.ControlKey;

/**
 * @author Clément
 *
 */
public class SavedConfig implements Serializable {
	
	private ControlKey[] controlsCodes=Config.getControlsCodes();
	//the black list is used to prevent the binding of some key 
	private double sizeW=Config.getSizeW(),sizeH=Config.getSizeH();
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Save the current configuration in a file
	 */
	public SavedConfig() {
	}
	
	public ControlKey[] getControlsCodes(){
		return this.controlsCodes;
	}

	public double getsizeH() {
		// TODO Auto-generated method stub
		return this.sizeH;
	}
	
	public double getsizeW() {
		// TODO Auto-generated method stub
		return this.sizeW;
	}

}
