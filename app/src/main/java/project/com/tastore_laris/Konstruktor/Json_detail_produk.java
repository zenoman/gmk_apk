package project.com.tastore_laris.Konstruktor;

public class Json_detail_produk {
    private String barang;
    private String kategori;
    private String kode_kategori;
    private String stok;
    private String gambar;
    private String hargaBeli;
    private String satuan;
    private String berat;
    private String kode;
    private String id;
    private String deskripsi;
    private String harga_jual;
    private String diskon;

    public Json_detail_produk(String barang, String kategori, String kode_kategori, String stok, String gambar, String hargaBeli, String satuan, String berat, String kode, String id, String deskripsi, String harga_jual, String diskon) {
        this.barang = barang;
        this.kategori = kategori;
        this.kode_kategori = kode_kategori;
        this.stok = stok;
        this.gambar = gambar;
        this.hargaBeli = hargaBeli;
        this.satuan = satuan;
        this.berat = berat;
        this.kode = kode;
        this.id = id;
        this.deskripsi = deskripsi;
        this.harga_jual = harga_jual;
        this.diskon = diskon;
    }

    public void setBarang(String barang) {
        this.barang = barang;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setKode_kategori(String kode_kategori) {
        this.kode_kategori = kode_kategori;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public void setHargaBeli(String hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setHarga_jual(String harga_jual) {
        this.harga_jual = harga_jual;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public String getBarang() {
        return barang;
    }

    public String getKategori() {
        return kategori;
    }

    public String getKode_kategori() {
        return kode_kategori;
    }

    public String getStok() {
        return stok;
    }

    public String getGambar() {
        return gambar;
    }

    public String getHargaBeli() {
        return hargaBeli;
    }

    public String getSatuan() {
        return satuan;
    }

    public String getBerat() {
        return berat;
    }

    public String getKode() {
        return kode;
    }

    public String getId() {
        return id;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getHarga_jual() {
        return harga_jual;
    }

    public String getDiskon() {
        return diskon;
    }
}
