package com.example.advmobile.lection2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ThreadActivity extends Activity implements View.OnClickListener {

    StringBuffer _outBuffer = new StringBuffer(); // StringBuffer is tread-safe
    TextView _tv;
    ScrollView _scroller;

    static int _asyncTaskCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_activity);

        _tv = (TextView)findViewById(R.id.out_text);
        Button btn;
        btn = (Button)findViewById(R.id.thread_start);
        btn.setOnClickListener(this);
        btn = (Button)findViewById(R.id.async_start);
        btn.setOnClickListener(this);
        btn = (Button)findViewById(R.id.timer_set);
        btn.setOnClickListener(this);

        _scroller = (ScrollView) findViewById(R.id.scrollView);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.thread_start:
                _outBuffer.append("Стартанули поток\n");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int count = 0;
                        while (++count < 10) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            _outBuffer.append("Поток ");
                            _outBuffer.append(Thread.currentThread().getId());
                            _outBuffer.append(" собщение ");
                            _outBuffer.append(count);
                            _outBuffer.append("\n");

                            runOnUiThread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            updateOutText();
                                        }
                                    });
                        }
                    }
                }).start();
                break;
            case R.id.async_start:
                _outBuffer.append("Запустили таск\n");
                new MyTask().execute();
                break;
            case R.id.timer_set:
                _outBuffer.append("Устновили таймер\n");
                Timer myTimer = new Timer();
                final Handler uiHandler = new Handler();
                myTimer.schedule(new TimerTask() { // Определяем задачу
                    @Override
                    public void run() {
                        _outBuffer.append("Таймер ");
                        _outBuffer.append(Thread.currentThread().getId());
                        _outBuffer.append("\n");

                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                updateOutText();
                            }
                        });
                    }
                }, 0L, 3000); // интервал - 60000 миллисекунд, 0 миллисекунд до первого запуска.
                break;
            default:
                _outBuffer.append("Чудо чудное\n");
        }
        updateOutText();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    void updateOutText() {
        _tv.setText(_outBuffer.toString());
        _scroller.fullScroll(View.FOCUS_DOWN);
    }

    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _asyncTaskCount++;
            _outBuffer.append("Task onPreExecute ");
            _outBuffer.append(_asyncTaskCount);
            _outBuffer.append("\n");
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            _outBuffer.append("Task doInBackground ");
            _outBuffer.append(_asyncTaskCount);
            _outBuffer.append("\n");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            _outBuffer.append("Task onPostExecute ");
            _outBuffer.append(_asyncTaskCount);
            _outBuffer.append("\n");
        }
    }
}
