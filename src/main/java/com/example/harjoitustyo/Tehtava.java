package com.example.harjoitustyo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Ohjelman perustietoluokka
 */
public class Tehtava implements Serializable {
    /**
     * Enum tehtävän tilan seuraamiseen
     */
    public enum Status {
            Luotu,
            Vaiheessa,
            Valmis
        }

    private static int idCounter = 1;

    private final int id;
    private String otsikko;
    private String kuvaus;
    private final LocalDate luontiPaiva;
    private LocalDate deadline;
    private LocalDate valmistumisPaiva;
    private Status tila;

    /**
     * Luo uuden tehtävän
     * @param otsikko Tehtävän otsikko
     * @param kuvaus Tehtävän kuvaus
     * @param deadline Tehtävän deadline
     */
    public Tehtava(String otsikko, String kuvaus, LocalDate deadline) {
        this.id = idCounter++;
        this.otsikko = otsikko;
        this.kuvaus = kuvaus;
        this.luontiPaiva = LocalDate.now();
        this.deadline = deadline;
        this.tila = Status.Luotu;
    }

    /**
     * Asettaa luokan staattisen muuttujan tiettyyn arvoon
     * @param idCounter kentän uusi arvo
     */
    protected static void setIdCounter(int idCounter) {
        Tehtava.idCounter = idCounter;
    }

    /**
     * Asettaa tehtävän tilan ja valmistumisPaiva kentän valmistumispäivään
     * @param tila tehtävän uusi tila
     */
    public void setTila(Status tila) {
        switch (tila) {
            case Luotu:
                this.tila = Status.Luotu;
                this.valmistumisPaiva = null;
                break;
            case Vaiheessa:
                this.tila = Status.Vaiheessa;
                this.valmistumisPaiva = null;
                break;
            case Valmis:
                if (this.tila == Status.Valmis) return; //Jos on jo valmis, ei tehdä mitään
                this.tila = Status.Valmis;
                this.valmistumisPaiva = LocalDate.now();
        }
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
}