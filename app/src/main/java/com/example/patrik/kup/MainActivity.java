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
        closeKeyboard();
        kupText.setText("");
        felKupText.setText("");
        kupArText.setText("");
        lejtText.setText("");
        if (kisAtmEdit.getText().toString().equals("")){
            kisAtmEdit.setText("0");
        }


        Ellenorzo nagyAtm = new Ellenorzo(nagyAtmEdit,getString(R.string.nagyAtmHiba));
        Ellenorzo kisAtm = new Ellenorzo(kisAtmEdit,getString(R.string.kisAtmHiba));
        Ellenorzo hossz = new Ellenorzo(hosszEdit,getString(R.string.hosszHiba));
        if(!(nagyAtm.getHiba() || kisAtm.getHiba() || hossz.getHiba())){
            if (!(nagyAtm.ertekEllenoriz(nagyAtm.getKimenet(),kisAtm.getKimenet(),hossz.getKimenet()))){
                Kiszamolo szamolgato = new Kiszamolo(nagyAtm.getKimenet(), kisAtm.getKimenet(), hossz.getKimenet());
                kupText.setText("Kúpszög: " + String.format("%.3f", szamolgato.getKupszog()) + "°");
                felKupText.setText("Félkúpszög: " + String.format("%.3f", szamolgato.getFelkupszog()) + "°");
                kupArText.setText("Kúpossági arány: 1:" + String.format("%.3f", szamolgato.getArany()));
                lejtText.setText("Lejtés: 1:" + String.format("%.3f", szamolgato.getLejtes()));
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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


    class Ellenorzo{

        private EditText bemenet;
        String hibauzenet;
        Double kimenet;
        private Boolean hiba;

        public Ellenorzo(EditText bemenet, String hibauzenet){
            this.bemenet    = bemenet;
            this.hibauzenet = hibauzenet;
            this.hiba       = false;

            bemenet.setBackgroundColor(COLOR_WHITE);

            karakterEllenoriz(this.bemenet,this.hibauzenet);
        }


        public void karakterEllenoriz(EditText bemenet, String hibauzenet) {

            try {
                this.kimenet = Double.parseDouble(bemenet.getText().toString());
            } catch (Exception e) {
                this.hiba = true;
                bemenet.setBackgroundColor(COLOR_RED);
                Toast.makeText(MainActivity.this, hibauzenet, Toast.LENGTH_SHORT).show();
            }
        }

        public Boolean ertekEllenoriz(Double dNagyAtm, Double dKisAtm, Double dHossz) {

            Boolean ertekhiba = false;

            if (dNagyAtm <= 0) {
                ertekhiba = true;
                nagyAtmEdit.setBackgroundColor(COLOR_RED);
                Toast.makeText(MainActivity.this, "A nagy átmérőnek nagyobbnak kell lennie 0-nál!!!", Toast.LENGTH_SHORT).show();
            }

            if (dHossz <= 0) {
                ertekhiba = true;
                hosszEdit.setBackgroundColor(COLOR_RED);
                Toast.makeText(MainActivity.this, "A hossznak nagyobbnak kell lennie 0-nál!!!", Toast.LENGTH_SHORT).show();
            }
            if (!(dNagyAtm <=0)) {
                if (dKisAtm >= dNagyAtm) {
                    ertekhiba = true;
                    nagyAtmEdit.setBackgroundColor(COLOR_RED);
                    kisAtmEdit.setBackgroundColor(COLOR_RED);
                    Toast.makeText(MainActivity.this, "A nagy átmérőnek nagyobbnak kell lennie, mint a kisátmérő!!!", Toast.LENGTH_SHORT).show();
                }
            }

            return ertekhiba;
        }

        public Boolean getHiba() {
            return hiba;
        }

        public Double getKimenet() {
            return kimenet;
        }
    }


    class Kiszamolo {
        private Double nagyAtm,
                kisAtm,
                hossz;

        Double  kupszog,
                felkupszog,
                arany,
                lejtes;

        public Kiszamolo(Double nagyAtm, Double kisAtm, Double hossz){
            this.nagyAtm = nagyAtm;
            this.kisAtm  = kisAtm;
            this.hossz   = hossz;

            this.felkupszog = felkupszog(this.nagyAtm, this.kisAtm, this.hossz);
            this.kupszog    = this.felkupszog * 2;
            this.arany      = arany(this.nagyAtm, this.kisAtm, this.hossz);
            this.lejtes     = this.arany / 2;
        }

        public Double felkupszog(Double nagyAtm, Double kisAtm, Double hossz){
            return Math.toDegrees(Math.atan(((nagyAtm - kisAtm) / 2) / hossz));
        }

        public Double arany(Double nagyAtm, Double kisAtm, Double hossz){
            return ((nagyAtm - kisAtm) / hossz);
        }

        public Double getKupszog(){
            return kupszog;
        }

        public Double getFelkupszog(){
            return felkupszog;
        }

        public Double getArany(){
            return arany;
        }

        public Double getLejtes(){
            return lejtes;
        }

    }

}