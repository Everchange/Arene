package ressources;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public abstract class CreateLangFile {
	
	public static final String[] content ={"Start","Back","Options","Create character","Restart","Quit",
			"Language","Graphics","Controls"};

	public static void create() {
		String[] content ={"Start","Back","Options","Create character","Restart","Quit",
				"Language","Graphics","Controls"};
		
		BufferedWriter bufW;
		FileWriter filW ;
		try{
			filW=new FileWriter("./resources/english.lang");
			bufW = new BufferedWriter(filW);
			
			for (int k=0 ;k<content.length ; k++){
				bufW.write(content[k]);
				if (k!=content.length-1){
				bufW.write(29);
				}
			}

			
			bufW.close();
			filW.close();
					
		}catch(FileNotFoundException e){
				System.out.println("File missing");
		} catch (IOException e) {
			System.out.println("Can't read the file");
		}
		
	}

}
