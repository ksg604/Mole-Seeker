package com.example.kevin.moleseeker;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.kevin.moleseeker.model.Game;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private Game game = Game.getInstance();
    private int boardRows = game.getNumRows();
    private int boardCols = game.getNumCols();
    private int totalMoles = game.getNumMoles();


    Button buttons[][] = new Button[boardRows][boardCols];
    private boolean[][] isMole = new boolean[boardRows][boardCols];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        game.setScansUsed(0);
        game.setMolesFound(0);
        getSelection();
        initIsMole();
        populateWithMoles();
        setupInitialTextViews();
        setupGameGrid();
    }




    //Sets up the entire field.  Creates a matrix of buttons(cells).
    private void setupGameGrid(){
        TableLayout buttonTable = findViewById(R.id.tableForButtons);




        for(int row = 0; row < boardRows; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT,1.0f));
            buttonTable.addView(tableRow);
            for(int col = 0; col < boardCols; col++){
                final int FINAL_ROW = row;
                final int FINAL_COL = col;
                final Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1.0f));
                button.setText("");
                buttons[row][col] = button;
                button.setOnClickListener(new View.OnClickListener() {
                    private boolean beenClicked = false;
                    @Override
                    public void onClick(View v) {
                        if(!beenClicked){
                            MediaPlayer scanSound;
                            scanSound = MediaPlayer.create(GameActivity.this,R.raw.scan_sound6);
                            scanSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.release();
                                }
                            });
                            scanSound.start();


                            Vibrator vibrator = (Vibrator) GameActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                            if (vibrator != null && vibrator.hasVibrator()) {
                                vibrator.vibrate(50);
                            }

                            game.useScan();
                            TextView scansUsed = findViewById(R.id.scansUsedTxt);
                            String scansUsedString = Integer.toString(game.getScansUsed());
                            scansUsed.setText("#Scans used: "+scansUsedString);
                            if(isMole[FINAL_ROW][FINAL_COL]){
                                moleDiscovered(FINAL_ROW,FINAL_COL);
                                game.foundMole();
                                TextView molesFound = findViewById(R.id.molesFoundTxt);
                                String getMolesFoundString = Integer.toString(game.getMolesFound());
                                String totalNumMolesString = Integer.toString(game.getNumMoles());
                                molesFound.setText("Found "+getMolesFoundString+" of "+totalNumMolesString + " moles.");

                                //If we find a mole, update the buttons that have already been scanned.
                                for(int col = 0; col < boardCols; col++){
                                    if(!buttons[FINAL_ROW][col].getText().equals("")){
                                       String oldNumOnButtonString = (String) buttons[FINAL_ROW][col].getText();
                                       int newNumOnButtonInt = Integer.parseInt(oldNumOnButtonString);
                                       newNumOnButtonInt--;
                                       String newNumOnButtonString = Integer.toString(newNumOnButtonInt);
                                       buttons[FINAL_ROW][col].setText(newNumOnButtonString);
                                    }
                                }

                                for(int row = 0; row < boardRows; row++){
                                    if(!buttons[row][FINAL_COL].getText().equals("")){
                                        String oldNumOnButtonString = (String) buttons[row][FINAL_COL].getText();
                                        int newNumOnButtonInt = Integer.parseInt(oldNumOnButtonString);
                                        newNumOnButtonInt--;
                                        String newNumOnButtonString = Integer.toString(newNumOnButtonInt);
                                        buttons[row][FINAL_COL].setText(newNumOnButtonString);
                                    }
                                }
                                checkIfFinished();

                            }else{
                                int localMolesHidden = 0;

                                for(int col = 0; col < boardCols; col++){
                                    if(isMole[FINAL_ROW][col]){
                                        localMolesHidden++;
                                    }
                                    if(buttons[FINAL_ROW][col].isSelected()){
                                        localMolesHidden--;
                                    }
                                }
                                for(int row = 0; row < boardRows; row++){
                                    if(isMole[row][FINAL_COL]){
                                        localMolesHidden++;
                                    }
                                    //This is to account for the case where we have already found a mole in the button's row and column.
                                    //localMolesHidden is updated to reflect that mole already found.
                                    if(buttons[row][FINAL_COL].isSelected()){
                                        localMolesHidden--;
                                    }
                                }

                                String localMolesString = Integer.toString(localMolesHidden);

                                button.setText(""+localMolesString);

                            }
                            beenClicked = true;
                        }
                    }
                });
                tableRow.addView(button);

            }
        }
    }

    //Initialize textView fields.
    private void setupInitialTextViews(){
        TextView scansText = findViewById(R.id.scansUsedTxt);
        String scansUsedStrings = Integer.toString(game.getScansUsed());
        scansText.setText("# Scans used: "+ scansUsedStrings);

        TextView molesText = findViewById(R.id.molesFoundTxt);
        String getMolesFoundString = Integer.toString(game.getMolesFound());
        String totalMolesString = Integer.toString(game.getNumMoles());
        molesText.setText("Found "+getMolesFoundString+" of "+ totalMolesString + " moles.");

        TextView timesPlayedText = findViewById(R.id.timesPlayedTxt);
        String timesPlayedNumString = Integer.toString(game.getTimesPlayed());
        timesPlayedText.setText("Times Played: "+timesPlayedNumString);

        int index;

        if(game.getTimesPlayed() > 0){
            TextView highScore = findViewById(R.id.highScoreTxt);

            //Config 4x6 board 6 moles
            if(game.getNumRows() == 4 && game.getNumCols() == 6 && game.getNumMoles() == 6){
                index = 0;
                if(game.getHighScore(index) == 9999){
                    highScore = findViewById(R.id.highScoreTxt);
                    highScore.setText(R.string.high_score);
                }else{
                    String highScoreNumString = Integer.toString(game.getHighScore(index));
                    highScore.setText("High Score: " + highScoreNumString + " scans");
                }

            }

            //Config 4x6 board 10 moles
            if(game.getNumRows() == 4 && game.getNumCols() == 6 && game.getNumMoles() == 10){
                index = 1;
                if(game.getHighScore(index) == 9999){
                    highScore = findViewById(R.id.highScoreTxt);
                    highScore.setText(R.string.high_score);
                }else{
                    String highScoreNumString = Integer.toString(game.getHighScore(index));
                    highScore.setText("High Score: " + highScoreNumString + " scans");
                }

            }

            //config 4x6 board 15 moles
            if(game.getNumRows() == 4 && game.getNumCols() == 6 && game.getNumMoles() == 15){
                index = 2;
                if(game.getHighScore(index) == 9999){
                    highScore = findViewById(R.id.highScoreTxt);
                    highScore.setText(R.string.high_score);
                }else{
                    String highScoreNumString = Integer.toString(game.getHighScore(index));
                    highScore.setText("High Score: " + highScoreNumString + " scans");
                }
            }

            //config 4x6 board 20 moles
            if(game.getNumRows() == 4 && game.getNumCols() == 6 && game.getNumMoles() == 20){
                index = 3;
                if(game.getHighScore(index) == 9999){
                    highScore = findViewById(R.id.highScoreTxt);
                    highScore.setText(R.string.high_score);
                }else{
                    String highScoreNumString = Integer.toString(game.getHighScore(index));
                    highScore.setText("High Score: " + highScoreNumString + " scans");
                }
            }

            //config 5x10 board 6 moles
            if(game.getNumRows() == 5 && game.getNumCols() == 10 && game.getNumMoles() == 6){
                index = 4;
                if(game.getHighScore(index) == 9999){
                    highScore = findViewById(R.id.highScoreTxt);
                    highScore.setText(R.string.high_score);
                }else{
                    String highScoreNumString = Integer.toString(game.getHighScore(index));
                    highScore.setText("High Score: " + highScoreNumString + " scans");
                }
            }

            //config 5x10 board 10 moles
            if(game.getNumRows() == 5 && game.getNumCols() == 10 && game.getNumMoles() == 10){
                index = 5;
                if(game.getHighScore(index) == 9999){
                    highScore = findViewById(R.id.highScoreTxt);
                    highScore.setText(R.string.high_score);
                }else{
                    String highScoreNumString = Integer.toString(game.getHighScore(index));
                    highScore.setText("High Score: " + highScoreNumString + " scans");
                }
            }

            //config 5x10 board 15 moles
            if(game.getNumRows() == 5 && game.getNumCols() == 10 && game.getNumMoles() == 15){
                index = 6;
                if(game.getHighScore(index) == 9999){
                    highScore = findViewById(R.id.highScoreTxt);
                    highScore.setText(R.string.high_score);
                }else{
                    String highScoreNumString = Integer.toString(game.getHighScore(index));
                    highScore.setText("High Score: " + highScoreNumString + " scans");
                }
            }

            //config 5x10 board 20 moles
            if(game.getNumRows() == 5 && game.getNumCols() == 10 && game.getNumMoles() == 20){
                index = 7;
                if(game.getHighScore(index) == 9999){
                    highScore = findViewById(R.id.highScoreTxt);
                    highScore.setText(R.string.high_score);
                }else{
                    String highScoreNumString = Integer.toString(game.getHighScore(index));
                    highScore.setText("High Score: " + highScoreNumString + " scans");
                }
            }

            //config 6x15 board 6 moles
            if(game.getNumRows() == 6 && game.getNumCols() == 15 && game.getNumMoles() == 6){
                index = 8;
                if(game.getHighScore(index) == 9999){
                    highScore = findViewById(R.id.highScoreTxt);
                    highScore.setText(R.string.high_score);
                }else{
                    String highScoreNumString = Integer.toString(game.getHighScore(index));
                    highScore.setText("High Score: " + highScoreNumString + " scans");
                }
            }

            //config 6x15 board 10 moles
            if(game.getNumRows() == 6 && game.getNumCols() == 15 && game.getNumMoles() == 10){
                index = 9;
                if(game.getHighScore(index) == 9999){
                    highScore = findViewById(R.id.highScoreTxt);
                    highScore.setText(R.string.high_score);
                }else{
                    String highScoreNumString = Integer.toString(game.getHighScore(index));
                    highScore.setText("High Score: " + highScoreNumString + " scans");
                }
            }

            //config 6x15 board 15 moles
            if(game.getNumRows() == 6 && game.getNumCols() == 15 && game.getNumMoles() == 15){
                index = 10;
                if(game.getHighScore(index) == 9999){
                    highScore = findViewById(R.id.highScoreTxt);
                    highScore.setText(R.string.high_score);
                }else{
                    String highScoreNumString = Integer.toString(game.getHighScore(index));
                    highScore.setText("High Score: " + highScoreNumString + " scans");
                }
            }

            //config 6x15 board 20 moles
            if(game.getNumRows() == 6 && game.getNumCols() == 15 && game.getNumMoles() == 20){
                index = 11;
                if(game.getHighScore(index) == 9999){
                    highScore = findViewById(R.id.highScoreTxt);
                    highScore.setText(R.string.high_score);
                }else{
                    String highScoreNumString = Integer.toString(game.getHighScore(index));
                    highScore.setText("High Score: " + highScoreNumString + " scans");
                }
            }
        }else {
            TextView highScore = findViewById(R.id.highScoreTxt);
            highScore.setText(R.string.high_score);
        }
    }

    //Case where the player has found a hidden mole.
    private void moleDiscovered(int row, int col){
        MediaPlayer moleFoundSound;
        moleFoundSound = MediaPlayer.create(GameActivity.this,R.raw.found_mole_sound_4);
        moleFoundSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        moleFoundSound.start();



        Vibrator vibrator = (Vibrator) this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(250);
        }

        Button button = buttons[row][col];

        lockButtonSizes();

        int imageWidth = button.getWidth();
        int imageHeight = button.getHeight();

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.moleicon);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap,imageWidth,imageHeight,true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap ));
        //Set selected true basically means that the button background has changed to a mole.
        button.setSelected(true);
    }

    private void lockButtonSizes(){
        for(int row = 0; row < boardRows; row++){
            for(int col = 0; col < boardCols; col++){
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMaxHeight(height);
                button.setMinHeight(height);
            }
        }
    }

    //Initialize a boolean 2D array to keep track of whether or not we have a mole in each index in the button 2D array
    private void initIsMole(){
        for(int i = 0; i < boardRows; i++){
            for(int j = 0; j < boardCols; j++){
                isMole[i][j] = false;
            }
        }
    }

    //Hide moles under random cells in the field.
    private void hideMoles(){
        Random random = new Random();
        int randomRow = random.nextInt(boardRows);
        int randomCol = random.nextInt(boardCols);

        while(isMole[randomRow][randomCol]){
            randomRow = random.nextInt(boardRows);
            randomCol = random.nextInt(boardCols);
        }
        isMole[randomRow][randomCol] = true;
    }

    //With regards to the total number of moles instantiated by Singleton model, populate the field.
    private void populateWithMoles(){
        int i = 0;
        while(i < totalMoles){
            hideMoles();
            i++;
        }
    }

    //Check if the number of moles found by the player is equal to the number of moles that have populated the field.
    //Updates the high score for each possible game configuration.
    private void checkIfFinished(){
        if(game.getNumMoles() == game.getMolesFound()){
            game.incTimesPlayed();

            //Config 4x6 board 6 moles
            if(game.getNumRows() == 4 && game.getNumCols() == 6 && game.getNumMoles() == 6){
                int score = game.getScansUsed();
                if(score < game.getHighScore(0)){
                    game.setHighScore(score,0);
                }
                saveGameData(0);
            }

            //Config 4x6 board 10 moles
            if(game.getNumRows() == 4 && game.getNumCols() == 6 && game.getNumMoles() == 10){
                int score = game.getScansUsed();
                if(score < game.getHighScore(1)){
                    game.setHighScore(score,1);
                }
                saveGameData(1);
            }

            //config 4x6 board 15 moles
            if(game.getNumRows() == 4 && game.getNumCols() == 6 && game.getNumMoles() == 15){
                int score = game.getScansUsed();
                if(score < game.getHighScore(2)){
                    game.setHighScore(score,2);
                }
                saveGameData(2);
            }

            //config 4x6 board 20 moles
            if(game.getNumRows() == 4 && game.getNumCols() == 6 && game.getNumMoles() == 20){
                int score = game.getScansUsed();
                if(score < game.getHighScore(3)){
                    game.setHighScore(score,3);
                }
                saveGameData(3);
            }

            //config 5x10 board 6 moles
            if(game.getNumRows() == 5 && game.getNumCols() == 10 && game.getNumMoles() == 6){
                int score = game.getScansUsed();
                if(score < game.getHighScore(4)){
                    game.setHighScore(score,4);
                }
                saveGameData(4);
            }

            //config 5x10 board 10 moles
            if(game.getNumRows() == 5 && game.getNumCols() == 10 && game.getNumMoles() == 10){
                int score = game.getScansUsed();
                if(score < game.getHighScore(5)){
                    game.setHighScore(score,5);
                }
                saveGameData(5);
            }

            //config 5x10 board 15 moles
            if(game.getNumRows() == 5 && game.getNumCols() == 10 && game.getNumMoles() == 15){
                int score = game.getScansUsed();
                if(score < game.getHighScore(6)){
                    game.setHighScore(score,6);
                }
                saveGameData(6);
            }

            //config 5x10 board 20 moles
            if(game.getNumRows() == 5 && game.getNumCols() == 10 && game.getNumMoles() == 20){
                int score = game.getScansUsed();
                if(score < game.getHighScore(7)){
                    game.setHighScore(score,7);
                }
                saveGameData(7);
            }

            //config 6x15 board 6 moles
            if(game.getNumRows() == 6 && game.getNumCols() == 15 && game.getNumMoles() == 6){
                int score = game.getScansUsed();
                if(score < game.getHighScore(8)){
                    game.setHighScore(score,8);
                }
                saveGameData(8);
            }

            //config 6x15 board 10 moles
            if(game.getNumRows() == 6 && game.getNumCols() == 15 && game.getNumMoles() == 10){
                int score = game.getScansUsed();
                if(score < game.getHighScore(9)){
                    game.setHighScore(score,9);
                }
                saveGameData(9);
            }

            //config 6x15 board 15 moles
            if(game.getNumRows() == 6 && game.getNumCols() == 15 && game.getNumMoles() == 15){
                int score = game.getScansUsed();
                if(score < game.getHighScore(10)){
                    game.setHighScore(score,10);
                }
                saveGameData(10);
            }

            //config 6x15 board 20 moles
            if(game.getNumRows() == 6 && game.getNumCols() == 15 && game.getNumMoles() == 20){
                int score = game.getScansUsed();
                if(score < game.getHighScore(11)){
                    game.setHighScore(score,11);
                }
                saveGameData(11);
            }

            TextView timesPlayed = findViewById(R.id.timesPlayedTxt);
            String timesPlayedNumString = Integer.toString(game.getTimesPlayed());
            timesPlayed.setText("Times Played: "+timesPlayedNumString);

            FragmentManager manager = getSupportFragmentManager();
            DialogFragment dialog = new DialogFragment();
            dialog.show(manager, "MessageDialog");
        }
    }

    private void saveGameData(int index){


        SharedPreferences pref = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("Times played",game.getTimesPlayed());
        editor.putInt("Highscore", game.getHighScore(index));
        editor.putInt("highScoreIndex", index);
        editor.apply();
    }

    //Retrieves data that is shared between activities.
    public void getSelection(){
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int timesPlayed = prefs.getInt("Times played",-1);
        int highScoreIndex = prefs.getInt("highScoreIndex",-1);

        if(timesPlayed != -1){
            game.setTimesPlayed(timesPlayed);
        }

        int highScore = prefs.getInt("Highscore",-1);
        if((highScore != -1) && (highScoreIndex != -1)){
            game.setHighScore(highScore,highScoreIndex);
        }
    }
}
