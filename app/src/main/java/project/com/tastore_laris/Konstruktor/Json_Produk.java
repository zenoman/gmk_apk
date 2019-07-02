package project.com.tastore_laris.Konstruktor;

public class Json_Produk {
    private String barang;
    private String id_kategori;
    private String nama;
    private String kode_barang;
    private String harga_barang;
    private  String harga_reseller;
    private String kategori;
    private String id;
    private String deskripsi;
    private String diskon;

    public Json_Produk(String barang, String id_kategori, String nama, String kode_barang, String harga_barang, String harga_reseller, String kategori, String id, String deskripsi, String diskon) {
        this.barang = barang;
        this.id_kategori = id_kategori;
        this.nama = nama;
        this.kode_barang = kode_barang;
        this.harga_barang = harga_barang;
        this.harga_reseller = harga_reseller;
        this.kategori = kategori;
        this.id = id;
        this.deskripsi = deskripsi;
        this.diskon = diskon;
    }

    public String getHarga_reseller() {
        return harga_reseller;
    }

    public void setHarga_reseller(String harga_reseller) {
        this.harga_reseller = harga_reseller;
    }

    public String getBarang() {
        return barang;
    }

    public void setBarang(String barang) {
        this.barang = barang;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKode_barang() {
        return kode_barang;
    }

    public void setKode_barang(String kode_barang) {
        this.kode_barang = kode_barang;
    }

    public String getHarga_barang() {
        return harga_barang;
    }

    public void setHarga_barang(String harga_barang) {
        this.harga_barang = harga_barang;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDiskon() {
        return diskon;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }
}
