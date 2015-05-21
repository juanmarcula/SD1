/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Laudelino
 */
class ServerSlave extends UnicastRemoteObject implements CarRental 
{
    public ServerSlave() throws RemoteException
    {

    }

        //método que gera de forma randomica o Id do cliente
    @Override
    public synchronized int generateId() throws RemoteException 
    {
        return 0;
    }
} 
