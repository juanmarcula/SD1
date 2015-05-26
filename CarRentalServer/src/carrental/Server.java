/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import Interface.ServidorInterface;
import RMICarRental.ICarRentalClient;
import RMICarRental.ICarRentalServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Laudelino
 */
public class Server extends UnicastRemoteObject implements ICarRentalServer 
{
    ArrayList<Car> cars;
    private ServidorInterface tela;
    
    public Server() throws RemoteException
    {
        cars = new ArrayList<>();
        cars.add(new Car("Uno", 75.9));
        cars.add(new Car("Gol", 99.9));
        cars.add(new Car("Corsa", 80.9));
        tela = new Interface.ServidorInterface(this);
        tela.setVisible(true);
    }

    //método que gera de forma randomica o Id do cliente
    @Override
    public synchronized int generateId() throws RemoteException 
    {
        int a = (int) (Math.random() * 10);
        System.out.println("Id gerada: " + a);
        return a;
    }
    
    public ArrayList<Car> getCars()
    {
        return cars;
    }
    
    /**
     * Recebe um objeto rent, contendo datas e locais
     * E um objeto contendo o veiculo a ser alugado
     * @param r
     * @param c
     * @return valor da diaria se estiver disponivel, -1 se não estiver disponivel
     * @throws RemoteException 
     */
    @Override
    public double checkAvailability(Rent r, String c) throws RemoteException
    {
        try
        {
            for(Car aux : cars)
            {
                if(aux.name.equals(c))
                {
                    return aux.checkAvailability(r);
                }
            }
            return -1;
        }
        catch (Exception e)
        {
            return -1;
        }
    }
    
     /**
     * Recebe um objeto rent, contendo datas, locais e dados do cartão de credito
     * E um objeto contendo o veiculo a ser alugado
     * @param r
     * @param c
     * @return true em caso de sucesso, false caso contrario
     * @throws RemoteException 
     */
    public boolean rentACar(Rent r, String c)  throws RemoteException
    {
        try
        {
            for(Car aux : cars)
            {
                if(aux.name.equals(c))
                {
                    return aux.rent(r);
                }
            }
            return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
     /**
     * Recebe um objeto contendo o veiculo que se tem interesse
     * @param c
     * @return true em caso de sucesso, false caso contrario
     * @throws RemoteException 
     */
    public boolean subscribeCar(ICarRentalClient c, String car) throws RemoteException
    { 
        System.out.println("oi");
        try
        {
            for(Car aux : cars)
            {
                if(aux.name.equals(car))
                {
                    return aux.subscribe(c);
                }
            }
            return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    /**
     * Unico metodo que pode alterar diretamento a diaria de um carro no servidor
     * Define a nova diaria, e envia para os clientes 
     * @param c
     * @param rate
     * @return 
     */
    public boolean setNewRate(String c, double rate)
    {
        try
        {
            for(Car aux : cars)
            {
                if(aux.name.equals(c))
                {
                    //Se o preço esta diminuindo, envia notificação
                    if(aux.rate > rate)
                    {
                        aux.rate = rate;
                        this.sendNotification(aux);
                    }
                    aux.rate = rate;
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
    
    private boolean sendNotification(Car c)
    {
       
        try
        {
            for(ICarRentalClient aux : c.subscribers)
            {
                aux.notification(c.name, c.rate);
            }
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
} 
