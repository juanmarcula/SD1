package RMICarRental;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Laudelino
 */
public interface ICarRentalServer  extends Remote 
{
    public int generateId() throws RemoteException;
    
    //Consulta
    public int checkAvailability() throws RemoteException;
    
    //locação de veiculos
    public int rentACar()  throws RemoteException;
    
    //registrar interesse
    public int subscribeCar() throws RemoteException;
    
}
