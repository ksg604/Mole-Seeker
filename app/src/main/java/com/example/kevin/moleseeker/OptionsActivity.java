package com.example.kevin.moleseeker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.kevin.moleseeker.model.Game;

public class OptionsActivity extends AppCompatActivity{

    private Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        game = Game.getInstance();
    }

    private void setupSpinners(){
        Spinner boardSize = findViewById(R.id.spinnerGridSize);
        String[] boardSizes = new String[]{"4x6","5x10","6x15"};
        ArrayAdapter<String> adapterBoardSizes = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,boardSizes);
        boardSize.setAdapter(adapterBoardSizes);
        boardSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        game.setNumRows(4);
                        game.setNumCols(6);
                        setupGameGrid(game.getNumRows(),game.getNumCols());
                }
            }
        });
    }

    private void setupGameGrid(int rows, int cols){
        TableLayout table = findViewById(R.id.tableForButtons);
        for(int i = 0; i < rows; i++){
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);
            for(int j = 0; j < cols; j++){
                Button button = new Button(this);
                tableRow.addView(button);
            }
        }
    }
}
