package ressources;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import InputOutputFile.SavedConfig;
import graphics.Main;
import javafx.scene.input.KeyCode;
import utilitiesOption.ControlKey;

public abstract class Config {
	
	//the following list is the default control key
		private static ControlKey[] controlsCodes={new ControlKey("Escape", KeyCode.ESCAPE)},
				devControlCodes={new ControlKey("Console", KeyCode.F11)};
		//the black list is used to prevent the binding of some key 
		public static final KeyCode[] blackList={KeyCode.ENTER};
		private static String version="v unknowed";
		//size of the Stage
		private static double sizeW=800;
		private static double sizeH=600;
		static boolean fullScreen=false;
		public final static boolean inDev=true;
		
		/**
		 * Set the configuration to the last one saved during the latest session
		 */
		public static void set(){
			Main.console.println("Started Project Arena "+Config.getVersion());
			Main.console.println("Retrieving configuration ...");
			// update version from README.md file
			setVersion();
			//retrive the last config
			if (Config.inDev){
			//save();
			}
			retrieve();
			
			Main.console.println("Config loaded");
		}
		
		
		/**
		 * Update the version String by adding the give string
		 * @param property
		 */
		public static void updateVersion(String property) {
			version+=property;
		}
		
		/**
		 * gather the version number
		 */
		public static String getVersion(){
			return version;
		}
		
		/**
		 * the following method allows to synchronize the version number from README.md file
		 *
		 * @return versionNumber
		 */
		private static void setVersion(){
			//if enable to find the version number, here is the default value
			String v="unknowed";
			//we prepare everything to read the file
			BufferedReader reader = null;
			FileReader fileReader = null;
			try{
				//relative path
				fileReader =new FileReader("./README.md");
				reader =new BufferedReader(fileReader);

				//used to read the file
				String ligne;

				//we read the file
				while((ligne=reader.readLine())!=null){
					//will there is still something
					if (ligne.startsWith("**")){
						//if the line start match the one of the one of the line that contains the version number
						v=ligne.replace("**", "");
						//we remove the stars
					}
				}
				//if the file was successfully opened
				if(fileReader!=null){
						fileReader.close();
					}
					if (reader!=null){reader.close();
					}
				}
			catch(Exception e){
				Main.console.println("Unable to find the README.md file !");
			}
			version=v;
		}
		
		public static KeyCode getControlCode(int k){
			if (k<controlsCodes.length){
				return controlsCodes[k].getCode();
			}
			else{
				return controlsCodes[0].getCode();
			}
		}

		public static KeyCode getDevControlCode(int i) {
			if (i<devControlCodes.length){
				return devControlCodes[i].getCode();
			}
			else{
				return devControlCodes[0].getCode();
			}
		}


		public static int getControlCodeLength(){
			return controlsCodes.length;
		}

		public static int setControlCode(int k, KeyCode x){
			int ret = 1;
			// the control exist and the key isn't in the black list
			if (k<controlsCodes.length && !Arrays.asList(blackList).contains(x)){
				for (int l=0;l<controlsCodes.length;l++){
					if (x==controlsCodes[l].getCode()){
						System.out.println("key already binded");
						ret=2;
					}
				}
				controlsCodes[k].setCode(x);
				Main.console.print(controlsCodes[k].getName()+" key set to "+controlsCodes[k].getKeyName());
			}
			else{
				ret=1;
			}

			return ret;
		}
		
		public static void resetControls(){
			controlsCodes[0]=new ControlKey("Escape", KeyCode.ESCAPE);
			devControlCodes[0]=new ControlKey("Console", KeyCode.F11);
		}
		
		
		/**
		 * @return the controlsCodes
		 */
		public static ControlKey[] getControlsCodes() {
			return controlsCodes;
		}


		/**
		 * @return the devControlCodes
		 */
		public static ControlKey[] getDevControlCodes() {
			return devControlCodes;
		}
		
		
		public static void save(){
			FileOutputStream fos =null;
			try {
				fos = new FileOutputStream("./config.ser");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ObjectOutputStream oos;
			try {
				if (fos!=null){
				oos = new ObjectOutputStream(fos);
				oos.writeObject(new SavedConfig());
				oos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
		}
		
		public static void retrieve(){
			try {
			FileInputStream fis = new FileInputStream("./config.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			SavedConfig result = (SavedConfig) ois.readObject();
			ois.close();
			//we set the config
			Config.controlsCodes=result.getControlsCodes();
			Config.sizeH=result.getsizeH();
			Config.sizeW=result.getsizeW();
			
			Main.console.println(Double.valueOf(result.getsizeH()).toString()+Double.valueOf(result.getsizeW()).toString());
			
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Main.console.println(Config.controlsCodes[0].toString());
		}
		
		public static double getSizeW(){
			return Config.sizeW;
		}
		
		public static double getSizeH(){
			return Config.sizeH;
		}
		
		public static boolean isFullScreen(){
			return Config.fullScreen;
		}

}
