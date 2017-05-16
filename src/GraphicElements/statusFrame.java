package GraphicElements;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDA.Status;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;

public class statusFrame extends JFrame{
	
	JFrame mainFrame;
	Label statusLabel;
	private Status connected;
	private JDA jda;
	private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Button conn;

	public statusFrame(JDA jda) {
		mainFrame = this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.connected = jda.getStatus();
		this.jda = jda;
		this.setStatusImg();
		this.pack();
		this.setLocation((int)Math.floor(screenSize.getWidth()/2), (int)Math.floor(screenSize.getHeight()/2));
		this.setResizable(false);
		//this.setButton();
		
		this.setSize(this.getWidth(), this.getHeight()+10);
		
		this.setVisible(true);
		
	}
	
	private void setButton(){
		this.conn = new Button();
		
		if(jda.getStatus() == Status.CONNECTED){
			conn.setLabel("DECONNEXION");
		}
		else if(jda.getStatus() == Status.SHUTDOWN){
			conn.setLabel("CONNEXION");
		}
		
		this.add(conn, BorderLayout.PAGE_END);
		conn.addActionListener(new ConnectorAction());
		
	
	}
	
	private void setStatusImg(){
		statusLabel = new Label();

		
		this.getContentPane().add(statusLabel, BorderLayout.CENTER);
	
		
		if(connected == Status.CONNECTED){
			for(Guild g : jda.getGuilds()){
				statusLabel.setText(String.format("%s est connecté au serveur %s", 
						jda.getGuildById(g.getId()).getMember(jda.getSelfUser()).getEffectiveName(), 
						jda.getGuildById(g.getId()).getName()));
			}
			
			//234661550282113026 sausage
			//311953226851155968 test
			statusLabel.setBackground(Color.GREEN);
		}
		else if(connected == Status.SHUTDOWN){
			statusLabel.setText(String.format("%s est déconnecté", 
					jda.getSelfUser().getName()));
			statusLabel.setBackground(Color.RED);
		}
		else{
			statusLabel.setText("UNKNOWN STATUS");
			statusLabel.setBackground(Color.YELLOW);
		}
	}
		
	/**
	 * Private class to handle actions on UI elements
	 * @author Guillaume
	 *
	 */
	private class ConnectorAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(jda.getStatus());
			if(jda.getStatus() == Status.CONNECTED){
				jda.setAutoReconnect(false);
				jda.shutdown();
				setStatusImg();
				setButton();
			}
			else if(jda.getStatus() == Status.SHUTDOWN){
			
				setStatusImg();
				setButton();
			}
		}
		
	}
}
