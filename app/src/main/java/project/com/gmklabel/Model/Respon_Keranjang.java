package project.com.gmklabel.Model;

import java.util.List;

import project.com.gmklabel.Konstruktor.Json_keranjang;

public class Respon_Keranjang {
    private List<Json_keranjang> data;
    private String pesan;
    private String total;

    public Respon_Keranjang(List<Json_keranjang> data, String total) {
        this.data = data;
        this.total = total;
    }

    public List<Json_keranjang> getData() {
        return data;
    }

    public void setData(List<Json_keranjang> data) {
        this.data = data;
    }

    public String getPesan() {
        return pesan;
    }

    public String getTotal() {
        return total;
    }
}
