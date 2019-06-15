package project.com.tastore;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.util.List;

import project.com.tastore.Config.env;
import project.com.tastore.Konstruktor.Json_Setting;
import project.com.tastore.Konstruktor.User_info;
import project.com.tastore.Model.Model;
import project.com.tastore.Model.Respon_Setting;
import project.com.tastore.User.Login;
import project.com.tastore.User.User_config;
import project.com.tastore.fragment.Beranda;
import project.com.tastore.fragment.Cari_Barang;
import project.com.tastore.fragment.Keranjang;
import project.com.tastore.fragment.Profile;
import project.com.tastore.fragment.Transaksi;
import project.com.tastore.fragment.katalog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
private BottomNavigationView menubawah;
private Toolbar toolbar;
private List<Json_Setting> settingList;
private CharSequence nope[];
private  String pilihan;
private Context context;
private env db=new env();
private  boolean doublePress=false;
public static  boolean aktif=false;
    //private TapTargetSequence sequence;
private TapTargetSequence sequence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_atas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        menubawah=(BottomNavigationView) findViewById(R.id.menu_bawah);
       // BottomNavigationViewHelper.disableShiftMode(menubawah);
        menubawah.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        menubawah.setOnNavigationItemSelectedListener(MainActivity.this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, Beranda.newInstance())
                .commit();

        //simpan intro
        SharedPreferences sharedPreferences=this.getSharedPreferences("INTRO", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        sequence = new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(menubawah.findViewById(R.id.katalog), "Halaman Beranda", "Tap Disini Untuk melihat Katalog Barang Dan Jenis Barang (Tap Untuk Refresh )")
                                .outerCircleColor(R.color.colorPrimary)
                                .transparentTarget(true),
                        TapTarget.forView(menubawah.findViewById(R.id.baru), "Halaman Barang Baru", "Tap Disini Untuk melihat Update 100 Barang Terbaru (Tap Untuk Refresh )")
                                .outerCircleColor(R.color.merah)
                                .transparentTarget(true),
                        TapTarget.forView(menubawah.findViewById(R.id.pemesanan), "Halaman Pemesanan", "Tap Disini Untuk melihat Barang Yang Telah Dipesan Dan Melakukan Pembelian (Tap Untuk Refresh )")
                                .outerCircleColor(R.color.hijauWA)
                                .transparentTarget(true),
                        TapTarget.forView(menubawah.findViewById(R.id.transaksi), "Halaman Terbayar", "Tap Disini Untuk melihat Barang Yang telah anda Beli , Silahkan Konfirmasi Terima Bila Sudah menerima Barang (Tap Untuk Refresh )")
                                .outerCircleColor(R.color.colorAccent)
                                .transparentTarget(true),
                        TapTarget.forView(menubawah.findViewById(R.id.akun), "Halaman Profile", "Tap Disini Untuk melihat Profile Dan setting akun Anda (Tap Untuk Refresh )")
                                .outerCircleColor(R.color.abugelap)
                                .transparentTarget(true),
                        TapTarget.forToolbarMenuItem(toolbar, R.id.login, "Ke Halaman Login", "Tap Disini Untuk Login, Login Dulu Untuk Melakukan Transaksi")
                                .outerCircleColor(R.color.kuning)
                                .transparentTarget(true),
                        TapTarget.forToolbarMenuItem(toolbar, R.id.chat, "Ke Halaman Bantuan", "Tap Disini Jika Anda Ingin Bertanya Kepada Admin")
                                .outerCircleColor(R.color.flic)
                                .transparentTarget(true),
                        TapTarget.forToolbarMenuItem(toolbar, R.id.cari, "Ke Halaman Cari ", "Tap Disini Jika Anda Ingin Mencari Barang yang Anda Inginkan")
                                .outerCircleColor(R.color.colorPrimaryDark)
                                .transparentTarget(true)

                ).listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        editor.putBoolean("finished", true);
                        editor.commit();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        editor.putBoolean("finished", true);
                        editor.commit();
                    }

                });
        boolean isintro_finish = sharedPreferences.getBoolean("finished", false);
        if (!isintro_finish) {
            sequence.start();
        }

        //permission v21 up
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.INTERNET,Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS};
        if(!hasPermissions(MainActivity.this, PERMISSIONS)){
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cari:
                Intent intent=new Intent(this, Cari_Barang.class);
                startActivity(intent);
                break;
            case R.id.chat:
                boolean inswa=instaledWap("com.whatsapp");
                if(!inswa){
                    Toast.makeText(this, "Anda Harus Install Whatsapp Dulu!", Toast.LENGTH_SHORT).show();
                }else {
                    String sendWa = "*Saya Perlu Bantuan*";
                    pilihNope(sendWa);
                    break;
                }
            case R.id.login:
                    if(User_config.getmInstance(MainActivity.this).isLogedIn()){
                        User_config.getmInstance(MainActivity.this).logout();
                        finish();
                        startActivity(new Intent(MainActivity.this, Login.class));
                    }else{
                        finish();
                        startActivity(new Intent(MainActivity.this, Login.class));
                    }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void pilihNope(final String sendWa) {
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.show();
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Setting> call=model.vsetting();
        call.enqueue(new Callback<Respon_Setting>() {
            @Override
            public void onResponse(Call<Respon_Setting> call, Response<Respon_Setting> response) {
                if(response.isSuccessful()){
                    dialog.dismiss();
                    settingList=response.body().getData();
                    nope=new String[settingList.size()];
                    for (int i=0;i<settingList.size();i++){
                        nope[i]=settingList.get(i).getNama();
                    }
                    final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Pilih Nomer Untuk mengirim Pesan");
                    builder.setSingleChoiceItems(nope, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String tlp=settingList.get(which).getTelp();
                            String admin=settingList.get(which).getNama();
                            CheckNumberPhone(admin,tlp,sendWa);
                            dialog.cancel();
                        }
                    });
                    builder.create();
                    builder.show();
                }
            }

            @Override
            public void onFailure(Call<Respon_Setting> call, Throwable throwable) {
                dialog.dismiss();
                //Toast.makeText(context, String.valueOf(throwable), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CheckNumberPhone(final String admin, final String tlp, final String sendWa) {
        Uri uri=Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(tlp));
        String[] phoneNum={ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur=getContentResolver().query(uri,phoneNum,null,null,null);
        try {
            if (cur.moveToFirst()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Pesan Dikirim Ke Whatsap Admin")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                User_info userIN=User_config.getmInstance(MainActivity.this).getUser();
                                String iduser = userIN.getId();
                                String nama = userIN.getNama();
                                //Transaksi
                                Intent sendIntent = new Intent("android.intent.action.MAIN");
                                sendIntent.setAction(Intent.ACTION_VIEW);
                                sendIntent.setPackage("com.whatsapp");
                                String url = "https://api.whatsapp.com/send?phone=" + tlp + "&text=" + sendWa;
                                sendIntent.setData(Uri.parse(url));
                                startActivity(sendIntent);

                            }
                        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setTitle("Perhatian!");
                builder.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Nomer Belum Ada Pada Kontak, Tambahkan Nomer? \n (Tekan lagi Mengirim Pesan)")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_INSERT);
                                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                                intent.putExtra(ContactsContract.Intents.Insert.NAME, admin);
                                intent.putExtra(ContactsContract.Intents.Insert.PHONE, tlp);
                                startActivity(intent);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setTitle("Info");
                builder.show();
            }
        }finally {
            if(cur!=null){
                //Toast.makeText(getActivity(),"Not Found",Toast.LENGTH_SHORT).show();
                cur.close();
            }
        }
    }

    //cek wa Installed
    private boolean instaledWap(String uri){
        PackageManager manager=MainActivity.this.getPackageManager();
        boolean instaledWa;
        try {
            manager.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            instaledWa=true;
        }catch (PackageManager.NameNotFoundException e){
            instaledWa=false;
        }
        return instaledWa;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_atas,menu);
            MenuItem mnLogin=menu.findItem(R.id.login);
            if(User_config.getmInstance(MainActivity.this).isLogedIn()){
                mnLogin.setTitle("Logout");
            }else{
                mnLogin.setTitle("login");
            }
        return super.onCreateOptionsMenu(menu);

    }
    public Fragment swapFragment(){
        Fragment fragment=Beranda.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();
        return  fragment;

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment=null;
        switch (menuItem.getItemId()){
            case R.id.katalog:
                fragment=Beranda.newInstance();
                pilihan="0";
                break;
            case R.id.baru:
                fragment= katalog.newInstance();
                pilihan="1";
                break;
            case R.id.pemesanan:
                fragment= Keranjang.newInstance();
                pilihan="2";
                break;
            case R.id.transaksi:
                fragment= Transaksi.newInstance();
                pilihan="3";
                break;
            case R.id.akun:
                fragment= Profile.newInstance();
                pilihan="4";
                break;
            default:
                fragment=Beranda.newInstance();
                break;
        }
        getSupportFragmentManager().beginTransaction()
        .addToBackStack(pilihan)
                .replace(R.id.frame,fragment,pilihan).commit();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        aktif=false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        aktif=true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String ide="";
        FragmentManager fragmentManager=getSupportFragmentManager();
        for(int i=0;i<fragmentManager.getBackStackEntryCount();i++){
            ide=fragmentManager.getBackStackEntryAt(i).getName();
        }
        if(ide.equals("0")){
            menubawah.getMenu().getItem(0).setChecked(true);
            if(doublePress){
                this.doublePress=true;
                Toast.makeText(MainActivity.this,"Tekan Sekali Lagi Untuk Keluar",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doublePress=false;
                    }
                },3000);
            }
        }else if(ide.equals("1")){
            menubawah.getMenu().getItem(1).setChecked(true);
        }else if(ide.equals("2")){
            menubawah.getMenu().getItem(2).setChecked(true);
        }else if(ide.equals("3")){
            menubawah.getMenu().getItem(3).setChecked(true);
        }else if(ide.equals("4")){
            menubawah.getMenu().getItem(4).setChecked(true);
        }else{
            menubawah.getMenu().getItem(0).setChecked(true);
            if(doublePress){
                super.onBackPressed();
                return;
            }
            this.doublePress=true;
            Toast.makeText(MainActivity.this,"Tekan Sekali Lagi Untuk Keluar",Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doublePress=false;
                }
            },3000);
        }

    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
