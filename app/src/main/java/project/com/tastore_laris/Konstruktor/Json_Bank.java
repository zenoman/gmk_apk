package project.com.tastore_laris.Konstruktor;

public class Json_Bank {
    private String id,nama_bank,rekening,atasnama;

    public Json_Bank(String id, String nama_bank, String rekening, String atasnama) {
        this.id = id;
        this.nama_bank = nama_bank;
        this.rekening = rekening;
        this.atasnama = atasnama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_bank() {
        return nama_bank;
    }

    public void setNama_bank(String nama_bank) {
        this.nama_bank = nama_bank;
    }

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public String getAtasnama() {
        return atasnama;
    }

    public void setAtasnama(String atasnama) {
        this.atasnama = atasnama;
    }
}
