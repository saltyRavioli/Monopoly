/*  
   Name: Nathan Lu
   Teacher: Ms. Krasteva
   Date: 01/27/19
   This class contains the animation of a faling coin
   
   Name          Type        Purpose
   --------------------------------------------------------------------------------
   c             Console     Placeholder for the main console
*/

import java.awt.*;
import hsa.Console;
import java.lang.*;     // to access Thread class

public class Coin extends Thread
{
    private Console c;
    
    /*
      This method is the flippingCoins() method. It displays a slowly falling coin that flips vertically.
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ------------------------------------------------------------------
      height        int         stores the height of the coin
      increment     int         stores the amount the height changes by

      for (int x=0; x<500; x++) {
	    animates the falling coin
	    if (height>=15)
		If the height is more than 15, change the increment to -1
	    else if (height<=0)
		If the height is less than 0, change the increment to 1
	    try
		Pauses the animations so you can see it happening
	    catch (Exception e)
	}
    */
    public void flippingCoins (int xLocation)
    {
	int height = 0;
	int increment = 1;
	for (int x=0; x<500; x++) {
	    c.setColor(Color.yellow);
	    c.fillRect(xLocation-1, -1+x, 17, 17);
	    c.setColor(new Color (255, 215, 0)); // gold
	    c.fillOval(xLocation, 0+x, 15, height);
	    height+=increment;
	    if (height>=15) {
		increment=-1;
	    }
	    else if (height<=0) {
		increment=1;
	    }
	    try {
		Thread.sleep(10);
	    }
	    catch (Exception e) {}
	}
    }
    


    public Coin (Console con)
    {
	c = con;
    }
    public void run () {
	flippingCoins((int)(Math.random() * 640));
    }
    
}



