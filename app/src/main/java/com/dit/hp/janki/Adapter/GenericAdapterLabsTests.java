package com.dit.hp.janki.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dit.hp.janki.Modal.TestDetailPojo;

import java.util.List;

public class GenericAdapterLabsTests extends ArrayAdapter<TestDetailPojo> {
    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private List<TestDetailPojo> values;

    public GenericAdapterLabsTests(Context context, int textViewResourceId, List<TestDetailPojo> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return values.size();
    }

    public TestDetailPojo getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        //label.setTextColor(Color.BLACK);
        label.setTextSize(17);
        label.setTextColor(Color.parseColor("#0091D7"));
        label.setPadding(5, 5, 5, 5);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values.get(position).getTestName());
        // label.setTypeface(Econstants.getTypefaceRegular(context));

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextColor(Color.parseColor("#585566"));
        label.setTextSize(17);
        label.setPadding(15, 15, 15, 15);
        label.setText(values.get(position).getTestName() );
        // label.setTypeface(Econstants.getTypefaceRegular(context));

        return label;
    }

}
