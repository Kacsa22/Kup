package com.example.patrik.kup;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText nagyAtmEdit;
    EditText kisAtmEdit;
    EditText hosszEdit;
    TextView felKupText;
    TextView kupText;
    TextView kupArText;
    TextView lejtText;

    ConstraintLayout main;

    public static int COLOR_RED = 0x77FF0000;
    public static int COLOR_WHITE = 0x00000000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGUI();

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(nagyAtmEdit.getWindowToken(), 0);
                }
            }
        });
    }

    public void Onclick(View v){
        nagyAtmEdit.setBackgroundColor(COLOR_WHITE);
        kisAtmEdit.setBackgroundColor(COLOR_WHITE);
        hosszEdit.setBackgroundColor(COLOR_WHITE);
        if (kisAtmEdit.getText().toString().equals("")){
            kisAtmEdit.setText("0");
        }
        karakterEllenoriz(nagyAtmEdit.getText().toString(), kisAtmEdit.getText().toString(), hosszEdit.getText().toString());

    }

    public void initGUI(){
        nagyAtmEdit = findViewById(R.id.nagyAtmEdit);
        kisAtmEdit  = findViewById(R.id.kisAtmEdit);
        hosszEdit   = findViewById(R.id.hosszEdit);
        felKupText  = findViewById(R.id.felKupText);
        kupText     = findViewById(R.id.kupText);
        kupArText   = findViewById(R.id.kupArText);
        lejtText    = findViewById(R.id.lejtText );
        main        = findViewById(R.id.main);
    }


    public void karakterEllenoriz(String nagyAtm, String kisAtm, String hossz){

        Boolean mindenfasza = true;
        Double  dNagyAtm = 0.0;
        Double dKisAtm = 0.0;
        Double dHossz = 0.0;

        try{
            dNagyAtm = Double.parseDouble(nagyAtm);
        }
        catch (Exception e){
            mindenfasza = false;
            nagyAtmEdit.setBackgroundColor(COLOR_RED);
            Toast.makeText(MainActivity.this,"A nagy átmérőnek számnak kell lennie!!!", Toast.LENGTH_SHORT).show();
        }

        try{
            dKisAtm = Double.parseDouble(kisAtm);
        }
        catch (Exception e){
            mindenfasza = false;
            kisAtmEdit.setBackgroundColor(COLOR_RED);
            Toast.makeText(MainActivity.this,"A kis átmérőnek számnak kell lennie!!!", Toast.LENGTH_SHORT).show();
        }

        try{
            dHossz = Double.parseDouble(hossz);
        }
        catch (Exception e){
            mindenfasza = false;
            hosszEdit.setBackgroundColor(COLOR_RED);
            Toast.makeText(MainActivity.this,"A hosszúságnak számnak kell lennie!!!", Toast.LENGTH_SHORT).show();
        }
        if (mindenfasza) {
           if(ertekEllenoriz(dNagyAtm, dKisAtm, dHossz)) {
               Kiszamolo eredmeny = new Kiszamolo(dNagyAtm, dKisAtm, dHossz);
               eredmeny.szog();
               eredmeny.arany();
           }
        }

    }

    public Boolean ertekEllenoriz(Double nagyAtm, Double kisAtm, Double hossz) {

        Boolean mindenfasza = true;

        if (nagyAtm <= 0) {
            mindenfasza = false;
            nagyAtmEdit.setBackgroundColor(COLOR_RED);
            Toast.makeText(MainActivity.this, "A nagy átmérőnek nagyobbnak kell lennie 0-nál!!!", Toast.LENGTH_SHORT).show();
        }

        if (hossz <= 0) {
            mindenfasza = false;
            hosszEdit.setBackgroundColor(COLOR_RED);
            Toast.makeText(MainActivity.this, "A hossznak nagyobbnak kell lennie 0-nál!!!", Toast.LENGTH_SHORT).show();
        }

        if(kisAtm >= nagyAtm) {
            mindenfasza = false;
            nagyAtmEdit.setBackgroundColor(COLOR_RED);
            kisAtmEdit.setBackgroundColor(COLOR_RED);
            Toast.makeText(MainActivity.this, "A nagy átmérőnek nagyobbnak kell lennie, mint a kisátmérő!!!", Toast.LENGTH_SHORT).show();
        }

        return mindenfasza;
    }

    class Ellenorzo{
        String nagyAtm;
        String kisAtm;
        String hossz;
    }

    class Kiszamolo {

        private Double nagyAtm;
        private Double kisAtm;
        private Double hossz;

        public Kiszamolo(Double nagyAtm, Double kisAtm, Double hossz) {

            this.nagyAtm = nagyAtm;
            this.kisAtm = kisAtm;
            this.hossz = hossz;

        }

        public void szog(){

            Double szog;

            szog = Math.atan(((nagyAtm - kisAtm) / 2) / hossz);
            szog = Math.toDegrees(szog);
            felKupText.setText("Félkúpszög: " + String.format("%.3f", szog));
            szog = szog * 2;
            kupText.setText("Kúpszög: " + String.format("%.3f",szog));
        }

        public void arany(){

            Double arany;

            arany = ((nagyAtm - kisAtm) / hossz);
            kupArText.setText("Kúpossági arány: 1:" + String.format("%.3f", arany));
            arany = arany / 2;
            lejtText.setText("Lejtés: 1:" + String.format("%.3f", arany));
        }
    }

}
