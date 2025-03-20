package com.example.harjoitustyo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public class Paaikkuna extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    //Ohjelman globaaleja asioita
    Tehtavat tehtavat = new Tehtavat();
    DatePicker dpHaunAlkupvm;
    DatePicker dpHaunLoppupvm;
    TextField tfHakuteksti;
    ComboBox<Tehtava.Status> cbValittuStatus;
    TableView<Tehtava> tvTehtavat =  new TableView<>();
    Predicate<Tehtava> tehtavaFiltteri;
    TextField tfOtsikko;
    TextArea taKuvaus;
    DatePicker dpDeadline;
    Button btLisaa, btPoista, btTallenna;
    ComboBox<Tehtava.Status> cbStatus;
    TextField tfValmistumisPaiva;

    //Tyylittelyvalintoja ohjelman käyttöön
    Insets oletusPadding = new Insets(10);
    ButtonType bttKylla = new ButtonType("Kyllä");
    ButtonType bttEi = new ButtonType("Ei");
    Border borderMusta = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT));

    /**
     * Päivittää tvTehtavat valittujen suodatusvalintojen mukaisesti
     */
    void paivitaTehtavanakyma() {
        ObservableList<Tehtava> olTehtavat = FXCollections.observableList(tehtavat.get());
        Tehtava.Status valittuStatus = cbValittuStatus.getValue();
        String hakuteksti = tfHakuteksti.getText().toLowerCase();
        LocalDate alkupvm = dpHaunAlkupvm.getValue();
        LocalDate loppupvm = dpHaunLoppupvm.getValue();

        tehtavaFiltteri = new Predicate<Tehtava>() {
            @Override
            public boolean test(Tehtava tehtava) {
                if (valittuStatus != null && valittuStatus != tehtava.getTila()) {
                    return false;
                }
                if (!tehtava.getOtsikko().toLowerCase().contains(hakuteksti) && !tehtava.getKuvaus().toLowerCase().contains(hakuteksti)) {
                    return false;
                }
                if ((alkupvm != null && tehtava.getDeadline().isBefore(alkupvm)) || (loppupvm != null && tehtava.getDeadline().isAfter(loppupvm))) {
                    return false;
                }
                return true;
            }
        };
        FilteredList<Tehtava> flTehtavat = new FilteredList<>(olTehtavat, tehtavaFiltteri);
        tvTehtavat.setItems(flTehtavat);
    }

    /**
     * Asettaa oletusarvot GUI:n tietokenttiin
     */
    void nollaaKentat() {
        tfOtsikko.clear();
        taKuvaus.clear();
        dpDeadline.setValue(LocalDate.now());
        cbStatus.getSelectionModel().select(Tehtava.Status.Luotu);
        tfValmistumisPaiva.clear();
    }

    /**
     * Nollaa hakukentät
     */
    void nollaaHakukentat() {
        tfHakuteksti.clear();
        cbValittuStatus.getSelectionModel().clearSelection();
    }

    /**
     * Asettaa valitun tehtävän tiedot kenttiin
     */
    void naytaTehtava() {
        Tehtava valittu = tvTehtavat.getSelectionModel().getSelectedItem();
        if (valittu == null) return;
        nollaaKentat();
        tfOtsikko.setText(valittu.getOtsikko());
        taKuvaus.setText(valittu.getKuvaus());
        dpDeadline.setValue(valittu.getDeadline());
        cbStatus.getSelectionModel().select(valittu.getTila());
        if (valittu.getTila() == Tehtava.Status.Valmis) {
            tfValmistumisPaiva.setText(valittu.getValmistumisPaiva().toString());
        }
    }

    /**
     * Tallentaa kenttien tiedot valittuun tehtävään
     */
    void tallennaTehtava() {
        Tehtava valittu = tvTehtavat.getSelectionModel().getSelectedItem();
        if (valittu == null) return;

        valittu.setOtsikko(tfOtsikko.getText());
        valittu.setKuvaus(taKuvaus.getText());
        valittu.setDeadline(dpDeadline.getValue());
        valittu.setTila(cbStatus.getSelectionModel().getSelectedItem());
        tvTehtavat.refresh();
    }

    /**
     * Poistaa valitun tehtävän
     */
    void poistaTehtava() {
        Tehtava valittu = tvTehtavat.getSelectionModel().getSelectedItem();
        if (valittu == null) return;

        Alert vahvistus = new Alert(Alert.AlertType.CONFIRMATION, "Jatketaanko?", bttKylla, bttEi);
        vahvistus.setTitle("Poiston vahvistaminen");
        vahvistus.setHeaderText("Olet poistamassa tehtävää\n" + valittu.getOtsikko());
        Optional<ButtonType> vastaus = vahvistus.showAndWait();
        if (vastaus.isPresent() && vastaus.get() == bttKylla) {
            tehtavat.poista(valittu);
            tvTehtavat.refresh();
            nollaaKentat();
        }
    }

    /**
     * Luo ikkunan, jossa käyttäjä voi luoda uuden tehtävän
     */
    void lisaaTehtava() {
        Stage stage = new Stage();
        stage.setTitle("Tehtävän lisääminen");
        stage.setResizable(false);

        GridPane rt = new GridPane();
        rt.setPadding(oletusPadding);
        rt.setVgap(10);
        rt.setHgap(10);
        rt.setAlignment(Pos.CENTER);

        TextField otsikko = new TextField();
        rt.addRow(0, new Label("Anna otsikko"),  otsikko);

        TextArea kuvaus = new TextArea();
        kuvaus.setPrefRowCount(2);
        rt.addRow(1, new Label("Anna kuvaus"),  kuvaus);

        DatePicker deadline = new DatePicker(LocalDate.now().minusDays(1));
        rt.addRow(2, new Label("Anna deadline"),  deadline);

        Button lisaaTehtava = new Button("Lisää tehtävä");
        Label info = new Label();
        info.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
        lisaaTehtava.setMinWidth(80);
        lisaaTehtava.setOnAction(e -> {
            if (otsikko.getText().isEmpty()) {
                info.setText("Lisää otsikko");
            }
            else if (deadline.getValue().isBefore(LocalDate.now())) {
                info.setText("Deadline ei voi olla menneisyydessä");
            }
            else {
                tehtavat.lisaa(new Tehtava(otsikko.getText(), kuvaus.getText(), deadline.getValue()));
                stage.close();
                paivitaTehtavanakyma();
            }
        });

        rt.addRow(3, lisaaTehtava, info);

        Scene sc =  new Scene(rt);
        stage.setScene(sc);
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        //Ikkunamäärittelyt
        primaryStage.setTitle("TODO");
        primaryStage.setResizable(false);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setOnCloseRequest(e -> tehtavat.tallenna());

        //Ikkunan asettelun pohja
        BorderPane root = new BorderPane();


        //Yläreunan suodatusvalinnat
        HBox ylaPalkki = new HBox();
        ylaPalkki.setAlignment(Pos.CENTER_RIGHT);
        ylaPalkki.setPadding(oletusPadding);
        ylaPalkki.setBorder(borderMusta);
        ylaPalkki.setSpacing(5);

        //Haku deadlinella
        dpHaunAlkupvm = new DatePicker();
        dpHaunAlkupvm.setMaxWidth(100);
        dpHaunAlkupvm.valueProperty().addListener(e -> paivitaTehtavanakyma());
        dpHaunLoppupvm = new DatePicker();
        dpHaunLoppupvm.setMaxWidth(100);
        dpHaunLoppupvm.valueProperty().addListener(e -> paivitaTehtavanakyma());
        ylaPalkki.getChildren().addAll(new Label("Deadline välillä"), dpHaunAlkupvm, new Label("-"), dpHaunLoppupvm);

        //Haku otsikosta ja kuvauksesta
        tfHakuteksti = new TextField();
        tfHakuteksti.textProperty().addListener(e-> paivitaTehtavanakyma());
        ylaPalkki.getChildren().addAll(new Label("Haettava teksti:"), tfHakuteksti);

        //Statuksella hakemista
        cbValittuStatus = new ComboBox<>(FXCollections.observableList(Arrays.asList(Tehtava.Status.values())));
        cbValittuStatus.getSelectionModel().selectedIndexProperty().addListener(e-> paivitaTehtavanakyma());
        Button bResetValittuStatus = new Button("Nollaa");
        bResetValittuStatus.setOnAction(e-> nollaaHakukentat());
        ylaPalkki.getChildren().addAll(new Label("Status"), cbValittuStatus, bResetValittuStatus);

//        Button bHae = new Button("Hae");
//        bHae.setOnAction(e -> paivitaTehtavanakyma());
//        ylaPalkki.getChildren().add(bHae);

        root.setTop(ylaPalkki);


        //ListView keskelle
        VBox tehtavaNakyma = new VBox();
        TableColumn<Tehtava, String> tcLuontipaiva = new TableColumn<>("Luontipäivä");
        tcLuontipaiva.setMinWidth(100);
        tcLuontipaiva.setCellValueFactory(new PropertyValueFactory<>("luontiPaiva"));
        tcLuontipaiva.setResizable(false);

        TableColumn<Tehtava, String> tcOtsikko = new TableColumn<>("Otsikko");
        tcOtsikko.setMinWidth(449);
        tcOtsikko.setCellValueFactory(new PropertyValueFactory<>("otsikko"));
        tcOtsikko.setResizable(false);

        TableColumn<Tehtava, String> tcDeadline = new TableColumn<>("Deadline");
        tcDeadline.setMinWidth(100);
        tcDeadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        tcDeadline.setResizable(false);

        TableColumn<Tehtava, String> tcValmis = new TableColumn<>("Tehtävän tila");
        tcValmis.setMinWidth(100);
        tcValmis.setCellValueFactory(new PropertyValueFactory<>("tila"));
        tcValmis.setResizable(false);

        tvTehtavat.getColumns().addAll(tcLuontipaiva, tcOtsikko, tcDeadline, tcValmis);
        tvTehtavat.setOnMouseClicked(e->naytaTehtava());
        tvTehtavat.setPlaceholder(new Label("Ei tehtäviä\nKokeile lisätä uusia!"));
        tehtavaNakyma.getChildren().add(tvTehtavat);
        tehtavaNakyma.setPadding(oletusPadding);
        root.setCenter(tehtavaNakyma);
        paivitaTehtavanakyma();


        //Alapuolen toiminnallisuus
        int btnLeveys = 100;
        GridPane gpAlapalkki = new GridPane();
        gpAlapalkki.setHgap(5);
        gpAlapalkki.setVgap(5);

        Label lbOtsikko = new Label("Otsikko:");
        tfOtsikko = new TextField();
        tfOtsikko.setMinWidth(450);
        btPoista = new Button("Poista");
        btPoista.setMinWidth(btnLeveys);
        btPoista.setOnAction(e->poistaTehtava());
        gpAlapalkki.addRow(0, lbOtsikko,  tfOtsikko, btPoista);

        Label lbKuvaus = new Label("Kuvaus:");
        taKuvaus = new TextArea();
        taKuvaus.setMinWidth(450);
        taKuvaus.setPrefRowCount(2);
        btLisaa = new Button("Lisää uusi");
        btLisaa.setMinWidth(btnLeveys);
        btLisaa.setOnAction(e->lisaaTehtava());
        gpAlapalkki.addRow(1,  lbKuvaus,  taKuvaus,  btLisaa);

        Label lbDeadline = new Label("Deadline");
        HBox hbDeadlineJaTila = new HBox();
        dpDeadline = new DatePicker(LocalDate.now());
        cbStatus = new ComboBox<>(FXCollections.observableList(Arrays.asList(Tehtava.Status.values())));
        tfValmistumisPaiva = new TextField();
        tfValmistumisPaiva.setEditable(false);
        hbDeadlineJaTila.getChildren().addAll(dpDeadline, new Label("Valmistuspäivä"), tfValmistumisPaiva, new Label("Status"), cbStatus);
        hbDeadlineJaTila.setSpacing(5);
        hbDeadlineJaTila.setAlignment(Pos.CENTER_LEFT);
        btTallenna = new Button("Tallenna");
        btTallenna.setMinWidth(btnLeveys);
        btTallenna.setOnAction(e->tallennaTehtava());
        gpAlapalkki.addRow(2,  lbDeadline,  hbDeadlineJaTila, btTallenna);
        gpAlapalkki.setPadding(oletusPadding);
        root.setBottom(gpAlapalkki);


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        nollaaKentat();
    }
}
