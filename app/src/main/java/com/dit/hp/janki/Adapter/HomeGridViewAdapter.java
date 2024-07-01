package com.dit.hp.janki.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dit.hp.janki.Modal.ModulesPojo;
import com.dit.hp.janki.R;
import com.dit.hp.janki.activities.BookAmbulance;
import com.dit.hp.janki.activities.BookLab;
import com.dit.hp.janki.activities.Registration;
import com.dit.hp.janki.lazyloader.ImageLoader;
import com.dit.hp.janki.presentation.CustomDialog;
import com.dit.hp.janki.utilities.Preferences;

import java.util.ArrayList;

public class HomeGridViewAdapter extends BaseAdapter {
    Context c;
    ArrayList<ModulesPojo> gridHome;


    ImageLoader il = new ImageLoader(c);
    CustomDialog CD = new CustomDialog();


    public HomeGridViewAdapter(Context c, ArrayList<ModulesPojo> spacecrafts) {
        this.c = c;
        this.gridHome = spacecrafts;
    }

    @Override
    public int getCount() {
        return gridHome.size();
    }

    @Override
    public Object getItem(int i) {
        return gridHome.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(c).inflate(R.layout.home_gridview_model, viewGroup, false);
        }

        final ModulesPojo s = (ModulesPojo) this.getItem(i);

        ImageView img = (ImageView) view.findViewById(R.id.spacecraftImg);
        TextView nameTxt = (TextView) view.findViewById(R.id.nameTxt);


        nameTxt.setText(s.getName());


        if (s.getLogo().equalsIgnoreCase("null")) {
            //show uk icon
            String fnm = "uttarakhand";
            String PACKAGE_NAME = c.getApplicationContext().getPackageName();
            int imgId = this.c.getApplicationContext().getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + fnm, null, null);
            System.out.println("IMG ID :: " + imgId);
            System.out.println("PACKAGE_NAME :: " + PACKAGE_NAME);
            img.setImageBitmap(BitmapFactory.decodeResource(c.getApplicationContext().getResources(), imgId));
        } else {
            String fnm = s.getLogo();
            String PACKAGE_NAME = c.getApplicationContext().getPackageName();
            int imgId = this.c.getApplicationContext().getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + fnm, null, null);
            System.out.println("IMG ID :: " + imgId);
            System.out.println("PACKAGE_NAME :: " + PACKAGE_NAME);
            img.setImageBitmap(BitmapFactory.decodeResource(c.getApplicationContext().getResources(), imgId));
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (s.getName().equalsIgnoreCase("Book Ambulance")) {
                    Intent mainIntent = new Intent(c.getApplicationContext(), BookAmbulance.class);
                    (c).startActivity(mainIntent);

                }
                if (s.getName().equalsIgnoreCase("Book Lab")) {
                    Intent mainIntent = new Intent(c.getApplicationContext(), BookLab.class);
                    (c).startActivity(mainIntent);

                }

                if (s.getName().equalsIgnoreCase("All Bookings")) {
                   // Intent mainIntent = new Intent(c.getApplicationContext(), BookLab.class);
                   // (c).startActivity(mainIntent);
                    CD.showDialog((Activity) c, "Under Process");

                }


                if (s.getName().equalsIgnoreCase("Log Out")) {

                    Preferences.getInstance().loadPreferences(c.getApplicationContext());
                    Preferences.getInstance().isLoggedIn = false;
                    Preferences.getInstance().savePreferences(c.getApplicationContext());
                    Toast.makeText(c.getApplicationContext(), "Logout Successful", Toast.LENGTH_LONG).show();

                    Intent mainIntent = new Intent(c.getApplicationContext(), Registration.class);
                    (c).startActivity(mainIntent);
                    ((Activity) c).finish();


                }




            }
        });

        return view;
    }


}