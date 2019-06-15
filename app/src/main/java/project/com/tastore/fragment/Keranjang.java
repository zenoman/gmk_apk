package project.com.tastore.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import project.com.tastore.Config.env;
import project.com.tastore.Konstruktor.Alamat_tujuan;
import project.com.tastore.Konstruktor.Json_Bank;
import project.com.tastore.Konstruktor.Json_Setting;
import project.com.tastore.Konstruktor.Json_keranjang;
import project.com.tastore.Konstruktor.User_info;
import project.com.tastore.Model.Model;
import project.com.tastore.Model.Respon_Bank;
import project.com.tastore.Model.Respon_Keranjang;
import project.com.tastore.Model.Respon_Setting;
import project.com.tastore.R;
import project.com.tastore.User.User_config;
import project.com.tastore.adapter.Adapter_Keranjang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Keranjang extends Fragment {
    private LinearLayout ly_alamat,ly_pil,ly_bayar,ly_lanjut;
    private RelativeLayout empty;
    private RecyclerView rv;
    private List<Json_keranjang> list;
    private List<Json_Setting> settingList;
    private List<Json_Bank> bankList;
    private GridLayoutManager manager;
    private Adapter_Keranjang adapter_keranjang;
    private TextView tv_total;
    private Button belanja;
    private String iduser;
    private env db=new env();
    private String total;
    Context context;
    private RadioButton rd_pil;
    private RadioGroup Rg;
    private String alamat_lengkap,kodb="",mtd="";
    private  CharSequence[] nope;
    private EditText nama,alamt,kota,pos,prov;
    private  User_info info;
    private Alamat_tujuan tujuan;
    private MaterialSpinner metod,rek;


    public Keranjang() {
        // Required empty public constructor
    }
    public static Keranjang newInstance(){
        return new Keranjang();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context context=getActivity();

        View view= inflater.inflate(R.layout.fragment_keranjang, container, false);
            info=User_config.getmInstance(getContext()).getUser();
            tujuan=User_config.getmInstance(getContext()).getTujuan();
            empty=(RelativeLayout) view.findViewById(R.id.empty);
            tv_total=(TextView) view.findViewById(R.id.total);
            belanja=(Button) view.findViewById(R.id.btn_trans);
            rv=(RecyclerView) view.findViewById(R.id.rv);
            Rg=(RadioGroup) view.findViewById(R.id.rg);
            metod=(MaterialSpinner) view.findViewById(R.id.metod);
            rek=(MaterialSpinner) view.findViewById(R.id.rek);

            //Alamat Tujuan
            nama=(EditText) view.findViewById(R.id.nama);
            alamt=(EditText) view.findViewById(R.id.alamat);
            kota=(EditText) view.findViewById(R.id.kota);
            pos=(EditText) view.findViewById(R.id.pos);
            prov=(EditText) view.findViewById(R.id.provinsi);
            //
            ly_alamat=(LinearLayout) view.findViewById(R.id.ly_alamat_lain);
            ly_bayar=(LinearLayout) view.findViewById(R.id.ly_bayar);
            ly_pil=(LinearLayout) view.findViewById(R.id.ly_pil_alamat);
            ly_lanjut=(LinearLayout) view.findViewById(R.id.ly_lanjut);
            ly_lanjut.setVisibility(View.GONE);
            ly_alamat.setVisibility(View.GONE);
            ly_pil.setVisibility(View.GONE);
            final int selectedID=Rg.getCheckedRadioButtonId();
            rd_pil=(RadioButton) view.findViewById(selectedID);
            Rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId==R.id.alamat_drop){
                        ly_alamat.setVisibility(View.VISIBLE);
                        alamat_lengkap="Nama : "+tujuan.getNama()+"Alamat : "+tujuan.getAlamat()+" " +tujuan.getKodepos()+"\n"+tujuan.getKota()+" "+tujuan.getProvinsi();
                    }else if(checkedId==R.id.alamat_asli){
                        ly_alamat.setVisibility(View.GONE);
                        alamat_lengkap="Nama : "+info.getNama()+"Alamat : "+info.getAlamat()+" " +info.getKodepos()+"\n"+info.getKota()+" "+info.getProvinsi();
                    }
                }
            });
            //set Spinner
            final List<String> lsmt=new ArrayList<>();
            lsmt.add("pesan");
            lsmt.add("Ambil di Toko");
            ArrayAdapter<String> adapter_mt=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,lsmt);
            adapter_mt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            metod.setAdapter(adapter_mt);
            metod.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    //Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                    mtd=item.toString();
                    if(mtd.equals("pesan")){
                        mtd="pesan";
                        ly_bayar.setVisibility(View.VISIBLE);
                    }else{
                        mtd="langsung";
                        ly_bayar.setVisibility(View.GONE);
                    }

                }
            });
            rek.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    for(int i=0;i<bankList.size();i++){
                        kodb=bankList.get(position).getId();
                    }
                }
            });

        adapter_keranjang=new Adapter_Keranjang(context);
            manager=new GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false);
            rv.setAdapter(adapter_keranjang);
            rv.setLayoutManager(manager);
            rv.setItemAnimator(new DefaultItemAnimator());
            if(User_config.getmInstance(context).isLogedIn()){
                showBelanja();
                showTotal();
            }else{
                empty.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);

            }
        vTujuan();
        vBank();
            belanja.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int ceckedI=Rg.getCheckedRadioButtonId();
                    if(mtd.equals("")){
                        
                    }else{
                        if(ceckedI==R.id.alamat_drop){
                            saveDrop();
                            boolean inWa=instaledWap("com.whatsapp");
                            if(inWa==true){
                                vTujuan();
                                alamat_lengkap=" "+tujuan.getNama()+"  "+tujuan.getAlamat()+"  " +tujuan.getKodepos()+"  " +tujuan.getKota()+"   "+tujuan.getProvinsi();
                                if(mtd.equals("pesan")){
                                    if(kodb.equals("")){
                                        Toast.makeText(getContext(), "Pilih Bank Dahulu", Toast.LENGTH_SHORT).show();
                                    }else{
                                        getDetail();
                                    }
                                }else{
                                    kodb="";
                                    getDetail();
                                }

                            }else{
                                Toast.makeText(getContext(), "Anda Belum Install Whatsapp", Toast.LENGTH_SHORT).show();
                            }
                        }else if(ceckedI==R.id.alamat_asli){
                            boolean inWa=instaledWap("com.whatsapp");
                            if(inWa==true){
                                if(User_config.getmInstance(getContext()).iscekNama()||User_config.getmInstance(getContext()).iscekAlamat()){
                                    alamat_lengkap="Nama : "+info.getNama()+" Alamat : "+info.getAlamat()+" " +info.getKodepos() + " Kota : " + info.getKota()+" Provinsi: "+info.getProvinsi();
                                    if(mtd.equals("pesan")){
                                        if(kodb.equals("")){
                                            Toast.makeText(getContext(), "Pilih Bank Dahulu", Toast.LENGTH_SHORT).show();
                                        }else{
                                            getDetail();
                                        }
                                    }else{
                                        kodb="";
                                        getDetail();
                                    }
                                }else{
                                    Toast.makeText(getContext(), "Anda Harus Melengkapi Profile Dulu", Toast.LENGTH_SHORT).show();
                                }
                               
                            }
                        }
                    }

                }
            });

        return view;
    }

    private void vBank() {
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Bank> call=model.vBank();
        call.enqueue(new Callback<Respon_Bank>() {
            @Override
            public void onResponse(Call<Respon_Bank> call, Response<Respon_Bank> response) {
                if(response.isSuccessful()){
                    List<String> ls=new ArrayList<>();
                    bankList=response.body().getData();
                    for(int i=0; i<bankList.size();i++){
                        ls.add(bankList.get(i).getNama_bank());
                    }
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,ls);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    rek.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Respon_Bank> call, Throwable throwable) {
                Log.e("error",String.valueOf(throwable));
            }
        });
    }

    private void saveDrop() {
        final String nm,al,kt,ps,pro,id;
         info=User_config.getmInstance(getContext()).getUser();
         tujuan=User_config.getmInstance(getContext()).getTujuan();
        nm=nama.getText().toString();
        al=alamt.getText().toString();
        kt=kota.getText().toString();
        ps=pos.getText().toString();
        pro=prov.getText().toString();
        id=info.getId();
        if(TextUtils.isEmpty(nm)){
            nama.setError("Harap Diisi");
            nama.requestFocus();
            return;
        }else if(TextUtils.isEmpty(al)){
            alamt.setError("Harap Diisi");
            alamt.requestFocus();
            return;
        }else if(TextUtils.isEmpty(kt)){
            kota.setError("Harap Diisi");
            kota.requestFocus();
            return;
        }else if(TextUtils.isEmpty(ps)){
            pos.setError("Harap Diisi");
            pos.requestFocus();
            return;
        }else if(TextUtils.isEmpty(pro)){
           prov.setError("Harap Diisi");
           prov.requestFocus();
            return;
        }
        Alamat_tujuan saveTujuan=new Alamat_tujuan(nm,al,kt,pro,ps);
        User_config.getmInstance(getContext()).usertujuan(saveTujuan);
    }
    public void vTujuan(){
      if(  User_config.getmInstance(getContext()).iscekTujuan()){
          nama.setText(tujuan.getNama());
          alamt.setText(tujuan.getAlamat());
          kota.setText(tujuan.getKota());
          pos.setText(tujuan.getKodepos());
          prov.setText(tujuan.getProvinsi());
      }
    }
    private void getDetail() {
        User_info info=User_config.getmInstance(getContext()).getUser();
        Alamat_tujuan infot=User_config.getmInstance(getContext()).getTujuan();
        String[] lspesan=new String[list.size()];
        String kirimPesan="";
        String SendWa="";
        for(int i=0;i<list.size();i++){
            lspesan[i]="Kode Barang : "+list.get(i).getKode_barang()+"\n Barang : "+ list.get(i).getBarang()+ "\n Varian : " + list.get(i).getVarian() + " - " + list.get(i).getWarna() +"\n Jumlah : " + list.get(i).getJumlah()+"\n";
            kirimPesan+=lspesan[i];
        }
        SendWa=alamat_lengkap + "\n Data Pembelian: " + "\n" + kirimPesan +"\n";
        pilihNope(SendWa);

    }

    private void pilihNope(final String sendWa) {
        final Dialog dialog=new Dialog(getContext());
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
                    final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                    builder.setTitle("Pilih Nomer Untuk mengirim Detail Pesan");
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
        Cursor cur=getActivity().getContentResolver().query(uri,phoneNum,null,null,null);
     try {
         if (cur.moveToFirst()) {
             AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
             builder.setMessage("Pesan Dikirim Ke Whatsap Admin")
                     .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             User_info userIN=User_config.getmInstance(getActivity()).getUser();
                             String iduser = userIN.getId();
                             String nama = userIN.getNama();
                             //Transaksi
                             Intent sendIntent = new Intent("android.intent.action.MAIN");
                             sendIntent.setAction(Intent.ACTION_VIEW);
                             sendIntent.setPackage("com.whatsapp");
                             String url = "https://api.whatsapp.com/send?phone=" + tlp + "&text=" + sendWa;
                             sendIntent.setData(Uri.parse(url));
                             startActivity(sendIntent);
                             transaksi();
                         }
                     }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                 }
             });
             builder.setTitle("Perhatian!");
             builder.show();
         } else {
             AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
             builder.setMessage("Nomer Belum Ada Pada Kontak, Tambahkan Nomer? \n (Tekan lagi Mengirim Pesan)")
                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {
                             Intent intent = new Intent(Intent.ACTION_INSERT);
                             intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                             intent.putExtra(ContactsContract.Intents.Insert.NAME, admin);
                             intent.putExtra(ContactsContract.Intents.Insert.PHONE, tlp);
                             getContext().startActivity(intent);

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

    private void transaksi() {
        info=User_config.getmInstance(getContext()).getUser();
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Keranjang> call=model.transaksi(info.getId(),tv_total.getText().toString(),alamat_lengkap,kodb,mtd);
        call.enqueue(new Callback<Respon_Keranjang>() {
            @Override
            public void onResponse(Call<Respon_Keranjang> call, Response<Respon_Keranjang> response) {
                if(response.isSuccessful()){
                    String psn=response.body().getPesan();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Transaksi Berhasil,Lihat Pada Menu Transaksi,\n Bagi Pemesanan Total Menunggu Ongkir")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,Keranjang.newInstance()).commit();
                                }
                            });
                    builder.setTitle("Info");
                    builder.show();

                }
            }

            @Override
            public void onFailure(Call<Respon_Keranjang> call, Throwable throwable) {
                Log.e("error",String.valueOf(throwable));
            }
        });
    }

    //cek wa Installed
    private boolean instaledWap(String uri){
        PackageManager manager=getActivity().getPackageManager();
        boolean instaledWa;
        try {
            manager.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            instaledWa=true;
        }catch (PackageManager.NameNotFoundException e){
            instaledWa=false;
        }
        return instaledWa;
    }
    //
    public void showTotal() {
        User_info userInfo=User_config.getmInstance(context).getUser();
        String iduser=userInfo.getId();
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Keranjang> call=model.vtotalK(iduser);
        call.enqueue(new Callback<Respon_Keranjang>() {
            @Override
            public void onResponse(Call<Respon_Keranjang> call, Response<Respon_Keranjang> response) {
                if(response.isSuccessful()){
                    try {
                        total = response.body().getTotal();
                        if (total.equals("")) {
                            tv_total.setText("0");
                        } else {
                            tv_total.setText(total);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<Respon_Keranjang> call, Throwable throwable) {
               // Toast.makeText(getContext(), String.valueOf(throwable), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showBelanja() {
        User_info user_info= User_config.getmInstance(context).getUser();
        final Dialog dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_loading);
        dialog.show();
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Keranjang> call=model.viewKeranjang(user_info.getId());
        call.enqueue(new Callback<Respon_Keranjang>() {
            @Override
            public void onResponse(Call<Respon_Keranjang> call, Response<Respon_Keranjang> response) {
                dialog.dismiss();
                if(response.isSuccessful()){
                    list=response.body().getData();
                    adapter_keranjang.addAll(list);
                    if(adapter_keranjang.getItemCount()==0){
                       empty.setVisibility(View.VISIBLE);
                       ly_pil.setVisibility(View.GONE);
                      ly_lanjut.setVisibility(View.GONE);
                    }else{
                        ly_pil.setVisibility(View.VISIBLE);
                        ly_lanjut.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Respon_Keranjang> call, Throwable throwable) {
                dialog.dismiss();
               // Toast.makeText(getContext(), String.valueOf(throwable), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
