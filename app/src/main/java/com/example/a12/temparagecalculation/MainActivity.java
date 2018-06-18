package com.example.a12.temparagecalculation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static int NUMBER_OF_ELEMENTS = 26;
    private List<Double> accordList;
    private InputAdapter inputAdapter;

    private Spinner typeSpinnerDeliver1, typeSpinnerKeeping2, timeSpinner;
    private Button calculateTempDeliving1, calculateTempKeeping2;
    private ImageButton hideButton;
    private boolean firstPartCondition = true;

    private EditText result1Calculation;
    private TextView result2Calculation;

    private List<Integer> getTimeKeepingList() {
        List<Integer> timeKeepingList = new ArrayList();
        for (int i = 0; i < 12; i++) {
            timeKeepingList.add((i + 1) * 20);
        }
        return timeKeepingList;
    }

    private void initialiseListByZero(){
        accordList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            accordList.add(0.0);
        }
    }

    private void initialSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.typesOfKeeping));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinnerDeliver1 = (Spinner) findViewById(R.id.typeOfKeeping1);
        typeSpinnerKeeping2 = (Spinner) findViewById(R.id.typeOfKeeping2);
        timeSpinner = (Spinner) findViewById(R.id.timeSpinner);

        typeSpinnerDeliver1.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.typesOfKeeping));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinnerKeeping2.setAdapter(adapter);
        ArrayAdapter<Integer> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getTimeKeepingList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        timeSpinner.setAdapter(timeAdapter);
    }

    private void calcButtonDeliver(){
        calculateTempDeliving1 = (Button) findViewById(R.id.calculate1);
        calculateTempDeliving1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double sum = 0.0;
                EditText currentTemp = (EditText) findViewById(R.id.currentTempMeaning);
                double currentTempMeaning;
                if (!String.valueOf(currentTemp.getText()).equals("")) {
                    currentTempMeaning = Double.valueOf(String.valueOf(currentTemp.getText()));
                } else {
                    currentTempMeaning = 0.0;
                }
                findViewById(R.id.result1).setVisibility(View.VISIBLE);

                double[] koef = getDoublesForFirstCalculation();
                for (int i = 0; i < accordList.size(); i++) {
                    if (accordList.get(i) > 0.0) {
                        sum += koef[i] * (accordList.get(i) - currentTempMeaning);
                    }
                }
                result1Calculation.setText(String.valueOf(currentTempMeaning + sum));
            }
        });
    }

    private void calcButtonKeeping(){
        calculateTempKeeping2 = (Button) findViewById(R.id.calculate2);
        calculateTempKeeping2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double result2;
                if (!String.valueOf(result1Calculation.getText()).equals("")) {
                    result2 = Double.valueOf(String.valueOf(result1Calculation.getText()));
                } else {
                    result2 = 0.0;
                }
                double[] koef2 = getDoublesForSecondCalculation();
                findViewById(R.id.result2).setVisibility(View.VISIBLE);

                EditText tempOfAirCurrent = (EditText) findViewById(R.id.airCurrentTemp);
                EditText tempOfAirDeliver = (EditText) findViewById(R.id.tempDeliverMeaning);

                if (!String.valueOf(tempOfAirCurrent.getText()).equals("") && Double.valueOf(String.valueOf(tempOfAirCurrent.getText())) != 0.0) {
                    double AirCurrent = (double) Double.valueOf(String.valueOf(tempOfAirCurrent.getText()));
                    double AirDeliver = (double) Double.valueOf(String.valueOf(tempOfAirDeliver.getText()));
                    result2 += koef2[timeSpinner.getSelectedItemPosition()] * (AirCurrent - AirDeliver) * 0.5;
                }

                result2Calculation.setText(String.format("%.2f", result2));
            }
        });
    }

    private double[] getDoublesForFirstCalculation() {
        switch (typeSpinnerDeliver1.getSelectedItemPosition()) {
            case (0):
                return DataCoef.getShtabelDeliver();
            case (1):
                return DataCoef.getStelajDeliver();
            case (2):
                return DataCoef.getBmDeliver();
            default: {
                return DataCoef.getShtabelDeliver();
            }
        }

    }

    private double[] getDoublesForSecondCalculation() {
        switch (typeSpinnerKeeping2.getSelectedItemPosition()) {
            case (0):
                return DataCoef.getShtabelKeeping();
            case (1):
                return DataCoef.getStelajKeeping();
            case (2):
                return DataCoef.getBmKeeping();
            default: {
                return DataCoef.getShtabelKeeping();
            }
        }
    }

    private void hideButton() {
        hideButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              findViewById(R.id.Part1).setVisibility(firstPartCondition?View.GONE:View.VISIBLE);
                                              firstPartCondition = !firstPartCondition;
                                          }
                                      }
        );
    }

    private void uploadAccordList() {
        accordList = new ArrayList<>();

        try {
            File accordFile = new File(getExternalFilesDir(null), "DataFile");
            if (accordFile.exists() && accordFile.canRead()) {
                accordList = FileWorker.readDoubleList(accordFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (accordList.isEmpty()) {
            initialiseListByZero();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialSpinners();
        hideButton = (ImageButton) findViewById(R.id.imageButton);
        uploadAccordList();
        inputAdapter = new InputAdapter(this, accordList);
        GridView gridDeliver = (GridView) findViewById(R.id.gridDeliverView);
        gridDeliver.setAdapter(inputAdapter);
        result1Calculation = (EditText) findViewById(R.id.result1Calculation);
        calcButtonDeliver();
        result2Calculation = (TextView) findViewById(R.id.result2Calculation);
        calcButtonKeeping();
        hideButton();

    }

    @Override
    protected void onStop() {
        super.onStop();
        File accordFile = new File(getExternalFilesDir(null), "DataFile");
        try {
            FileWorker.delete(accordFile);
            FileWorker.writeDoubleList(accordList, accordFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
