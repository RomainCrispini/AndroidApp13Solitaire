package com.romain.app13solitaire;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

// En étendant non pas AppCompatActivity mais Activity
public class MainActivity extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        gameView = findViewById( R.id.gameView );

    }
}
