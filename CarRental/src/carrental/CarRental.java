package carrental;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Laudelino
 */
public interface CarRental  extends Remote 
{
    public int generateId() throws RemoteException;
}
