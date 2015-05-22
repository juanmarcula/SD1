/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import RMICarRental.ICarRentalClient;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Laudelino
 */
public class Client extends UnicastRemoteObject implements ICarRentalClient  
{
    public Client() throws RemoteException
    {

    }
    
    @Override
    public synchronized int notification() throws RemoteException
    {
        return 0;
    }    
}
