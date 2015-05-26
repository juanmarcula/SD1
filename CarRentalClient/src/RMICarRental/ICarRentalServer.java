package RMICarRental;

import carrental.Car;
import carrental.Client;
import carrental.Rent;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Juan e Laudelino
 */
public interface ICarRentalServer  extends Remote 
{
    public int generateId() throws RemoteException;
    
    public ArrayList<Car> getCars() throws RemoteException;
    
    //Consulta
    public double checkAvailability(Rent r, String c) throws RemoteException;
    
    //locação de veiculos
    public boolean rentACar(Rent r, String c)   throws RemoteException;
    
    //registrar interesse
    public boolean subscribeCar(ICarRentalClient c, String car) throws RemoteException;
    
}
