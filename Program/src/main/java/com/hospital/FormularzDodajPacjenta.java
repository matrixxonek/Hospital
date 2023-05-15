package com.hospital;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.NumberStringConverter;

public class FormularzDodajPacjenta {

    MySQL mysql = new MySQL();

    Button btDodaj, btAnuluj;
    ChoiceBox<String> cbOddzial, cbStanCywilny;
    ChoiceBox<Lekarz> cbLekarz;
    DatePicker dpDataUrodzenia;
    // GridPane gridPane;
    VBox vbDodajPacjenta;
    RadioButton rbMezczyzna, rbKobieta;
    ToggleGroup tgPlec;
    Label lbImie, lbDrugieImie, lbNazwisko, lbDataUrodzenia, lbPlec, lbPESEL, lbMiejsceUrodzenia, lbObywatelstwo,
            lbOddzial, lbLekarz, lbAdres, lbUlica, lbNrDomu, lbNrLokalu, lbKod, lbMiejscowosc, lbWojewodztwo, lbPanstwo,
            lbStanCywilny;
    TextField tfImie, tfDrugieImie, tfNazwisko, tfPESEL, tfMiejsceUrodzenia, tfObywatelstwo, tfUlica, tfNrDomu,
            tfNrLokalu, tfMiejscowosc, tfKod, tfWojewodztwo, tfPanstwo;
    HBox hbImie, hbAdres, hbPlecStan, hbKodMiej, hbDaturoPESEL, hbWojPansObyw, hbStan, hbPrzyciski;
    StackPane spFormRoot;
    Scene spFormScene;
    Stage stageFormularz;

    public FormularzDodajPacjenta() {
        InitializeElements();

        lbImie.setText("Imię");
        lbDrugieImie.setText("Drugie imię");
        lbNazwisko.setText("Nazwisko");
        tfImie.setMinWidth(120);
        tfImie.setMaxWidth(120);
        tfDrugieImie.setMinWidth(120);
        tfDrugieImie.setMaxWidth(120);
        tfNazwisko.setMinWidth(240);
        tfNazwisko.setMaxWidth(240);
        hbImie.getChildren().addAll(lbImie, tfImie, lbDrugieImie, tfDrugieImie, lbNazwisko, tfNazwisko);
        hbImie.setAlignment(Pos.CENTER_LEFT);
        hbImie.setSpacing(5);

        lbPlec.setText("Płeć");
        lbStanCywilny.setText("Stan cywilny");
        lbStanCywilny.setPadding(new Insets(0, 0, 0, 30));
        rbMezczyzna.setText("Mężczyzna");
        rbMezczyzna.setUserData(0);
        rbMezczyzna.setToggleGroup(tgPlec);
        rbKobieta.setText("Kobieta");
        rbMezczyzna.setUserData(1);
        rbKobieta.setToggleGroup(tgPlec);
        cbStanCywilny.getItems().addAll("panna/kawaler", "zamężna/żonaty", "rozwiedziona/rozwiedziony",
                "wdowa/wdowiec");
        hbPlecStan.getChildren().addAll(lbPlec, rbMezczyzna, rbKobieta, lbStanCywilny, cbStanCywilny);
        hbPlecStan.setAlignment(Pos.CENTER_LEFT);
        hbPlecStan.setSpacing(5);

        lbDataUrodzenia.setText("Data urodzenia");
        lbMiejsceUrodzenia.setText("Miejsce urodzenia");
        lbPESEL.setText("PESEL");
        tfPESEL.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        hbDaturoPESEL.getChildren().addAll(lbDataUrodzenia, dpDataUrodzenia, lbMiejsceUrodzenia, tfMiejsceUrodzenia, lbPESEL, tfPESEL);
        hbDaturoPESEL.setAlignment(Pos.CENTER_LEFT);
        hbDaturoPESEL.setSpacing(5);

        lbUlica.setText("Ulica");
        lbNrDomu.setText("Nr domu");
        lbNrLokalu.setText("Nr localu");
        tfNrDomu.setMinWidth(100);
        tfNrDomu.setMaxWidth(100);
        tfNrLokalu.setMinWidth(100);
        tfNrLokalu.setMaxWidth(100);
        hbAdres.getChildren().addAll(lbUlica, tfUlica, lbNrDomu, tfNrDomu, lbNrLokalu, tfNrLokalu);
        hbAdres.setAlignment(Pos.CENTER_LEFT);
        hbAdres.setSpacing(5);

        lbKod.setText("Kod pocztowy");
        lbMiejscowosc.setText("Miejscowość");
        tfKod.setMinWidth(60);
        tfKod.setMaxWidth(60);
        hbKodMiej.getChildren().addAll(lbKod, tfKod, lbMiejscowosc, tfMiejscowosc);
        hbKodMiej.setAlignment(Pos.CENTER_LEFT);
        hbKodMiej.setSpacing(5);

        lbWojewodztwo.setText("Województwo");
        lbPanstwo.setText("Państwo");
        lbObywatelstwo.setText("Obywatelstwo");
        hbWojPansObyw.getChildren().addAll(lbWojewodztwo, tfWojewodztwo, lbPanstwo, tfPanstwo, lbObywatelstwo, tfObywatelstwo);
        hbWojPansObyw.setAlignment(Pos.CENTER_LEFT);
        hbWojPansObyw.setSpacing(5);

        for (String entry : mysql.PobierzListeOddzialow()) {
            cbOddzial.getItems().add(entry);
        }
        cbOddzial.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(cbOddzial.getSelectionModel().getSelectedIndex());
                ZaktualizujListeLekarzy(cbOddzial.getItems().get((Integer) newValue));
            }
        });

        btDodaj.setText("Dodaj");
        btDodaj.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mysql.DodajPacjenta(tfImie.getText(), tfDrugieImie.getText(), tfNazwisko.getText(),
                        dpDataUrodzenia.getValue().toString(), (Integer)((RadioButton)tgPlec.getSelectedToggle()).getUserData(),
                        tfPESEL.getText(), cbStanCywilny.getSelectionModel().getSelectedIndex(),
                        tfMiejsceUrodzenia.getText(), tfUlica.getText(), tfNrDomu.getText(), tfNrLokalu.getText(),
                        tfMiejscowosc.getText(), tfKod.getText(), tfWojewodztwo.getText(), tfPanstwo.getText(),
                        tfObywatelstwo.getText());
            };
        });
        btAnuluj.setText("Anuluj");
        hbPrzyciski.getChildren().addAll(btDodaj, btAnuluj);
        hbPrzyciski.setAlignment(Pos.CENTER_RIGHT);
        hbPrzyciski.setSpacing(3);

        vbDodajPacjenta.getChildren().addAll(hbImie, hbPlecStan, hbDaturoPESEL, hbAdres, hbKodMiej, hbWojPansObyw, hbPrzyciski);
        vbDodajPacjenta.setSpacing(10);

        spFormRoot.getChildren().add(vbDodajPacjenta);

        spFormScene = new Scene(spFormRoot);
        spFormRoot.setPadding(new Insets(10, 10, 10, 10));

        stageFormularz = new Stage();
        stageFormularz.setTitle("Dodaj pacjenta");
        stageFormularz.setScene(spFormScene);
        stageFormularz.sizeToScene();
        stageFormularz.setAlwaysOnTop(true);
        stageFormularz.initModality(Modality.APPLICATION_MODAL);
        stageFormularz.initStyle(StageStyle.UTILITY);
        stageFormularz.setMinHeight(280);
        stageFormularz.setMinWidth(700);
        stageFormularz.setMaxHeight(280);
        stageFormularz.setMaxWidth(700);
        stageFormularz.setResizable(false);
    }

    public void show() {
        stageFormularz.show();
    }

    private void InitializeElements() {
        lbImie = new Label();
        lbDrugieImie = new Label();
        lbNazwisko = new Label();
        tfImie = new TextField();
        tfDrugieImie = new TextField();
        tfNazwisko = new TextField();
        hbImie = new HBox();
        lbPlec = new Label();
        tgPlec = new ToggleGroup();
        rbMezczyzna = new RadioButton();
        rbKobieta = new RadioButton();
        hbPlecStan = new HBox();
        lbDataUrodzenia = new Label();
        lbPESEL = new Label();
        dpDataUrodzenia = new DatePicker();
        tfPESEL = new TextField();
        hbDaturoPESEL = new HBox();
        lbUlica = new Label();
        lbNrDomu = new Label();
        lbNrLokalu = new Label();
        tfUlica = new TextField();
        tfNrDomu = new TextField();
        tfNrLokalu = new TextField();
        hbAdres = new HBox();
        lbKod = new Label();
        lbMiejscowosc = new Label();
        tfKod = new TextField();
        tfMiejscowosc = new TextField();
        hbKodMiej = new HBox();
        lbPanstwo = new Label();
        tfWojewodztwo = new TextField();
        tfPanstwo = new TextField();
        hbWojPansObyw = new HBox();
        lbMiejsceUrodzenia = new Label();
        lbObywatelstwo = new Label();
        tfMiejsceUrodzenia = new TextField();
        tfObywatelstwo = new TextField();
        lbStanCywilny = new Label();
        cbStanCywilny = new ChoiceBox<String>();
        hbStan = new HBox();
        btDodaj = new Button();
        vbDodajPacjenta = new VBox();
        spFormRoot = new StackPane();
        hbPrzyciski = new HBox();
        btAnuluj = new Button();
        cbLekarz = new ChoiceBox<Lekarz>();
        lbWojewodztwo = new Label();
        cbOddzial = new ChoiceBox<String>();
    }

    private void ZaktualizujListeLekarzy(String oddzial) {
        cbLekarz.setDisable(true);
        cbLekarz.getItems().clear();
        cbLekarz.getItems().addAll(mysql.PobierzListeLekarzy(oddzial));
        if (cbLekarz.getItems().size() != 0)
            cbLekarz.setDisable(false);
    }
}
