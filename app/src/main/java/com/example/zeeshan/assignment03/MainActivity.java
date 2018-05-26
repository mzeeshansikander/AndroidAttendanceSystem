package com.example.zeeshan.assignment03;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    int v_year ;
    String v_month;
    int v_day;
    int v_hour;
    int v_min;
    String am_pm;
    String date;
    String time;
    String t_hour;
    String t_min;
    int t_month;
    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button saveAttendance = findViewById(R.id.saveAttendance);
        Button viewAttendance = findViewById(R.id.ViewAttendance);
        final Button checkOut = findViewById(R.id.checkOut);


        DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerId);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePickerId);
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        v_year = Calendar.getInstance().get(Calendar.YEAR);
        t_month= Calendar.getInstance().get(Calendar.MONTH);



        v_day=calendar.get(Calendar.DAY_OF_MONTH);
        v_hour=calendar.get(Calendar.HOUR_OF_DAY);
        v_min = calendar.get(Calendar.MINUTE);




        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {


            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {


                v_year = year;
                t_month = month;
                v_day = dayOfMonth;
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                v_hour = hourOfDay;
                v_min = minute;
                //Log.e("test",hourOfDay+"    "+minute+"");
            }
        });





        saveAttendance.setOnClickListener(new View.OnClickListener() {
            Button checkOut = findViewById(R.id.checkOut);


            @Override
            public void onClick(View v) {
                saveAttendance.setEnabled(false);



                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                calendar.set(Calendar.MONTH,t_month);
                v_month =  month_date.format(calendar.getTime());

                if (v_hour>12) {
                    am_pm = "PM";
                    v_hour -= 12;
                }else if(v_hour == 0) {

                    am_pm = "AM";
                    v_hour=12;

                }else{
                    am_pm = "AM";
                }

                if (v_hour < 10){
                    t_hour = "0"+v_hour+"";
                }
                else{
                    t_hour = v_hour+"";
                }

                if (v_min < 10){
                    t_min = "0"+v_min+"";
                }
                else{
                    t_min = v_min+"";
                }



                checkOut.setEnabled(true);
                //Log.e("Date",v_year+"  "+v_month+ "   "+v_day);
                date= v_year+"  "+v_month+ "   "+v_day;
                time = t_hour+":"+t_min;
                writeToFile(date + ";  " +time +"  "+ am_pm ," ; Check In");

            }
        });
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOut.setEnabled(false);


                saveAttendance.setEnabled(true);
                date= v_year+"  "+v_month+ "   "+v_day;
                time = t_hour+":"+t_min;
                writeToFile(date + ";  " +time+"  "+am_pm," ; Check Out");

            }
        });
        viewAttendance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    readFile();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });


    }


    private void writeToFile(String data,String check) {

        try {

            FileOutputStream fos = openFileOutput("config.txt", MODE_APPEND);
            PrintStream ps = new PrintStream(fos);

            ps.println(data +"  "+ check);


            ps.close();

        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private void readFile() throws FileNotFoundException {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        Scanner scanner = new Scanner(cw.openFileInput("config.txt"));
        String str = scanner.nextLine();
        Log.e("Date",str);

        Intent intent = new Intent(MainActivity.this,ViewLog.class);
        startActivity(intent);
        scanner.close();
    }

}
