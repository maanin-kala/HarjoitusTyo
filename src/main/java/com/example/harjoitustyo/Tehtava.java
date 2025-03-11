package com.example.harjoitustyo;

import java.io.Serializable;
import java.time.LocalDate;

public class Tehtava implements Serializable {
    private static int idCounter = 0;

    private final int id;
    private String otsikko;
    private String kuvaus;
    private final LocalDate luontiPaiva;
    private final LocalDate deadline;
    private LocalDate valmistumisPaiva;
    private boolean valmis;

    public Tehtava(String otsikko, String kuvaus, LocalDate deadline) {
        this.id = idCounter++;
        this.otsikko = otsikko;
        this.kuvaus = kuvaus;
        this.luontiPaiva = LocalDate.now();
        this.deadline = deadline;
        this.valmis = false;
    }

    /**
     * Asettaa tehtävän valmiiksi ja rekisteröi valmistumispäivän
     */
    public void teeValmiiksi() {
        this.valmis = true;
        this.valmistumisPaiva = LocalDate.now();
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

    public LocalDate getLuontiPaiva() {
        return luontiPaiva;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public LocalDate getValmistumisPaiva() {
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