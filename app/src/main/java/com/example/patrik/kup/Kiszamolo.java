package com.example.patrik.kup;

import android.content.Context;

public class Kiszamolo {
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
