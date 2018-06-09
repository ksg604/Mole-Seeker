package com.example.kevin.moleseeker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.kevin.moleseeker.model.Game;

public class OptionsActivity extends AppCompatActivity {

    private Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        game = Game.getInstance();
    }

    private void setupGameGrid(){
        TableLayout table = findViewById(R.id.tableForButtons);
        for(int i = 0; i < game.getNumRows(); i++){
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);
            for(int j = 0; j < game.getNumCols(); j++){
                Button button = new Button(this);
                tableRow.addView(button);
            }
        }
    }
}
