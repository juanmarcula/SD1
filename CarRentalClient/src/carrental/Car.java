package carrental;

import RMICarRental.ICarRentalClient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Juan e Laudelino
 */
public class Car implements Serializable

{
    /**
     * PickUpPlace, acho que o pick up place tem que ser o da ultima reserva, senão não faz
     * sentido ter um lugar.
     * 
     */
    String name;
    double rate;
    
    ArrayList<Rent> rents;
    ArrayList<ICarRentalClient> subscribers;
    ArrayList<Date> notAvailable;
    //ArrayList<Double> clientInterestRate;;
    
    public Car(String n, double r)
    {
        this.name = n;
        this.rate = r;
        
        rents = new ArrayList<>();
        notAvailable = new ArrayList<>();
        subscribers = new ArrayList<>();
        
    }
    
    /**
     * Retorna o nome do carro
     * 
     * @return 
     */
    public String getName(){
        return name;
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
            ArrayList<Date> Vetor = this.VetorDeDatasEntreDuasData(r.pickUpDate, r.dropOfDate);
            if(Vetor.isEmpty()){
                return -1;
            }
            for(Date d1:Vetor){
                if(this.notAvailable.isEmpty()){
                    break;
                }
                for(Date d2:this.notAvailable){
                    if(this.diferencaEmDias(d1, d2)==0){
                        return -1;
                    }
                }
            }
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
            ArrayList<Date> Vetor = this.VetorDeDatasEntreDuasData(r.pickUpDate, r.dropOfDate);
            this.notAvailable.addAll(Vetor);
            this.rents.add(r);
            System.out.println(this.notAvailable.toString());
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
     
    /**
     * Retorna um vetor contendo os clientes que mostraram interesse nesse carro.
     * 
     * @return 
     */
    public ArrayList<ICarRentalClient> getSubscribers()
    {
        return this.subscribers;
    }
    /**
     * Calcula a diferença em dias entre a dataInicial e a dataFinal, sendo que se a dataInicial for depois da dataFinal
     * a função retorna numeros negativos, se dataInicial antes de dataFinal retorna o valor positivo de dias entre as duas
     * datas. o resultado é double pois ele retorna a fração de dias em relação a horas.
     * 
     * @param dataInicial
     * @param dataFinal
     * @return 
     */
    public double diferencaEmDias(Date dataInicial, Date dataFinal){  
        double result = 0;  
        long diferenca = dataFinal.getTime() - dataInicial.getTime();  
        double diferencaEmDias = (diferenca /1000) / 60 / 60 /24; //resultado é diferença entre as datas em dias  
        long horasRestantes = (diferenca /1000) / 60 / 60 %24; //calcula as horas restantes  
        result = diferencaEmDias + (horasRestantes /24d); //transforma as horas restantes em fração de dias  
      
        return result;  
    }
    
    /**
     * Cria um vetor de Date contendo todas as datas entre primeira e segunda, sendo que o vetor inclui a primeira e
     * a segunda
     * 
     * @param primeira
     * @param segunda
     * @return 
     */
    public ArrayList<Date> VetorDeDatasEntreDuasData(Date primeira,Date segunda){
            if(this.diferencaEmDias(primeira, segunda)<0){
                return null;
            }
            ArrayList<Date> vetor = new ArrayList<>();
            Date endDate=primeira;
            while(this.diferencaEmDias(endDate, segunda)!=0)
            {
               vetor.add(endDate);
               Calendar cal = Calendar.getInstance();
                cal.setTime(endDate); // Objeto Date() do usuário
                cal.add(cal.DAY_OF_MONTH, +1);
                endDate = cal.getTime();
            }
            vetor.add(segunda);
            return vetor;
    }
}
