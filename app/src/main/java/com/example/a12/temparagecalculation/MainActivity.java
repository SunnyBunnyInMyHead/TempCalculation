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
    private FileDoubleWorker fileDoubleWorker = new FileDoubleWorker();
    private File accordFile;
    private static int NUMBER_OF_ELEMENTS = 26;
    private List<Double> accordList = new ArrayList<>();
    private InputAdapter inputAdapter;
    private String[] typesOfKeeping;


    private Spinner keepSpinner1, keepSpinner2, timeSpinner;
    private Button calculate1, calculate2;
    private ImageButton hideButton;
    private boolean firstPartCondition = true;

    private EditText currentTemp, result1, tempOfAirDeliver, tempOfAirCurrent;
    private TextView result2Calculation;
    private GridView gvMain;
    private double currentTempMeaning;

    private List<Integer> getTimeKeepingList() {
        List<Integer> timeKeepingList = new ArrayList();
        for (int i = 0; i < 12; i++) {
            timeKeepingList.add((i + 1) * 20);
        }
        return timeKeepingList;
    }

    private void initialSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typesOfKeeping);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        keepSpinner1 = (Spinner) findViewById(R.id.typeOfKeeping1);
        keepSpinner2 = (Spinner) findViewById(R.id.typeOfKeeping2);
        timeSpinner = (Spinner) findViewById(R.id.timeSpinner);

        keepSpinner1.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typesOfKeeping);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        keepSpinner2.setAdapter(adapter);
        ArrayAdapter<Integer> timeAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, getTimeKeepingList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        timeSpinner.setAdapter(timeAdapter);
    }

    private void initialPart1() {
        hideButton = (ImageButton) findViewById(R.id.imageButton);
        inputAdapter = new InputAdapter(this, accordList);
        gvMain = (GridView) findViewById(R.id.gvMain2);
        gvMain.setAdapter(inputAdapter);
        //another part of 1 calculation
        currentTemp = (EditText) findViewById(R.id.currentTempMeaning);
        result1 = (EditText) findViewById(R.id.result1Calculation);
        calculate1 = (Button) findViewById(R.id.calculate1);
    }

    private void initialPart2() {
        calculate2 = (Button) findViewById(R.id.calculate2);
        tempOfAirCurrent = (EditText) findViewById(R.id.airCurrentTemp);
        tempOfAirDeliver = (EditText) findViewById(R.id.tempDeliverMeaning);
        result2Calculation = (TextView) findViewById(R.id.result2Calculation);
    }

    private void mainPart() {
        typesOfKeeping = getResources().getStringArray(R.array.typesOfKeeping);
        //spinners
        initialSpinners();
        initialPart1();

        calculate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double sum = 0.0;
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
                result1.setText(String.valueOf(currentTempMeaning + sum));
            }
        });

        initialPart2();

        calculate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double result2;
                if (!String.valueOf(result1.getText()).equals("")) {
                    result2 = Double.valueOf(String.valueOf(result1.getText()));
                } else {
                    result2 = 0.0;
                }
                double[] koef2 = getDoublesForSecondCalculation();
                findViewById(R.id.result2).setVisibility(View.VISIBLE);

                if (!String.valueOf(tempOfAirCurrent.getText()).equals("") && Double.valueOf(String.valueOf(tempOfAirCurrent.getText())) != 0.0) {
                    double AirCurrent = (double) Double.valueOf(String.valueOf(tempOfAirCurrent.getText()));
                    double AirDeliver = (double) Double.valueOf(String.valueOf(tempOfAirDeliver.getText()));
                    result2 += koef2[timeSpinner.getSelectedItemPosition()] * (AirCurrent - AirDeliver) * 0.5;
                }

//                result2 = ((int)(result2*100))/100.0;
                result2Calculation.setText(String.format("%.2f", result2));
            }
        });
    }

    private double[] getDoublesForFirstCalculation() {
        switch (keepSpinner1.getSelectedItemPosition()) {
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
        switch (keepSpinner2.getSelectedItemPosition()) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uploadAccordList();
        setContentView(R.layout.activity_main);
        mainPart();
        hideButton();

    }

    private void hideButton() {


        hideButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              if (firstPartCondition) {
                                                  findViewById(R.id.Part1).setVisibility(View.GONE);
                                                  firstPartCondition = false;
                                              } else {
                                                  findViewById(R.id.Part1).setVisibility(View.VISIBLE);
                                                  firstPartCondition = true;
                                              }
                                          }
                                      }
        );
    }

    public void initialiseListByZero(){
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            accordList.add(0.0);
        }
    }

    private void uploadAccordList() {
        try {
            accordFile = new File(getExternalFilesDir(null), "DataFile");
            if (accordFile.exists() && accordFile.canRead()) {
                accordList = fileDoubleWorker.readDouble(accordFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (accordList.isEmpty()) {
            initialiseListByZero();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            fileDoubleWorker.delete(accordFile);
            fileDoubleWorker.write(accordList, accordFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
