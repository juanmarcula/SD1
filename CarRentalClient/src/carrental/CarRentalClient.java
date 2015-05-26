package carrental;

import RMICarRental.ICarRentalServer;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Juan e Laudelino
 */
public class CarRentalClient 
{   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try 
        {
            Client c = new Client();
        } 
        catch (RemoteException ex) 
        {
            Logger.getLogger(CarRentalClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){}
    }
    
}
