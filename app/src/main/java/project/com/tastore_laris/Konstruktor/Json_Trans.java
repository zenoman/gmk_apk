package project.com.tastore_laris.Konstruktor;

public class Json_Trans {
    private String id,iduser,faktur,tgl,total,status,alamat_tujuan,total_akhir,ongkir,nama_bank,rekening,atasnama,metode;

    public Json_Trans(String id, String iduser, String faktur, String tgl, String total, String status, String alamat_tujuan, String total_akhir, String ongkir, String nama_bank, String rekening, String atasnama, String metode) {
        this.id = id;
        this.iduser = iduser;
        this.faktur = faktur;
        this.tgl = tgl;
        this.total = total;
        this.status = status;
        this.alamat_tujuan = alamat_tujuan;
        this.total_akhir = total_akhir;
        this.ongkir = ongkir;
        this.nama_bank = nama_bank;
        this.rekening = rekening;
        this.atasnama = atasnama;
        this.metode = metode;
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

    public String getMetode() {
        return metode;
    }

    public void setMetode(String metode) {
        this.metode = metode;
    }

    public String getOngkir() {
        return ongkir;
    }

    public void setOngkir(String ongkir) {
        this.ongkir = ongkir;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlamat_tujuan() {
        return alamat_tujuan;
    }

    public void setAlamat_tujuan(String alamat_tujuan) {
        this.alamat_tujuan = alamat_tujuan;
    }

    public String getTotal_akhir() {
        return total_akhir;
    }

    public void setTotal_akhir(String total_akhir) {
        this.total_akhir = total_akhir;
    }
}
