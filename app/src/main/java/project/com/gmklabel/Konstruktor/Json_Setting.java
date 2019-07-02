package project.com.gmklabel.Konstruktor;

public class Json_Setting {
    private String id,username,nama,telp,email;

    public Json_Setting(String id, String username, String nama, String telp, String email) {
        this.id = id;
        this.username = username;
        this.nama = nama;
        this.telp = telp;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
