package project.com.gmklabel.Model;

import java.util.List;

import project.com.gmklabel.Konstruktor.Json_kategori;

public class Respon_Kategori {
    private  String pesan,error;
    private List<Json_kategori> data;

    public String getPesan() {
        return pesan;
    }

    public String getError() {
        return error;
    }

    public List<Json_kategori> getData() {
        return data;
    }
}
