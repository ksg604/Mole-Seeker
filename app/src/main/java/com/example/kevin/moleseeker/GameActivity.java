package com.example.kevin.moleseeker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        initIsMole();
        populateWithMoles();
        setupInitialTextViews();
        setupGameGrid();
    }

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
                                vibrator.vibrate(100);
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

    private void setupInitialTextViews(){
        TextView scansText = findViewById(R.id.scansUsedTxt);
        String scansUsedStrings = Integer.toString(game.getScansUsed());
        scansText.setText("# Scans used: "+ scansUsedStrings);

        TextView molesText = findViewById(R.id.molesFoundTxt);
        String getMolesFoundString = Integer.toString(game.getMolesFound());
        String totalMolesString = Integer.toString(game.getNumMoles());
        molesText.setText("Found "+getMolesFoundString+" of "+ totalMolesString + " moles.");
    }

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
            vibrator.vibrate(1000);
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

    private void setIsMole(){
        Random random = new Random();
        int randomRow = random.nextInt(boardRows);
        int randomCol = random.nextInt(boardCols);

        while(isMole[randomRow][randomCol]){
            randomRow = random.nextInt(boardRows);
            randomCol = random.nextInt(boardCols);
        }
        isMole[randomRow][randomCol] = true;
    }

    private void populateWithMoles(){
        int i = 0;
        while(i < totalMoles){
            setIsMole();
            i++;
        }
    }

    private void checkIfFinished(){
        if(game.getNumMoles() == game.getMolesFound()){
            FragmentManager manager = getSupportFragmentManager();
            DialogFragment dialog = new DialogFragment();
            dialog.show(manager, "MessageDialog");
        }
    }


}
