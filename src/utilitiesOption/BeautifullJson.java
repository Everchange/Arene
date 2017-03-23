package utilitiesOption;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Help to have a .json file  that can be easily read
 * @author Clément Fraitot
 *
 */
public abstract class BeautifullJson {
	
	/**
	 * the given file must not have already been converted with this function
	 * @param path the path to the file
	 * @throws IOException
	 */
	public static void clean(String path) throws IOException{
		File file =new File(path);
		if (file.length()>1073741824){
			return;
		}
		BufferedReader reader = new BufferedReader(new FileReader(path));
		ArrayList<String> text=new ArrayList<String>();
		String line="";
		
		while((line=reader.readLine())!=null){
			text.add(line);
		}
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		//-1 because there is a bracket at the start and at the end of the file
		int openedBracket=-1;
		//limit per line : 150 chracater
		for (String lineT : text){
			String inter="";
			for (int k=0;k<lineT.length();k++){
				
				if (k==lineT.length()-1 &&openedBracket==0 && lineT.charAt(k)=='}'){
					writer.newLine();
					writer.newLine();
				}
				writer.write(lineT.charAt(k));
				switch(lineT.charAt(k)){
				case '{':
					if (openedBracket==-1){
						writer.newLine();
						writer.newLine();
					}
					openedBracket++;
					break;
				case '[':
					openedBracket++;
					break;
				case '}':
					openedBracket--;
					
					break;
				case ']':
					openedBracket--;
					break;
				case ',':
					if (openedBracket<1){
						writer.newLine();
						writer.newLine();
					}/*
					else if((k%350)>320){
						writer.newLine();
						for (int h =0 ; h<openedBracket ; h++){
							writer.write("\t");
						}
					}*/
					break;
				}
				
			}
		}
		writer.close();

	}
}
