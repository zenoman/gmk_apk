package project.com.tastore_laris.adapter;

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
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import project.com.tastore_laris.Config.env;
import project.com.tastore_laris.Konstruktor.Json_Setting;
import project.com.tastore_laris.Konstruktor.Json_Trans;
import project.com.tastore_laris.Konstruktor.User_info;
import project.com.tastore_laris.MainActivity;
import project.com.tastore_laris.Model.Model;
import project.com.tastore_laris.Model.Respon_Setting;
import project.com.tastore_laris.R;
import project.com.tastore_laris.User.User_config;
import project.com.tastore_laris.fragment.Detail_transaksi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_Trans extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Json_Trans> list=new ArrayList<>();
    private List<Json_Setting> listS=new ArrayList<>();
    MainActivity context;
    private  CharSequence[] nope;
    private env db=new env();
    private String sendWa;

    public Adapter_Trans( Context context) {
        this.context = (MainActivity) context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_trans,viewGroup,false);
        return  new HolderTrans(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final HolderTrans holderTrans=(HolderTrans) viewHolder;
        holderTrans.id.setText(list.get(i).getId());

        holderTrans.faktur.setText(list.get(i).getFaktur());
        holderTrans.total.setText(list.get(i).getTotal());
        holderTrans.ongkir.setText(list.get(i).getOngkir());
        holderTrans.total_a.setText(list.get(i).getTotal_akhir());
        holderTrans.status.setText(list.get(i).getStatus());
        holderTrans.alamat.setText(Html.fromHtml(list.get(i).getAlamat_tujuan()));
        holderTrans.metode.setText(list.get(i).getMetode());
        holderTrans.tgl.setText(list.get(i).getTgl());
        holderTrans.tv_bank.setText(list.get(i).getRekening()+" a.n " + list.get(i).getAtasnama() + "(" + list.get(i).getNama_bank()+")");
        if(holderTrans.metode.getText().toString().equals("langsung")||holderTrans.status.getText().toString().equals("sukses")||holderTrans.status.getText().toString().equals("batal")||holderTrans.status.getText().toString().equals("ditolak")){
           holderTrans.konf.setVisibility(View.GONE);
        }
        holderTrans.konf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean inWa=instaledWap("com.whatsapp");
                if(!inWa){
                    Toast.makeText(context, "Anda Harus Menginstall Whatsapp", Toast.LENGTH_SHORT).show();
                }else {
                    sendWa = "Konfirmasi Pembayaran \n Faktur: *" + holderTrans.faktur.getText().toString() + "* \n Sertakan bukti foto atau screenshot Transfer \n";
                    pilihNope(sendWa);
                }
            }
        });
        holderTrans.lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                String fk=holderTrans.faktur.getText().toString();
                bundle.putString("fk",fk);
                Detail_transaksi detail_transaksi=new Detail_transaksi();
                detail_transaksi.setArguments(bundle);
                detail_transaksi.show(holderTrans.fm,"trans");
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    private class HolderTrans extends RecyclerView.ViewHolder {
        TextView id,faktur,total,status,alamat,ongkir,potongan,total_a,metode,tv_bank,tgl;
        Button konf,lihat;
        FragmentManager fm;
        public HolderTrans(View view) {
            super(view);
            fm=context.getSupportFragmentManager();
            id=(TextView) view.findViewById(R.id.id);
            faktur=(TextView) view.findViewById(R.id.faktur);
            total=(TextView) view.findViewById(R.id.total);
            ongkir=(TextView) view.findViewById(R.id.ongkir);
            potongan=(TextView) view.findViewById(R.id.potongan);
            total_a=(TextView) view.findViewById(R.id.total_ak);
            status=(TextView) view.findViewById(R.id.status);
            alamat=(TextView) view.findViewById(R.id.alamat);
            metode=(TextView) view.findViewById(R.id.metode);
            tv_bank=(TextView) view.findViewById(R.id.bank);
            tgl=(TextView) view.findViewById(R.id.tg);
            konf=(Button) view.findViewById(R.id.btn_konf);
            lihat=(Button) view.findViewById(R.id.btn_lihat);

        }
    }
    public void add(Json_Trans jsonTrans){
        list.add(jsonTrans);
        notifyItemInserted(list.size());
    }
    public void addAll(List<Json_Trans> jsonTrans){
        for(Json_Trans js:jsonTrans){
            add(js);
        }
    }
    //cek wa Installed
    private boolean instaledWap(String uri){
        PackageManager manager=context.getPackageManager();
        boolean instaledWa;
        try {
            manager.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            instaledWa=true;
        }catch (PackageManager.NameNotFoundException e){
            instaledWa=false;
        }
        return instaledWa;
    }
    private void pilihNope(final String sendWa) {
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.show();
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Setting> call=model.vsetting();
        call.enqueue(new Callback<Respon_Setting>() {
            @Override
            public void onResponse(Call<Respon_Setting> call, Response<Respon_Setting> response) {
                if(response.isSuccessful()){
                    dialog.dismiss();
                    listS=response.body().getData();
                    nope=new String[listS.size()];
                    for (int i=0;i<listS.size();i++){
                        nope[i]=listS.get(i).getNama();
                    }
                    final AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("Pilih Nomer Untuk mengirim Detail Pesan");
                    builder.setSingleChoiceItems(nope, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String tlp=listS.get(which).getTelp();
                            String admin=listS.get(which).getNama();
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
        Cursor cur=context.getContentResolver().query(uri,phoneNum,null,null,null);
        try {
            if (cur.moveToFirst()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Pesan Dikirim Ke Whatsap Admin")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                User_info userIN= User_config.getmInstance(context).getUser();
                                String iduser = userIN.getId();
                                String nama = userIN.getNama();
                                //Transaksi
                                Intent sendIntent = new Intent("android.intent.action.MAIN");
                                sendIntent.setAction(Intent.ACTION_VIEW);
                                sendIntent.setPackage("com.whatsapp");
                                String url = "https://api.whatsapp.com/send?phone=" + tlp + "&text=" + sendWa;
                                sendIntent.setData(Uri.parse(url));
                                context.startActivity(sendIntent);

                            }
                        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setTitle("Perhatian!");
                builder.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Nomer Belum Ada Pada Kontak, Tambahkan Nomer? \n (Tekan lagi Mengirim Pesan)")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_INSERT);
                                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                                intent.putExtra(ContactsContract.Intents.Insert.NAME, admin);
                                intent.putExtra(ContactsContract.Intents.Insert.PHONE, tlp);
                                context.startActivity(intent);

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

}
