package com.example.harjoitustyo;

import java.io.*;
import java.util.ArrayList;

public class Tehtavat {
    private static final String tiedostoPolku = "tehtavat.dat";

    /**
     * Hakee ja lataa tehtävät luokassa määritellystä tiedostopolusta
     * @return ArrayList&lt;Tehtava>
     */
    public static ArrayList<Tehtava> lataa() {
        ArrayList<Tehtava> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(tiedostoPolku);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object luettu = ois.readObject();
            if (luettu instanceof ArrayList<?>) {
                lista = (ArrayList<Tehtava>) luettu;
                System.out.println(lista.size() + " tehtävää ladattu tiedostosta");
                Tehtava.setIdCounter(lista.size());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Tiedostoa ei löytynyt");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Tallentaa tehtävälistan luokkaan määriteltyyn tiedostoon
     * @param lista ArrayList&lt;Tehtava>
     */
    public static void tallenna(ArrayList<Tehtava> lista) {
        try (FileOutputStream fos = new FileOutputStream(tiedostoPolku);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(lista);
            System.out.println("Tehtävät tallennettu");
        } catch (FileNotFoundException e) {
            System.out.println("Tiedostoa ei löytynyt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}