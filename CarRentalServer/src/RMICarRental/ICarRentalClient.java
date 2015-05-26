/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMICarRental;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Juan e Laudelino
 */
public interface ICarRentalClient extends Remote 
{
    //Recebe notificação de evento
    public boolean notification(String c, double rate) throws RemoteException;
}
