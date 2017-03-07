package InputOutputFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import graphics.Main;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ExportText {
	private boolean success=false;

	public ExportText(String fileName,String content) {
		// TODO Auto-generated constructor stub

		BufferedWriter writer = null;
		FileWriter fileWriter = null;
		
		//we create the file
		String filePath="./"+fileName+".txt";
		File file=new File(filePath);
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try{
			//relative path
			fileWriter =new FileWriter(filePath);
			writer =new BufferedWriter(fileWriter);

			writer.write(content);
			System.out.println("write");
			//if the file was successfully opened
			if (writer!=null){writer.close();
			}
			if(fileWriter!=null){
				fileWriter.close();
			}
			
			this.success=true;
			
		}
		catch(Exception e){
			Main.console.println("Unable to export the content of the given text");
		}
	}

	public ExportText(String fileName,TextFlow tF) {
		// TODO Auto-generated constructor stub

		BufferedWriter writer = null;
		FileWriter fileWriter = null;
		
		//we create the file
		String filePath="./"+fileName+".txt";
		File file = new File(filePath);
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try{
			//relative path
			fileWriter =new FileWriter(filePath);
			writer =new BufferedWriter(fileWriter);

			//writer.write(content);

			ObservableList<Node> content=tF.getChildren();
			for (Node n :content){
				try{
					writer.write(((Text)(n)).getText());
					writer.newLine();
					System.out.println(((Text)(n)).getText().toString());
					
				}catch(ClassCastException e){
					e.printStackTrace();
					System.out.println("conversion impossible");
				}
			}

			//if the file was successfully opened
			if (writer!=null){writer.close();
			}
			if(fileWriter!=null){
				fileWriter.close();
			}
			
			this.success=true;
		}
		catch(Exception e){
			Main.console.println("Unable to export the content of the TextFlow object");
		}
	}
	
	public boolean getSuccess(){
		return this.success;
	}
}