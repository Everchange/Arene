/**
 * 
 */
package utilitiesOption;

import javafx.scene.input.KeyCode;

/**
 * @author Clément
 *
 */
public class ControlKey {
	
	private String name;
	private KeyCode code = null;
	private String keyName = null;

	/**
	 * 
	 * @param name the name of the control
	 * @param code the key associated with it
	 */
	public ControlKey(String name, KeyCode code) {
		this.name=name;
		this.code=code;
		this.keyName=code.getName();
	}

	/**
	 * @return the code
	 */
	public KeyCode getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(KeyCode code) {
		this.code = code;
		this.keyName=code.getName();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the keyName
	 */
	public String getKeyName() {
		return keyName;
	}
	
	

}
