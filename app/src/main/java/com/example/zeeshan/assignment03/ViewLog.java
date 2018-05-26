package com.example.zeeshan.assignment03;

import android.content.ContextWrapper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewLog extends AppCompatActivity {

    private List<String> log = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log);


        final ListView attendaceLog = findViewById(R.id.viewLog);

        try {
            readLog();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final ArrayAdapter adapter = new ArrayAdapter<String>(ViewLog.this,android.R.layout.simple_list_item_1,log);
        attendaceLog.setAdapter(adapter);


    }
    public void readLog() throws FileNotFoundException {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        Scanner scanner = new Scanner(cw.openFileInput("config.txt"));
        //Scanner scanner = new Scanner(is);
        while(scanner.hasNext()){
            log.add(scanner.nextLine());
        }
        scanner.close();
    }
}
