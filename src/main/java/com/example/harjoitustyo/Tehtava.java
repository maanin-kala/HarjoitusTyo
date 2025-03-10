package com.example.harjoitustyo;

import java.util.Date;

public class Tehtava {
    private static int idCounter = 0;

    private final int id;
    private String otsikko;
    private String kuvaus;
    private final Date luontiPaiva;
    private final Date deadline;
    private Date valmistumisPaiva;
    private boolean valmis;

    public Tehtava(String otsikko, String kuvaus, Date deadline) {
        this.id = idCounter++;
        this.otsikko = otsikko;
        this.kuvaus = kuvaus;
        this.luontiPaiva = new Date();
        this.deadline = deadline;
        this.valmis = false;
    }

    public void setValmis() {
        this.valmis = true;
        this.valmistumisPaiva = new Date();
    }

    public int getId() {
        return id;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public Date getLuontiPaiva() {
        return luontiPaiva;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Date getValmistumisPaiva() {
        return valmistumisPaiva;
    }

    public boolean isValmis() {
        return valmis;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }
}