package ressources;

import java.awt.Toolkit;
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
		private static ControlKey[] controlsCodes={new ControlKey("Escape", KeyCode.ESCAPE)};
		
		private static ControlKey[]devControlCodes={new ControlKey("Console", KeyCode.F11)};
		//the black list is used to prevent the binding of some key 
		public static final KeyCode[] blackList={KeyCode.ENTER};
		private static String version="v unknowed";
		//size of the Stage
		private static double sizeW=1000;
		private static double sizeH=700;
		static boolean fullScreen=false;
		public final static boolean inDev=true;
		public static int[][] stageResolution={{720,480},{1024,768},{1600,900},{1920,1080}};
		public static ArenaText arenaText = new ArenaText("English");
		
		/**
		 * Set the configuration to the last one saved during the latest session
		 */
		public static void set(){
			
			Main.console.println("Loading configuration ...");
			// update version from README.md file
			setVersion();
			//retrive the last config
			if (Config.inDev){
				Main.console.println("Development mode enable, config.ser file overwriten");
				save();
			} else{
				retrieve();
			}
			
			//we now set up the maximum size of the window
			int[] maxSize={(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()};
			for (int k=0 ; k<stageResolution.length ; k++){
				if (maxSize[0]<stageResolution[k][0] || maxSize[1]<stageResolution[k][1]){
					stageResolution[k]=new int[] {0,0};
				}
			}
			
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
		
		public static ControlKey getControl(int k){
			if (k<controlsCodes.length){
				return controlsCodes[k];
			}
			else{
				return controlsCodes[0];
			}
		}

		public static ControlKey getDevControl(int i) {
			if (i<devControlCodes.length){
				return devControlCodes[i];
			}
			else{
				return devControlCodes[0];
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
				Main.console.println(controlsCodes[k].getName()+" key set to "+controlsCodes[k].getKeyName());
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
			//we set the config from the saved config.ser file
			Config.controlsCodes=result.getControlsCodes();
			Config.sizeH=result.getsizeH();
			Config.sizeW=result.getsizeW();
			//set the language
			Config.arenaText=new ArenaText(result.getLang());
			
			} catch (FileNotFoundException e1) {
				// the config.ser file is missing, so we create it
				try {
					Main.console.println("config.ser file is missing, switching to default configuration");
					FileOutputStream fos = new FileOutputStream("./config.ser");
					fos.close();
				} catch (IOException e) {
					//if an exception occurs, i don't know yet how to solve it :(
					e.printStackTrace();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		
		public static boolean updateLang(String pLang){
			//change the language
			return arenaText.setLang(pLang);
		}

}
