package com.example.a12.temparagecalculation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Double> accordList = new ArrayList<>();
    private ImputAdapter imputAdapter ;
    private DataCoef dataCoef = new DataCoef();
    private final String[] typesOfKeeping = {"Штабель", "Стеллаж", "БМ"};
    private List<Integer> timeKeepingList = new ArrayList();
    private Spinner keepspinner1,  keepspinner2 ;
    private EditText currentTemp;
    private double currentTempMeaning;
    private Button calculate1, calculate2;
    private TextView result1;

    public void initialTimeKeepingList(){
        for(int i=0; i<12;i++){
            timeKeepingList.add((i+1)*20);
        }
    }

    public void initialise(){
        initialTimeKeepingList();

        //list view
        imputAdapter = new ImputAdapter(this,accordList);
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(imputAdapter);
        //spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typesOfKeeping);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        keepspinner1 = (Spinner) findViewById(R.id.typeOfKeeping1);
        keepspinner2 = (Spinner) findViewById(R.id.typeOfKeeping2);

        keepspinner1.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typesOfKeeping);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        keepspinner2.setAdapter(adapter);
        //another part of 1 calculation
        currentTemp = (EditText) findViewById(R.id.currentTempMeaning );

        result1 = (TextView)findViewById(R.id.result1Calculation);

        calculate1 =(Button) findViewById(R.id.calculate1);
        calculate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double sum = 0.0;
                if(!String.valueOf(currentTemp.getText()).equals("")){
                    currentTempMeaning = Double.valueOf(String.valueOf(currentTemp.getText()));
                }else {
                    currentTempMeaning = 0.0;
                }
                findViewById(R.id.res1).setVisibility(View.VISIBLE);
                findViewById(R.id.result1Layout).setVisibility(View.VISIBLE);
                double[] koef;
                koef = getDoubles();
                for(int i=0; i<accordList.size();i++){
                    if(accordList.get(i)>0.0){
                        sum+=koef[i]  *(accordList.get(i) - currentTempMeaning);
                    }
                }
                result1.setText(String.valueOf(currentTempMeaning +sum));
            }
        });

        //another part of 2 calculation
        calculate2 =(Button) findViewById(R.id.calculate2);
        calculate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //action
            }
        });
     }

    private double[] getDoubles() {
        double[] koef;
        switch ( keepspinner1.getSelectedItemPosition()){
            case (0):{
                return  dataCoef.getShtabelDeliver();
            }
            case (1):{
                return  dataCoef.getStelajDeliver();
            }
            case (2):{
                return  dataCoef.getBmDeliver();
            }
            default:{
                return  dataCoef.getShtabelDeliver();
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();









    }

}
