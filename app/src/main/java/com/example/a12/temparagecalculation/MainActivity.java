package com.example.a12.temparagecalculation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Double> accordList = new ArrayList<>();
    ImputAdapter imputAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imputAdapter = new ImputAdapter(this,accordList);
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(imputAdapter);



    }

}
