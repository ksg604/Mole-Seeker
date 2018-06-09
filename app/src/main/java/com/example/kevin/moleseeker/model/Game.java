package com.example.kevin.moleseeker.model;

public class Game {

    private int numRows;
    private int numCols;
    private int numMines;
    private int scansUsed;
    private int[][] grid;

    /*Singleton model support
     * allows same game object to be shared between two activities
     */
    private static Game instance;
    private Game(){
        //Private constructor so no one else can instantiate an instance of Game except us.
    }
    public static Game getInstance(){
        if (instance == null){
            instance = new Game();
        }
        return instance;
    }


    /*
       Normal object support
     */
    public int getNumMines(){
        return numMines;
    }

    public void setupGrid(int rows,int cols){

        for(int i = 0; i < numCols; i++){
            for(int j = 0; j < numRows; j++){
                grid[i][j] = 0;
            }
        }
    }

}
