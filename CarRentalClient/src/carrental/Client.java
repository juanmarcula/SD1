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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan e Laudelino
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
            r = LocateRegistry.getRegistry("localhost", 8099);
            rent = (ICarRentalServer) r.lookup("CarRental"); //localizar o server

            cars = new ArrayList<>();
            cars = rent.getCars();
            tela = new Interface.ClienteInterface(this);

            
            
            //o servidor vai gerar um ID, armazenar e retornar para cliente
            System.out.println("Id antes: " + id);
            id = rent.generateId();
            System.out.println("Id depois: " + id);
            
            //cadastra o nome associado com o id
            //rent.setName(nome, id);
            //coloca as informações na interface gráfica
            tela.setCars(cars);
            tela.setVisible(true);
            //System.out.println("Cliente \"" + nome + "\" criado com ID:" + id);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(CarRentalClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Verifica se o carro está disponivel e retorna sua diaria
     * 
     * @param car
     * @param pickUpPlace
     * @param dropOffPlace
     * @param pickUpDate
     * @param dropOfDate
     * @return 
     */
    public double checkAvailabilityServer(String car, String pickUpPlace, 
            String dropOffPlace, Date pickUpDate, Date dropOfDate)
    {
        try
        {
            return rent.checkAvailability(new Rent(pickUpPlace, dropOffPlace, pickUpDate, dropOfDate), car);
        }
        catch(Exception e)
        {
            return -1;
        }
    }
    
    /**
     * Tenta alugar um carro e retorna se foi possivel ou não, True ou False
     * 
     * @param car
     * @param pickUpPlace
     * @param dropOffPlace
     * @param pickUpDate
     * @param dropOfDate
     * @param driverAge
     * @param ccNumber
     * @param ccCode
     * @param ccName
     * @param ccExpDate
     * @return 
     */
    public boolean rentACarServer(String car, String pickUpPlace, 
            String dropOffPlace, Date pickUpDate, Date dropOfDate, int driverAge,
            String ccNumber, String ccCode, String ccName, String ccExpDate)
    {
        try
        {
            return rent.rentACar(new Rent(pickUpPlace, dropOffPlace, pickUpDate, dropOfDate, driverAge,
            ccNumber, ccCode, ccName, ccExpDate), car);
        }
        catch(Exception e)
        {
            return false;
        }
    }
    /**
     * Registra interrese em um carro.
     * 
     * @param car
     * @return 
     */
    public boolean subscribeCarServer(String car)
    {
        try
        {
            return rent.subscribeCar(this, car);
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    /**
     * Gera uma notificação na tela do usuario avisando que o valor da diaria de um carro foi alterada
     * @param c
     * @param rate
     * @return
     * @throws RemoteException 
     */
    //RMI
    @Override
    public synchronized boolean notification(String c, double rate) throws RemoteException
    {
        System.out.println("OIIII");
        try
        {
            for(Car aux : cars)
            {
                if(aux.name.equals(c))
                {
                    aux.rate = rate;
                    //JOptionPane.showConfirmDialog(tela, );
                    JOptionPane.showMessageDialog(tela,"Carro " + c + " esta com diaria R$" + rate,"O gerente ficou maluco!!!!",JOptionPane.PLAIN_MESSAGE);
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
