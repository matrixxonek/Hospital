package com.hospital;

public class Lekarz {
    public int id;
    public String nazwisko;
    public String imie;
    public String drugieImie;
    public String oddzial;

    public Lekarz(int id, String nazwisko, String imie, String drugieImie, String oddzial){
        this.id = id;
        this.imie = imie;
        this.drugieImie = drugieImie;
        this.nazwisko = nazwisko;
        this.oddzial = oddzial;
    }

    @Override
    public String toString() { return nazwisko + " " + imie + (drugieImie.length() > 0 ? " " + drugieImie : ""); }
}
