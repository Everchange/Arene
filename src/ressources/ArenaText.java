package ressources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


import graphics.Main;

public class ArenaText {

	private String lang = "English";
	private final String[] langs={"Français","English"};

	private ArrayList<String> text=new ArrayList<String>();
	
	public ArenaText(){}

	public ArenaText(String pLang){

		setLang(pLang);
		retrieve();

	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String pLang){
		for (String lang : langs){
			//System.out.println(lang.replaceAll("[^\\p{L}\\p{Z}]");
			if (lang.toLowerCase().equals(pLang) || lang.toLowerCase().contains(pLang)){
				if (!(this.lang.toLowerCase().equals(pLang) || this.lang.toLowerCase().contains(pLang))){
					this.lang=lang;
					retrieve();
				}
				else{
					Main.console.println("Language already set to : "+this.lang);
				}
				return;
			}
		}

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
		

	}
	
	public String startBt(){
		return this.text.get(0);
	}
	
	public String backBt(){
		return this.text.get(1);
	}
	
	public String optionBt(){
		return this.text.get(2);
	}
	
	public String newCharacterBt(){
		return this.text.get(3);
	}
	
	public String restartBt(){
		return this.text.get(4);
	}
	
	public String quitBt(){
		return this.text.get(5);
	}
	
	public String languageBt(){
		return this.text.get(6);
	}

	public String graphicsBt() {
		return this.text.get(7);
	}
}
