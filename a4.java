/*
						Avery Jacobson
						     csci 1203
						  James Polzin					


Assignment 4:                Given: 06/12/14     Due: 06/20/14
------------                                  Points: 10
Purpose: - Start working on the game of Battleship
         - Create a class to model a Ship in the game 
           Battleship.
           - This class will be similar to the SimpleDotCom 
             class from Chap. 5 in the Sierra text.

Description:
    Create a class Ship to model a ship in the game of 
    Battleship. Just like the SimpleDotCom class, an object 
    of this class should know its location and whether it has
    been sunk. These will be important when a board that
    contains several ships is part of a game.
    
    However, this assignment is only about the class ship.
    In that regard:
      - do not work on the game
      - do not work on the board
      - do not work on multiple players
    These things will be issues later, but for now, you are
    only coding a class Ship.
      
Notes:
-----
  - A description of the game and its rules can be obtained 
    from Mr. Polzin
  - Write a good ShipTestDrive class to exercise the Ship 
    class and show that it is working the way it should.
    Be sure to:
    ----------
      - create several objects in the test code
      - demonstrate Hit/Miss/Sunk

*/


import java.util.*;

public class a4 
{
  public static void main( String args[] ) 
  {
	Ship[] playerOneShips = new Ship[5];
	Ship[] playerTwoShips = new Ship[5];

	
	

	playerOneShips[0] = new Ship("Aircraft carrier",5,1,'a',1,'e',0);  // player, ship, size, row front, column front, row back, column back, hit
	playerOneShips[1] = new Ship("Battleship",      4,2,'b',5,'b',0);
 	playerOneShips[2] = new Ship("Submarine",       3,2,'c',4,'c',0);
	playerOneShips[3] = new Ship("Destroyer",       3,2,'d',4,'d',0);
	playerOneShips[4] = new Ship("Patrol boat",     2,2,'e',3,'e',0);

	playerTwoShips[0] = new Ship("Aircraft carrier",5,1,'a',1,'e',0);  // player, ship, size, row front, column front, row back, column back, hit
	playerTwoShips[1] = new Ship("Battleship",      4,2,'b',5,'b',0);
 	playerTwoShips[2] = new Ship("Submarine",       3,2,'c',4,'c',0);
	playerTwoShips[3] = new Ship("Destroyer",       3,2,'d',4,'d',0);
	playerTwoShips[4] = new Ship("Patrol boat",     2,2,'e',3,'e',0);


	int[][] playerOneBoard  = new int[11][11];   //All the ships in an array
	int[][] playerOneGuess  = new int[11][11];
	
	

	int[][] playerTwoBoard  = new int[11][11];
	int[][] playerTwoGuess  = new int[11][11];
	int[][] count  = new int[1][2];

	
	
	int player = 1;	
	int count1 = 0;
	int count2 = 0;


	for(Ship e: playerOneShips)
		arrayAssignShip(e, playerOneBoard);

	for(Ship e: playerTwoShips)	
		arrayAssignShip(e, playerTwoBoard);



	while( (count1 < 17) && (count2 < 17) )
	{

		System.out.println("count[0][0] = " + count[0][0]);
		System.out.println("count[0][1] = " + count[0][1]);

		if(player == 1)
			player = play(playerOneBoard, playerOneGuess, playerOneShips,player,count);

		if((player == 2) && (count[0][0] < 17))
			player = play(playerTwoBoard, playerTwoGuess, playerTwoShips,player,count);

		count1 = count[0][0];
		count2 = count[0][1];

		System.out.println("count1 = " + count1);
		System.out.println("count2 = " + count2);

	}	
  }


  public static int play(int[][] playerBoard, int[][] playerGuess, Ship[] playerShips, int player, int[][] count)
  {
	Scanner in = new Scanner(System.in);
	int[][] shipBoard  = new int[11][11];	     //One Ship at a time in an array to check if ship was hit
	int[][] currentGuess  = new int[11][11];     //Current Guess in an array

	int i = 0;
	int j = 0;
	int k = 0;
	int l = 0;
	int m = 0;
	int b = 0;
	char c;
	int inrow;
	

		
	System.out.println("\n\n\nPlayer: " + player + "'s turn:\n");


	for(i = 1; i <= 10; i++)
	{
		for(j = 1; j <= 10; j++)
		{
			if(playerGuess[i][j] == 3)
				System.out.print("* ");
			if(playerGuess[i][j] == 1)
				System.out.print("X ");
			if(playerGuess[i][j] == 0)
				System.out.print(playerGuess[i][j] + " "); 
		}

		System.out.print("\n");
	}

	System.out.print("\nEnter a charactor from A - k for column and and number from 1 - 10 \nrespectively for the quardents of your shot: ");
	c = in.next().charAt(0);
	inrow  = in.nextInt();



	for(i = 1; i <= 10; i++)
		for(j = 1; j <= 10; j++)
			currentGuess[i][j] = 5;


	arrayAssignInput(currentGuess,c,inrow);



	for(i = 1; i <= 10; i++)
		for(j = 1; j <= 10; j++)
			if(  (currentGuess[i][j] == 1) && ((playerGuess[i][j] == 3) || (playerGuess[i][j] == 1)))
				b = 1;


	if( b == 0)
	{
		arrayAssignInput(playerGuess,c,inrow);

		for(i = 1; i <= 10; i++)
			for(j = 1; j <= 10; j++)
			{
				if( playerBoard[i][j] == 1 && playerGuess[i][j] == 1)
				{
					for(Ship x : playerShips)
					{	
		
						for(k = 0; k <= 10; k++) 
						{
							for(l = 0; l <= 10; l++)
							{
								shipBoard[k][l] = 0;
							}
						}


						arrayAssignShip(x,shipBoard);
		
				
						for(k = 1; k<=10; k++)
							for(l = 1; l<=10; l++)
							{
								if((shipBoard[k][l] == 1) && (playerGuess[k][l] == 1))
								{
									x.setHit();

									if(player == 1)
										count[0][0]++;
									if(player == 2)
										count[0][1]++;
	
									if(x.getHit() == x.getSize())
										System.out.print("you sunk my : " + x.getName() + "\n");
									else
										System.out.print("\n\nPlayer hit a ship\n");
											
									playerGuess[k][l] = 3;
								}		
							}
					}	
				}
						//else
							//System.out.print("miss");
			}
			
		
		if(player == 1)
			player = 2;
		else if(player == 2)
			player = 1;
			
	}
	else
		System.out.println("\n\nGuess already made");

	return player;
  }





  public static void arrayAssignInput(int[][] a, char ch, int ir)
  {

	if(ch == 'A' || ch == 'a')
		a[ir][1] = 1;
	if(ch == 'B' || ch == 'b')
		a[ir][2] = 1;
	if(ch == 'C' || ch == 'c')
		a[ir][3] = 1;
	if(ch == 'D' || ch == 'd')
		a[ir][4] = 1;
	if(ch == 'E' || ch == 'e')
		a[ir][5] = 1;
	if(ch == 'F' || ch == 'f')
		a[ir][6] = 1;
	if(ch == 'G' || ch == 'g')
		a[ir][7] = 1;
	if(ch == 'H' || ch == 'h')
		a[ir][8] = 1;
	if(ch == 'I' || ch == 'i')
		a[ir][9] = 1;
	if(ch == 'J' || ch == 'j')
		a[ir][10] = 1;
  }




  public static void arrayAssignShip(Ship x, int[][] a)
  {

	int i;
		
	if((x.getColf() == 'a' || x.getColf() == 'A'))
	{
		if(x.getColf() == x.getColb())
			for(i = x.getRowf(); i < (x.getRowf()+x.getSize()); i++ )
				a[i][1] = 1;
			
		if(x.getRowf() == x.getRowb())
			for(i = 1; i < (1+x.getSize()); i++ )
				a[x.getRowf()][i] = 1;
			
	}
		
	if((x.getColf() == 'b' || x.getColf() == 'B'))
	{
		if(x.getColf() == x.getColb())
			for(i = x.getRowf(); i < (x.getRowf()+x.getSize()); i++ )
				a[i][2] = 1;

		if(x.getRowf() == x.getRowb())
			for(i = 2; i < (2+x.getSize()); i++ )
				a[x.getRowf()][i] = 1;
				
	}


	if((x.getColf() == 'c' || x.getColf() == 'C'))
	{
		if(x.getColf() == x.getColb())
			for(i = x.getRowf(); i < (x.getRowf()+x.getSize()); i++ )
				a[i][3] = 1;
				
		if(x.getRowf() == x.getRowb())
			for(i = 3; i < (3+x.getSize()); i++ )
				a[x.getRowf()][i] = 1;
				
	}

	if((x.getColf() == 'd' || x.getColf() == 'D'))
	{
		if(x.getColf() == x.getColb())
			for(i = x.getRowf(); i < (x.getRowf()+x.getSize()); i++ )
				a[i][4] = 1;
				
		if(x.getRowf() == x.getRowb())
			for(i = 4; i < (4+x.getSize()); i++ )
				a[x.getRowf()][i] = 1;
				
	}  

	if((x.getColf() == 'e' || x.getColf() == 'E'))
	{
		if(x.getColf() == x.getColb())
			for(i = x.getRowf(); i < (x.getRowf()+x.getSize()); i++ )
				a[i][5] = 1;
			
		if(x.getRowf() == x.getRowb())
			for(i = 5; i < (5+x.getSize()); i++ )
				a[x.getRowf()][i] = 1;
				
	}   

	if((x.getColf() == 'f' || x.getColf() == 'F'))
	{
		if(x.getColf() == x.getColb())
			for(i = x.getRowf(); i < (x.getRowf()+x.getSize()); i++ )
				a[i][6] = 1;
				
		if(x.getRowf() == x.getRowb())
			for(i = 6; i < (6+x.getSize()); i++ )
				a[x.getRowf()][i] = 1;
				
	}   

	if((x.getColf() == 'g' || x.getColf() == 'G'))
	{
		if(x.getColf() == x.getColb())
			for(i = x.getRowf(); i < (x.getRowf()+x.getSize()); i++ )
				a[i][7] = 1;
				
		if(x.getRowf() == x.getRowb())
			for(i = 7; i < (7+x.getSize()); i++ )
				a[x.getRowf()][i] = 1;
				
	}  

	if((x.getColf() == 'h' || x.getColf() == 'H'))
	{
		if(x.getColf() == x.getColb())
			for(i = x.getRowf(); i < (x.getRowf()+x.getSize()); i++ )
					a[i][8] = 1;
			
		if(x.getRowf() == x.getRowb())
			for(i = 8; i < (8+x.getSize()); i++ )
				a[x.getRowf()][i] = 1;
				
	}   

	if((x.getColf() == 'i' || x.getColf() == 'I'))
	{
		if(x.getColf() == x.getColb())
			for(i = x.getRowf(); i < (x.getRowf()+x.getSize()); i++ )
				a[i][9] = 1;
				
		if(x.getRowf() == x.getRowb())
			for(i = 9; i < (9+x.getSize()); i++ )
				a[x.getRowf()][i] = 1;
				
	}   

	if((x.getColf() == 'j' || x.getColf() == 'J'))
	{
		if(x.getColf() == x.getColb())
			for(i = x.getRowf(); i < (x.getRowf()+x.getSize()); i++ )
				a[i][10] = 1;
				
		if(x.getRowf() == x.getRowb())
			for(i = 10; i < (10+x.getSize()); i++ )
				a[x.getRowf()][i] = 1;
			
	}       

  }



}

class Ship
{
	public Ship(int p, String s, int z, int rf, char cf, int rb, char cb, int h)
 	{
		player = p;
		name   = s;		
 		size   = z; 		
		rowFront   = rf; 
 		colFront   = cf;
 		rowBack   = rb; 
 		colBack   = cb;
 		hit    = h;
 	}

	public String getName()
	{
		return name;
	}

	public int getSize()
	{
		return size;
	}

	public int getRowf()
	{
		return rowFront;
	}

	public int getRowb()
	{
		return rowBack;
	}


	public char getColf()
	{
		return colFront;
	}


	public char getColb()
	{
		return colBack;
	}


	public int getPlayer()
	{
		return player;
	}
	public int getHit()
	{
		return hit;
	}
	
	public void setHit()
	{
		hit = hit +1;
	}


			
	
	private String name;		
 	private int size; 		    //size of ship
	private int rowFront; 
 	private char colFront;		    //column front char
 	private int rowBack; 
 	private char colBack;		    //column back char
 	private int player;
	private int hit;
} 






