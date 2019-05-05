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
        
        NagyAtmEllenorzo nagyAtmEll = new NagyAtmEllenorzo(nagyAtmEdit, getString(R.string.nagyAtmHiba), getString(R.string.nagyAtmHiba2));
        KisAtmEllenorzo kisAtmEll = new KisAtmEllenorzo(kisAtmEdit, nagyAtmEdit, getString(R.string.kisAtmHiba), getString(R.string.kisAtmHiba2));
        HosszEllenorzo hosszEll = new HosszEllenorzo(hosszEdit, getString(R.string.hosszHiba), getString(R.string.hosszHiba2);
        
        if(!(nagyAtmEll.getHiba() || kisAtmEll.getHiba() || hosszEll.getHiba())){
            Kiszamolo szamolgato = new Kiszamolo(nagyAtm.getKimenet(), kisAtm.getKimenet(), hossz.getKimenet());
            kupText.setText("Kúpszög: " + String.format("%.3f", szamolgato.getKupszog()) + "°");
            felKupText.setText("Félkúpszög: " + String.format("%.3f", szamolgato.getFelkupszog()) + "°");
            kupArText.setText("Kúpossági arány: 1:" + String.format("%.3f", szamolgato.getArany()));
            lejtText.setText("Lejtés: 1:" + String.format("%.3f", szamolgato.getLejtes()));
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

    
    class Ellenorzo {
        String karakterHibaUzenet;
        String ertekHibaUzenet;
        boolean hiba;
        Double ertek;
        
        public Ellenorzo(String karakterHibaUzenet, String ertekHibaUzenet) {
            this.karakterHibaUzenet = karakterHibaUzenet;
            this.ertekHibaUzenet = ertekHibaUzenet;
        }
        
        public boolean karakterEllenoriz(EditText bemenet, String karakterHibaUzenet) {
            Boolean karakter_ok;
            try {                
                this.kimenet = Double.parseDouble(bemenet.getText().toString());
                
                karakter_ok = true;              
            } 
            catch (Exception e) {                
                bemenet.setBackgroundColor(COLOR_RED);
                Toast.makeText(MainActivity.this, karakterHibaUzenet, Toast.LENGTH_SHORT).show();
                
                karakter_ok = false;
            }
            
            return karakter_ok;
        }
        
        boolean getHiba(){
            return hiba;
        }
        
        Double getErtek(){
            return ertek;
        }
    }
    
    class NagyAtmEllenorzo extends Ellenorzo {

        public NagyAtmEllenorzo(EditText nagyAtmEdit, String karakterHibaUzenet, String ertekHibaUzenet) {
            super(String karakterHibaUzenet, String ertekHibaUzenet);
            
            karakterEllenoriz(nagyAtmEdit, karakterHibaUzenet);
            ertekEllenoriz(nagyAtmEdit, ertekHibaUzenet);
        }

        public void ertekEllenoriz(EditText nagyAtmEdit, String ertekHibaUzenet) {

            Double nagyAtm = Double.parseDouble(nagyAtmEdit.getText().toString());
            
            if (nagyAtm <= 0) {
                hiba = true;
                nagyAtm.setBackgroundColor(COLOR_RED);
                Toast.makeText(MainActivity.this, ertekHibaUzenet, Toast.LENGTH_SHORT).show();
            }
            else {
                kimenet = nagyAtm;
            }
        }
    }
    
    class KisAtmEllenorzo extends Ellenorzo {
    
        public KisAtmEllenorzo(EditText kisAtmEdit, EditText nagyAtmEdit, String karakterHibaUzenet, String ertekHibaUzenet) {
            super(EditText kisAtmEdit, EditText nagyAtmEdit, String karakterHibaUzenet, String ertekHibaUzenet);
            
            karakterEllenoriz(EditText kisAtmEdit, String karakterHibaUzenet);
            ertekEllenoriz(EditText kisAtmEdit, EditText nagyAtmEdit, String ertekHibaUzenet);
        }

        public Boolean ertekEllenoriz(EditText kisAtmEdit, EditText nagyAtmEdit, String ertekHibaUzenet) {

            Double kisAtm = Double.parseDouble(kisAtmEdit.getText().toString());
            Double nagyAtm = Double.parseDouble(nagyAtmEdit.getText().toString());
            
            if (nagyAtm > 0) {
                if (kisAtm >= nagyAtm) {
                    hiba = true;
                    nagyAtmEdit.setBackgroundColor(COLOR_RED);
                    kisAtmEdit.setBackgroundColor(COLOR_RED);
                    Toast.makeText(MainActivity.this, ertekHibaUzenet, Toast.LENGTH_SHORT).show();
                }
                else {
                    kimenet = kisAtm;
                }
            }
            
        }
        
    }
    
    class HosszEllenorzo extends Ellenorzo {
    
        public HosszEllenorzo(EditText hosszEdit, String karakterHibaUzenet, String ertekHibaUzenet) {
            super(EditText hosszEdit, String karakterHibaUzenet, String ertekHibaUzenet);
            
            karakterEllenoriz(EditText hosszEdit, String ertekHibaUzenet);
            ertekEllenoriz(EditText hosszEdit, String ertekHibaUzenet);
        }
        
        public void ertekEllenoriz(EditText hosszEdit, String ertekHibaUzenet) {

            Double hossz = Double.parseDouble(hosszEdit.getText().toString());         

            if (hossz <= 0) {
                hiba = true;
                hosszEdit.setBackgroundColor(COLOR_RED);
                Toast.makeText(MainActivity.this, hibauzenet, Toast.LENGTH_SHORT).show();
            }
            else
            {
                kimenet = hossz;
            }
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
