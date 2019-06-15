package project.com.tastore.Model;

import java.util.List;

import project.com.tastore.Konstruktor.Json_Trans;

public class Respon_Trans {
    private List<Json_Trans> data;

    public Respon_Trans(List<Json_Trans> data) {
        this.data = data;
    }

    public List<Json_Trans> getData() {
        return data;
    }
}
