package project.com.gmklabel.Konstruktor;

public class Json_gambar {
    private String id,kode_barang,nama;

    public Json_gambar(String id, String kode_barang, String nama) {
        this.id = id;
        this.kode_barang = kode_barang;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode_barang() {
        return kode_barang;
    }

    public void setKode_barang(String kode_barang) {
        this.kode_barang = kode_barang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
