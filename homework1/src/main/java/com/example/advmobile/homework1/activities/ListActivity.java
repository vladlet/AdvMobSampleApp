package com.example.advmobile.homework1.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.advmobile.homework1.R;

public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);
        ListView lv = (ListView)findViewById(R.id.list_view);
        lv.setAdapter(new MyAdapter(this));
    }

    static class MyAdapter extends BaseAdapter {

        private final Context _context;

        private final int MAX_NUM_ELEMENTS = 1000;

        private final String[] numbers = {
                "",
                "один",
                "два",
                "три",
                "четыре",
                "пять",
                "шесть",
                "семь",
                "восемь",
                "девять",
                "десять",
                "одинадцать",
                "двенадцать",
                "тринадцать",
                "четырнадцать",
                "пятнадцать",
                "шестнадцать",
                "семнадцать",
                "восемнадцать",
                "девятнадцать"
        };

        private final String[] tens = {
                "",
                "десять",
                "двадцать",
                "тридцать",
                "сорок",
                "пятьдесят",
                "шестьдесят",
                "семьдесят",
                "восемдесят",
                "девяносто",
        };

        private final String[] hundreds = {
                "",
                "сто",
                "двести",
                "триста",
                "четыреста",
                "пятьсот",
                "шестьсот",
                "семьсот",
                "восемьсот",
                "девятьсот",
                "тысяча",
        };

        MyAdapter(Context context) {
            _context = context;
        }

        private String getName(int position) {
            StringBuilder sb  = new StringBuilder();
            if (position >= 100) {
                sb.append(hundreds[position/100]);
                sb.append(" ");
                position %= 100;
            }
            if (position >= 20 ) {
                sb.append(tens[position/10]);
                sb.append(" ");
                position %= 10;
            }
            if (position > 0) {
                sb.append(numbers[position]);
            }
            return sb.toString();
        }

        @Override
        public int getCount() {
            return MAX_NUM_ELEMENTS;
        }

        @Override
        public Object getItem(int position) {
            return getName(position+1);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String str = (String)getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(_context).inflate(R.layout.list_element, parent, false);
            }

            TextView tv = (TextView)convertView.findViewById(R.id.text_item);
            tv.setText(str);
            if ((position & 1) == 1) {
                tv.setBackgroundColor(Color.rgb(170, 170, 170));
            } else {
                tv.setBackgroundColor(Color.rgb(255, 255, 255));
            }
            return convertView;

        }
    }

}
