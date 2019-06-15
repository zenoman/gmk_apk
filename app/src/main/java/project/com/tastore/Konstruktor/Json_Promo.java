package project.com.tastore.Konstruktor;

public class Json_Promo {
    private String id;
    private String judul;
    private String foto;

    public Json_Promo(String id, String judul, String foto) {
        this.id = id;
        this.judul = judul;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
