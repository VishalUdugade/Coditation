import java.lang.*;
import java.util.*;


class myexception extends Exception{
    myexception(){}

    myexception(String str)
    {
         System.out.println(str);
    }
}


class base{
    public int current_state;
    public int previous_state;
    public int liveneighbours;

    public base(int cur,int pre,int ln)
    {
        this.current_state = cur;
        this.previous_state = pre;
        this.liveneighbours = ln;
    }

}

class orthrogonalgrid{

  public static void main(String args[])
  {
       try
       {
                 Scanner sobj = new Scanner(System.in);

    
                System.out.println("Enter the size 1 (max limit 100) :");
                int M = sobj.nextInt();

                System.out.println("Enter the size 2 (max limit 100) :");
                int N = sobj.nextInt();
  
                //Handle the limit to create the cell  
                if((M > 100) || (N > 100))
                {
                      throw new myexception("You are Exceeding limit");
                }
    

                //for creating generation
                System.out.println("Enter tick (Max limit 100) = ");
                int tick = sobj.nextInt();
     
                //create the array of  objects of base class
  	            base[][] obj = new base[M][N];
                
                //this outer for loop for rows 
            	for(int i = 0;i < obj.length;i++)
	            {
                    //this inner for loop for columns
	                for(int j = 0; j < obj[i].length;j++)
	                {
	   	                 obj[i][j] = new base((int)Math.round(Math.random()),0,0);  //math.random number creates a and value in decimal number ex.0.1,0.2.
                                                                                    // and   math.round function convert that number in exact 1 or 0
                                                                                    // Hence we get and random number 1 or 0.
	                 }
	            }



                //Display original gerneration
                printgrid(obj,"Original Generation");


             	// Displaying the grid
                 System.out.println();
               for(int i = 0;i < tick ;i++)
               {
                    nextGeneration(obj, M, N); //Creating next generation of grid at tick times
                    printgrid(obj,"Next Generation");
               }


               int choice;
                do
                {
                          System.out.println("\n1.Display the state of cell \n2.Exit \n3.Enter Your choice");
                          choice = sobj.nextInt();

                          switch(choice)
                          {
                                 case 1:
                                       System.out.println("Enter the row value = ");
                                       int mval = sobj.nextInt();
               
                                        System.out.println("Enter the column value = ");
                                        int nval = sobj.nextInt();
                    
                                        System.out.println("current State of Cell :");
                                        if(obj[mval][nval].current_state == 0)
                                        {
                                               System.out.println("The Cell is Dead");
                                        }
                                        else
                                        {
                                               System.out.println("The Cell is Live");
                                         }

                                        System.out.println("previous State of Cell :");
                                        if(obj[mval][nval].previous_state == 0)
                                        {
                                                System.out.println("The Cell is Dead");
                                        }
                                        else
                                        {
                                                System.out.println("The Cell is Live");
                                        }
                    
                                 break;

                                 case 2:
                                        System.exit(0);
                                  break;

                                default:
                                     System.out.println("Invalid choice...");
                                break;
                           }

                 }while(choice != 2);
               }
               catch(myexception ex)
               {
                     System.out.println(ex);
               }
              catch(Exception ex)
               {
                      System.out.println(ex);
               }
        
    }
  
    // Function to print next generation
    static void nextGeneration(base grid[][], int M, int N)
    {

        for (int row = 0 ; row < M ; row++) {
    
            for (int col = 0 ; col < N ; col++) {
             getLiveNeighbours(row, col, grid); //set the count of liverneighbours of cell to the 'liveneighbours' member of class base
             getNewCellState(row, col, grid);   //set the current state of cell to the 'current_state' member of class base
            }
        }
  
    }

    

    static void getLiveNeighbours(int cellRow, int cellCol, base[][] grid) {

         grid[cellRow][cellCol].liveneighbours = 0; //make liveneighbour count zero to add new count
        int rowEnd = Math.min(grid.length , cellRow + 2); //get the next row number from cellrow
        int colEnd = Math.min(grid[0].length, cellCol + 2);//get the next column number from cellcol

        for (int row = Math.max(0, cellRow - 1) ; row < rowEnd ; row++) {
            
            for (int col = Math.max(0, cellCol - 1) ; col < colEnd ; col++) {
                
                // make sure to exclude the cell itself from calculation
                if ((row != cellRow || col != cellCol) && grid[row][col].current_state == 1) {
                    grid[cellRow][cellCol].liveneighbours++;
                }
            }
        }
    }


    static void getNewCellState(int cellRow, int cellCol, base grid[][] ) {

        grid[cellRow][cellCol].previous_state = grid[cellRow][cellCol].current_state;

        switch (grid[cellRow][cellCol].current_state) {
        case 1:

            // Any live cell with fewer than two 
            // live neighbours dies
            if (grid[cellRow][cellCol].liveneighbours < 2) {
                grid[cellRow][cellCol].current_state = 0;
            }

            // Any live cell with two or three live   
            // neighbours lives on to the next generation.
            if (grid[cellRow][cellCol].liveneighbours == 2 || grid[cellRow][cellCol].liveneighbours == 3) {
                grid[cellRow][cellCol].current_state = 1;
            }

            // Any live cell with more than three live neighbours
            // dies, as if by overcrowding.
            if (grid[cellRow][cellCol].liveneighbours > 3) {
                grid[cellRow][cellCol].current_state = 0;
            }
            break;

        case 0:
            // Any dead cell with exactly three live neighbours becomes a 
            // live cell, as if by reproduction.
            if (grid[cellRow][cellCol].liveneighbours == 3) {
                grid[cellRow][cellCol].current_state = 1;
            }
            break;

        default:
           System.out.println("Invalid case..");
        }           
    }

    //Show Orthrogonalgrid
    static void printgrid(base grid[][],String str) {
        System.out.println(str);

        for (int i = 0, e = grid.length ; i < e ; i++) {

            for (int j = 0, f = grid[i].length ; j < f ; j++) {
                System.out.print(grid[i][j].current_state + ",");
            } 
            System.out.println("\n");
        }
    }
}