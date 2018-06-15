package com.example.kevin.moleseeker;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kevin.moleseeker.model.Game;

import static java.lang.System.exit;

public class MainMenuActivity extends AppCompatActivity {

    private Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        game = Game.getInstance();
        setupOptionsBtn();
        setupPlayGameBtn();
        setupHelpBtn();
    }

    private void setupOptionsBtn(){
        Button optionsBtn = findViewById(R.id.optionsBtn);
        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent optionsIntent = new Intent(getApplicationContext(),OptionsActivity.class);
                startActivity(optionsIntent);
            }
        });
    }

    private void setupPlayGameBtn(){
        Button playGameBtn = findViewById(R.id.playGameBtn);
        playGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent playGameIntent = new Intent(getApplicationContext(),GameActivity.class);
                startActivity(playGameIntent);
            }
        });
    }

    private void setupHelpBtn(){
        Button helpBtn = findViewById(R.id.helpBtn);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent helpIntent = new Intent(getApplicationContext(),HelpActivity.class);
                startActivity(helpIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
