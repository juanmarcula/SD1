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
    static int port = 7896;
    static int nPeers = 4;
    static String names[] = {"A", "B", "C", "D"};
    public static void main(String[] args) 
    {
        for(int i = 0; i < nPeers; i++)
        {
            Peer p;
            p = new Peer(names[i], "localhost", port, true);       
            Thread t = new Thread((Runnable) p);
            Interface.InterfaceUser a = new InterfaceUser(p);
            a.setVisible(true);
            t.start();    
            port++;
        }
    }
    
}
