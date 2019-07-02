package project.com.tastore_laris.Konstruktor;

public class Json_Cvarian {
    private String id,kode_v,varian,hex,idbarang,kode,stok,warna,barang_jenis;

    public Json_Cvarian(String id, String kode_v, String varian, String hex, String idbarang, String kode, String stok, String warna, String barang_jenis) {
        this.id = id;
        this.kode_v = kode_v;
        this.varian = varian;
        this.hex = hex;
        this.idbarang = idbarang;
        this.kode = kode;
        this.stok = stok;
        this.warna = warna;
        this.barang_jenis = barang_jenis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode_v() {
        return kode_v;
    }

    public void setKode_v(String kode_v) {
        this.kode_v = kode_v;
    }

    public String getVarian() {
        return varian;
    }

    public void setVarian(String varian) {
        this.varian = varian;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getIdbarang() {
        return idbarang;
    }

    public void setIdbarang(String idbarang) {
        this.idbarang = idbarang;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getBarang_jenis() {
        return barang_jenis;
    }

    public void setBarang_jenis(String barang_jenis) {
        this.barang_jenis = barang_jenis;
    }
}
