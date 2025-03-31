package com.example.harjoitustyo;

import javafx.scene.control.TableCell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Kustomoitu TableCell, joka muokkaa sen sisältämän LocalDaten hienompaan muotoon
 */
public class PaivamaaraTableCell extends TableCell<Tehtava, LocalDate> {
    public PaivamaaraTableCell() {}

    @Override
    protected void updateItem(LocalDate aika, boolean empty) {
        super.updateItem(aika, empty);
        if (empty || aika == null) return;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String muokattuAika = aika.format(formatter);
        setText(muokattuAika);
    }
}