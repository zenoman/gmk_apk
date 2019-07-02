package project.com.gmklabel.Model;

import java.util.List;

import project.com.gmklabel.Konstruktor.Json_Cvarian;

public class Respon_Cvarian {
    private List<Json_Cvarian> data;

    public Respon_Cvarian(List<Json_Cvarian> data) {
        this.data = data;
    }

    public List<Json_Cvarian> getData() {
        return data;
    }
}
