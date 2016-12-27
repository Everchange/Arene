import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrameGUI extends JFrame{


	private static final long serialVersionUID = 1L;
	protected JPanel  container;
	protected String iconPath ="./data/Objnum.png";
	
	public FrameGUI(String name) {
		this.setTitle(name);
		this.setSize(600, 200);
		this.setIconImage(new ImageIcon(iconPath).getImage());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		container = new JPanel();
		container.setLayout(new FlowLayout());
		
		this.setContentPane(container);
	}
	
	public FrameGUI(String name,int pIntDefaultCloseOperation) {
		this.setTitle(name);
		this.setSize(600, 200);
		this.setIconImage(new ImageIcon(iconPath).getImage());
		this.setDefaultCloseOperation(pIntDefaultCloseOperation);
		this.setLocationRelativeTo(null);
		
		container = new JPanel();
		container.setLayout(new FlowLayout());
		
		this.setContentPane(container);
	}
	
	public void addJPanel(JPanel pan){
		this.getContentPane().add(pan);
		pan.setBounds(10,10, 50, 50);
	}
	
	public void setIcon(String path){
		this.setIconImage(new ImageIcon(path).getImage());
		this.repaint();
	}
	/**
	 * 
	 * @param str an HTML text
	 */
	public void addTxt(String str,boolean display){
		JLabel txtLabel = new JLabel();
		txtLabel.setText("<html>"+str+"</html>");
		this.getContentPane().add(txtLabel);
		txtLabel.setBounds(10,100, 50, 50);
		if (display){
			this.display();
		}
	}
	
	public void display(){
		this.repaint();
		this.setVisible(true);
	}

}
