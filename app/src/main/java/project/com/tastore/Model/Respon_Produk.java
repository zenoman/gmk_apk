package project.com.tastore.Model;

import java.util.List;

import project.com.tastore.Konstruktor.Json_Produk;
import project.com.tastore.Konstruktor.Json_rinci_poduk;

public class Respon_Produk {
    private  String pesan,error,status;
    int page,page_total,total;
    private List<Json_Produk> data;
    private List<Json_rinci_poduk> data_barang;

    public String getPesan() {
        return pesan;
    }

    public String getError() {
        return error;
    }

    public int getPage() {
        return page;
    }

    public int getPage_total() {
        return page_total;
    }

    public int getTotal() {
        return total;
    }

    public List<Json_Produk> getData() {
        return data;
    }

    public List<Json_rinci_poduk> getData_barang() {
        return data_barang;
    }

    public String getStatus() {
        return status;
    }
}
