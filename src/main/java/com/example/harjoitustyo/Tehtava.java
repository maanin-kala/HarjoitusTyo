package com.example.harjoitustyo;

import java.io.Serializable;
import java.time.LocalDate;

public class Tehtava implements Serializable {
    public static enum Status {
            Valmis,
            Kesken
        }

    private static int idCounter = 1;

    private final int id;
    private String otsikko;
    private String kuvaus;
    private final LocalDate luontiPaiva;
    private LocalDate deadline;
    private LocalDate valmistumisPaiva;
    private Status tila;

    public Tehtava(String otsikko, String kuvaus, LocalDate deadline) {
        this.id = idCounter++;
        this.otsikko = otsikko;
        this.kuvaus = kuvaus;
        this.luontiPaiva = LocalDate.now();
        this.deadline = deadline;
        this.tila = Status.Kesken;
    }

    /**
     * Asettaa luokan staattisen muuttujan tiettyyn arvoon
     * @param idCounter kentän uusi arvo
     */
    protected static void setIdCounter(int idCounter) {
        Tehtava.idCounter = idCounter;
    }

    /**
     * Asettaa tehtävän valmiiksi ja rekisteröi valmistumispäivän
     */
    public void teeValmiiksi() {
        this.tila = Status.Valmis;
        this.valmistumisPaiva = LocalDate.now();
    }

    /**
     * Asettaa tehtävän takaisin keskeneräiseksi
     */
    public void teeKeskeneraiseksi() {
        this.tila = Status.Kesken;
        this.valmistumisPaiva = null;
    }

    /**
     * ListViewiä varten toString() metodi
     * @return tehtävän luontipäivä, otsikko, deadline
     */
    protected static String toStringFormat = "%-15s %-100s %15s";
    public String toString() {
        return String.format(toStringFormat, luontiPaiva, otsikko, deadline);
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

    public Status getTila() {
        return tila;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}