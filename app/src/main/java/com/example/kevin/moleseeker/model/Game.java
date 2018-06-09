package com.example.kevin.moleseeker.model;


import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;


public class Game {

    private int numRows;
    private int numCols;
    private int numMoles;
    private int scansUsed;

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
    public int getNumMoles(){
        return numMoles;
    }
    public int getNumCols(){
        return numCols;
    }
    public int getNumRows(){
        return numRows;
    }
    public int getScansUsed(){
        return scansUsed;
    }
    public void useScan(){
        scansUsed++;
    }
    public void moleFound(){
        numMoles--;
    }

    /*
    public Button[][] setupGrid(int rows,int cols){
        TableLayout table;
        for(int i = 0; i < numCols; i++){
            TableRow tableRow = new TableRow(this);
            for(int j = 0; j < numRows; j++){

            }
        }
    }*/

}
