/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import RMICarRental.ICarRentalServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Laudelino
 */
class Server extends UnicastRemoteObject implements ICarRentalServer 
{
    public Server() throws RemoteException
    {

    }

        //método que gera de forma randomica o Id do cliente
    @Override
    public synchronized int generateId() throws RemoteException 
    {
        return 0;
    }
    
        //Consulta
    public synchronized int checkAvailability() throws RemoteException
    {
        return 0;
    }
    
    //locação de veiculos
    public synchronized int rentACar()  throws RemoteException
    {
        return 0;
    }
    
    //registrar interesse
    public synchronized int subscribeCar() throws RemoteException
    {
        return 0;
    }
} 
