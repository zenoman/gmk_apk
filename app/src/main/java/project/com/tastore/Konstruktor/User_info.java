package project.com.tastore.Konstruktor;

public class User_info {
    private String  id,username,password,email,telp,nama,alamat,kota,provinsi,kodepos,level,ktp_gmb;



    public User_info(String id, String username, String password, String email, String telp, String nama, String alamat, String kota, String provinsi, String kodepos, String level, String ktp_gmb) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telp = telp;
        this.nama = nama;
        this.alamat = alamat;
        this.kota = kota;
        this.provinsi = provinsi;
        this.kodepos = kodepos;
        this.level = level;
        this.ktp_gmb = ktp_gmb;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKodepos() {
        return kodepos;
    }

    public void setKodepos(String kodepos) {
        this.kodepos = kodepos;
    }

    public String getKtp_gmb() {
        return ktp_gmb;
    }

    public void setKtp_gmb(String ktp_gmb) {
        this.ktp_gmb = ktp_gmb;
    }
}
