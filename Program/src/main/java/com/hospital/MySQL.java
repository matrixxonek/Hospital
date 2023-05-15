// God has abandoned us

package com.hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQL {

    private static String dbCLink = "jdbc:mysql://localhost:3306/szpital";
    private static String dbLogin = "administrator";
    private static String dbPassw = "A%*xc}$59K2:3hx";

    public String[] PobierzListeOddzialow() {

        Connection con = null;
        ArrayList<String> lista = new ArrayList<>();
        try {
            con = DriverManager.getConnection(dbCLink, dbLogin, dbPassw);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT od_Id, od_Nazwa FROM sl_Oddzial;");
            while (rs.next()){
                lista.add(rs.getString("od_Nazwa"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista.toArray(new String[]{});
    }

    public Lekarz[] PobierzListeLekarzy(){ return PobierzListeLekarzy(null);}

    public Lekarz[] PobierzListeLekarzy(String odddzial) {

        Connection con = null;
        ArrayList<Lekarz> lista = new ArrayList<>();
        try {
            con = DriverManager.getConnection(dbCLink, dbLogin, dbPassw);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT l.lk_Id AS 'id', l.lk_Nazwisko AS 'nazwisko', l.lk_Imie AS 'imie', l.lk_drugieImie AS 'drugieImie', IFNULL(GROUP_CONCAT(o.od_Nazwa SEPARATOR '|'), '') AS 'oddzialy' FROM lk_Lekarz l LEFT JOIN lk_OddzialLekarze ol ON ol.odl_LekarzId=l.lk_Id LEFT JOIN sl_Oddzial o ON o.od_Id=ol.odl_OddzialId GROUP BY l.lk_Id, l.lk_Nazwisko, l.lk_Imie, l.lk_drugieImie" + (odddzial.length() > 0 ? " HAVING IFNULL(GROUP_CONCAT(o.od_Nazwa SEPARATOR '|'), '') LIKE '%" + odddzial + "%'" : "") + " ORDER BY l.lk_Nazwisko;");
            while (rs.next()){
                lista.add(new Lekarz(rs.getInt("id"), rs.getString("nazwisko"), rs.getString("imie"), rs.getString("drugieImie"), rs.getString("oddzialy")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista.toArray(new Lekarz[]{});
    }

    public Integer DodajPacjenta(String imie, String drugieImie, String nazwisko, String dataUrodzenia, Integer plec, String pesel, Integer stanCywilny, String miejsceUrodzenia, String ulica, String nrDomu, String nrLokalu, String miejscowosc, String kod, String wojewodztwo, String panstwo, String obywatelstwo) {
        Integer result = null;
        Connection con = null;
        try {
            con = DriverManager.getConnection(dbCLink, dbLogin, dbPassw);
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            System.out.println(String.format(
                "CALL DodajPacjenta('%s','%s','%s',%d,'%s','%s',%d,'%s','%s','%s','%s','%s','%s','%s','%s','%s',@_);",
                imie,
                drugieImie,
                nazwisko,
                plec,
                dataUrodzenia,
                pesel,
                stanCywilny,
                miejsceUrodzenia,
                ulica,
                nrDomu,
                nrLokalu,
                miejscowosc,
                kod,
                wojewodztwo,
                panstwo,
                obywatelstwo
                ));
            stmt.executeQuery(String.format(
                    "CALL DodajPacjenta('%s','%s','%s',%d,'%s','%s',%d,'%s','%s','%s','%s','%s','%s','%s','%s','%s',@_);",
                    imie,
                    drugieImie,
                    nazwisko,
                    plec,
                    dataUrodzenia,
                    pesel,
                    stanCywilny,
                    miejsceUrodzenia,
                    ulica,
                    nrDomu,
                    nrLokalu,
                    miejscowosc,
                    kod,
                    wojewodztwo,
                    panstwo,
                    obywatelstwo
                    ));
            con.commit();
        } catch (Exception e) {
            try {
                con.rollback();
                System.out.println(e);
            } catch (SQLException ee) {
                ee.printStackTrace();
            }
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
