/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;
import Control.Peer;


/**
 *
 * @author Juan
 */
public class SD1 {

    /**
     * @param args the command line arguments
     */
    //static int port = 7896;
    static int nPeers = 4;
    static String names[] = {"K", "Y", "Z", "W", "X", "A", "B", "C"};
    public static void main(String[] args) 
    {
       
        if(true == false)
        {
             int port = 7905;
            for(int i = 0; i < nPeers; i++)
            {
                
                System.out.println(port);
                Peer p;
                p = new Peer(names[i], "localhost", port, true);       
                Thread t = new Thread((Runnable) p);
                t.start();    
                port++;
            }
        }       
        else
        {
             int port = 7896;
            for(int i = 4; i < 8; i++)
            {
                System.out.println(port);
                Peer p;
                p = new Peer(names[i], "localhost", port, true);       
                Thread t = new Thread((Runnable) p);
                t.start();    
                port++;
            }
        }
    }
    
}
