/**
 * 
 */
package utilitiesOption;

import javafx.scene.input.KeyCode;

/**
 * @author Clément
 *
 *
 *
 */
public class ControlKey{	
	
	//structure :name of the control,code of the key
	private String name;
	private KeyCode code = null;

	/**
	 * 
	 * @param name the name of the control
	 * @param code the key associated with it
	 */
	public ControlKey(String name, KeyCode code) {
		this.name=name;
		this.code=code;
	}
	
	/**
	 * 
	 * @param name the name of the control
	 * @param keyName the textual name of the key associated with it
	 */
	public ControlKey(String name, String keyName) {
		this.name=name;
		this.code=KeyCode.getKeyCode(keyName);
	}

	/**
	 * @return the code of the key associated with this option
	 */
	public KeyCode getCode() {
		return code;
	}

	/**
	 * @param code the Keycode to bind to this option
	 */
	public void setCode(KeyCode code) {
		this.code = code;
	}
	
	/**
	 * @param keyName the name of the key to set
	 */
	public void setCode(String keyName) {
		this.code = KeyCode.getKeyCode(keyName);
	}

	/**
	 * @return The name of the associated option
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the keyName of the binded key
	 */
	public String getKeyName() {
		return this.code.getName();
	}
	
	

}
