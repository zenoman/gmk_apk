package project.com.gmklabel.Model;

import java.util.List;

import project.com.gmklabel.Konstruktor.Json_Promo;

public class Respon_Promo {
    private  String pesan,error;
    private List<Json_Promo> data;

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Json_Promo> getData() {
        return data;
    }

    public void setData(List<Json_Promo> data) {
        this.data = data;
    }
}
