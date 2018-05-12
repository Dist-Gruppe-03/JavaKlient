package galgeleg;

import java.net.URL;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;


public class Galgeklient {

    public static void main(String[] arg) throws Exception {
        //URL url = new URL("http://ubuntu4.saluton.dk:9924/galgeleg?wsdl");
        URL url = new URL("http://localhost:9924/galgelegtjeneste?wsdl");
        QName qname = new QName("http://galgeleg/", "GalgelogikService");
        Service service = Service.create(url, qname);
        boolean spilAktivt = true;
        String bruger;

        GalgeI spil = service.getPort(GalgeI.class);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Velkommen til galgeleg.");
        System.out.println("Log ind for at spille");
        
        while(true) {
        System.out.println("Indtast brugernavn: ");
        String brugernavn = scanner.nextLine();

        System.out.println("Indtast password: ");
        String password = scanner.nextLine();
        
        if (spil.hentBruger(brugernavn, password)) {
            bruger = brugernavn;
            System.out.println("Velkommen " + bruger);
            spil.nulstil(bruger);
            spil.getHighscores();
            break;
        } else {
            System.out.println("Forkert login - prøv igen");
        }
        }

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
                        System.out.println("korrekt "+bogstav+ " var en del af ordet.");
                        if (spil.erSpilletVundet(bruger) == true) {
                            System.out.println("Du har vundet, ordet var: " + spil.getOrdet(bruger));

                            spil.highscoreCheck(bruger, spil.getAntalForkerteBogstaver(bruger));

                            System.out.println("Vil du spille igen? skriv Y");
                            bogstav = scanner.next();
                            System.out.println(bogstav);
                            if (bogstav.equals("Y") ||bogstav.equals("y")) {
                                System.out.println("Nyt spil starter");
                                spil.nulstil(bruger);
                            } else {

                                spilAktivt = false;
                            }
                        }
                    } else {

                        if (spil.erSpilletTabt(bruger) == true) {
                            System.out.println("Du har gættet forkert for mange gange, du har tabt.");
                            System.out.println("Ordet var: " + spil.getOrdet(bruger));

                            System.out.println("User: " + bruger);

                            System.out.println("Vil du spille igen? skriv Y");
                            bogstav = scanner.next();
                            System.out.println(bogstav);
                            if (bogstav.equals("Y")) {
                                System.out.println("Nyt spil starter");
                                spil.nulstil(bruger);
                            } else {

                                spilAktivt = false;
                            }
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
