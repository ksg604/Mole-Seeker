package com.example.kevin.moleseeker;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutAuthorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_author);

        setupBackBtn();
        TextView aboutAuthorText = findViewById(R.id.aboutAuthorTxt);
        aboutAuthorText.setText(getResources().getString(R.string.about_author_txt));


        TextView hyperLink = findViewById(R.id.hyperlink);
        hyperLink.setText(R.string.hyperlinkString);
        hyperLink.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void setupBackBtn(){

        Button btn = findViewById(R.id.backBtnAboutAuthor);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AboutAuthorActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AboutAuthorActivity.this.finish();
    }
}
