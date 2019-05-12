package com.example.patrik.kup;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class NagyAtmEllenorzo extends Ellenorzo {
    public NagyAtmEllenorzo(EditText nagyAtmEdit, String karakterHibaUzenet, String ertekHibaUzenet, Context context) {
        super(karakterHibaUzenet, ertekHibaUzenet, context);

        if(!(karakterEllenoriz(nagyAtmEdit, ertekHibaUzenet))) {
            ertekEllenoriz(nagyAtmEdit, ertekHibaUzenet);
        }
    }

    public void ertekEllenoriz(EditText nagyAtmEdit, String ertekHibaUzenet) {

        Double nagyAtm = Double.parseDouble(nagyAtmEdit.getText().toString());

        if (nagyAtm <= 0) {
            hiba = true;
            nagyAtmEdit.setBackgroundColor(COLOR_RED);
            Toast.makeText(context, ertekHibaUzenet, Toast.LENGTH_SHORT).show();
        }
        else {
            ertek = nagyAtm;
        }
    }
}
