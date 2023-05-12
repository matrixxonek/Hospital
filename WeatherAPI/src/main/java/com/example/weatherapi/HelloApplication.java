package com.example.weatherapi;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.sql.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {//Creating Text Filed for email
        TextField imie = new TextField();
        TextField nazwisko = new TextField();
        DatePicker data_urodzenia = new DatePicker();
        ChoiceBox plec = new ChoiceBox();
            plec.getItems().add("kobieta");
            plec.getItems().add("mezczyzna");
        TextField pesel = new TextField();
        TextField miasto_urodzenia = new TextField();
        TextField obywatelstwo = new TextField();
        ChoiceBox oddzial = new ChoiceBox();
            oddzial.getItems().add("ogolny");
            oddzial.getItems().add("zakazny");
            oddzial.getItems().add("skorny");
            oddzial.getItems().add("kardiologiczny");
            oddzial.getItems().add("chemioterapi");
        TextField idlekarza = new TextField();
        TextField uwagi = new TextField();
        Button button1 = new Button("dodaj pacjenta");
        Text imie1 = new Text();
        imie1.setText("imie: ");
        Text nazwisko1 = new Text();
        nazwisko1.setText("nazwisko: ");
        Text data_urodzenia1 = new Text();
        data_urodzenia1.setText("data urodzenia: ");
        Text plec1 = new Text();
        plec1.setText("plec: ");
        Text pesel1 = new Text();
        pesel1.setText("pesel: ");
        Text miasto_urodzenia1 = new Text();
        miasto_urodzenia1.setText("miasto urodzenia: ");
        Text obywatelstwo1 = new Text();
        obywatelstwo1.setText("obywatelstwo: ");
        Text oddzial1 = new Text();
        oddzial1.setText("oddzial: ");
        Text idlekarza1 = new Text();
        idlekarza1.setText("id lekarza: ");
        Text uwagi1 = new Text();
        uwagi1.setText("uwagi: ");


        pesel.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));

        Label label = new Label();
        GridPane gridPane = new GridPane();

        //Setting size for the pane
        gridPane.setMinSize(600, 600);

        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(3);
        gridPane.setHgap(11);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(imie, 1, 0);
        gridPane.add(nazwisko,1,1);
        gridPane.add(data_urodzenia, 1, 2);
        gridPane.add(plec, 1, 3);
        gridPane.add(pesel, 1, 4);
        gridPane.add(miasto_urodzenia, 1, 5);
        gridPane.add(obywatelstwo, 1, 6);
        gridPane.add(oddzial, 1, 7);
        gridPane.add(idlekarza, 1, 8);
        gridPane.add(uwagi, 1, 9);
        gridPane.add(button1, 1, 11);
        gridPane.add(label, 3, 1);

        gridPane.add(imie1, 0, 0);
        gridPane.add(nazwisko1,0,1);
        gridPane.add(data_urodzenia1, 0, 2);
        gridPane.add(plec1, 0, 3);
        gridPane.add(pesel1, 0, 4);
        gridPane.add(miasto_urodzenia1, 0, 5);
        gridPane.add(obywatelstwo1, 0, 6);
        gridPane.add(oddzial1, 0, 7);
        gridPane.add(idlekarza1, 0, 8);
        gridPane.add(uwagi1, 0, 9);

        //Creating a scene object
        Scene scene = new Scene(gridPane);
        //Setting title to the Stage
        stage.setTitle("Grid Pane Example");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //imie nazwisko data urodzenia płeć pesel miejsce urodzenia obywatelstwo oddział idlekarza uwagi
                String imie2 = imie.getText();
                String nazwisko2 = imie.getText();
                LocalDate data_urodzenia2 = data_urodzenia.getValue();
                String plec2 = plec.getValue().toString();
                Integer pesel2 = Integer.parseInt(pesel.getText());
                String miasto_urodzenia2 = miasto_urodzenia.getText();
                String obywatelstwo2 = obywatelstwo.getText();
                String oddzial2 = oddzial.getValue().toString();
                Integer idlekarza2 = Integer.parseInt(idlekarza.getText());
                String uwagi2 = uwagi.getText();

                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/sonoo","root","root");
//here sonoo is database name, root is username and password
                    Statement stmt=con.createStatement();
                    ResultSet rs=stmt.executeQuery("select * from emp");
                    while(rs.next())
                        System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
                    con.close();
                }catch(Exception e){ System.out.println(e);

                }
            }

        }
        );
    }

    public static void main(String[] args) {
        launch();
    }
}