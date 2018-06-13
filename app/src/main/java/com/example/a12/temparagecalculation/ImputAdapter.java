package com.example.a12.temparagecalculation;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ImputAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private int numberOfElements = 26;
    private List<Double> list;

    public ImputAdapter(Context context, List<Double> list) {
        this.list = list;
        ctx = context;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initialise(list);
    }

    private void initialise(List<Double> listOfDouble){
        for (int i = 0; i < numberOfElements; i++) {
            listOfDouble.add(0.0);
        }
    }

    public List<Double> getList(){
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final int current = i;
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, viewGroup, false);
        }
        TextView tempByTime = (TextView) view.findViewById(R.id.tempByTime);
        final EditText tempOfArea = ((EditText) view.findViewById(R.id.tempOfArea));
        tempByTime.setText(String.valueOf((i+1)*2));
        tempOfArea.setText(String.valueOf(list.get(i)));
        tempOfArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!String.valueOf(tempOfArea.getText()).equals("")) {
                    list.set(current, Double.valueOf(String.valueOf(tempOfArea.getText())));
                }else {
                    list.set(current, 0.0);
                }
            }
        });





        return view;
    }
}
