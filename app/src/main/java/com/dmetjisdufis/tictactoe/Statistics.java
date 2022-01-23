package com.dmetjisdufis.tictactoe;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Statistics extends AppCompatActivity {

    private TextView wonE;
    private TextView lostE;
    private TextView drawE;
    private TextView wonD;
    private TextView lostD;
    private TextView drawD;

    StatisticsDBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        db = new StatisticsDBHandler(this);
        wonE = findViewById(R.id.wonE);
        lostE = findViewById(R.id.lostE);
        drawE = findViewById(R.id.drawE);
        wonD = findViewById(R.id.wonD);
        lostD = findViewById(R.id.lostD);
        drawD = findViewById(R.id.drawD);

        wonE.setText(db.getCount(getString(R.string.wonE)));
        lostE.setText(db.getCount(getString(R.string.lostE)));
        drawE.setText(db.getCount(getString(R.string.drawE)));
        wonD.setText(db.getCount(getString(R.string.wonD)));
        lostD.setText(db.getCount(getString(R.string.lostD)));
        drawD.setText(db.getCount(getString(R.string.drawD)));


    }

}
