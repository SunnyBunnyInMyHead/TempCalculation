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

    private List<Double> accordList = new ArrayList<>();
    private InputAdapter inputAdapter;
    private DataCoef dataCoef = new DataCoef();
    private String[] typesOfKeeping;
    private List<Integer> timeKeepingList = new ArrayList();

    private Spinner keepSpinner1, keepSpinner2, timeSpinner;
    private Button calculate1, calculate2;
    private ImageButton hideButton;
    private boolean firstPartCondition = true;

    private EditText currentTemp, result1, tempOfAirDeliver, tempOfAirCurrent;
    private TextView result2Calculation;
    private GridView gvMain;
    private double currentTempMeaning;

    private void initialTimeKeepingList() {
        for (int i = 0; i < 12; i++) {
            timeKeepingList.add((i + 1) * 20);
        }
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
        ArrayAdapter<Integer> timeAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, timeKeepingList);
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
        initialTimeKeepingList();
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

                result2 = ((int)(result2*100))/100.0;
                result2Calculation.setText(String.valueOf(result2));
            }
        });
    }

    private double[] getDoublesForFirstCalculation() {
        switch (keepSpinner1.getSelectedItemPosition()) {
            case (0): {
                return dataCoef.getShtabelDeliver();
            }
            case (1): {
                return dataCoef.getStelajDeliver();
            }
            case (2): {
                return dataCoef.getBmDeliver();
            }
            default: {
                return dataCoef.getShtabelDeliver();
            }
        }

    }

    private double[] getDoublesForSecondCalculation() {
        switch (keepSpinner2.getSelectedItemPosition()) {
            case (0): {
                return dataCoef.getShtabelKeeping();
            }
            case (1): {
                return dataCoef.getStelajKeeping();
            }
            case (2): {
                return dataCoef.getBmKeeping();
            }
            default: {
                return dataCoef.getShtabelKeeping();
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

    private void uploadAccordList() {

        try {
            accordFile = new File(getExternalFilesDir(null), "DataFile");

            if (accordFile.exists() && accordFile.canRead()) {

                accordList = fileDoubleWorker.readDouble(accordFile);
                if (accordList.size() <= 0) {
                    inputAdapter.initialiseListByZero(accordList);
                    fileDoubleWorker.write(accordList, accordFile);

                }
            } else {
                System.out.println("file not exists");
                accordFile.createNewFile();
                inputAdapter.initialiseListByZero(accordList);
                fileDoubleWorker.write(accordList, accordFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        uploadAccordList();
        gvMain.invalidate();

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
