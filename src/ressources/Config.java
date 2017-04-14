package ressources;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import graphics.Main;
import javafx.scene.input.KeyCode;
import utilitiesOption.BeautifullJson;
import utilitiesOption.ControlKey;

import javax.json.*;

public abstract class Config {
	
		//the following list is the default control key
		private static ControlKey[] controlsCodes={new ControlKey("Escape", KeyCode.ESCAPE),
				new ControlKey("Chat", KeyCode.T)};
		public static final int ESCAPE_KEY_INDEX=0;
		
		private static ControlKey[]devControlsCodes={new ControlKey("Console", KeyCode.F11)};
		//the black list is used to prevent the binding of some key 
		public static final KeyCode[] blackList={KeyCode.ENTER};
		private static String version="v unknowed";
		//size of the Stage (width,height)
		private static double[] size={1000,700};
		private static boolean alwaysDisplayNames=false;
		static boolean fullScreen=false;
		public final static boolean inDev=true;
		public static double[][] stageResolution={{720,480},{1024,768},{1600,900},{1920,1080}};
		public static ArenaText arenaText = new ArenaText();
		//all properties in this class
		public static final String[] property={"fullscreen","size"};
		private static final String JSON_FILE_PATH="./resources/config.json";
		private static final int JSON_VERSION=1;
		private static final String[] JSON_ELEM={"Json version","Controls codes","Language","Size","Full screen","always display names"};
		
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
				retrieve();
			} else{
				retrieve();
			}
			
			//we now set up the maximum size of the window
			int[] maxSize={(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()};
			System.out.print("Stage resolutions available : ");
			for (int k=0 ; k<stageResolution.length ; k++){
				if (maxSize[0]<stageResolution[k][0] || maxSize[1]<stageResolution[k][1]){
					stageResolution[k]=new double[] {0,0};
				}
				System.out.print(stageResolution[k][0]+","+stageResolution[k][1]+"  ");
			}
			System.out.println((" "));
			
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
			if (i<devControlsCodes.length){
				return devControlsCodes[i].getCode();
			}
			else{
				return devControlsCodes[0].getCode();
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
			if (i<devControlsCodes.length){
				return devControlsCodes[i];
			}
			else{
				return devControlsCodes[0];
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
			devControlsCodes[0]=new ControlKey("Console", KeyCode.F11);
		}
		
		
		/**
		 * @return the controlsCodes
		 */
		public static ControlKey[] getControlsCodes() {
			return controlsCodes;
		}


		/**
		 * @return the devControlsCodes
		 */
		public static ControlKey[] getdevControlsCodes() {
			return devControlsCodes;
		}
		
		/**
		 * save the configuration as a .json file
		 */
		public static void save(){
			
			//we create the skeleton of the json file
			JsonObjectBuilder model = Json.createObjectBuilder();
			
			//JSON version number
			model.add(JSON_ELEM[0], JSON_VERSION);
			
			//control keys
			JsonObjectBuilder cKArray = Json.createObjectBuilder();
			for (int k =0 ; k<controlsCodes.length;k++){
				cKArray.add(controlsCodes[k].getName(),controlsCodes[k].getKeyName());
			}
			
			model.add(JSON_ELEM[1], Json.createArrayBuilder()
					.add(cKArray));
			//language
			model.add(JSON_ELEM[2], arenaText.getLang());
			//size
			model.add(JSON_ELEM[3], Json.createArrayBuilder()
					.add(Json.createObjectBuilder()
							.add("Width",size[0])
							.add("Height",size[1])));
			
			//full screen
			model.add(JSON_ELEM[4], fullScreen);
			
			//always display names
			model.add(JSON_ELEM[5], alwaysDisplayNames);
			
			//build the json object
			JsonObject jsonFie= model.build();
			//we write it
			try {
				JsonWriter jsonWriter = Json.createWriter(new FileWriter(JSON_FILE_PATH));
				jsonWriter.writeObject(jsonFie);
				jsonWriter.close();
			} catch (IOException e) {
				Main.console.println("Unable to write the json file !");
			}
			
			try {
				BeautifullJson.clean(JSON_FILE_PATH);
			} catch (IOException e) {
				System.out.println("Unable to clean the .Json file");
			}
		}
		
		public static void retrieve(){
			// we read the Json file
			try {
				JsonReader reader = Json.createReader(new FileReader(JSON_FILE_PATH));
				JsonStructure jsonst = reader.read();
				reader.close();
				//we retrieve the elements saved
				navigateJsonTree(jsonst,null);
				
			} catch (FileNotFoundException e) {
				Main.console.println("config.ser file is missing, switching to default configuration");
				save();
			}
		}
		
		@SuppressWarnings("incomplete-switch")
		private static void retrieveElement(JsonValue tree,int k){
			//k is used to remember which element of the json file we are currently retrieving
			switch(tree.getValueType()){
			case STRING :
				switch(k){
				case 2:
					Config.arenaText=new ArenaText(((JsonString)tree).getString());
					break;
				}
				
				break;
				
			case ARRAY:
				//if it's a list
				JsonArray array = (JsonArray) tree;
		         for (JsonValue val : array){
		        	 navigateJsonArray(val, null,k,0);
		         
		         }
		         break;
			case TRUE:
			case FALSE:
				switch(k){
				case 4:
					fullScreen=(tree.getValueType()==javax.json.JsonValue.ValueType.TRUE);
					break;
				case 5:
					Config.alwaysDisplayNames=(tree.getValueType()==javax.json.JsonValue.ValueType.TRUE);
					break;
				}
			}
		}
		
		/**
		 * quick way to retrieve an array of element
		 * @param tree
		 */
		@SuppressWarnings("incomplete-switch")
		private static void navigateJsonArray(JsonValue tree,String nom ,int k, int index){
			 switch(tree.getValueType()) {
		      case OBJECT:
		    	  //if we just received a list
		         JsonObject object = (JsonObject) tree;
		         for (String name : object.keySet()){
		        	 //we iterate through the list 
		        	 navigateJsonArray(object.get(name), name,k,index);
		        	 index++;
		         }
		         break;
		      case STRING :
		    	  switch(k){
		    	  case 1: 
		    		  controlsCodes[index]=new ControlKey(nom, KeyCode.getKeyCode(((JsonString) tree).getString()));
		    	  }
		      case NUMBER:
		    	  switch(k){
		    	  case 3: 
		    		  size[index]=((JsonNumber) tree).intValue();
		    	  }
			 }
		}
		
		
		/**
		 * navigate through the json file
		 * @param tree
		 * @param key
		 */
		public static void navigateJsonTree(JsonValue tree, String key) {
			   if (key != null)
				   //if we haven't reach a known element
				   for (int k=0  ; k<JSON_ELEM.length ; k++){
					   if (key.equals(JSON_ELEM[k])){
						   retrieveElement(tree,k);
					   }
				   }
			   switch(tree.getValueType()) {
			   
			      case OBJECT:
			         JsonObject object = (JsonObject) tree;
			         for (String name : object.keySet())
			        	 navigateJsonTree(object.get(name), name);
			         break;
			         
			      default :
			    	  break;
			   }
		}
		
		public static double getSizeW(){
			if (fullScreen){
				return Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			}
			else{
			return Config.size[0];
			}
		}
		
		public static double getSizeH(){
			if (fullScreen){
				return Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			}
			else{
			return Config.size[1];
			}
		}
		
		public static boolean isFullScreen(){
			return Config.fullScreen;
		}
		
		public static boolean alwaysDisplayNames(){
			return Config.alwaysDisplayNames;
		}
		
		public static void setAlwaysDisplayName (boolean b){
			Config.alwaysDisplayNames=b;
		}
		
		public static boolean updateLang(String pLang){
			//change the language
			return arenaText.setLang(pLang);
		}
		public static void setFullScreen(boolean b){
			fullScreen=b;
			if(b){
			Main.resize(new double[]{Toolkit.getDefaultToolkit().getScreenSize().getWidth(),Toolkit.getDefaultToolkit().getScreenSize().getHeight()});
			Main.getStage().setX(0);
			Main.getStage().setY(0);
			}
			else{
				Main.resize(size);
				Main.getStage().centerOnScreen();
			}
		}
		
		public static void describe(){
			Main.console.println("State of the config class :");
			Main.console.println("Version of the application : "+version);
			Main.console.println("Fullscreen : "+fullScreen);
			Main.console.println("Size : "+size[0]+" by "+size[1]);
			Main.console.println("In development : "+inDev);
			Main.console.println("Current language : "+arenaText.getLang());
		}
		
		public static void changeProperty(String propertyName, String value){
			switch(propertyName){
			case "fullscreen":
				if (value.startsWith("true")||value.startsWith("1")){
					setFullScreen(true);
					Main.console.println("Fullscreen set to "+fullScreen);
				}
				else if (value.startsWith("false")||value.startsWith("0")){
					setFullScreen(false);
					Main.console.println("Fullscreen set to "+fullScreen);
				}
				break;
			case "size":
				setSize(value);
				break;
			default:
				Main.console.println("Unknowed property");
			}
		}


		public static void printProperty(String propertyName) {
			switch(propertyName){
			case "fullscreen":
				Main.console.println("Fullscreen : "+fullScreen);
				break;
			case "version":
				Main.console.println("Version of the application : "+version);
				break;
			case "inDev":
				Main.console.println("In development : "+inDev);
				break;
			default:
				Main.console.println("Unknowed property");
			}
		}
		
		/**
		 * set the size of the Stage
		 * @param size
		 */
		public static void setSize(double[] size){
			Config.size=size;
			Main.resize(size);
		}
		
		/**
		 * set the size of the Stage when using the console
		 * @param size
		 */
		public static void setSize(String strSize){
			double[] size={0 , 0};
			try{
				size[0]=(Integer.parseInt(strSize.split(",")[0]));
				size[1]=(Integer.valueOf(strSize.split(",")[1]));
			}catch (Exception e){
				Main.console.println("Unable to parse the given string to an double array (Config.setSize)");
			}
			if (size[0]!=0){
				setSize(size);
			}
		}
}
