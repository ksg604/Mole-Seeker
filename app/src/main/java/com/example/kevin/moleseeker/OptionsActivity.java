package com.example.kevin.moleseeker;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.kevin.moleseeker.model.Game;

public class OptionsActivity extends AppCompatActivity{

    private Game game;
    Spinner boardSizeSpinner;
    Spinner numMolesSpinner;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        game = Game.getInstance();

        setupEraseTimesPlayedBtn();
        setupSpinners();
        getSelection();
    }

    private void setupEraseTimesPlayedBtn(){
        Button eraseTimesPlayed = findViewById(R.id.eraseTimesPlayedBtn);
        eraseTimesPlayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setTimesPlayed(0);
                Toast.makeText(getBaseContext(),"Number of times played reset to 0.",Toast.LENGTH_LONG)
                        .show();
            }
        });
    }


    private void setupSpinners(){
        boardSizeSpinner = findViewById(R.id.spinnerBoardSize);
        adapter = ArrayAdapter.createFromResource(this,R.array.board_sizes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardSizeSpinner.setAdapter(adapter);

        boardSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                saveSelection(position);

                Toast.makeText(getBaseContext(),"Board size set to "+parent.getItemAtPosition(position)+".",Toast.LENGTH_LONG)
                        .show();

                switch(position) {
                    case 0:
                        //Case for 4x6 board size.
                        game.setNumRows(4);
                        game.setNumCols(6);
                        Log.i("4x6", "Set game grid size to 4 rows by 6 columns.");
                        break;
                    case 1:
                        //Case for 5x10 board size.
                        game.setNumRows(5);
                        game.setNumCols(10);
                        //setupGameGrid(game.getNumRows(),game.getNumCols());
                        Log.i("5x10", "Set game grid size to 5 rows by 10 columns.");
                        break;
                    case 2:
                        game.setNumRows(6);
                        game.setNumCols(15);
                        //setupGameGrid(game.getNumRows(),game.getNumCols());
                        Log.i("6x15", "Set game grid size to 6 rows by 15 columns.");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        numMolesSpinner = findViewById(R.id.spinnerNumMoles);
        adapter2 = ArrayAdapter.createFromResource(this,R.array.num_moles,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numMolesSpinner.setAdapter(adapter2);

        numMolesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(),"Number of moles set to "+parent.getItemAtPosition(position)+".",Toast.LENGTH_LONG)
                        .show();
                saveSelection(position);

                switch(position){
                    case 0:
                        game.setNumMoles(6);
                        Log.i("6 moles", "Set number of moles to 6.");
                        break;
                    case 1:
                        game.setNumMoles(10);
                        Log.i("10 moles", "Set number of moles to 10.");
                        break;
                    case 2:
                        game.setNumMoles(15);
                        Log.i("15 moles", "Set number of moles to 15.");
                        break;
                    case 3:
                        game.setNumMoles(20);
                        Log.i("20 moles", "Set number of moles to 20.");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //Saves selection in options activity so when we exit that activity and re-run it we still have the same selection.
    private void saveSelection(int itemPosition){

        int boardSizeSelected = boardSizeSpinner.getSelectedItemPosition();
        int numMolesSelected = numMolesSpinner.getSelectedItemPosition();
        SharedPreferences pref = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("Board Size", boardSizeSelected);
        editor.putInt("Num Moles", numMolesSelected);
        editor.apply();

    }

    //Retrieves data that is shared between activities.
    public void getSelection(){
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int boardSizeSpinnerValue = prefs.getInt("Board Size",-1);
        if(boardSizeSpinnerValue != -1){
            boardSizeSpinner.setSelection(boardSizeSpinnerValue);
        }
        int numMolesSpinnerValue = prefs.getInt("Num Moles", -1);
        if(numMolesSpinnerValue != 0-1){
            numMolesSpinner.setSelection(numMolesSpinnerValue);
        }
    }

}
