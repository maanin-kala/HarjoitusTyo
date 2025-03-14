package com.example.harjoitustyo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Kayttoliittyma extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    Tehtavat tehtavat = new Tehtavat();
    TableView<Tehtava> tvTehtavat =  new TableView<>();
    TextField tfOtsikko;
    TextArea taKuvaus;
    DatePicker dpDeadline;

    //Tyylittelyvalintoja ohjelman käyttöön
    Insets oletusPadding = new Insets(10);
    Border borderMusta = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

    /**
     * Päivittää tvTehtavat valittujen suodatusvalintojen mukaisesti
     */
    void paivitaTehtavanakyma() {
        ObservableList<Tehtava> olTehtavat = FXCollections.observableList(tehtavat.get());
        tvTehtavat.setItems(olTehtavat);
    }

    @Override
    public void start(Stage primaryStage) {
        //Ikkunamäärittelyt
        primaryStage.setTitle("TODO");
        primaryStage.setResizable(false);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

        //Ikkunan asettelun pohja
        BorderPane root = new BorderPane();


//        //Yläreunan suodatusvalinnat
//        HBox ylaPalkki = new HBox();
//        ylaPalkki.setAlignment(Pos.CENTER);
//        ylaPalkki.setPadding(oletusPadding);
//        ylaPalkki.setBorder(borderMusta);
//        root.setTop(ylaPalkki);


        //ListView keskelle
        VBox tehtavaNakyma = new VBox();
        //Label ohjeTeksti =  new Label(String.format(Tehtava.toStringFormat, "Luontipäivä", "Tehtävän otsikko", "Deadline"));
        TableColumn<Tehtava, String> tcLuontipaiva = new TableColumn<>("Luontipäivä");
        tcLuontipaiva.setMinWidth(100);
        tcLuontipaiva.setCellValueFactory(new PropertyValueFactory<>("luontiPaiva"));

        TableColumn<Tehtava, String> tcOtsikko = new TableColumn<>("Otsikko");
        tcOtsikko.setMinWidth(449);
        tcOtsikko.setCellValueFactory(new PropertyValueFactory<>("otsikko"));

        TableColumn<Tehtava, String> tcDeadline = new TableColumn<>("Deadline");
        tcDeadline.setMinWidth(100);
        tcDeadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        TableColumn<Tehtava, String> tcValmis = new TableColumn<>("Tehtävän tila");
        tcValmis.setMinWidth(100);
        tcValmis.setCellValueFactory(new PropertyValueFactory<>("tila"));

        tvTehtavat.getColumns().addAll(tcLuontipaiva, tcOtsikko, tcDeadline, tcValmis);
        tehtavaNakyma.getChildren().add(tvTehtavat);
        tehtavaNakyma.setPadding(oletusPadding);
        root.setCenter(tehtavaNakyma);
        paivitaTehtavanakyma();


        //Alapuolen toiminnallisuus
        GridPane gpAlapalkki = new GridPane();
        gpAlapalkki.setHgap(5);
        gpAlapalkki.setVgap(5);

        Label lbOtsikko = new Label("Otsikko:");
        tfOtsikko = new TextField();
        tfOtsikko.setMinWidth(450);
        gpAlapalkki.addRow(0, lbOtsikko,  tfOtsikko);

        Label lbKuvaus = new Label("Kuvaus:");
        taKuvaus = new TextArea();
        taKuvaus.setMinWidth(450);
        taKuvaus.setPrefRowCount(2);
        gpAlapalkki.addRow(1,  lbKuvaus,  taKuvaus);

        Label lbDeadline = new Label("Deadline");
        dpDeadline = new DatePicker(LocalDate.now());
        gpAlapalkki.addRow(2,  lbDeadline,  dpDeadline);

        gpAlapalkki.setPadding(oletusPadding);
        root.setBottom(gpAlapalkki);







        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
