package com.example.kevin.moleseeker.model;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.kevin.moleseeker.R;


public class Game extends AppCompatActivity{

    //By default, board size is 4x6 and number of moles is 6.
    private int numRows = 4;
    private int numCols = 6;
    private int numMoles = 6;
    private int scansUsed;
    private int timesPlayed = 0;
    private int molesFound;

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
    public void setTimesPlayed(int times){
        timesPlayed = times;
    }
    public int getTimesPlayed(){
        return timesPlayed;
    }
    public void incTimesPlayed(){
        timesPlayed++;
    }
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

    //Total moles
    public int getNumMoles(){
        return numMoles;
    }

    public void setMolesFound(int moles){
        molesFound = moles;
    }

    public int getMolesFound(){
        return molesFound;
    }

    public void foundMole(){
        molesFound++;
    }

    public int getScansUsed(){
        return scansUsed;
    }
    public void setScansUsed(int scans){
        scansUsed = scans;
    }
    public void useScan(){
        scansUsed++;
    }



}
