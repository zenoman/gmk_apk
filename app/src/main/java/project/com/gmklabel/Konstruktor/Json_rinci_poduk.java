package project.com.tastore_laris.Konstruktor;

public class Json_rinci_poduk {
    private String idbarang,kode,stok,warna,barang_jenis;

    public Json_rinci_poduk(String idbarang, String kode, String stok, String warna, String barang_jenis) {
        this.idbarang = idbarang;
        this.kode = kode;
        this.stok = stok;
        this.warna = warna;
        this.barang_jenis = barang_jenis;
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
