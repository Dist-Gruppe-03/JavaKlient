package galgeleg;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Galgeklient{
	
	public static void main(String[] arg) throws Exception {
		String brugernavn;
		//URL url = new URL("http://ubuntu4.saluton.dk:9924/galgeleg?wsdl");
		URL url = new URL("http://localhost:9924/galgelegtjeneste?wsdl");
		QName qname = new QName("http://galgeleg/", "GalgelogikService");
		Service service = Service.create(url, qname);
		boolean spilAktivt = false;

		GalgeI spil = service.getPort(GalgeI.class);
		GalgeGUI GUI = new GalgeGUI(spil);

		//spil.nulstil();
		//spil.hentOrdFraDr();

		Scanner scanner = new Scanner(System.in);

		System.out.println("Velkommen til galgeleg.");
		System.out.println("Log ind for at spille");
		
		
		System.out.println("Indtast brugernavn: "); 
		String bruger = scanner.nextLine();		   	
				  
		System.out.println("Indtast password: "); 
		String password = scanner.nextLine();
		
		 if (spil.hentBruger(bruger, password)){
			  System.out.println("Velkommen " + bruger); 
			  spilAktivt = true;
		 	}
		 else
		 System.out.println("Forkert login - prøv igen");
		 

		//GUI.GUI();

		//while (GUI.login() == false) {

			//Thread.sleep(100);  
			 
			  
		//)}

		while (spilAktivt) {
			System.out.println("Gæt ordet: " + spil.getSynligtOrd(bruger));
			System.out.println("Dine gæt: " + spil.getBrugteBogstaver(bruger));
			System.out.println("Gæt på et bogstav");
			System.out.println("det rigtige ord er: " + spil.getOrdet(bruger));
			String bogstav = scanner.next();
			if (bogstav.matches("[a-zA-ZæøåÆØÅ]") && bogstav.length() == 1) {
				if (spil.getBrugteBogstaver(bruger).contains(bogstav)) {
					System.out.println("Du har allerede gættet på: " + bogstav);
				} else {
					spil.gætBogstav(bogstav, bruger);
					if (spil.erSidsteBogstavKorrekt(bruger)) {

						if (spil.erSpilletVundet(bruger) == true) {
							System.out.println("Du har vundet, ordet var: " + spil.getOrdet(bruger));
							spil.nulstil(bruger);
							spil.highscoreCheck(GUI.getBruger(), spil.getAntalForkerteBogstaver(bruger));
						}
					} else {

						if (spil.erSpilletTabt(bruger) == true) {
							System.out.println("Du har gættet forkert for mange gange, du har tabt.");
							System.out.println("Ordet var: " + spil.getOrdet(bruger));
							spil.nulstil(bruger);
							String user = GUI.getBruger();
							System.out.println("User: " + user);
						}
					}
				}
			} else {
				System.out.println("Ikke ét bogstav, prøv igen");
			}
		}

		scanner.close();
	}
		
}
