package project.com.tastore_laris.Konstruktor;

public class Json_keranjang {
    private String id,idwarna,iduser,faktur,tgl,tgl_kadaluarsa,kode_barang,barang,harga,jumlah,total_a,diskon,total,admin,metode,warna,nama,varian;

    public Json_keranjang(String id, String idwarna, String iduser, String faktur, String tgl, String tgl_kadaluarsa, String kode_barang, String barang, String harga, String jumlah, String total_a, String diskon, String total, String admin, String metode, String warna, String nama, String varian) {
        this.id = id;
        this.idwarna = idwarna;
        this.iduser = iduser;
        this.faktur = faktur;
        this.tgl = tgl;
        this.tgl_kadaluarsa = tgl_kadaluarsa;
        this.kode_barang = kode_barang;
        this.barang = barang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.total_a = total_a;
        this.diskon = diskon;
        this.total = total;
        this.admin = admin;
        this.metode = metode;
        this.warna = warna;
        this.nama = nama;
        this.varian = varian;
    }

    public String getVarian() {
        return varian;
    }

    public void setVarian(String varian) {
        this.varian = varian;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdwarna() {
        return idwarna;
    }

    public void setIdwarna(String idwarna) {
        this.idwarna = idwarna;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getFaktur() {
        return faktur;
    }

    public void setFaktur(String faktur) {
        this.faktur = faktur;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getTgl_kadaluarsa() {
        return tgl_kadaluarsa;
    }

    public void setTgl_kadaluarsa(String tgl_kadaluarsa) {
        this.tgl_kadaluarsa = tgl_kadaluarsa;
    }

    public String getKode_barang() {
        return kode_barang;
    }

    public void setKode_barang(String kode_barang) {
        this.kode_barang = kode_barang;
    }

    public String getBarang() {
        return barang;
    }

    public void setBarang(String barang) {
        this.barang = barang;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTotal_a() {
        return total_a;
    }

    public void setTotal_a(String total_a) {
        this.total_a = total_a;
    }

    public String getDiskon() {
        return diskon;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getMetode() {
        return metode;
    }

    public void setMetode(String metode) {
        this.metode = metode;
    }
}
