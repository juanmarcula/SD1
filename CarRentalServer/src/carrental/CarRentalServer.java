package carrental;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Laudelino
 */
public class CarRentalServer 
{
    public static void main(String[] args){
        try 
        {
            Registry r = LocateRegistry.createRegistry(8099);
            Server obj;
            obj = new Server();
            
            r.rebind("CarRental", obj);
            System.out.println("Server running...");
            
        } 
        catch (RemoteException ex) {
            Logger.getLogger(CarRentalServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
}
