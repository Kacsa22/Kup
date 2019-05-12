package com.example.patrik.kup;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class KisAtmEllenorzo extends Ellenorzo {
    public KisAtmEllenorzo(EditText kisAtmEdit, EditText nagyAtmEdit, String karakterHibaUzenet, String ertekHibaUzenet, Context context) {
        super(karakterHibaUzenet,ertekHibaUzenet, context);

        if(!(karakterEllenoriz(kisAtmEdit, ertekHibaUzenet))) {
            ertekEllenoriz(kisAtmEdit, nagyAtmEdit, ertekHibaUzenet);
        }
    }

    public void ertekEllenoriz(EditText kisAtmEdit, EditText nagyAtmEdit, String ertekHibaUzenet) {

        Double kisAtm = Double.parseDouble(kisAtmEdit.getText().toString());
        Double nagyAtm = Double.parseDouble(nagyAtmEdit.getText().toString());

        if (nagyAtm > 0) {
            if (kisAtm >= nagyAtm) {
                hiba = true;
                nagyAtmEdit.setBackgroundColor(COLOR_RED);
                kisAtmEdit.setBackgroundColor(COLOR_RED);
                Toast.makeText(context, ertekHibaUzenet, Toast.LENGTH_SHORT).show();
            }
            else {
                ertek = kisAtm;
            }
        }

    }
}
