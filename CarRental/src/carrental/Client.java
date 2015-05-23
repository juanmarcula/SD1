/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import RMICarRental.ICarRentalClient;
import RMICarRental.ICarRentalServer;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Laudelino
 */
public class Client extends UnicastRemoteObject implements ICarRentalClient  
{
    //RMI
    Registry r;
    ICarRentalServer rent;
    
    //Design
    Interface.ClienteInterface tela;
    
    //Local
    int id = 0;
    ArrayList<Car> cars;
    
    public Client() throws RemoteException
    {
        try 
        {
            r = LocateRegistry.getRegistry("localhost", 1010);
            rent = (ICarRentalServer) r.lookup("CarRental"); //localizar o server

            cars = new ArrayList<>();
            cars = rent.getCars();
            tela = new Interface.ClienteInterface();

            
            
            //o servidor vai gerar um ID, armazenar e retornar para cliente
            System.out.println("Id antes: " + id);
            id = rent.generateId();
            System.out.println("Id depois: " + id);
            
            //cadastra o nome associado com o id
            //rent.setName(nome, id);
            //coloca as informações na interface gráfica
            tela.setVisible(true);
            //System.out.println("Cliente \"" + nome + "\" criado com ID:" + id);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(CarRentalClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public synchronized boolean notification(String c, double rate) throws RemoteException
    {
        try
        {
            for(Car aux : cars)
            {
                if(aux.name.equals(c))
                {
                    aux.rate = rate;
                    JOptionPane.showConfirmDialog(tela, "Carro " + c + " esta com diaria R$" + rate);
                    return true;
                }
            }
            return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }    
}
