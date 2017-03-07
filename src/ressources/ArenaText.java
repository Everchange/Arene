package ressources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


import graphics.Main;

public class ArenaText {

	private String lang = "English";
	private static final String[] langs={"Français","English"};
	public static final int  startBt=0,backBt=1,optionBt=2,newCharacterBt=3,restartBt=4,quitBt=5,languageBt=6,
			graphicsBt=7,controlsBt=8;

	private ArrayList<String> text=new ArrayList<String>();
	
	public ArenaText(){}

	public ArenaText(String pLang){

		setLang(pLang);
		retrieve();

	}

	public String getLang() {
		return this.lang;
	}

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

	public void retrieve()  {

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
			if (contentB.get(k)==29 || contentB.get(k)==-1){
				this.text.add(txt);
				txt="";
				
			}else{
			txt+=(char)contentB.get(k).intValue();
			}
			
		}
		
		
		//if there is something missing
		boolean test=false;
		if (this.text.size()!=CreateLangFile.content.length && this.lang=="English" && !test){
			test=true;
			//we recreate the ".lang" file
			CreateLangFile.create();
			retrieve();
			System.out.println("lang file recreated due to an incoherence (origin : ArenaText)");
		}

	}
	
	public String getText(int index){
		if (index>-1 && index<this.text.size()){
		return this.text.get(index);
		}
		System.out.println("Tried to get an text from ArenaText wich is out of range : "+index
				+" (max : "+(this.text.size()-1)+")");
		return "ERR .lang file";
	}

}
