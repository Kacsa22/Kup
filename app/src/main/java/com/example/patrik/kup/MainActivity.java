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
        
        NagyAtmEllenorzo nagyAtmEll = new NagyAtmEllenorzo(nagyAtmEdit, getString(R.string.nagyAtmHiba), getString(R.string.nagyAtmHiba2), MainActivity.this);
        KisAtmEllenorzo kisAtmEll = new KisAtmEllenorzo(kisAtmEdit, nagyAtmEdit, getString(R.string.kisAtmHiba), getString(R.string.kisAtmHiba2), MainActivity.this);
        HosszEllenorzo hosszEll = new HosszEllenorzo(hosszEdit, getString(R.string.hosszHiba), getString(R.string.hosszHiba2), MainActivity.this);
        
        if(!(nagyAtmEll.getHiba() || kisAtmEll.getHiba() || hosszEll.getHiba())){
            Kiszamolo szamolgato = new Kiszamolo(nagyAtmEll.getErtek(), kisAtmEll.getErtek(), hosszEll.getErtek());
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



}
