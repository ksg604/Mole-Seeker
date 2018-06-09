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
    public void setNumCols(int cols) {
        if(cols < 0){
            throw new IllegalArgumentException("Number of columns cannot be negative.");
        }
        numCols = cols;
    }
    public int getNumCols(){
        return numCols;
    }

    public void setNumRows(int rows) {
        if(rows < 0){
            throw new IllegalArgumentException("Number of rows cannot be negative.");
        }
        numRows = rows;
    }
    public int getNumRows(){
        return numRows;
    }

    public void setNumMoles(int moles){
        if(moles < 0){
            throw new IllegalArgumentException("Number of moles cannot be negative.");
        }
        numMoles = moles;
    }

    public int getNumMoles(){
        return numMoles;
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
