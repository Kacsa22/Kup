package com.example.patrik.kup;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class Ellenorzo {
    public static int COLOR_RED = 0x77FF0000;
    public static int COLOR_WHITE = 0x00000000;

    String karakterHibaUzenet;
    String ertekHibaUzenet;
    boolean hiba;
    Double ertek;
    Context context;

    public Ellenorzo(String karakterHibaUzenet, String ertekHibaUzenet, Context context) {
        this.karakterHibaUzenet = karakterHibaUzenet;
        this.ertekHibaUzenet = ertekHibaUzenet;
        this.context = context;
    }

    public boolean karakterEllenoriz(EditText bemenet, String karakterHibaUzenet) {
        Boolean karakter_ok;
        try {
            Double kimenet = Double.parseDouble(bemenet.getText().toString());

            karakter_ok = true;

            bemenet.setBackgroundColor(COLOR_WHITE);
        }
        catch (Exception e) {
            bemenet.setBackgroundColor(COLOR_RED);
            Toast.makeText(this.context, karakterHibaUzenet, Toast.LENGTH_SHORT).show();

            karakter_ok = false;
        }

        return karakter_ok;
    }

    public boolean getHiba(){
        return hiba;
    }

    public Double getErtek(){
        return ertek;
    }
}
