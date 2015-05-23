package carrental;

import RMICarRental.ICarRentalServer;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Juan
 */
public class CarRentalClient 
{
    //RMI
    Registry r;
    ICarRentalServer rent;
    
    //Design
    Interface.ClienteInterface tela;
    
    //Local
    int id = 0;
    
    
    public CarRentalClient() 
    {
        try 
        {
            r = LocateRegistry.getRegistry("localhost", 1010);
            rent = (ICarRentalServer) r.lookup("CarRental"); //localizar o server

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
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Client c = new Client();
        } catch (RemoteException ex) {
            Logger.getLogger(CarRentalClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){}
    }
    
}
