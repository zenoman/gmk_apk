package project.com.tastore.Model;

import java.util.List;

import project.com.tastore.Konstruktor.Json_Cvarian;

public class Respon_Cvarian {
    private List<Json_Cvarian> data;

    public Respon_Cvarian(List<Json_Cvarian> data) {
        this.data = data;
    }

    public List<Json_Cvarian> getData() {
        return data;
    }
}
