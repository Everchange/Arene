import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class CreateLangFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedWriter bufW;
		FileWriter filW ;
		try{
			filW=new FileWriter("./english.lang");
			bufW = new BufferedWriter(filW);
			
			bufW.write("Start");
			bufW.write(29);
			bufW.write("Back");
			bufW.write(29);
			bufW.write("Options");
			bufW.write(29);
			bufW.write("Create character");
			bufW.write(29);
			bufW.write("Restart");
			bufW.write(29);
			bufW.write("Quit");
			
			bufW.close();
			filW.close();
					
		}catch(FileNotFoundException e){
				System.out.println("File missing");
		} catch (IOException e) {
			System.out.println("Can't read the file");
		}
		
	}

}
