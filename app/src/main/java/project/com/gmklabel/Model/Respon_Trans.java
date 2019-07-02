package project.com.gmklabel.Model;

import java.util.List;

import project.com.gmklabel.Konstruktor.Json_Trans;

public class Respon_Trans {
    private List<Json_Trans> data;
    private String pesan,status;

    public String getPesan() {
        return pesan;
    }

    public String getStatus() {
        return status;
    }

    public Respon_Trans(List<Json_Trans> data) {
        this.data = data;
    }

    public List<Json_Trans> getData() {
        return data;
    }
}
