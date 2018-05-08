package galgeleg;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sun.glass.events.WindowEvent;

public class GalgeGUI implements ActionListener {
	
	private GalgeI spil;
	private boolean login = false;
	JFrame frame = new JFrame("Galgeleg");		
	JPanel newPanel = new JPanel(new GridBagLayout());
	JLabel labelBruger, labelKode, besked;
	JTextField brugernavn;
	JPasswordField kodeord;
	JButton loginKnap;
	String bruger;

	public GalgeGUI(GalgeI spil) {
		this.spil = spil;
	}

	public void GUI() {
		
		frame.setSize(300, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.add(newPanel);

		newPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Login Panel"));

		newPanel.setLayout(null);

		labelBruger = new JLabel("Indtast brugernavn: ");
		labelBruger.setBounds(20,20,200,25);
		newPanel.add(labelBruger);

		labelKode = new JLabel("Indtast kodeord: ");
		labelKode.setBounds(20,60,200,25);
		newPanel.add(labelKode);

		besked = new JLabel("");
		besked.setBounds(20,160,200,25);
		newPanel.add(besked);

		brugernavn = new JTextField(20);
		brugernavn.setBounds(20,40,200,25);
		newPanel.add(brugernavn);

		kodeord = new JPasswordField(20);
		kodeord.setBounds(20,80,200,25);
		newPanel.add(kodeord);

		loginKnap = new JButton("Login");
		loginKnap.setBounds(20,120,80,25);
		newPanel.add(loginKnap);
		loginKnap.addActionListener(this);
		
		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        bruger = brugernavn.getText();
        String password = String.valueOf(kodeord.getPassword());
        
        try {
			if (spil.hentBruger(bruger, password)){
			    this.login = true;
			    //db
			    Class.forName("com.mysql.jdbc.Driver");
			    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/galgeleg?user=root&password=mvc23");
			    
			    //select the current user from users-table
			    PreparedStatement st = con.prepareStatement("select * from users where username = ?" );
			    st.setString(1, bruger);
			    ResultSet r1=st.executeQuery();
			    if(r1.next()) {
			    }
			    else { //if user not in db, insert.
			    st = con.prepareStatement("insert into users (username, highscore) values(?,?)");
			    st.setString(1, bruger);
			    st.setInt(2, 0);
			    st.executeUpdate();
			    }
			    frame.setVisible(false);
			}
			else {
				besked.setText("Forkert login - prøv igen");
			}
		} catch (RemoteException | SQLException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public String getBruger() {
		return bruger;
	}
	
	public boolean login() {
		return this.login;
	}
}
