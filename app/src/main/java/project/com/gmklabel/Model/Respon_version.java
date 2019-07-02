package project.com.gmklabel.Model;

import java.util.List;

import project.com.gmklabel.Konstruktor.Json_Version;

public class Respon_version {
private String id,version;
private List<Json_Version> data;

    public List<Json_Version> getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }
}
