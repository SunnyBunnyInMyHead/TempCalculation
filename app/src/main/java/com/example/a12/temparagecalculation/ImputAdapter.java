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
        final ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.item, null);
            holder.textView1 = (TextView) convertView.findViewById(R.id.tempByTime);
            holder.tempOfArea = (EditText) convertView.findViewById(R.id.tempOfArea);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.ref = i;

        holder.textView1.setText(String.valueOf((i+1)*2));
       // if(!String.valueOf(list.get(i)).equals("")){
       // holder.tempOfArea.setText(String.valueOf(list.get(i)));}

        holder.tempOfArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!String.valueOf(holder.tempOfArea.getText()).equals("")) {
                    list.set(holder.ref, Double.valueOf(String.valueOf(holder.tempOfArea.getText())));
                }else {
                    list.set(holder.ref, 0.0);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView textView1;
        EditText tempOfArea;
        int ref;
    }
}
