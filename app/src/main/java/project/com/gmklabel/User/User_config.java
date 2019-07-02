package project.com.gmklabel.User;

import android.content.Context;
import android.content.SharedPreferences;

import project.com.gmklabel.Konstruktor.Alamat_tujuan;
import project.com.gmklabel.Konstruktor.User_info;

public class User_config {
    private static final String SHARED_PREF_NAME="PrefGmk";
    private static final String KEY_USERNAME="keyusername";
    private static final String KEY_PASSWORD="keypassword";
    private static final String KEY_ID="keyid";
    private static final String KEY_email="keyemail";
    private static final String KEY_nama="keynama";
    private static final String KEY_telp="keytelp";
    private static final String KEY_alamat="keyalamat";
    private static final String KEY_pos="pos";
    private static final String KEY_level="level";
    private static final String KEY_ktp="ktp";
    private static final String KEY_kota="kota";
    private static final String KEY_prov="provinsi";
    //Tujuan Baru
    private static final String KEY_namat="keynamat";
    private static final String KEY_alamatt="keyalamatt";
    private static final String KEY_postt="post";
    private static final String KEY_kotat="kotat";
    private static final String KEY_provt="provinsit";

    private static User_config mInstance;
    private static Context mCtx;

    private User_config(Context context){
        mCtx=context;
    }
    public static synchronized  User_config getmInstance(Context context){
        if(mInstance==null){
            mInstance=new User_config(context);
        }
        return mInstance;
    }
    public void usertujuan(Alamat_tujuan tujuan){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_namat,tujuan.getNama());
        editor.putString(KEY_alamatt,tujuan.getAlamat());
        editor.putString(KEY_kotat,tujuan.getKota());
        editor.putString(KEY_provt,tujuan.getProvinsi());
        editor.putString(KEY_postt,tujuan.getKodepos());
        editor.apply();
    }
    public Alamat_tujuan getTujuan(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new Alamat_tujuan(
                sharedPreferences.getString(KEY_namat,null),
                sharedPreferences.getString(KEY_alamatt,null),
                sharedPreferences.getString(KEY_kotat,null),
                sharedPreferences.getString(KEY_provt,null),
                sharedPreferences.getString(KEY_postt,null)
        );
    }
    public boolean iscekTujuan(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(KEY_namat,null)!=null;

    }

    public void userLogin(User_info user){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME,user.getUsername());
        editor.putString(KEY_PASSWORD,user.getPassword());
        editor.putString(KEY_email,user.getEmail());
        editor.putString(KEY_nama,user.getNama());
        editor.putString(KEY_telp,user.getTelp());
        editor.putString(KEY_alamat,user.getAlamat());
        editor.putString(KEY_kota,user.getKota());
        editor.putString(KEY_prov,user.getProvinsi());
        editor.putString(KEY_pos,user.getKodepos());
        editor.putString(KEY_level,user.getLevel());
        editor.putString(KEY_ktp,user.getKtp_gmb());
        editor.apply();
    }

    public boolean isLogedIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(KEY_USERNAME,null)!=null;

    }
    public boolean iscekNama(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_nama,null)!=null;
    }
    public boolean iscekAlamat(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_alamat,null)!=null;
    }
    public boolean isceKota(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_kota,null)!=null;
    }
    public boolean iscekPos(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_pos,null)!=null;
    }
    public boolean iscekProv(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_prov,null)!=null;
    }

    public User_info getUser(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return  new User_info(
                sharedPreferences.getString(KEY_ID,null),
                sharedPreferences.getString(KEY_USERNAME,null),
                sharedPreferences.getString(KEY_PASSWORD,null),
                sharedPreferences.getString(KEY_email,null),
                sharedPreferences.getString(KEY_telp,null),
                sharedPreferences.getString(KEY_nama,null),
                sharedPreferences.getString(KEY_alamat,null),
                sharedPreferences.getString(KEY_kota,null),
                sharedPreferences.getString(KEY_prov,null),
                sharedPreferences.getString(KEY_pos,null),
                sharedPreferences.getString(KEY_level,null),
                sharedPreferences.getString(KEY_ktp,null)
        );

    }

    public void logout(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}
