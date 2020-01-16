/* 
* Nathan Lu
* Monopoly
* Ms. Krasteva
* 12/20/2019
* This program allows the user(s) to play a version of 2 player Monopoly.
  When the player first enters the program, a splash screen is shown
  The player is taken to the main menu, where they can press 1, 2, 3, 4 for the options of instructions, high scores, play game, or quit, respectively.
  Selecting instructions gives a description of the game as well as how to use the program.
  Selecting high scores lets the player view the high scores of the game
  Selecting play game allows the user to play the game
    The players are then prompted for their names
    The game commences, where the players take turns rolling dice, moving the number of spaces indicated by the dice, and interacting with the space they're on
    The game ends when someone's balance reaches $0, where a message congratulating the winning player is shown
    The program returns to the main menu
  Selecting quit closes the screen
  
  CREDITS:
  GeeksForGeeks - Taught me how to use Math.random();
  Celeste       - Taught me how to use synchronized(c)
  
  TABLE OF INSTANCE VARIABLES:
  Name            Type        Purpose
-----------------------------------------------------------------------------------------------------------------------------------------------------------------
  c               Console      Displays the screen
  choice          char         Stores the user's choice in the main menu
  name            String[]     Array storing the names of the players
  players         String[][]   Array that stores the information of the players (balance, current position, is player in jail, how many railroads owned)
  properties      String[][]   Array that stores the information of properties (name, price, base cost, multiplier, owner)
  chanceCards     String[][]   Array that stores the information of the chanceCards
  communityCards  String[][]   Array that stores the information of the communityCards
  boardLayout     String[][]   Array that stores the information of the spaces for the current board layout
  curPlayer       boolean      A boolean that is false if it's player 1 and true if it's player 2
*/
import java.io.*;
import java.awt.*;                              //Gives access to Java command libraries
import hsa.Console;                             //gives access to Console class file
import javax.swing.JOptionPane;

public class Monopoly                          //creates a new class called Monopoly
{ //creates an instance variable of Console
    Console c;  //class so the output window can be made
    char choice = ' ';
    String []name = new String [2]; //stores the names of the players 
    int [][] players = new int [2][4]; //stores the information of the players (balance, current position, is player in jail, how many railroads owned)
    String [][] properties = new String [26][5]; // stores the information of properties (name, price, base cost, multiplier, owner).
    String [][] chanceCards = new String [17][2];
    String [][] communityCards = new String [17][2];
    String [][] boardLayout = new String [20][5];
    boolean curPlayer = false;
    /*
      This method is the title() method. It prints a centred title at the top of each screen, and leaves an empty line below it. It is called in every method.
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ----------------------------------------------
      ~There are no local variables in this method~

      There are no loops, conditionals, or segment blocks in this method.
    */
    private void title ()                  //title method
    {
	c.setColor(Color.yellow);
	c.fillRect(0, 0, 640, 500);
	c.setColor(Color.black);
	c.setFont (new Font ("Arial", Font.BOLD, 30));
	c.drawString ("MONOPOLY", 230, 40);
    }
    /*
      This method is the splashScreen() method. It displays a money bag falling on the word "MONOPOLY", causing both of them to fall to the bottom of the screen.
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      -----------------------------------------------------------------------------------------------------------------------
      xCoordinates  int[]       Stores the xCoordinates of the triangle section of the money bag
      yCoordinates  int[]       Stores the yCoordinates of the triangle section of the money bag

      for (int x=0; x<155; x++)
	used to animate the money bag dropping onto the word "MONOPOLY"
	if (((int)(Math.random() * 100)<5) && x>100)
	    there's a 5% chance that a coin might fall from the top of the screen. Because the coin might overlap with the bag, no coins will fall until x>100
	try
	    pauses the program so the user can see the animation
	catch (Exception e)

      for (int x=0; x<20; x++)
	animates the word "MONOPOLY" dropping a little from the money bag's weight
	if (((int)(Math.random() * 100)<5))
	    there's a 5% chance that a coin might fall from the top of the screen.
	try
	    pauses the program so the user can see the animation
	catch (Exception e)
	
      try
	pauses the program to have a moment of suspense before the word "MONOPOLY" comes crashing down
      catch (Exception e)
	
      for (int x=0; x<400; x++)
	animates the bag and the word falling together to the bottom of the screen
	if (((int)(Math.random() * 100)<5) && x<100)
	    there's a 5% chance that a coin might fall from the top of the screen. Because the coin might overlap with the mainMenu, no coins will fall unless x<100
	try
	    pauses the program so the user can see the animation
	catch (Exception e)
	
	try
	    pauses the program to let the user see the end result
	catch (Exception e)
    */
     private void splashScreen ()                  //splashScreen method
    {
	synchronized (c) {
	    c.setColor(Color.yellow);
	    c.fillRect(0, 0, 640, 500);
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 30));
	    c.drawString("M O N O P O L Y", 200, 200);
	    for (int x=0; x<155; x++) {
		if (((int)(Math.random() * 100)<10) && x>100) {
		    fallingCoin();
		}
		c.setColor(Color.yellow);
		c.fillRect(249, -61+x, 102, 52);
		int [] xCoordinates = {280, 300, 320};
		int [] yCoordinates = {-50+x, -30+x, -50+x};
		c.setColor(new Color (205,133,63)); // brown
		c.fillPolygon(xCoordinates, yCoordinates, 3);
		c.fillOval(250,-30+x, 100, 50);
		c.setColor(new Color (255, 215, 0)); // gold
		c.setFont (new Font ("Arial", Font.BOLD, 30));
		c.drawString("$", 292, 5+x);
		try {
		    Thread.sleep(5);
		}
		catch (Exception e) {}
	    }
	    for (int x=0; x<20; x++) {
		if (((int)(Math.random() * 100)<10)) {
		    fallingCoin();
		}
		c.setColor(Color.yellow);
		c.fillRect(200, 94+x, 250, 150);
		int [] xCoordinates = {280, 300, 320};
		int [] yCoordinates = {100+x, 125+x, 100+x};
		c.setColor(new Color (205,133,63)); // brown
		c.fillPolygon(xCoordinates, yCoordinates, 3);
		c.fillOval(250,125+x, 100, 50);
		c.setColor(Color.black);
		c.drawString("M O N O P O L Y", 200, 200+x);
		c.setColor(new Color (255, 215, 0)); // gold
		c.setFont (new Font ("Arial", Font.BOLD, 30));
		c.drawString("$", 292, 160+x);
		try {
		    Thread.sleep(5);
		}
		catch (Exception e) {}
	    }
	
	    try {
		Thread.sleep(500);
	    }
	    catch (Exception e) {}
	    for (int x=0; x<400; x++) {
		if (((int)(Math.random() * 100)<10) && x<100) {
		    fallingCoin();
		}
		c.setColor(Color.yellow);
		c.fillRect(200, 114+x, 250, 150);
		int [] xCoordinates = {280, 300, 320};
		int [] yCoordinates = {120+x, 145+x, 120+x};
		c.setColor(new Color (205,133,63)); // brown
		c.fillPolygon(xCoordinates, yCoordinates, 3);
		c.fillOval(250,145+x, 100, 50);
		c.setColor(Color.black);
		c.drawString("M O N O P O L Y", 200, 220+x);
		c.setColor(new Color (255, 215, 0)); // gold
		c.setFont (new Font ("Arial", Font.BOLD, 30));
		c.drawString("$", 292, 180+x);
		try {
		    Thread.sleep(5);
		}
		catch (Exception e) {}
	    }
	
	    try {
		Thread.sleep(3000);
	    }
	    catch (Exception e) {}
	    }
    }

    /*
      This method is the instructions() method. It gives instructions on how to play the game and what the game is.
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ----------------------------------------------
      ~There are no local variables in this method~

      There are no loops, conditionals, or segment blocks in this method.
    */
    public void instructions ()                  //instructions method
    {
	title();
	synchronized (c) {
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.setColor(Color.black);
	    c.drawString ("This is a 2 player, Toronto themed version of Monopoly, the popular board game that", 20, 100);
	    c.drawString ("embodies the spirit of capitalism!", 20, 120);
	
	    c.drawString ("The rules are rather simple. Player 1 (red) moves first, and Player 2 (blue) moves", 20, 150);
	    c.drawString ("moves second. Both players start with $500.", 20, 170);
	
	    c.drawString ("On each turn, a pair of dice is rolled and the player moves by the number of spaces", 20, 200);
	    c.drawString ("indicated on the dice.", 20, 220);
	
	    c.drawString ("Then the player can interact with the space it's on, whether it's", 20, 250);
	    c.drawString ("buying/paying rent/upgrading a property or drawing chance or community cards.", 20, 270);
	
	    c.drawString ("Each property has its base value and a multiplier that increases its value when you ", 20, 300);
	    c.drawString ("upgrade it. Each card has its effects as well, with community cards being generally", 20, 320);
	    c.drawString ("related to money and chance cards being more varied..", 20, 340);
	
	    c.drawString ("The game ends when a player's balance reaches $0. You can view the top 10 players by", 20, 370);
	    c.drawString ("choosing the High Scores option on the main menu.", 20, 390);
	
	    pauseProgram();
	    
	    title();
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString ("There are 6 kinds of spaces: normal properties, railroads (called \"Lines\"), utilities", 20, 100);
	    c.drawString ("(called \"Toronto Hydro\"), card drawing spaces, the Jail space, and the Go space.", 20, 120);
	
	    c.drawString ("Properties, railroads, and utilities can be bought. When bought, the opposing player", 20, 150);
	    c.drawString ("must pay rent (the amount is specified on the property space) to the property owner if", 20, 170);
	    c.drawString ("they land on the property.", 20, 190);
	    
	    c.drawString ("Properties can be upgraded, which means the rent the opposing player must pay is", 20, 210);
	    c.drawString ("multiplied by the multiplier indicated on the property.", 20, 230);
	
	    c.drawString ("Railroads and utilities cannot be upgraded. The amount of rent that you must pay for ", 20, 260);
	    c.drawString ("railroads is determined by the amount of railroads you own multiplied by 50.", 20, 280);
	    c.drawString ("The amount of rent you would pay for utilities is a dice roll multiplied by 4.", 20, 300);
	   
	    c.drawString ("Have fun!", 270, 330);
	    pauseProgram();
	}
    }
    /*
      This method is the highScores() method. It reads input from a file that stores the top 10 high scores (balance at end of game) and displays it.
      TABLE OF LOCAL VARIABLES:
      Name          Type            Purpose
      --------------------------------------------------------------------------------------------------------------------------
      input         BufferedReader  reads input from the highScores file
      output        PrintWriter     outputs onto the highScores file
      scores        String[][]      Stores the scores from the highScores file to be displayed and printed back into the file
      cur           String          Stores the current line being read from the highScores file
      
      try
	tries to read from the highScores file into the scores array. There may be an exception since highScores might not exist.
	for (int x=0; x<10; x++)
	  reads the contents of the file and stores it into the scores array
	  if (curName!=null)
	    this if statement checks if there is nothing more to read. That means that there are less than 10 entries, so the for loop is broken out of
      catch (Exception e)
	try
	  creats the highScores if there was an exception from trying to read it and then transfers its contents into the scores array
	  for (int x=0; x<10; x++)
	    reads the contents of the file and stores it into the scores array
	    if (curName!=null)
	      this if statement checks if there is nothing more to read. That means that there are less than 10 entries, so the for loop is broken out of
	catch (Exception f)
	  catches any exception and ignores it
      for (int x=0; x<10; x++)
	displays the contents of the scores array onto the screen
	if (scores[x][0]==null)
	   checks if the entry is blank. If it is, then there is no point in continuing to print the contents, and it breaks out
      try
	sets the output variable to write to the highScores file and writes to the file
	for (int x=0; x<10; x++)
	    outputs the contents of the array back into the file
	    if (curName!=null)
	      this if statement checks if there is nothing more to read. That means that there are less than 10 entries, so the for loop is broken out of
      catch (Exception e)
	if anything goes wrong, an error message is displayed
    */
     public void highScores ()                  //highScores method
    {
	title();
	c.setFont (new Font ("Arial", Font.BOLD, 15));
	c.drawString ("Here are the top 10 highest scores (in currency at end of game) achieved in this game.", 20, 100);
	PrintWriter output;
	BufferedReader input;
	String [][] scores = new String [10][2];
	try {
	    input = new BufferedReader(new FileReader("highScores.txt"));
	    String cur = input.readLine();
	    for (int x=0; x<10; x++)
	    {
		if (cur==null) {
		    break;
		}
		scores[x][0] = cur;
		cur = input.readLine();
		scores[x][1] = cur;
		cur = input.readLine();
	    }
	}
	catch (Exception e) {
	    try {
		output = new PrintWriter (new FileWriter("highScores.txt"));
		output.close();
		input = new BufferedReader(new FileReader("highScores.txt"));
		String cur = input.readLine();
		for (int x=0; x<10; x++)
		{
		    if (cur!=null) {
			break;
		    }
		    scores[x][0] = cur;
		    cur = input.readLine();
		    scores[x][1] = cur;
		    cur = input.readLine();
		}
	    }
	    catch (Exception f) {
		JOptionPane.showMessageDialog (null, "Something went wrong.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	c.drawString("Name", 120, 150);
	c.drawString("Score", 450, 150);
	c.drawLine(120, 160, 500, 160);
	for (int x=0; x<10; x++) {
	    if (scores[x][0]==null) {
		break;
	    }
	    c.drawString(scores[x][0], 120, 180+20*x);
	    c.drawString(scores[x][1], 450, 180+20*x);
	}
	
	try {
	    output = new PrintWriter (new FileWriter("highScores.txt"));
	    for (int x=0; x<10; x++)
	    {
		if (scores[x][0]==null) {
		    break;
		}
		output.println(scores[x][0]);
		output.println(scores[x][1]);
	    }
	    output.close();
	}
	catch (Exception e) {
	    JOptionPane.showMessageDialog (null, "Something went wrong.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	pauseProgram();
    }
    
    /*
      This method is the enterName() method. It asks for the 2 players' names.
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ----------------------------------------------
      ~There are no local variables in this method~

      for (int x=1; x<=2; x++)
	this for loop runs twice to get both players' names
	    while (true)
		the while loop that takes input for the players' names
		try
		    the try statement that will produce an exception if the name doesn't meet the requirements of not being empty and being unique
		    if (name[x-1].equals(""))
			checks if the current name being inputted is empty. If it is, an Exception is produced
		    if (name[x-1].length()>20)
			checks if the current name being inputted is over 20 characters in length. If it is, an Exception is produced
		    if (x==2)
			checks if this is the second name being inputted. Only then will the following errortrap work.
			if (name[x-1].equals(name[x-2]))
			    checks if the second input is the same as the first, and throws an exception if it is.

		catch (Exception e) {
		    this code segment catches all the exceptions that can be thrown by the errortraps
		    it colours out the white lines made by the user entering input and displays an error message.
		    
    */
    public void enterName ()                  //enterName method
    {
	for (int x=1; x<=2; x++) {
	    while (true) {
		try {
		    title();
		    c.setFont (new Font ("Arial", Font.BOLD, 20));
		    c.setColor(Color.black);
		    c.drawString("Player "+x+", enter your name:", 200, 100);
		    c.setCursor(7, 35);
		    name[x-1] = c.readLine();
		    if (name[x-1].equals("")) {
			throw new Exception();
		    }
		    if (name[x-1].length()>20) {
			throw new Exception();
		    }
		    if (x==2) {
			if (name[x-1].equals(name[x-2])) {
			    throw new Exception();
			}
		    }
		    break;
		}
		catch (Exception e) {
		    c.setColor(Color.yellow);
		    c.fillRect(0, 120, 640, 300);
		    c.setColor(Color.black);
		    JOptionPane.showMessageDialog (null, "Please enter a non-empty name shorter than 20 characters that is unique to the other player's name.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	}
    }
    /*
      This method is the setupBoard() method. It fills in the arrays that stores the properties, chance cards, and community cards arrays and randomizes the current game board layout.
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      --------------------------------------------------------------------------------------------------------------
      chosenIndexes int[]       Stores the property indexes that have already been inserted into the random layout
      curChosen     int         Stores the current randomly chosen property index that will be added to the layout
      check         boolean     Is a checking variable that is true if the curChosen index has not been added yet
      
      for (int x=0; x<20; x++)
	This for loop fills the array with -1, an index no property should have, so the program doesn't think the default value of 0 is taken already.
      for (int x=1; x<20; x++)
	Randomly assigns the empty spaces a property
	if (x!=7 && x!=16) 
	    This if statement checks if the indexes being filled isn't 7 or 16 (because these spaces are reserved)
	    while (check == false)
		as long check is false, the loop reruns. This while loop checks if the index has been used before
		for (int y=0; y<20; y++)
		    This for loop checks each index of the chosenIndexes array and changes the check variable accordingly
		    if (curChosen == chosenIndexes[y])
			Actually checks each index
    */
    public void setupBoard ()                  //setupBoard method
    {
	//Setting up properties
	properties[0] = new String[] {"The Bridal Path", "1000", "500", "1.15", "None",};
	properties[1] = new String[] {"Rosedale Park", "900", "450", "1.15", "None"};
	properties[2] = new String[] {"Bay Street", "800", "400", "1.1", "None"};
	properties[3] = new String[] {"Yorkville Area", "700", "350", "1.1", "None"};
	properties[4] = new String[] {"Bayview Avenue", "600", "300", "1.1", "None"};
	properties[5] = new String[] {"High Park", "550", "275", "1.15", "None"};
	properties[6] = new String[] {"Clinton Park", "500", "250", "1.15", "None"};
	properties[7] = new String[] {"Eglinton Avenue", "400", "200", "1.2", "None"};
	properties[8] = new String[] {"Sheppard Avenue", "300", "150", "1.2", "None"};
	properties[9] = new String[] {"Finch Avenue", "150", "75", "1.25", "None"};
	properties[10] = new String[] {"Community Card", "0", "0", "1", "None"};
	properties[11] = new String[] {"Community Card", "0", "0", "1", "None"};
	properties[12] = new String[] {"Community Card", "0", "0", "1", "None"};
	properties[13] = new String[] {"Chance Card", "0", "0", "1", "None"};
	properties[14] = new String[] {"Chance Card", "0", "0", "1", "None"};
	properties[15] = new String[] {"Chance Card", "0", "0", "1", "None"};
	properties[16] = new String[] {"Distillery District", "400", "200", "1.15", "None"};
	properties[17] = new String[] {"Lawrence Avenue", "250", "125", "1.1", "None"};
	properties[18] = new String[] {"Steeles Avenue", "70", "35", "1.1", "None"};
	properties[19] = new String[] {"King Street", "600", "300", "1.15", "None"};
	properties[20] = new String[] {"Queen Street", "600", "300", "1.15", "None"};
	properties[21] = new String[] {"Line 1", "150", "25", "1", "None"};
	properties[22] = new String[] {"Line 2", "150", "25", "1", "None"};
	properties[23] = new String[] {"Line 3", "150", "25", "1", "None"};
	properties[24] = new String[] {"Line 4", "150", "25", "1", "None"};
	properties[25] = new String[] {"Toronto Hydro", "150", "50", "1", "None"};
	
	//Setting up chance cards
	chanceCards[0] = new String[] {"Advance to Go", "Move your token back to Go and collect $200."};
	chanceCards[1] = new String[] {"Advance to <a property>", "Move your token to the property specified and collect $200 if you pass Go."};
	chanceCards[2] = new String[] {"Get out of Jail Free", "Keep this card until you use it. Can be used to get out of jail without paying anything."};
	chanceCards[3] = new String[] {"Go to Jail", "Go directly to jail. Do not collect money if you pass Go."};
	chanceCards[4] = new String[] {"Make Repairs to your Properties", "Pay $25 for each upgrade on any of your properties."};
	chanceCards[5] = new String[] {"Missing Taxes", "You owe the bank taxes! Pay $50 to the bank."};
	chanceCards[6] = new String[] {"Take a walk on <a property>", "Move your token to the property specified but do not interact with it. Collect $200 if you pass Go."};
	chanceCards[7] = new String[] {"Elected as Chairman of the Board", "Pay the bank $50 and the other player $50"};
	chanceCards[8] = new String[] {"Your Investment Pays Off", "Obtain $100."};
	chanceCards[9] = new String[] {"Win the Lottery", "Obtain $200."};
	chanceCards[10] = new String[] {"It's your birthday!", "Collect $50 from your opponent."};
	chanceCards[11] = new String[] {"Bank Error in your Favour", "Collect $100 from the bank."};
	chanceCards[12] = new String[] {"Sell some stocks.", "Collect a random amount between $50 and $100."};
	chanceCards[13] = new String[] {"Go back 3 Spaces.", "Go back 3 spaces."};
	chanceCards[14] = new String[] {"Poor tax.", "Pay $15."};
	chanceCards[15] = new String[] {"Christmas Gifts!", "Collect $50."};
	chanceCards[16] = new String[] {"Your Investment Fails", "Lose $100."};
	
	//Setting up community cards
	communityCards[0] = new String[] {"Advance to Go", "Move your token back to Go and collect $200."};
	communityCards[1] = new String[] {"Advance to <a property>", "Move your token to the property specified and collect $200 if you pass Go."};
	communityCards[2] = new String[] {"Get out of Jail Free", "Keep this card until you use it. Can be used to get out of jail without paying anything."};
	communityCards[3] = new String[] {"Go to Jail", "Go directly to jail. Do not collect money if you pass Go."};
	communityCards[4] = new String[] {"Bank Error in your Favour", "Collect $100 from the bank."};
	communityCards[5] = new String[] {"Missing Taxes", "You owe the bank taxes! Pay $50 to the bank."};
	communityCards[6] = new String[] {"Win a Crossword Contest", "Collect $50 from the bank."};
	communityCards[7] = new String[] {"You Inherit $100", "Obtain $100."};
	communityCards[8] = new String[] {"Your Investment Pays Off", "Obtain $100."};
	communityCards[9] = new String[] {"Win the Lottery", "Obtain $200."};
	communityCards[10] = new String[] {"Elected as Chairman of the Board", "Pay the bank $50 and the other player $50"};
	communityCards[11] = new String[] {"You are injured", "Pay $100."};
	communityCards[12] = new String[] {"It's your birthday!", "Collect $50 from your opponent."};
	communityCards[13] = new String[] {"School Fees", "Pay $50."};
	communityCards[14] = new String[] {"Sell some stocks.", "Collect a random amount between $50 and $100."};
	communityCards[15] = new String[] {"Poor tax.", "Pay $15."};
	communityCards[16] = new String[] {"Christmas Gifts!", "Collect $50."};
	
	//randomizing the game board layout (a Go space, a Chance Card space, a Community Card space, and a Jail space are fixed though)
	boardLayout[0] = new String[] {"Go  ", "-200", "0", "1", "None"};
	boardLayout[7] = new String[] {"Chance Card", "0", "0", "1", "None"};
	boardLayout[16] = new String[] {"Community Card", "0", "0", "1", "None"};
	boardLayout[11] = new String[] {"Jail", "0", "0", "1", "None"};
	int [] chosenIndexes = new int [20];
	for (int x=0; x<20; x++) {
	    chosenIndexes[x] = -1;
	}
	for (int x=1; x<20; x++) {
	    //Credit for how Math.random() works goes to GeeksForGeeks
	    if (x!=7 && x!=16 && x!=11) {
		int curChosen = 0;
		boolean check = false;
		while (check == false) {
		    curChosen = (int)(Math.random() * 26); //chooses a random number between 0 and 25.
		    check = true;
		    for (int y=0; y<20; y++) {
			if (curChosen == chosenIndexes[y]) {
			    check = false;
			}
		    }
		}
		boardLayout[x] = properties[curChosen];
		chosenIndexes[x] = curChosen;
	    }
	}
    }
    /*
      This method is the displayBoard() method. It runs functions related to it.
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ------------------------------------------------------------------------------------------------------------
      roll          int         Stores the amount returned by rolLDice()
      player        int         Stores the value of the current player: 0 for player 1 or 1 for player 2
      while (true)
	Errortraps input for whether the user wants to skip to the end 
	    try
		This try statement tries to get input (which can only be captial or lowercase 'n' or 'y'. If the input is proper, the loop is broken out of
		if (choice != 'Y' && choice != 'N' && choice != 'y' && choice != 'n')
		    This if statement checks if the input is proper. If it isn't, an exception is thrown
	    catch (Exception e)
		If there's an exception, it is caught by the catch statement which displays an error message
	if (choice == 'Y' || choice == 'y')
	    If the user's choice is yes, then player 2's balance is set to 0, which means player 2 loses
	    
      while (players[0][0]>0 && players[1][0]>0)
	    This while loop runs the game: it determines the distance the players move and how they interact with what space they're on until a pleyer's balance runs out
	    if (curPlayer == false) {
		If the curPlayer variable is false, the current player is 0
	    else {
		If curPlayer is true, then the current player is 1
	    
	    if (players[player][2]!=1)
		This if statement checks if the player is in jail. If the player isn't, rollDice() is called to determine how much they move
		for (int x=0; x<move; x++)
		    The for loop animates the movement
		    if (players[player][1]==0) 
			If the player passes 0, they should get $200
		    try
			Delays the animation so it's visible
		    catch (Exception e){}

	    if (boardLayout[players[player][1]][0].equals("Community Card"))
		Calls the communityCard() function if the player ends up on a community card space
	    else if (boardLayout[players[player][1]][0].equals("Chance Card"))
		Calls the communityCard() function if the player ends up on a chance card space
	    else if (!boardLayout[players[player][1]][0].equals("Go") || !boardLayout[players[player][1]][0].equals("Jail")){
		Calls the property() function if the player does not end up on Go or the Jail cell, as all other spaces would just be property spaces
	    else if (players[player][2]==1) {
		Handles if the player is in jail and they don't have a Get Out Of Jail Free Card
		if (choice == 'R' || choice=='r') {
		    If their choice is to roll, this part runs
		    if (rollDice()==12)
			Only a roll of 12 will get the player out of jail. If they rolled 12, they are notified that they got out of jail
		    else 
			A message saying that they did not get out of jail
		
		else if (choice == 'P' || choice=='p')
		    If their choice is to pay, this part runs 
		    if (players[player][0]<=50)
			If the player's balance is under $50, they get a message saying that they cannot pay
		    else 
			They pay to get out of jail

	    else if (players[player][2]==3) {
		Handles if the player is in jail and they have a Get Out Of Jail Free Card
		if (choice == 'R' || choice=='r') {
		    If their choice is to roll, this part runs
		    if (rollDice()==12)
			Only a roll of 12 will get the player out of jail. If they rolled 12, they are notified that they got out of jail
		    else 
			A message saying that they did not get out of jail
		
		else if (choice == 'P' || choice=='p')
		    If their choice is to pay, this part runs 
		    if (players[player][0]<=50)
			If the player's balance is under $50, they get a message saying that they cannot pay
		    else 
			They pay to get out of jail
		else if (choice == 'C' || choice=='c')
		    Allows the player to use their Get Out of Jail Free card to get out of jail

	    if (curPlayer==true)
		Switches the status of the current player from true to false
	    else
		Switches the status of the current player from false to true
		
    */
    public void displayBoard ()                  //displayBoard method
    {
	players[0][0] = 500;
	players[1][0] = 500;
	drawBoard();
	c.setFont (new Font ("Arial", Font.BOLD, 15));
	c.setColor(Color.black);
	c.drawString("Do you want to skip to the congradulatory screen?", 140, 245);
	c.drawString("It will show that "+name[0]+" won. Press 'Y' or 'N'.", 140, 265);
	while (true) {
	    try
	    {
		choice = c.getChar();
		if (choice != 'Y' && choice != 'N' && choice != 'y' && choice != 'n')
		{
		    throw new Exception ();
		}
		break;
	    }
	    catch (Exception e)
	    {
		JOptionPane.showMessageDialog (null, "Please enter 'Y' or 'N'.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	if (choice == 'Y' || choice == 'y') {
	    players[1][0] = 0;
	}
	pauseProgram();
	while (checkGameEnd()) {
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    title ();
	    int player;
	    if (curPlayer == false) {
		player = 0;
	    }
	    else {
		player = 1;
	    }
	    c.drawString("It is "+name[player]+"'s turn.", 150, 245);
	    pauseProgram();
	    drawBoard();
	    if (players[player][2]==0 || players[player][2]==2) {
		int roll = rollDice();
		pauseProgram();
		for (int x=0; x<roll; x++) {
		    players[player][1]++;
		    players[player][1]%=20;
		    if (players[player][1]==0) {
			players[player][0]+=200;
		    }
		    drawBoard();
		    try {
			Thread.sleep(200);
		    }
		    catch (Exception e){}
		}
		players[player][1]%=20;
		if (boardLayout[players[player][1]][0].equals("Community Card")) {
		    communityCard();
		}
		else if (boardLayout[players[player][1]][0].equals("Chance Card")) {
		    chanceCard();
		}
		else if (!boardLayout[players[player][1]][0].equals("Go") && !boardLayout[players[player][1]][0].equals("Jail")){
		    property();
		}
	    }
	    else if (players[player][2]==1) {
		c.setColor(Color.black);
		c.setFont (new Font ("Arial", Font.BOLD, 15));
		c.drawString("You are in jail! Pay $50 or roll a 12 to get out.", 150, 200);
		c.drawString("Press 'R' to roll, 'P' to pay, or anything else to skip", 150, 215);
		c.drawString("your turn.", 150, 230);
		choice = c.getChar();
		if (choice == 'R' || choice=='r') {
		    if (rollDice()==12) {
			c.setFont (new Font ("Arial", Font.BOLD, 15));
			c.drawString("You rolled a 12! You are now out of jail.", 150, 245);
			players[player][2] = 0;
		    }
		    else {
			c.setFont (new Font ("Arial", Font.BOLD, 15));
			c.drawString("Unfortunately, you did not roll a 12.", 150, 245);
		    }
		}
		else if (choice == 'P' || choice=='p') {
		    if (players[player][0]<=50) {
			c.drawString("You cannot afford to pay!", 150, 245);
		    }
		    else {
			c.drawString("You paid to get out of jail.", 150, 245);
			players[player][2] = 0;
			players[player][0] -= 50;
		    }
		}
		pauseProgram();
	    }
	    else if (players[player][2]==3) {
		c.setColor(Color.black);
		c.setFont (new Font ("Arial", Font.BOLD, 15));
		c.drawString("You are in jail! Pay $50, roll a 12 to get out, or use.", 150, 200);
		c.drawString("your Get Out of Jail card.", 150, 215);
		c.drawString("Press 'R' to roll, 'P' to pay, 'C' to use your card, or", 150, 230);
		c.drawString("anything else to skip your turn.", 150, 245);
		choice = c.getChar();
		if (choice == 'R' || choice=='r') {
		    if (rollDice()==12) {
			c.setFont (new Font ("Arial", Font.BOLD, 15));
			c.drawString("You rolled a 12! You are now out of jail.", 150, 260);
			players[player][2] = 2;
		    }
		    else {
			c.setFont (new Font ("Arial", Font.BOLD, 15));
			c.drawString("Unfortunately, you did not roll a 12.", 150, 260);
		    }
		}
		else if (choice == 'P' || choice=='p') {
		    if (players[player][0]<=50) {
			c.drawString("You cannot afford to pay!", 150, 260);
		    }
		    else {
			c.drawString("You paid to get out of jail.", 150, 260);
			players[player][2] = 2;
			players[player][0] -= 50;
		    }
		}
		else if (choice == 'C' || choice=='c') {
		    c.drawString("You have used your card.", 150, 260);
		    players[player][2] = 0;
		}
		pauseProgram();
	    }
	    if (curPlayer==true) {
		curPlayer=false;
	    }
	    else {
		curPlayer=true;
	    }
	}
    }
    /*
      This method is the communityCard() method. It runs if a player lands on a community card space. It chooses a card and applies its effects onto the player
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ----------------------------------------------------------------------------------------------------------------------
      card          int         stores the index of the chosen card
      xCooredinates int array   Stores the x-coordinates of the triangle part of the arrow
      yCooredinates int array   Stores the y-coordinates of the triangle part of the arrow
      xPos          int         Stores the x position of the current letter of the card description. Used for formatting
      player        int         Stores the value of the current player: 0 for player 1 or 1 for player 2
      chosenSpace   int         Stores the randomly chosen space the player is placed on if they draw a card that sends them to a random space
	if (curPlayer == false)
		If the curPlayer variable is false, the current player is 0
	    else
		If curPlayer is true, then the current player is 1
		
	if (card == 0)
	    Draws the card and applies the affect for the card in the 0th index of the communityCards array
	else if (card == 1)
	    Draws the card and applys the affect for the card in the 1st index of the communityCards array (this card sends the player to a random property space)
	    while (boardLayout[chosenSpace][0].equals("Community Card") || boardLayout[chosenSpace][0].equals("Chance Card") || boardLayout[chosenSpace][0].equals("Go") )
		continually chooses a space and only stops if the space is a valid property space

	else if (card == 2)
	    Draws the card and applies the affect for the card in the 2nd index of the communityCards array
	else if (card == 3)
	    Draws the card and applies the affect for the card in the 3rd index of the communityCards array (this card sends the player to jail)
	    if (players[player][2] == 0)
		If the player's status was 0, meaning they are not in jail but without a Get Out of Jail Free card
	    else
		If the player's status was 0, meaning they are not in jail but with  a Get Out of Jail Free card
		
	else if (card == 4)
	    Draws the card and applies the affect for the card in the 4th index of the communityCards array
	else if (card == 5)
	    Draws the card and applies the affect for the card in the 5th index of the communityCards array
	else if (card == 6)
	    Draws the card and applies the affect for the card in the 6th index of the communityCards array
	    for (int x=0; x<7; x++)
		draws a series of boxes for the crossword graphic
	    for (int x=0; x<4; x++)
		draws a series of boxes for the crossword graphic
	    for (int x=0; x<5; x++)
		draws a series of boxes for the crossword graphic
	    for (int x=0; x<3; x++)
		draws a series of boxes for the crossword graphic
	else if (card == 7)
	    Draws the card and applies the affect for the card in the 7th index of the communityCards array
	else if (card == 8)
	    Draws the card and applies the affect for the card in the 8th index of the communityCards array
	else if (card == 9)
	    Draws the card and applies the affect for the card in the 9th index of the communityCards array
	else if (card == 10)
	    Draws the card and applies the affect for the card in the 10th index of the communityCards array
	else if (card == 11)
	    Draws the card and applies the affect for the card in the 11th index of the communityCards array
	else if (card == 12)
	    Draws the card and applies the affect for the card in the 12th index of the communityCards array
	else if (card == 13)
	    Draws the card and applies the affect for the card in the 13th index of the communityCards array
	else if (card == 14)
	    Draws the card and applies the affect for the card in the 14th index of the communityCards array
	else if (card == 15)
	    Draws the card and applies the affect for the card in the 15th index of the communityCards array
	else if (card == 16)
	    Draws the card and applies the affect for the card in the 16th index of the communityCards array
	    
	for (int x=0; x<communityCards[card][1].length(); x++)
	    This for loop prints the description of the card chosen, 18 characters per line
	    if (x%18==0)
		resets the x-coordinate variable when 18 characters is reached
	    if (cur=='M' || cur=='W' || cur=='G' || cur=='w'  || cur=='O')
		Custom spacing for M, W, G, w, and O, because they are wide characters
	    else if (cur=='I' || cur=='i' || cur=='j' || cur=='l' || cur=='t' || cur=='f' || cur=='r')
		Custom spacing for I, i, j, 1, t, f, and r, because they are narrow characters
	    else
		Normal spacing for all other characters

	if (card==1)
	    If the index is 1, the player should have the opportunity to interact with the property they are on
    */
    public void communityCard ()                  //communityCard method
    {   
	int player;
	if (curPlayer == false) {
	    player = 0;
	}
	else {
	    player = 1;
	}
	int card = (int)(Math.random() * 16); //chooses a random number between 0 and 19 inclusive. (thanks to GeeksForGeeks for the Math.random() code)
	c.setColor(Color.white);
	c.fillRect(250, 160, 130, 200);
	if (card == 0) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Advance to Go", 264, 180);
	    c.setFont (new Font ("Arial", Font.BOLD, 30));
	    c.drawString("GO", 290, 215);
	    c.fillRect(285, 225, 43, 15);
	    int [] xCoordinates = {325, 325, 345};
	    int [] yCoordinates = {215, 250, 232};
	    c.fillPolygon(xCoordinates, yCoordinates, 3);
	    players[player][1]=0;
	}
	else if (card == 1) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Advance to:", 274, 180);
	    int chosenSpace = 10;//(int) (Math.random() * 19);
	    while (boardLayout[chosenSpace][0].equals("Community Card") || boardLayout[chosenSpace][0].equals("Chance Card") || boardLayout[chosenSpace][0].equals("Go") ) {
		chosenSpace = (int) (Math.random() * 19);
	    }
	    c.drawString(boardLayout[chosenSpace][0], 310-4*boardLayout[chosenSpace][0].length(), 195);
	    c.fillRect(285, 225, 43, 15);
	    int [] xCoordinates = {325, 325, 345};
	    int [] yCoordinates = {215, 250, 232};
	    c.fillPolygon(xCoordinates, yCoordinates, 3);
	    players[player][1] = chosenSpace;
	}
	else if (card == 2) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Get Out of Jail", 264, 180);
	    c.drawString("Free", 300, 200);
	    c.fillRect(287, 210, 55, 55);
	    c.setColor(Color.white);
	    c.fillRect(293, 215, 7, 45);
	    c.fillRect(305, 215, 7, 45);
	    c.fillRect(317, 215, 7, 45);
	    c.fillRect(329, 215, 7, 45);
	    c.fillRect(300, 235, 30, 25);
	    players[player][2]=2;
	}
	else if (card == 3) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Go To Jail", 278, 180);
	    c.fillRect(287, 200, 55, 55);
	    c.setColor(Color.white);
	    c.fillRect(293, 205, 7, 45);
	    c.fillRect(305, 205, 7, 45);
	    c.fillRect(317, 205, 7, 45);
	    c.fillRect(329, 205, 7, 45);
	    if (players[player][2] == 0) {
		players[player][2] = 1;
	    }
	    else {
		players[player][2] = 3;
	    }
	    players[player][1] = 11;
	}
	else if (card == 4) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Bank Error in", 270, 180);
	    c.drawString("Your Favour", 273, 195);
	    c.fillRect(297, 200, 40, 55);
	    c.setColor(Color.white);
	    c.fillRect(302, 205, 30, 45);
	    c.setFont (new Font ("Arial", Font.BOLD, 40));
	    c.setColor(Color.black);
	    c.drawString("$", 306, 242);
	    players[player][0]+=100;
	}
	else if (card == 5) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Missing Taxes", 265, 180);
	    c.fillRect(297, 200, 40, 55);
	    c.setColor(Color.white);
	    c.fillRect(302, 205, 30, 45);
	    c.setFont (new Font ("Arial", Font.BOLD, 14));
	    c.setColor(Color.black);
	    c.drawString("DUE", 302, 217);
	    c.setFont (new Font ("Arial", Font.BOLD, 30));
	    c.drawString("-$", 304, 242);
	    players[player][0]-=50;
	}
	else if (card == 6) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Win a Crossword", 256, 180);
	    c.drawString("Contest", 288, 195);
	    for (int x=0; x<7; x++) {
		c.drawRect(297, 210 + x*7, 7, 7);
	    }
	    for (int x=0; x<4; x++) {
		c.drawRect(304 + x*7, 238, 7, 7);
	    }
	    for (int x=0; x<5; x++) {
		c.drawRect(318, 217 + x*7, 7, 7);
	    }
	    for (int x=0; x<3; x++) {
		c.drawRect(290 + x*7, 224, 7, 7);
	    }
	    players[player][0]+=50;
	}
	else if (card == 7) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Inherit $100", 273, 180);
	    c.setFont (new Font ("Arial", Font.BOLD, 25));
	    c.drawString("X   $$$", 263, 230);
	    c.fillRect(280, 230, 80, 3);
	    players[player][0]+=100;
	}
	else if (card == 8) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Your Investment", 255, 180);
	    c.drawString("Pays Off", 285, 195);
	    c.drawLine(280, 205, 280, 260);
	    c.drawLine(280, 260, 340, 260);
	    c.drawLine(280, 260, 310, 230);
	    c.drawLine(310, 230, 320, 240);
	    c.drawLine(320, 240, 340, 220);
	    players[player][0]+=100;
	}
	else if (card == 9) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Win the Lottery", 261, 180);
	    int [] xCoordinates = {290, 310, 330};
	    int [] yCoordinates = {190, 210, 190};
	    c.fillPolygon(xCoordinates, yCoordinates, 3);
	    c.fillOval(270, 210, 80, 50);
	    c.setColor(Color.white);
	    c.setFont (new Font ("Arial", Font.BOLD, 30));
	    c.drawString("$", 302, 244);
	    players[player][0]+=200;
	}
	else if (card == 10) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Elected Chairman", 253, 180);
	    c.drawString("of the Board", 270, 195);
	    c.fillOval(307, 200, 20, 20);
	    c.fillRoundRect(300, 220, 30, 40, 19, 10);
	    c.fillRect(290, 240, 50, 30);
	    players[player][0]-=100;
	    players[(player+1)%2][0]+=50;
	}
	else if (card == 11) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("You are Injured", 263, 180);
	    c.fillRect(305, 200, 10, 50);
	    c.fillRect(285, 220, 50, 10);
	    players[player][0]-=100;
	}
	else if (card == 12) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("It's Your Birthday!", 253, 180);
	    c.fillRect(285, 220, 50, 30);
	    c.fillRect(295, 200, 5, 20);
	    c.fillRect(308, 200, 5, 20);
	    c.fillRect(321, 200, 5, 20);
	    players[player][0]+=50;
	    players[(player+1)%2][0]-=50;
	}
	else if (card == 13) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Pay School Fees", 257, 180);
	    c.fillRect(285, 220, 50, 25);
	    int [] xCoordinates = {295, 310, 325};
	    int [] yCoordinates = {225, 205, 225};
	    c.fillPolygon(xCoordinates, yCoordinates, 3);
	    c.setColor(Color.white);
	    c.fillOval(307, 220, 7, 7);
	    players[player][0]-=50;
	}
	else if (card == 14) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Sell some Stocks", 254, 180);
	    c.drawLine(280, 205, 280, 260);
	    c.drawLine(280, 260, 340, 260);
	    c.drawLine(280, 260, 310, 230);
	    c.drawLine(310, 230, 320, 240);
	    c.drawLine(320, 240, 340, 220);
	    players[player][0] += (int)(Math.random() * 50) + 50;
	}
	else if (card == 15) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Pay a Poor Tax", 262, 180);
	    c.drawLine(280, 205, 280, 260);
	    c.drawLine(280, 260, 340, 260);
	    c.drawLine(285, 205, 310, 230);
	    c.drawLine(310, 230, 320, 220);
	    c.drawLine(320, 220, 340, 240);
	    players[player][0] -= 15;
	}
	else if (card == 16) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Recieve", 283, 180);
	    c.drawString("Christmas Gifts", 259, 195);
	    c.fillRect(295, 220, 40, 40);
	    c.setColor(Color.black);
	    c.fillRect(295, 236, 40, 8);
	    c.fillRect(311, 220, 8, 40);
	    players[0][0] += 50;
	    players[0][1] += 50;
	}
	int xPos = 255;
	c.setColor(Color.black);
	c.setFont (new Font ("Arial", Font.BOLD, 10));
	for (int x=0; x<communityCards[card][1].length(); x++) {
	    if (x%18==0) {
		xPos = 255;
	    }
	    char cur = communityCards[card][1].charAt(x);
	    c.drawString(cur+"", xPos, 280+(x/18)*12);
	    if (cur=='M' || cur=='W' || cur=='G' || cur=='w'  || cur=='O') {
		xPos+=9;
	    }
	    else if (cur=='I' || cur=='i' || cur=='j' || cur=='l' || cur=='t' || cur=='f' || cur=='r') {
		xPos+=3;
	    }
	    else {
		xPos+=7;
	    }
	}
	pauseProgram();
	if (card==1) {
	    drawBoard();
	    property();
	}
    }
    
    /*
      This method is the chanceCard() method. It runs if a player lands on a chance card space. It chooses a card and applies its effects onto the player
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      -------------------------------------------------------------------------
      card          int         stores the index of the chosen card
      xCooredinates int array   Stores the x-coordinates of the triangle part of the arrow
      yCooredinates int array   Stores the y-coordinates of the triangle part of the arrow
      xPos          int         Stores the x position of the current letter of the card description. Used for formatting
      player        int         Stores the value of the current player: 0 for player 1 or 1 for player 2
      chosenSpace   int         Stores the randomly chosen space the player is placed on if they draw a card that sends them to a random space
	if (curPlayer == false)
		If the curPlayer variable is false, the current player is 0
	    else
		If curPlayer is true, then the current player is 1
		
	if (card == 0)
	    Draws the card and applies the affect for the card in the 0th index of the communityCards array
	else if (card == 1)
	    Draws the card and applys the affect for the card in the 1st index of the communityCards array (this card sends the player to a random property space)
	    while (boardLayout[chosenSpace][0].equals("Community Card") || boardLayout[chosenSpace][0].equals("Chance Card") || boardLayout[chosenSpace][0].equals("Go") )
		continually chooses a space and only stops if the space is a valid property space

	else if (card == 2)
	    Draws the card and applies the affect for the card in the 2nd index of the communityCards array
	else if (card == 3)
	    Draws the card and applies the affect for the card in the 3rd index of the communityCards array (this card sends the player to jail)
	    if (players[player][2] == 0)
		If the player's status was 0, meaning they are not in jail but without a Get Out of Jail Free card
	    else
		If the player's status was 0, meaning they are not in jail but with  a Get Out of Jail Free card
		
	else if (card == 4)
	    Draws the card and applies the affect for the card in the 4th index of the communityCards array
	else if (card == 5)
	    Draws the card and applies the affect for the card in the 5th index of the communityCards array
	else if (card == 6)
	    Draws the card and applies the affect for the card in the 6th index of the communityCards array (this one sets players onto a random space, but doesn't allow them to interact with it)
	    while (boardLayout[chosenSpace][0].equals("Community Card") || boardLayout[chosenSpace][0].equals("Chance Card") || boardLayout[chosenSpace][0].equals("Go") )
		continually chooses a space and only stops if the space is a valid property space
	else if (card == 7)
	    Draws the card and applies the affect for the card in the 7th index of the communityCards array
	else if (card == 8)
	    Draws the card and applies the affect for the card in the 8th index of the communityCards array
	else if (card == 9)
	    Draws the card and applies the affect for the card in the 9th index of the communityCards array
	else if (card == 10)
	    Draws the card and applies the affect for the card in the 10th index of the communityCards array
	else if (card == 11)
	    Draws the card and applies the affect for the card in the 11th index of the communityCards array
	else if (card == 12)
	    Draws the card and applies the affect for the card in the 12th index of the communityCards array
	else if (card == 13)
	    Draws the card and applies the affect for the card in the 13th index of the communityCards array
	else if (card == 14)
	    Draws the card and applies the affect for the card in the 14th index of the communityCards array
	else if (card == 15)
	    Draws the card and applies the affect for the card in the 15th index of the communityCards array
	else if (card == 16)
	    Draws the card and applies the affect for the card in the 16th index of the communityCards array
	    
	for (int x=0; x<communityCards[card][1].length(); x++)
	    This for loop prints the description of the card chosen, 18 characters per line
	    if (x%18==0)
		resets the x-coordinate variable when 18 characters is reached
	    if (cur=='M' || cur=='W' || cur=='G' || cur=='w'  || cur=='O')
		Custom spacing for M, W, G, w, and O, because they are wide characters
	    else if (cur=='I' || cur=='i' || cur=='j' || cur=='l' || cur=='t' || cur=='f' || cur=='r')
		Custom spacing for I, i, j, 1, t, f, and r, because they are narrow characters
	    else
		Normal spacing for all other characters

	if (card==1)
	    If the index is 1, the player should have the opportunity to interact with the property they are on
    */
    public void chanceCard ()                  //chanceCard method
    {   
	int player;
	if (curPlayer == false) {
	    player = 0;
	}
	else {
	    player = 1;
	}
	int card = (int)(Math.random() * 16); //chooses a random number between 0 and 19 inclusive. (thanks to GeeksForGeeks for the Math.random() code)
	c.setColor(Color.white);
	c.fillRect(250, 160, 130, 200);
	if (card == 0) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Advance to Go", 264, 180);
	    c.setFont (new Font ("Arial", Font.BOLD, 30));
	    c.drawString("GO", 290, 215);
	    c.fillRect(285, 225, 43, 15);
	    int [] xCoordinates = {325, 325, 345};
	    int [] yCoordinates = {215, 250, 232};
	    c.fillPolygon(xCoordinates, yCoordinates, 3);
	    players[player][1]=0;
	}
	else if (card == 1) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Advance to:", 274, 180);
	    int chosenSpace = 10;//(int) (Math.random() * 19);
	    while (boardLayout[chosenSpace][0].equals("Community Card") || boardLayout[chosenSpace][0].equals("Chance Card") || boardLayout[chosenSpace][0].equals("Go") ) {
		chosenSpace = (int) (Math.random() * 19);
	    }
	    c.drawString(boardLayout[chosenSpace][0], 310-4*boardLayout[chosenSpace][0].length(), 195);
	    c.fillRect(285, 225, 43, 15);
	    int [] xCoordinates = {325, 325, 345};
	    int [] yCoordinates = {215, 250, 232};
	    c.fillPolygon(xCoordinates, yCoordinates, 3);
	    players[player][1] = chosenSpace;
	}
	else if (card == 2) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Get Out of Jail", 264, 180);
	    c.drawString("Free", 300, 200);
	    c.fillRect(287, 210, 55, 55);
	    c.setColor(Color.white);
	    c.fillRect(293, 215, 7, 45);
	    c.fillRect(305, 215, 7, 45);
	    c.fillRect(317, 215, 7, 45);
	    c.fillRect(329, 215, 7, 45);
	    c.fillRect(300, 235, 30, 25);
	    players[player][2]=2;
	}
	else if (card == 3) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Go To Jail", 278, 180);
	    c.fillRect(287, 200, 55, 55);
	    c.setColor(Color.white);
	    c.fillRect(293, 205, 7, 45);
	    c.fillRect(305, 205, 7, 45);
	    c.fillRect(317, 205, 7, 45);
	    c.fillRect(329, 205, 7, 45);
	    if (players[player][2] == 0) {
		players[player][2] = 1;
	    }
	    else {
		players[player][2] = 3;
	    }
	    players[player][1] = 11;
	}
	else if (card == 4) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Make Repairs to", 258, 180);
	    c.drawString("Your Properties", 259, 195);
	    c.fillRect(280, 200, 70, 70);
	    c.setColor(Color.white);
	    c.drawLine(290, 210, 321, 230);
	    c.drawLine(321, 230, 310, 240);
	    c.drawLine(310, 240, 306, 230);
	    c.drawLine(310, 240, 309, 250);
	    c.drawLine(309, 250, 320, 247);
	    c.drawLine(321, 230, 330, 240);
	    c.drawLine(330, 240, 340, 230);
	    c.drawLine(321, 230, 330, 215);
	    players[0][0] += 50;
	    players[0][1] += 50;
	}
	else if (card == 5) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Missing Taxes", 265, 180);
	    c.fillRect(297, 200, 40, 55);
	    c.setColor(Color.white);
	    c.fillRect(302, 205, 30, 45);
	    c.setFont (new Font ("Arial", Font.BOLD, 14));
	    c.setColor(Color.black);
	    c.drawString("DUE", 302, 217);
	    c.setFont (new Font ("Arial", Font.BOLD, 30));
	    c.drawString("-$", 304, 242);
	    players[player][0]-=50;
	}
	if (card == 6) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Take a Walk On", 260, 180);
	    int chosenSpace = 10;//(int) (Math.random() * 19);
	    while (boardLayout[chosenSpace][0].equals("Community Card") || boardLayout[chosenSpace][0].equals("Chance Card") || boardLayout[chosenSpace][0].equals("Go") ) {
		chosenSpace = (int) (Math.random() * 19);
	    }
	    c.drawString(boardLayout[chosenSpace][0], 310-4*boardLayout[chosenSpace][0].length(), 195);
	    c.fillRect(285, 225, 43, 15);
	    int [] xCoordinates = {325, 325, 345};
	    int [] yCoordinates = {215, 250, 232};
	    c.fillPolygon(xCoordinates, yCoordinates, 3);
	    players[player][1] = chosenSpace;
	}
	else if (card == 7) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Elected Chairman", 253, 180);
	    c.drawString("of the Board", 270, 195);
	    c.fillOval(307, 200, 20, 20);
	    c.fillRoundRect(300, 220, 30, 40, 19, 10);
	    c.fillRect(290, 240, 50, 30);
	    players[player][0]-=100;
	    players[(player+1)%2][0]+=50;
	}
	else if (card == 8) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Your Investment", 255, 180);
	    c.drawString("Pays Off", 285, 195);
	    c.drawLine(280, 205, 280, 260);
	    c.drawLine(280, 260, 340, 260);
	    c.drawLine(280, 260, 310, 230);
	    c.drawLine(310, 230, 320, 240);
	    c.drawLine(320, 240, 340, 220);
	    players[player][0]+=100;
	}
	else if (card == 9) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Win the Lottery", 261, 180);
	    int [] xCoordinates = {290, 310, 330};
	    int [] yCoordinates = {190, 210, 190};
	    c.fillPolygon(xCoordinates, yCoordinates, 3);
	    c.fillOval(270, 210, 80, 50);
	    c.setColor(Color.white);
	    c.setFont (new Font ("Arial", Font.BOLD, 30));
	    c.drawString("$", 302, 244);
	    players[player][0]+=200;
	}
	else if (card == 10) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("It's Your Birthday!", 253, 180);
	    c.fillRect(285, 220, 50, 30);
	    c.fillRect(295, 200, 5, 20);
	    c.fillRect(308, 200, 5, 20);
	    c.fillRect(321, 200, 5, 20);
	    players[player][0]+=50;
	    players[(player+1)%2][0]-=50;
	}
	else if (card == 11) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Bank Error in", 270, 180);
	    c.drawString("Your Favour", 273, 195);
	    c.fillRect(297, 200, 40, 55);
	    c.setColor(Color.white);
	    c.fillRect(302, 205, 30, 45);
	    c.setFont (new Font ("Arial", Font.BOLD, 40));
	    c.setColor(Color.black);
	    c.drawString("$", 306, 242);
	    players[player][0]+=100;
	}
	else if (card == 12) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Sell some Stocks", 254, 180);
	    c.drawLine(280, 205, 280, 260);
	    c.drawLine(280, 260, 340, 260);
	    c.drawLine(280, 260, 310, 230);
	    c.drawLine(310, 230, 320, 240);
	    c.drawLine(320, 240, 340, 220);
	    players[player][0] += (int)(Math.random() * 50) + 50;
	}
	else if (card == 13) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Go Back 3 Spaces", 251, 180);
	    c.fillRect(305, 225, 43, 15);
	    int [] xCoordinates = {305, 305, 285};
	    int [] yCoordinates = {215, 250, 232};
	    c.fillPolygon(xCoordinates, yCoordinates, 3);
	    players[player][1] -= 3;
	    if (players[player][1]<0) {
		players[player][1]+=20;
	    }
	}
	else if (card == 14) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Pay a Poor Tax", 262, 180);
	    c.drawLine(280, 205, 280, 260);
	    c.drawLine(280, 260, 340, 260);
	    c.drawLine(285, 205, 310, 230);
	    c.drawLine(310, 230, 320, 220);
	    c.drawLine(320, 220, 340, 240);
	    players[player][0] -= 15;
	}
	else if (card == 15) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Recieve", 283, 180);
	    c.drawString("Christmas Gifts", 259, 195);
	    c.fillRect(295, 220, 40, 40);
	    c.setColor(Color.white);
	    c.fillRect(295, 236, 40, 8);
	    c.fillRect(311, 220, 8, 40);
	    players[0][0] += 50;
	    players[0][1] += 50;
	}
	else if (card == 16) {
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString("Your Investment", 255, 180);
	    c.drawString("Fails", 300, 195);
	    c.drawLine(280, 205, 280, 260);
	    c.drawLine(280, 260, 340, 260);
	    c.drawLine(285, 205, 310, 230);
	    c.drawLine(310, 230, 320, 220);
	    c.drawLine(320, 220, 340, 240);
	    players[player][0] -= 100;
	}     
	int xPos = 255;
	c.setColor(Color.black);
	c.setFont (new Font ("Arial", Font.BOLD, 10));
	for (int x=0; x<chanceCards[card][1].length(); x++) {
	    if (x%18==0) {
		xPos = 255;
	    }
	    char cur = chanceCards[card][1].charAt(x);
	    c.drawString(cur+"", xPos, 280+(x/18)*12);
	    if (cur=='M' || cur=='W' || cur=='G' || cur=='w'  || cur=='O') {
		xPos+=9;
	    }
	    else if (cur=='I' || cur=='i' || cur=='j' || cur=='l' || cur=='t' || cur=='f' || cur=='r') {
		xPos+=3;
	    }
	    else {
		xPos+=7;
	    }
	}
	pauseProgram();
	if (card==1) {
	    drawBoard();
	    property();
	}
    }
     /*
      This method is the property() method. It deals will all property-related actions
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ---------------------------------------------------------------------------------------------------------------------
      player        int         Stores the value of the current player: 0 for player 1 or 1 for player 2
      roll          int         Stores the sum of the roll, used for determining the rent price of the Toronto Hydro space
      
      if (curPlayer == false)
		If the curPlayer variable is false, the current player is 0
	    else
		If curPlayer is true, then the current player is 1

	if (boardLayout[curPosition][4].equals("None"))
	    This handles the action if the property is unowned
	    while (true)
		This loops until a valid input is entered
		try
		    The try statement takes input and checks if it's valid
		    if (choice != 'Y' && choice != 'N' && choice != 'y' && choice != 'n')
			If the choice isn't some form of Y or N, then an Exception is produced
		catch (Exception e)
		    Displays and error message
		    
	    if (choice == 'Y' || choice == 'y')
		Runs if the player wants to buy the property
		if (players[player][0] - Integer.parseInt(boardLayout[curPosition][1]) > 0) 
		    If the player has enough money, they are now the owners of the property
		    if (boardLayout[curPosition][0].substring(0, 4).equals("Line"))
			If the player wants to buy a railroad, whose price increases the more they own, this for loop runs to update the rent costs
			for (int x=1; x<20; x++)
			    This for loop runs to check if the current property if a railroad and if it is owned is the current player
			    if (boardLayout[x][0].substring(0, 4).equals("Line") && boardLayout[curPosition][4].equals(name[player]))
				This if statement actually does the check
		else
		    If not, the player does not buy the property
	else if (Integer.parseInt(boardLayout[curPosition][4])!=player)
	    This handles the case if the property is owned by someone else.
	    The current player loses money
	else 
	    if (!boardLayout[curPosition][0].substring(0, 4).equals("Line") && !boardLayout[curPosition][0].equals("Toronto Hydro"))
		while (true)
		    This loops until a valid input is entered
		    try
			The try statement takes input and checks if it's valid
			if (choice != 'Y' && choice != 'N' && choice != 'y' && choice != 'n')
			    If the choice isn't some form of Y or N, then an Exception is produced
		    catch (Exception e)
			Displays and error message
	       
		if (choice == 'Y' || choice == 'y')
		    If the player chooses to upgrade, the property value is increased as they lose moeny
    */
    public void property ()                  //property method
    {
	int player;
	if (curPlayer == false) {
	    player = 0;
	}
	else {
	    player = 1;
	}
	int curPosition = players[player][1];
	c.setFont (new Font ("Arial", Font.BOLD, 15));
	c.setColor(Color.black);
	if (boardLayout[curPosition][4].equals("None")) {
	    c.drawString("Do you want to buy this property? Press 'Y' or 'N'.", 150, 200);
	    while (true) {
		try
		{
		    choice = c.getChar();
		    if (choice != 'Y' && choice != 'N' && choice != 'y' && choice != 'n')
		    {
			throw new Exception ();
		    }
		    break;
		}
		catch (Exception e)
		{
		    JOptionPane.showMessageDialog (null, "Please enter 'Y' or 'N'.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	    if (choice == 'Y' || choice == 'y') {
		if (players[player][0] - Integer.parseInt(boardLayout[curPosition][1]) > 0) {
		    boardLayout[curPosition][4] = name[player];
		    players[player][0] -= Integer.parseInt(boardLayout[curPosition][1]);
		    if (boardLayout[curPosition][0].substring(0, 4).equals("Line")) {
			players[player][3]+=1;
			for (int x=1; x<20; x++) {
			    if (boardLayout[x][0].substring(0, 4).equals("Line") && boardLayout[curPosition][4].equals(name[player])) {
				boardLayout[x][2] = Integer.toString(players[player][3]*50);
			    }
			}
		    }
		}
		else {
		    c.setColor(Color.yellow);
		    c.fillRect(149, 160, 350, 100);
		    c.setColor(Color.black);
		    c.drawString("You don't have enough money!", 150, 200);
		    pauseProgram();
		}
	    }
	}
	else if (!boardLayout[curPosition][4].equals(name[player])) {
	    c.drawString("You must pay rent!", 150, 200);
	    if (boardLayout[curPosition][0].equals("Toronto Hydro")) {
		int roll = rollDice();
		c.drawString("Pay the amount determined by the result of a dice roll * 4", 150, 215);
		pauseProgram();
		players[player][0]-=roll*4;
	    }
	    else {
		players[player][0]-=Integer.parseInt(boardLayout[curPosition][2]);
		pauseProgram();
	    }
	}
	else {
	    if (!boardLayout[curPosition][0].substring(0, 4).equals("Line") && !boardLayout[curPosition][0].equals("Toronto Hydro")) {
		c.drawString("Do you want to upgrade this property?", 150, 200);
		while (true) {
		    try
		    {
			choice = c.getChar();
			if (choice != 'Y' && choice != 'N' && choice != 'y' && choice != 'n')
			{
			    throw new Exception ();
			}
			break;
		    }
		    catch (Exception e)
		    {
			JOptionPane.showMessageDialog (null, "Please enter 'Y' or 'N'.", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
		if (choice == 'Y' || choice == 'y') {
		    boardLayout[curPosition][2] = (int)(Double.parseDouble(boardLayout[curPosition][2]) * Double.parseDouble(boardLayout[curPosition][3])) + "";
		    players[player][0] -= Integer.parseInt(boardLayout[curPosition][2])/10;
		}
	    }
	}
    }
    /*
      This method is the rollDice() method. It rolls dice that determine how much the player moves and returns it
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      -------------------------------------------------------------------------
      firstRoll     int         Stores the current value of the first die roll
      secondRoll    int         Stores the current value of the second die roll
      if (players[0][2]==2 || players[0][2]==3)
	Checks if the player has a Get Out of Jail Free card. If they do, print a line stating it
      if (players[1][2]==2|| players[1][2]==3)
	Checks if the player has a Get Out of Jail Free card. If they do, print a line stating it
      for (int x=0; x<20; x++) {
	This for loop simulates the dice rolling. It calls on the Math.random() for both the firstRoll and secondRoll variable and displays the results onto the escren
     
    */
    public int rollDice ()                  //rollDice method
    {
	title();
	c.setFont (new Font ("Arial", Font.BOLD, 15));
	c.drawString(name[0], 30, 30);
	c.drawString("$"+players[0][0], 30, 45);
	if (players[0][2]==2 || players[0][2]==3) {
	    c.setFont (new Font ("Arial", Font.BOLD, 10));
	    c.drawString("Get Out of Jail Free", 30, 55);
	}
	c.setFont (new Font ("Arial", Font.BOLD, 15));
	c.drawString(name[1], 600-7*name[1].length(), 30);
	c.drawString("$"+players[1][0], 600-7*name[1].length(), 45);
	if (players[1][2]==2 || players[1][2]==3) {
	    c.setFont (new Font ("Arial", Font.BOLD, 10));
	    c.drawString("Get Out of Jail Free", 540, 55);
	}
	int firstRoll = 1;
	int secondRoll = 1;
	for (int x=0; x<20; x++) {
	    firstRoll = (int)(Math.random() * 6)+1; //chooses a random number between 1 and 6. (again thanks to GeeksForGeeks for the Math.random() code)
	    secondRoll = (int)(Math.random() * 6)+1;
	    c.setColor(Color.yellow);
	    c.fillRect(249, 169, 200, 200);
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 30));
	    c.drawString(firstRoll+"          "+secondRoll, 250, 200);
	    try {
		Thread.sleep(100);
	    }
	    catch(Exception e) {}
	}
	c.setFont (new Font ("Arial", Font.BOLD, 15));
	return firstRoll+secondRoll;
    }
    /*
      This method is the drawBoard() method. It draws the Monopoly board.
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ----------------------------------------------
      ~There are no local variables in this method~

      if (players[0][2]==2 || players[0][2]==3)
	Checks if the player has a Get Out of Jail Free card. If they do, print a line stating it
      if (players[1][2]==2|| players[1][2]==3)
	Checks if the player has a Get Out of Jail Free card. If they do, print a line stating it
      for (int x=0; x<7; x++)
	This for loop draws the bottom row of the board
	if (!boardLayout[x][4].equals("None"))
	    if the space is owned by someone, their name appears as the owner and the board colour changes to be the player's colour
		if (boardLayout[x+10][4].equals(name[0]))
		    Sets the color to red if player 1 owns the property
		else
		    Sets the color to blue otherwise
	if (!boardLayout[x][0].equals("Community Card") && !boardLayout[x][0].equals("Chance Card") && !boardLayout[x][0].equals("Go") && !boardLayout[x][0].equals("Jail"))
	    This if statement checks if the price or rent cost needs to be printed (only if the space is a property)
	if (!boardLayout[x][0].equals("Community Card") && !boardLayout[x][0].equals("Chance Card") && !boardLayout[x][0].equals("Go") && !boardLayout[x][0].equals("Jail") && !boardLayout[x][0].equals("Toronto Hydro") && !boardLayout[x][0].substring(0, 4).equals("Line"))
	    This if statement checks if the multiplier needs to be printed (only if the space is a normal property space)
	    
	if (boardLayout[x+7][0].equals("Community Card") || boardLayout[x+7][0].equals("Chance Card"))
	    If the space is a card space, draw a card symbol
	else if (boardLayout[x+7][0].substring(0, 4).equals("Line"))
	    If the space is a railroad space, draw a railroad symbol
	else if (boardLayout[x+7][0].equals("Toronto Hydro"))
	    If the space is a utility space, draw a factory symbol    
		    
      for (int x=0; x<3; x++)
	This for loop draws the left row of the board
	if (!boardLayout[x][4].equals("None"))
	    if the space is owned by someone, their name appears as the owner and the board colour changes to be the player's colour
		if (boardLayout[x+10][4].equals(name[0]))
		    Sets the color to red if player 1 owns the property
		else
		    Sets the color to blue otherwise
	
	if (!boardLayout[x+7][0].equals("Community Card") && !boardLayout[x+7][0].equals("Chance Card") && !boardLayout[x+7][0].equals("Go") && !boardLayout[x+7][0].equals("Jail"))
	    This if statement checks if the price or rent cost needs to be printed (only if the space is a property)
	if (!boardLayout[x+7][0].equals("Community Card") && !boardLayout[x+7][0].equals("Chance Card") && !boardLayout[x+7][0].equals("Go") && !boardLayout[x+7][0].equals("Jail") && !boardLayout[x+7][0].equals("Toronto Hydro") && !boardLayout[x+7][0].substring(0, 4).equals("Line"))
	    This if statement checks if the multiplier needs to be printed (only if the space is a normal property space)
	    
	if (boardLayout[x+7][0].equals("Community Card") || boardLayout[x+7][0].equals("Chance Card"))
	    If the space is a card space, draw a card symbol
	else if (boardLayout[x+7][0].substring(0, 4).equals("Line"))
	    If the space is a railroad space, draw a railroad symbol
	else if (boardLayout[x+7][0].equals("Toronto Hydro"))
	    If the space is a utility space, draw a factory symbol          
	 
      for (int x=0; x<7; x++)
	This for loop draws the top row of the board
	if (!boardLayout[x][4].equals("None"))
	    if the space is owned by someone, their name appears as the owner and the board colour changes to be the player's colour
		if (boardLayout[x+10][4].equals(name[0]))
		    Sets the color to red if player 1 owns the property
		else
		    Sets the color to blue otherwise
	
	if (!boardLayout[x+10][0].equals("Community Card") && !boardLayout[x+10][0].equals("Chance Card") && !boardLayout[x+10][0].equals("Go") && !boardLayout[x+10][0].equals("Jail"))
	    This if statement checks if the price or rent cost needs to be printed (only if the space is a property)
	if (!boardLayout[x+10][0].equals("Community Card") && !boardLayout[x+10][0].equals("Chance Card") && !boardLayout[x+10][0].equals("Go") && !boardLayout[x+10][0].equals("Jail") && !boardLayout[x+10][0].equals("Toronto Hydro") && !boardLayout[x+10][0].substring(0, 4).equals("Line"))
	    This if statement checks if the multiplier needs to be printed (only if the space is a normal property space)
	    
	if (boardLayout[x+10][0].equals("Community Card") || boardLayout[x+10][0].equals("Chance Card"))
	    If the space is a card space, draw a card symbol
	else if (boardLayout[x+10][0].equals("Jail"))
	    If the space is a jail space, draw a jail symbol
	else if (boardLayout[x+10][0].substring(0, 4).equals("Line"))
	    If the space is a railroad space, draw a railroad symbol
	else if (boardLayout[x+10][0].equals("Toronto Hydro"))
	    If the space is a utility space, draw a factory symbol
	
      for (int x=0; x<3; x++) {
	This for loop draws the right row of the board
	if (!boardLayout[x][4].equals("None"))
	    if the space is owned by someone, their name appears as the owner and the board colour changes to be the player's colour
		if (boardLayout[x+10][4].equals(name[0]))
		    Sets the color to red if player 1 owns the property
		else
		    Sets the color to blue otherwise
	
	if (!boardLayout[x+17][0].equals("Community Card") && !boardLayout[x+17][0].equals("Chance Card") && !boardLayout[x+17][0].equals("Go") && !boardLayout[x+17][0].equals("Jail"))
	    This if statement checks if the price or rent cost needs to be printed (only if the space is a property)
	if (!boardLayout[x+17][0].equals("Community Card") && !boardLayout[x+17][0].equals("Chance Card") && !boardLayout[x+17][0].equals("Go") && !boardLayout[x+17][0].equals("Jail") && !boardLayout[x+17][0].equals("Toronto Hydro") && !boardLayout[x+17][0].substring(0, 4).equals("Line"))
	    This if statement checks if the multiplier needs to be printed (only if the space is a normal property space)
	    
	if (boardLayout[x+17][0].equals("Community Card") || boardLayout[x+17][0].equals("Chance Card"))
	    If the space is a card space, draw a card symbol
	else if (boardLayout[x+17][0].substring(0, 4).equals("Line"))
	    If the space is a railroad space, draw a railroad symbol
	else if (boardLayout[x+17][0].equals("Toronto Hydro"))
	    If the space is a utility space, draw a factory symbol
      
      if (players[0][1]%20<7) {
	This if structure checks where the first player is and draws them accordingly
	This specific if statement draws the marker on one of the bottom row spots    
      else if (players[0][1]%20<10)
	This specific if statement draws the marker on one of the left row spots    
      else if (players[0][1]%20<17)
	This specific if statement draws the marker on one of the top row spots    
      else if (players[0][1]%20<20)
	This specific if statement draws the marker on one of the right row spots    
	
      if (players[1][1]%20<7) {
	This if structure checks where the second player is and draws them accordingly
	This specific if statement draws the marker on one of the bottom row spots    
      else if (players[1][1]%20<10)
	This specific if statement draws the marker on one of the left row spots    
      else if (players[1][1]%20<17)
	This specific if statement draws the marker on one of the top row spots    
      else if (players[1][1]%20<20)
	This specific if statement draws the marker on one of the right row spots    
    */
    private void drawBoard ()                  //drawBoard method
    {
	title ();
	c.setFont (new Font ("Arial", Font.BOLD, 15));
	c.drawString(name[0], 30, 30);
	c.drawString("$"+players[0][0], 30, 45);
	if (players[0][2]==2 || players[0][2]==3) {
	    c.setFont (new Font ("Arial", Font.BOLD, 10));
	    c.drawString("Get Out of Jail Free", 30, 55);
	}
	c.setFont (new Font ("Arial", Font.BOLD, 15));
	c.drawString(name[1], 600-7*name[1].length(), 30);
	c.drawString("$"+players[1][0], 600-7*name[1].length(), 45);
	if (players[1][2]==2 || players[1][2]==3) {
	    c.setFont (new Font ("Arial", Font.BOLD, 10));
	    c.drawString("Get Out of Jail Free", 540, 55);
	}
	c.setFont (new Font ("Arial", Font.BOLD, 8));
	
	for (int x=0; x<7; x++) {
	    if (!boardLayout[x][4].equals("None")) {
		if (boardLayout[x][4].equals(name[0])) {
		    c.setColor(Color.red);
		}
		else {
		    c.setColor(Color.blue);
		}
		c.drawString("Owned by: " + boardLayout[x][4], 518-80*x, 430);
	    }
	    c.drawRect(515-80*x, 380, 80, 80);
	    c.drawString(boardLayout[x][0], 518-80*x, 388);
	    if (!boardLayout[x][0].equals("Community Card") && !boardLayout[x][0].equals("Chance Card") && !boardLayout[x][0].equals("Go") && !boardLayout[x][0].equals("Jail")) {
		c.drawString("Price: " + boardLayout[x][1], 518-80*x, 400);
		c.drawString("Rent Cost: " + boardLayout[x][2], 518-80*x, 410);
	    }
	    if (!boardLayout[x][0].equals("Community Card") && !boardLayout[x][0].equals("Chance Card") && !boardLayout[x][0].equals("Go") && !boardLayout[x][0].equals("Jail") && !boardLayout[x][0].equals("Toronto Hydro") && !boardLayout[x][0].substring(0, 4).equals("Line")) {
		c.drawString("Multiplier: x" + boardLayout[x][3], 518-80*x, 420);
	    }
	    if (boardLayout[x][0].equals("Community Card") || boardLayout[x][0].equals("Chance Card")) {
		c.setColor(Color.black);
		c.fillRect(538-80*x, 405, 26, 30);
		c.setColor(Color.white);
		c.fillRect(541-80*x, 408, 20, 24);
	    }
	    else if (boardLayout[x][0].substring(0, 4).equals("Line")) {
		c.fillRect(525-80*x, 420, 53, 5);
		c.fillRect(525-80*x, 440, 53, 5);
		c.fillRect(530-80*x, 414, 3, 38);
		c.fillRect(540-80*x, 414, 3, 38);
		c.fillRect(550-80*x, 414, 3, 38);
		c.fillRect(560-80*x, 414, 3, 38);
		c.fillRect(570-80*x, 414, 3, 38);
	    }
	    else if (boardLayout[x][0].equals("Toronto Hydro")) {
		c.fillRect(528-80*x, 418, 10, 5);
		c.fillRect(530-80*x, 418, 5, 10);
		c.fillRect(530-80*x, 428, 30, 20);
	    }
	    c.setColor(Color.black);
	}
	for (int x=0; x<3; x++) {
	    if (!boardLayout[x+7][4].equals("None")) {
		if (boardLayout[x+7][4].equals(name[0])) {
		    c.setColor(Color.red);
		}
		else {
		    c.setColor(Color.blue);
		}
		c.drawString("Owned by: " + boardLayout[x+7][4], 38, 348-80*x);
	    }
	    c.drawRect(35, 300-80*x, 80, 80);
	    c.setColor(Color.black);
	    c.drawString(boardLayout[x+7][0], 38, 308-80*x);
	    if (!boardLayout[x+7][0].equals("Community Card") && !boardLayout[x+7][0].equals("Chance Card") && !boardLayout[x+7][0].equals("Go") && !boardLayout[x+7][0].equals("Jail")) {
			    c.drawString("Price: " + boardLayout[x+7][1], 38, 318-80*x);
	    c.drawString("Rent Cost: " + boardLayout[x+7][2], 38, 328-80*x);
	    }
	    if (!boardLayout[x+7][0].equals("Community Card") && !boardLayout[x+7][0].equals("Chance Card") && !boardLayout[x+7][0].equals("Go") && !boardLayout[x+7][0].equals("Jail") && !boardLayout[x+7][0].equals("Toronto Hydro") && !boardLayout[x+7][0].substring(0, 4).equals("Line")) {
		c.drawString("Multiplier: x" + boardLayout[x+7][3], 38, 338-80*x);
	    }
	    if (boardLayout[x+7][0].equals("Community Card") || boardLayout[x+7][0].equals("Chance Card")) {
		c.setColor(Color.black);
		c.fillRect(58, 328-80*x, 26, 30);
		c.setColor(Color.white);
		c.fillRect(61, 331-80*x, 20, 24);
	    }
	    else if (boardLayout[x+7][0].substring(0, 4).equals("Line")) {
		c.fillRect(43, 338-80*x, 53, 5);
		c.fillRect(43, 358-80*x, 53, 5);
		c.fillRect(48, 332-80*x, 3, 38);
		c.fillRect(58, 332-80*x, 3, 38);
		c.fillRect(68, 332-80*x, 3, 38);
		c.fillRect(78, 332-80*x, 3, 38);
		c.fillRect(88, 332-80*x, 3, 38);
	    }
	    else if (boardLayout[x+7][0].equals("Toronto Hydro")) {
		c.fillRect(53, 338-80*x, 10, 5);
		c.fillRect(55, 338-80*x, 5, 10);
		c.fillRect(55, 348-80*x, 30, 20);
	    }
	    c.setColor(Color.black);
	}
	for (int x=0; x<7; x++) {
	    if (!boardLayout[x+10][4].equals("None")) {
		if (boardLayout[x+10][4].equals(name[0])) {
		    c.setColor(Color.red);
		}
		else {
		    c.setColor(Color.blue);
		}
		c.drawString("Owned by: " + boardLayout[x+10][4], 38+80*x, 108);
	    }
	    c.drawRect(35+80*x, 60, 80, 80);
	    c.drawString(boardLayout[x+10][0], 38+80*x, 68);
	    if (!boardLayout[x+10][0].equals("Community Card") && !boardLayout[x+10][0].equals("Chance Card") && !boardLayout[x+10][0].equals("Go") && !boardLayout[x+10][0].equals("Jail")) {
		c.drawString("Price: " + boardLayout[x+10][1], 38+80*x, 78);
		c.drawString("Rent Cost: " + boardLayout[x+10][2], 38+80*x, 88);
	    }
	    if (!boardLayout[x+10][0].equals("Community Card") && !boardLayout[x+10][0].equals("Chance Card") && !boardLayout[x+10][0].equals("Go") && !boardLayout[x+10][0].equals("Jail") && !boardLayout[x+10][0].equals("Toronto Hydro") && !boardLayout[x+10][0].substring(0, 4).equals("Line")) {
		c.drawString("Multiplier: x" + boardLayout[x+10][3], 38+80*x, 98);
	    }
	    
	    if (boardLayout[x+10][0].equals("Community Card") || boardLayout[x+10][0].equals("Chance Card")) {
		c.setColor(Color.black);
		c.fillRect(58+80*x, 83, 26, 30);
		c.setColor(Color.white);
		c.fillRect(61+80*x, 86, 20, 24);
	    }
	    else if (boardLayout[x+10][0].equals("Jail")) {
		c.fillRect(47+80*x, 78, 55, 55);
		c.setColor(Color.yellow);
		c.fillRect(53+80*x, 82, 7, 46);
		c.fillRect(65+80*x, 82, 7, 46);
		c.fillRect(77+80*x, 82, 7, 46);
		c.fillRect(89+80*x, 82, 7, 46);
	    }
	    else if (boardLayout[x+10][0].substring(0, 4).equals("Line")) {
		c.fillRect(47+80*x, 98, 53, 5);
		c.fillRect(47+80*x, 118, 53, 5);
		c.fillRect(52+80*x, 92, 3, 38);
		c.fillRect(62+80*x, 92, 3, 38);
		c.fillRect(72+80*x, 92, 3, 38);
		c.fillRect(82+80*x, 92, 3, 38);
		c.fillRect(92+80*x, 92, 3, 38);
	    }
	    else if (boardLayout[x+10][0].equals("Toronto Hydro")) {
		c.fillRect(47+80*x, 98, 10, 5);
		c.fillRect(50+80*x, 98, 5, 10);
		c.fillRect(50+80*x, 108, 30, 20);
	    }
	    
	    c.setColor(Color.black);
	}
	for (int x=0; x<3; x++) {
	    if (!boardLayout[x+17][4].equals("None")) {
		if (boardLayout[x+17][4].equals(name[0])) {
		    c.setColor(Color.red);
		}
		else {
		    c.setColor(Color.blue);
		}
		c.drawString("Owned by: " + boardLayout[x+17][4], 518, 188+80*x);
	    }
	    c.drawRect(515, 140+80*x, 80, 80);
	    c.drawString(boardLayout[x+17][0], 518, 148+80*x);
	    if (!boardLayout[x+17][0].equals("Community Card") && !boardLayout[x+17][0].equals("Chance Card") && !boardLayout[x+17][0].equals("Go") && !boardLayout[x+17][0].equals("Jail")) {
		c.drawString("Price: " + boardLayout[x+17][1], 518, 158+80*x);
		c.drawString("Rent Cost: " + boardLayout[x+17][2], 518, 168+80*x);
	    }
	    if (!boardLayout[x+17][0].equals("Community Card") && !boardLayout[x+17][0].equals("Chance Card") && !boardLayout[x+17][0].equals("Go") && !boardLayout[x+17][0].equals("Jail") && !boardLayout[x+17][0].equals("Toronto Hydro") && !boardLayout[x+17][0].substring(0, 4).equals("Line")) {
		c.drawString("Multiplier: x" + boardLayout[x+17][3], 518, 178+80*x);
	    }
	    if (boardLayout[x+17][0].equals("Community Card") || boardLayout[x+17][0].equals("Chance Card")) {
		c.setColor(Color.black);
		c.fillRect(538, 168+80*x, 26, 30);
		c.setColor(Color.white);
		c.fillRect(541, 171+80*x, 20, 24);
	    }
	    else if (boardLayout[x+17][0].substring(0, 4).equals("Line")) {
		c.fillRect(523, 178+80*x, 53, 5);
		c.fillRect(523, 198+80*x, 53, 5);
		c.fillRect(528, 172+80*x, 3, 38);
		c.fillRect(538, 172+80*x, 3, 38);
		c.fillRect(548, 172+80*x, 3, 38);
		c.fillRect(558, 172+80*x, 3, 38);
		c.fillRect(568, 172+80*x, 3, 38);
	    }
	    else if (boardLayout[x+17][0].equals("Toronto Hydro")) {
		c.fillRect(533, 178+80*x, 10, 5);
		c.fillRect(535, 178+80*x, 5, 10);
		c.fillRect(535, 178+80*x, 30, 20);
	    }
	    c.setColor(Color.black);
	}
	c.setColor(Color.red);
	if (players[0][1]%20<7) {
	    c.fillRect(515-80*players[0][1], 380, 10, 10);
	}
	else if (players[0][1]%20<10) {
	   c.fillRect(35, 300-80*(players[0][1]-7), 10, 10);
	}
	else if (players[0][1]%20<17) {
	    c.fillRect(35+80*(players[0][1]-10), 60, 10, 10);
	}
	else if (players[0][1]%20<20) {
	    c.fillRect(515, 140+80*(players[0][1]-17), 10, 10);
	}
	
	c.setColor(Color.blue);
	if (players[1][1]%20<7) {
	    c.fillRect(515-80*players[1][1], 390, 10, 10);
	}
	else if (players[1][1]%20<10) {
	   c.fillRect(35, 310-80*(players[1][1]-7), 10, 10);
	}
	else if (players[1][1]%20<17) {
	    c.fillRect(35+80*(players[1][1]-10), 70, 10, 10);
	}
	else if (players[1][1]%20<20) {
	    c.fillRect(515, 150+80*(players[1][1]-17), 10, 10);
	}
    }
    
    /*
      This method is the checkGameEnd() method. It checks if the game has ended and returns if it has
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      -------------------------------------------------------------------------
      
      if (players[0][0]>0 && players[1][0]>0)
	Returns true if the game is still going (that is, neither players' balances are 0). Otherwise, the game has ended
    */
    private boolean checkGameEnd ()                  //checkGameEnd method
    {
	if (players[0][0]>0 && players[1][0]>0) {
	    return true;
	}
	return false;
    }
    
    /*
      This method is the winScreen() method. It displays a win screen as well as update the highScores file.
      TABLE OF LOCAL VARIABLES:
      Name              Type            Purpose
      ---------------------------------------------------------------------------------------------------------
      winningBalance    int             Stores the balance the winning player has
      winningPlayer     String          Stores the name of the winning player
      input             BufferedReader  reads input from the highScores file
      output            PrintWriter     outputs onto the highScores file 
      worseScore        String          Stores the scores worse than the winningBalance
      worseIndex        int             Stores the location of the first score worse than the winningBalance
      try
	tries to read from the highScores file into the scores array. There may be an exception since highScores might not exist.
	for (int x=0; x<10; x++)
	  reads the contents of the file and stores it into the scores array
	  if (curName!=null)
	    this if statement checks if there is nothing more to read. That means that there are less than 10 entries, so the for loop is broken out of
      catch (Exception e)
	try
	  creats the highScores if there was an exception from trying to read it and then transfers its contents into the scores array
	  for (int x=0; x<10; x++)
	    reads the contents of the file and stores it into the scores array
	    if (curName!=null)
	      this if statement checks if there is nothing more to read. That means that there are less than 10 entries, so the for loop is broken out of
	catch (Exception f)
	  catches any exception and ignores it
      
	
	String worseScore="";
	int worseIndex=0;
	for (int x=0; x<10; x++) {
	    Compares if the current index's score is smaller than winningBalance or empty
	    if (scores[x][1]==null)
		If the current score is blank, all we have to do is insert the current score and move on
	    if (Integer.parseInt(scores[x][1])<winningBalance)
		If the scores are less, then we will have to move on to the next loop. Thus, we need to set up the worstScore and worseIndex variables
	
	if (!worseScore.equals(""))
	    If worseScores isn't empty, that means there are worse scores than the winningBalance. We need to move the following indices down
	    for (int x=worseIndex+1; x<10; x++) 
		the for loop moves the indices down by one

      try
	sets the output variable to write to the highScores file and writes to the file
	for (int x=0; x<10; x++)
	    outputs the contents of the array back into the file
	    if (curName!=null)
	      this if statement checks if there is nothing more to read. That means that there are less than 10 entries, so the for loop is broken out of
      catch (Exception e)
	if anything goes wrong, an error message is displayed
    */
    public void winScreen()                  //winScreen method
    {
	title();
	c.setColor(Color.black);
	c.setFont (new Font ("Arial", Font.BOLD, 30));
	int winningBalance = 0;
	String winningPlayer = "";
	if (players[0][0]>0) {
	    winningBalance = players[0][0];
	    winningPlayer = name[0];
	}
	else {
	    winningBalance = players[1][0];
	    winningPlayer = name[1];
	}
	c.drawString(winningPlayer+" wins!", 230, 250);
	PrintWriter output;
	BufferedReader input;
	String [][] scores = new String [10][2];
	try {
	    input = new BufferedReader(new FileReader("highScores.txt"));
	    String cur = input.readLine();
	    for (int x=0; x<10; x++)
	    {
		if (cur==null) {
		    break;
		}
		scores[x][0] = cur;
		cur = input.readLine();
		scores[x][1] = cur;
		cur = input.readLine();
	    }
	}
	catch (Exception e) {
	    try {
		output = new PrintWriter (new FileWriter("highScores.txt"));
		output.close();
		input = new BufferedReader(new FileReader("highScores.txt"));
		String cur = input.readLine();
		for (int x=0; x<10; x++)
		{
		    if (cur!=null) {
			break;
		    }
		    scores[x][1] = cur;
		    cur = input.readLine();
		    scores[x][1] = cur;
		    cur = input.readLine();
		}
	    }
	    catch (Exception f) {
		JOptionPane.showMessageDialog (null, "Something went wrong.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	String worseScore="";
	int worseIndex=0;
	for (int x=0; x<10; x++) {
	    if (scores[x][1]==null) {
		scores[x][0] = winningPlayer;
		scores[x][1] = winningBalance+"";
		break;
	    }
	    if (Integer.parseInt(scores[x][1])<winningBalance) {
		worseScore = scores[x][1];
		worseIndex=x;
		scores[x][1] = winningBalance+"";
		scores[x][0] = winningPlayer;
		break;
	    }
	}
	
	if (!worseScore.equals("")) {
	    for (int x=worseIndex+1; x<10; x++) {
		String temp = scores[x][1];
		scores[x][1] = worseScore+"";
		worseScore = temp;
	    }
	}
	
	try {
	    output = new PrintWriter (new FileWriter("highScores.txt"));
	    for (int x=0; x<10; x++)
	    {
		if (scores[x][0]==null) {
		    output.close();
		    break;
		}
		output.println(scores[x][0]);
		output.println(scores[x][1]);
	    }
	    output.close();
	}
	catch (Exception e) {
	    JOptionPane.showMessageDialog (null, "Something went wrong.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	pauseProgram();
    }
    
    /*
      This method is the mainMenu() method. It displays the a welcome message, then prompts the user for input among the options on the screen.
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ----------------------------------------------
      ~There are no local variables in this method~

      while (true)
	this while loop will run forever until the break; part of the try block is reached. It is used to run the input code again if improper input is entered
	    try
		reads the input and checks with the following if statement if it's proper input. If the if statement doesn't run, the code block will reach the break statement, exiting the loop
		if (choice != 1 && choice != 2 && choice != 3 && choice != 4)
		    checks if the input is proper, that is, if input is 1, 2, 3, or 4
		      if it is, the code block won't run, and an exception isn't created
		      if it isn't, the code block will run, creating an exception to be caught
	    catch (Exception e)
		This block checks if there is an exception (which was created in the if statement), and if there is, displays an error message and continues with the while loop
    */
    public void mainMenu ()                  //mainMenu method
    {
	while (true)
	{
	    title ();
	    c.setColor(Color.black);
	    c.setFont (new Font ("Arial", Font.BOLD, 15));
	    c.drawString ("Welcome to Monopoly! Choose one of the following options by pressing 1, 2, 3, or 4.", 20, 80);
	    c.setFont (new Font ("Arial", Font.BOLD, 25));
	    c.setColor(Color.black);
	    c.drawString ("1. Instructions", 230, 130);
	    c.setColor(Color.black);
	    c.drawString ("2. High Scores", 228, 180);
	    c.setColor(Color.black);
	    c.drawString ("3. Play", 275, 230);
	    c.setColor(Color.black);
	    c.drawString ("4. Quit", 275, 280);
	    try
	    {
		choice = c.getChar();
		if (choice != '1' && choice != '2' && choice != '3' && choice != '4')
		{
		    throw new Exception ();
		}
		break;
	    }
	    catch (Exception e)
	    {
		JOptionPane.showMessageDialog (null, "Please enter 1, 2, 3, or 4.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }


    /*
      This method is the goodbye() method. It displays the message that users will see before they exit the program. It can be called if 4 is pressed at mainMenu().
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ----------------------------------------------
      ~There are no local variables in this method~

      There are no loops, conditionals, or segment blocks in this method.
    */
    public void goodbye ()                  //goodbye method
    {
	title ();
	c.setFont (new Font ("Arial", Font.BOLD, 40));
	c.drawString ("Thank you for playing Monopoly.", 10, 250);
	c.setFont (new Font ("Arial", Font.BOLD, 25));
	c.drawString ("Created by: Nathan Lu.", 180, 300);
	pauseProgram();
	c.close();
    }
    /*
      This method is the pauseProgram() method. It prompts the user to press any key, causing the program to pause until the user does, giving the user a chance to read what's currently on screen.
      It is called in some methods where the screen would otherwise be instantly replaced by the next screen.
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ----------------------------------------------
      ~There are no local variables in this method~

      There are no loops, conditionals, or segment blocks in this method.
    */
    private void pauseProgram ()                  //pauseProgram method
    {
	c.setFont (new Font ("Arial", Font.BOLD, 15));
	c.setColor(Color.black);
	c.drawString("Press any key to continue.", 220, 480);
	c.setFont (new Font ("Arial", Font.BOLD, 30));
	c.getChar ();
    }

    /*
      This method is the fallingCoin() method. It sets up the thread of FallingCoin.
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ----------------------------------------------
      ~There are no local variables in this method~

      There are no loops, conditionals, or segment blocks in this method.
    */
    public void fallingCoin () {
	Thread d = new Thread (new Coin (c));
	d.start();
    }
    
    /*
      This method is the class constructor. It sets up the console to be used by the class.
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ----------------------------------------------
      ~There are no local variables in this method~

      There are no loops, conditionals, or segment blocks in this method.
    */
    public Monopoly ()                         //Monopoly class constructor
    {
	c = new Console ("Monopoly");                     //creates a new Console object window
    }

    /*
      This method is the main method. It creates an instance of the class and calls all the other methods in the correct order.
      After intro, it checks if the instance/global variable exit is true or not.
      TABLE OF LOCAL VARIABLES:
      Name          Type        Purpose
      ----------------------------------------------
      ~There are no local variables in this method~

      while (m.choice != '4')
	The while loop ensures that the program runs indefinitely until the user chooses to exit (hence why the exit loop condition is the choice variable being 'e')
	The while loop also contains an if structure that determines which functions will run based on what the user inputted. After the choice has been executed, mainMenu() is called again for the next choice to be inputted
	    if (m.choice == '1')
	      checks if the user choice is for instructions()
	    else if (choice == '2')
	      checks if the user choice is for highScores()
	    else if (choice == '3')
	      checks if the user choice is to play the game and runs all the functions associated with it
    */
    public static void main (String[] args)
    {
	Monopoly m = new Monopoly ();
	m.splashScreen();
	while (m.choice != '4')
	{
	    m.mainMenu();
	    if (m.choice == '1') {
		m.instructions();
	    }
	    else if (m.choice == '2') {
		m.highScores();
	    }
	else if (m.choice == '3') {
		m.enterName();
		m.setupBoard();
		m.displayBoard();
		m.winScreen();
	    }
	}
	m.goodbye();
    }
}



