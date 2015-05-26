/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import java.util.Date;

/**
 *
 * @author Laudelino
 */
public class Rent 
{
    String pickUpLocation;
    String dropOfLocation;
    
    Date pickUpDate;
    Date dropOfDate;
    
    int driverAge;
    String ccNumber;
    String ccCode;
    String ccName;
    String ccExpDate;
    
   
    public Rent(String pickUpPlace, 
            String dropOffPlace, Date pickUpDate, Date dropOfDate)
    {
        this.pickUpDate = pickUpDate;
        this.pickUpLocation = pickUpPlace;
        this.dropOfDate = dropOfDate;
        this.dropOfLocation = dropOffPlace;
    }
    
    public Rent(String pickUpPlace, String dropOffPlace, Date pickUpDate, Date dropOfDate,
            int driverAge, String ccNumber, String ccCode, String ccName, String ccExpDate)
    {
        this.pickUpDate = pickUpDate;
        this.pickUpLocation = pickUpPlace;
        this.dropOfDate = dropOfDate;
        this.dropOfLocation = dropOffPlace;
        
        this.driverAge = driverAge; 
        this.ccNumber = ccNumber;
        this.ccCode = ccCode;
        this.ccName = ccName;
        this.ccExpDate = ccExpDate;        
    }
    
}
