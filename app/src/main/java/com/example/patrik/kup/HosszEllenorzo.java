package com.example.patrik.kup;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class HosszEllenorzo extends Ellenorzo {
    public HosszEllenorzo(EditText hosszEdit, String karakterHibaUzenet, String ertekHibaUzenet, Context context) {
        super(karakterHibaUzenet, ertekHibaUzenet, context);

        if(!(karakterEllenoriz(hosszEdit, ertekHibaUzenet))) {
            ertekEllenoriz(hosszEdit, ertekHibaUzenet);
        }
    }

    public void ertekEllenoriz(EditText hosszEdit, String ertekHibaUzenet) {

        Double hossz = Double.parseDouble(hosszEdit.getText().toString());

        if (hossz <= 0) {
            hiba = true;
            hosszEdit.setBackgroundColor(COLOR_RED);
            Toast.makeText(context, ertekHibaUzenet, Toast.LENGTH_SHORT).show();
        }
        else
        {
            ertek = hossz;
        }
    }
}
