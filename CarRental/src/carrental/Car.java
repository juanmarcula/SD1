package carrental;

import RMICarRental.ICarRentalClient;
import java.util.ArrayList;

/**
 *
 * @author Laudelino
 */
public class Car 
{
    String name;
    double rate;
    
    ArrayList<Rent> rents;
    ArrayList<ICarRentalClient> subscribers;
    //ArrayList<Double> clientInterestRate;;
    
    public Car(String n, double r)
    {
        this.name = n;
        this.rate = r;
        
        rents = new ArrayList<>();
        //subscribers = new ArrayList<>();
        
    }
    
    /**
     * Verifica se um carro esta disponivel nas datas e locais desejados
     * @param r
     * @return 
     */
    public double checkAvailability(Rent r)
    {
        try
        {
            return rate;
        }
        catch (Exception e)
        {
            return -1;
        }
    }
    
    /**
     * Tenta alugar um carro, se for possivel, retorna true, caso contrario, false
     * @param r
     * @return 
     */
    public boolean rent(Rent r)
    {
        try
        {
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    /**
     * Registra interesse no carro
     * @param c
     * @return 
     */
    public boolean subscribe(ICarRentalClient c)
    {
        try
        {
            subscribers.add(c);
            //clientInterestRate.add(rate);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
            
    public ArrayList<ICarRentalClient> getSubscribers()
    {
        return this.subscribers;
    }
}
