package project.com.gmklabel.Model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Model {
    //mengambil kategori
    @GET("listKategori")
    Call<Respon_Kategori> viewKategori();
    //mengambil Slider
    @GET("slider")
    Call<Respon_Promo> viewPromo();
    //mengambil Barang
    @GET("listBarang")
    Call<Respon_Produk> viewBarng(@Query("page") int id);
    //ambil gambar
    @GET("gambar_item/{id}")
    Call<Respon_Produk> viewgmb(@Path("id") String id);
    //ambil warna
    @GET("warna_item/{id}")
    Call<Respon_Produk> viewWarna(@Path("id") String id);
    //list Etalase
    @GET("listEtalase")
    Call<Respon_Produk> viewEta(@Query("page") int id);
    //getKategori
    @GET("kategori/{id}")
    Call<Respon_Produk> viewdetailK(@Path("id") String id,@Query("page") int page);
    //getKeranjang
    @GET("keranjang/{id}")
    Call<Respon_Keranjang> viewKeranjang(@Path("id") String id);
    //view TotalKeranjang
    @GET("totalk/{id}")
    Call<Respon_Keranjang> vtotalK(@Path("id") String id);
    @GET("setting")
    Call<Respon_Setting> vsetting();
    //ambil Bank
    @GET("bank")
    Call<Respon_Bank> vBank();
    @GET("vtrans/{id}")
    Call<Respon_Trans> vTrans(@Path("id") String id);
    //cari Produk
    @GET("CariProduk")
    Call<Respon_Produk> Cari(@Query("param") String param);
    //detail Transaksi
    @GET("detailTrans/{fk}")
    Call<Respon_Keranjang> DetailTrans(@Path("fk") String fk);
    //get Cvarian
    @GET("cvarian/{id}")
    Call<Respon_Cvarian> showCvarian(@Path("id") String id);
    //get Size
    @GET("csize/{id}/{kdv}")
    Call<Respon_Csize> showSize(@Path("id") String id,@Path("kdv") String kdv);
    //cek version
    @GET("version")
    Call<Respon_version> vver();
    //cek stok
    @GET("ceks/{id}")
    Call<Respon_Trans> ceks(@Path("id") String id);

    //Login Customer
    @FormUrlEncoded
    @POST("login")
    Call<Respon_User> userProfile(@Field("username") String username, @Field("password") String password );
    //Daftar Customer
    @FormUrlEncoded
    @POST("register")
    Call<Respon_User> register(@Field("username") String username,@Field("password") String pass,@Field("email") String email,@Field("telp") String telp);
    //Update Login,Pass
    @FormUrlEncoded
    @POST("updatePas")
    Call<Respon_User> updateLog(@Field("id") String id,@Field("username") String username,@Field("password") String pass,@Field("email") String email,@Field("telp") String telp);
    //Update Login,NPass
    @FormUrlEncoded
    @POST("updatePas")
    Call<Respon_User> updateLogN(@Field("id") String id,@Field("username") String username,@Field("email") String email,@Field("telp") String telp);
    //update Profile User
    @FormUrlEncoded
    @POST("updateLeng")
    Call<Respon_User> updateLeng(@Field("id") String id,@Field("nama") String nama,@Field("alamat") String alamt,@Field("kota") String kota,@Field("provinsi") String prov,@Field("kodepos") String kodepos);
    //Pesan Barang
    @FormUrlEncoded
    @POST("order")
    Call<Respon_Produk> orderBarang(@Field("kode_v") String kodev,@Field("idwarna") String idw,@Field("iduser") String idu,@Field("kode_barang") String kode,@Field("barang") String brng,@Field("harga") String hrg,@Field("jumlah") String jml,@Field("diskon") String dsk);
    //Hapus Keranjang
    @FormUrlEncoded
    @POST("hapusKeranjang")
    Call<Respon_Keranjang> hapusK(@Field("id") String id,@Field("idwarna") String idwarna,@Field("qty") String qty);
    //Transaksi
    @FormUrlEncoded
    @POST("transaksi")
    Call<Respon_Keranjang> transaksi(@Field("id") String id,@Field("total") String total ,@Field("alamat") String alamat ,@Field("bank") String  bank,@Field("mtd") String metod);
    //Cari Barang
    @FormUrlEncoded
    @POST("cari")
    Call<Respon_Produk> vCari(@Field("cari") String cari);


}

