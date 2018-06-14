package com.example.kevin.moleseeker;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setupBackBtn();
        setupAboutAuthorBtn();

        TextView helpTextTitle = findViewById(R.id.helpTxtTitle);
        helpTextTitle.setText(getResources().getString(R.string.help_title));
        TextView helpText = findViewById(R.id.helpTxt);
        helpText.setText(getResources().getString(R.string.help_txt));

    }

    private void setupAboutAuthorBtn(){
        Button aboutAuthorBtn = findViewById(R.id.aboutAuthor);
        aboutAuthorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent aboutAuthorIntent = new Intent(getApplicationContext(),AboutAuthorActivity.class);
                startActivity(aboutAuthorIntent);
            }
        });

    }

    private void setupBackBtn(){
        Button backBtn = findViewById(R.id.backBtnHelp);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HelpActivity.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
