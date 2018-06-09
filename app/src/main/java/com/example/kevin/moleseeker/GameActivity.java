package com.example.kevin.moleseeker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kevin.moleseeker.model.Game;

public class GameActivity extends AppCompatActivity {
    private Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        game = Game.getInstance();
    }
}
