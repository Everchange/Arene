package ressources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


import graphics.Main;

public class ArenaText {

	private String lang = "English";
	public static final String[] langs={"Français","English"};
	public static final int  startBt=0,backBt=1,optionBt=2,newCharacterBt=3,restartBt=4,quitBt=5,languageBt=6,
			graphicsBt=7,controlsBt=8;
	private static boolean preventLoop=false;
	private ArrayList<String> text=new ArrayList<String>();
	
	/**
	 * Default language : English
	 */
	public ArenaText(){
		retrieve();
	}

	/**
	 * Create a new Arena text with the given language
	 * @param pLang a language from ArenaText.lang array
	 */
	public ArenaText(String pLang){

		setLang(pLang);
		retrieve();

	}
	/**
	 * @return the current language
	 */
	public String getLang() {
		return this.lang;
	}
	/**
	 * 
	 * @param pLang a language from ArenaText.lang array
	 * @return true or false (success or fail)
	 */
	public boolean setLang(String pLang){
		for (String lang : langs){
			//System.out.println(lang.replaceAll("[^\\p{L}\\p{Z}]");
			if (lang.toLowerCase().equals(pLang) || lang.toLowerCase().contains(pLang)){
				if (!(this.lang.toLowerCase().equals(pLang) || this.lang.toLowerCase().contains(pLang))){
					this.lang=lang;
					retrieve();

					//update language 
					Main.updateLang();
					return true;
				}
				else{
					Main.console.println("Language already set to : "+this.lang);
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * retrieve the text in the language from the ".lang" file
	 */
	private void retrieve()  {

		ArrayList<Integer> contentB=new ArrayList<Integer>();


		BufferedReader bufR;
		FileReader filR ;
		/*try{
			filR=new FileReader()
		}catch(FileNotFoundException e){
			filR=new FileReader("./resources/"+this.lang+".lang");
		}*/ //for resources pack 


		try{
			filR=new FileReader("./resources/"+this.lang.toLowerCase()+".lang");
			bufR = new BufferedReader(filR);

			int byteR=0;

			while(byteR!=-1){
				byteR=bufR.read();
				contentB.add(byteR);
			}


			bufR.close();
			filR.close();
			System.out.println("retrieve lang : "+this.lang);


		}catch(FileNotFoundException e){

			System.out.println("The "+this.lang.toLowerCase()+".lang file is missing");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Can't read the file");
		}
		//remove the old language
		this.text.clear();
		//create the new
		String txt="";
		for (int k =0 ; k<contentB.size();k++){
			//convert bytes to text
			if (contentB.get(k)==29 || contentB.get(k)==-1){
				//if we reach a breakpoint or the end of the bytes array
				if (!txt.isEmpty()){
					//if the text array is not empty
					this.text.add(txt);
				}
				//reset the text element
				txt="";
			}else{
				//if there is still something to convert
				txt+=(char)contentB.get(k).intValue();
			}
		}


		//if there is something missing
		if (this.text.size()!=CreateLangFile.content.length && this.lang=="English" && !preventLoop){
			preventLoop=true;
			//we recreate the ".lang" file
			CreateLangFile.create();
			retrieve();
			System.out.println("lang file recreated due to an incoherence (origin : ArenaText)");
		}
	}
	
	/**
	 * Return a text in the current language
	 * 
	 * @param index The index of the required text (See Arenatext.smth)
	 * @return a String (from the beach...)
	 */
	public String getText(int index){
		if (index>-1 && index<this.text.size()){
			return this.text.get(index);
		}
		System.out.println("Tried to get an text from ArenaText wich is out of range : "+index
				+" (max : "+(this.text.size()-1)+")");
		return "ERR .lang file";
	}

}
