package com.example.advmobile.homework1.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.advmobile.homework1.R;

import java.lang.ref.WeakReference;

public class StartScreen extends Activity {

    private static WeakReference<Activity> activity = new WeakReference<>(null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        activity = new WeakReference<Activity>(this);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            ActionBar actionBar = getActionBar();
            if (actionBar != null)
                actionBar.hide();
        }

        // Check the re-create of the activity
        if (savedInstanceState == null)  {
            WaitTask wt = new WaitTask();
            wt.execute();
        }
    }

    private static class WaitTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Activity activity = StartScreen.activity.get();
            if (activity != null) {
                Intent intent = new Intent(activity, ListActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable this while splash screen is showing
    }
}
