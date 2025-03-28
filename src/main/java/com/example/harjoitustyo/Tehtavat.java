package com.example.harjoitustyo;

import java.io.*;
import java.util.ArrayList;

/**
 * Luokka, joka hoitaa tehtävien lukemisen ja kirjoittamisen tiedostoon
 */
public class Tehtavat {
    //Polku datatiedostoon
    private final String tiedostoPolku = "tehtavat.dat";

    //Tehtävälista
    private ArrayList<Tehtava> tehtavat = new ArrayList<>();

    /**
     * Luo Tehtavat olion, joka sisältää ohjelmassa käsiteltävät tehtävät
     * Hakee automaattisesti tiedostosta tehtävät, mikäli niitä on.
     */
    public Tehtavat() {
        try (FileInputStream fis = new FileInputStream(tiedostoPolku);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object luettu = ois.readObject();
            if (luettu instanceof ArrayList<?>) {
                tehtavat = (ArrayList<Tehtava>) luettu;
                System.out.println(tehtavat.size() + " tehtävää ladattu tiedostosta");
                Tehtava.setIdCounter(getMaxId() + 1);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Tiedostoa ei löytynyt");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Palauttaa tehtävälistan
     * @return tehtävälistan
     */
    public ArrayList<Tehtava> get() {
        return tehtavat;
    }


    /**
     * Lisää tehtävän listaan
     * @param tehtava lisättävä tehtävä
     */
    public void lisaa(Tehtava tehtava) {
        tehtavat.add(tehtava);
    }


    /**
     * Poistaa tehtävän
     * @param tehtava poistettava tehtava
     */
    public void poista(Tehtava tehtava) {
        tehtavat.remove(tehtava);
    }

    /**
     * Tallentaa tehtävälistan luokkaan määriteltyyn tiedostoon
     */
    public void tallenna() {
        try (FileOutputStream fos = new FileOutputStream(tiedostoPolku);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this.tehtavat);
            System.out.println("Tehtävät tallennettu");
        } catch (FileNotFoundException e) {
            System.out.println("Tiedostoa ei löytynyt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodi tehtävien suurimman id:n etsimiseen
     * @return max(Tehtava.getId())
     */
    private int getMaxId() {
        int max = 0;
        for (Tehtava tehtava : tehtavat) {
            if (tehtava.getId() > max) {
                max = tehtava.getId();
            }
        }
        return max;
    }
}