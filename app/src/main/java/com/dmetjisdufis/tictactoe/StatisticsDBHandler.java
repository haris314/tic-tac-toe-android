package com.dmetjisdufis.tictactoe;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dmetjisdufis.tictactoe.R;


public class StatisticsDBHandler {

    private static final String TAG = "StatisticsDBHandler";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public StatisticsDBHandler(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getString(context.getString(R.string.firstTime), "true").equals("true")){
            editor.putString(context.getString(R.string.firstTime), "false");
            editor.putInt(context.getString(R.string.wonE), 0);
            editor.putInt(context.getString(R.string.lostE), 0);
            editor.putInt(context.getString(R.string.drawE), 0);
            editor.putInt(context.getString(R.string.wonD), 0);
            editor.putInt(context.getString(R.string.lostD), 0);
            editor.putInt(context.getString(R.string.drawD), 0);
            editor.commit();
        }

    }

    public void increment(String string){
        int n = sharedPreferences.getInt(string, 0);
        editor.putInt(string, ++n);
        editor.commit();
        Log.d(TAG, "increment: "+ sharedPreferences.getInt(string, -10));
    }

    public String getCount(String string){
        String countStr;
        int count;
        count =  sharedPreferences.getInt(string, 0);
        countStr = Integer.toString(count);
        return countStr;
    }
}
