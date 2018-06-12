package com.example.kevin.moleseeker;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.kevin.moleseeker.model.Game;

public class GameActivity extends AppCompatActivity {
    private Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        game = Game.getInstance();
        setupGameGrid();
    }

    public void setupGameGrid(){
        TableLayout buttonTable = findViewById(R.id.tableForButtons);


        for(int i = 0; i < game.getNumRows(); i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT,1.0f));
            buttonTable.addView(tableRow);
            for(int j = 0; j < game.getNumCols(); j++){
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1.0f));
                tableRow.addView(button);
            }
        }
    }

}
