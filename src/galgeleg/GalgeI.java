package galgeleg;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface GalgeI extends java.rmi.Remote {

    @WebMethod public ArrayList<String> getBrugteBogstaver(String bruger) throws java.rmi.RemoteException;

    @WebMethod public String getSynligtOrd(String bruger) throws java.rmi.RemoteException;

    @WebMethod public String getOrdet(String bruger) throws java.rmi.RemoteException;

    @WebMethod public int getAntalForkerteBogstaver(String bruger) throws java.rmi.RemoteException;

    @WebMethod public boolean erSidsteBogstavKorrekt(String bruger) throws java.rmi.RemoteException;

    @WebMethod public boolean erSpilletVundet(String bruger) throws java.rmi.RemoteException;

    @WebMethod public boolean erSpilletTabt(String bruger) throws java.rmi.RemoteException;

    @WebMethod public boolean erSpilletSlut(String bruger) throws java.rmi.RemoteException;

    @WebMethod public void nulstil(String bruger) throws java.rmi.RemoteException;

    @WebMethod public void opdaterSynligtOrd(String bruger) throws java.rmi.RemoteException;

    @WebMethod public void gætBogstav(String bogstav, String bruger) throws java.rmi.RemoteException;

    @WebMethod public void logStatus(String bruger) throws java.rmi.RemoteException;
    
    @WebMethod boolean hentBruger(String brugernavn, String adgangskode) throws java.rmi.RemoteException;

    @WebMethod public void highscoreCheck(String bruger, int score) throws java.rmi.RemoteException, ClassNotFoundException, SQLException;
}