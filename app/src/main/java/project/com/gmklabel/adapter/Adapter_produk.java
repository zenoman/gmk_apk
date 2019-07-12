package project.com.gmklabel.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import project.com.gmklabel.Config.env;
import project.com.gmklabel.Konstruktor.Json_Produk;
import project.com.gmklabel.Konstruktor.User_info;
import project.com.gmklabel.R;
import project.com.gmklabel.User.User_config;
import project.com.gmklabel.fragment.Detail_Barang;

public class Adapter_produk extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private List<Json_Produk> produkList=new ArrayList<>();
    private env db=new env();


    public Adapter_produk(Context context) {
        this.context = context;
        this.produkList=produkList;
    }
    public void ClearFilter(List<Json_Produk> list){
        produkList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_produk,viewGroup,false);
        return new ProdukHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ProdukHolder holder=(ProdukHolder) viewHolder;
       final Double hg;
        holder.tv_kode.setText(produkList.get(i).getKode_barang());
        holder.tv_barang.setText(produkList.get(i).getBarang());
        holder.url.setText(env.barang_url+produkList.get(i).getNama());
        holder.desk=produkList.get(i).getDeskripsi();
        Glide.with(context).load(db.barang_url+produkList.get(i).getNama()).into(holder.img);
        try {
            if (User_config.getmInstance(context).isLogedIn()) {

                if (holder.info.getLevel().equals("reseller")) {
                    hg = Double.parseDouble(produkList.get(i).getHarga_reseller());
                } else {
                    hg = Double.parseDouble(produkList.get(i).getHarga_barang());
                }
            } else {
                hg = Double.parseDouble(produkList.get(i).getHarga_barang());
            }
            // DecimalFormat frm=(DecimalFormat) DecimalFormat.getCurrencyInstance();
            // DecimalFormatSymbols frmatRp=new DecimalFormatSymbols();
            // frmatRp.setCurrencySymbol("Rp. ");
            // frmatRp.setMonetaryDecimalSeparator(',');
            // frmatRp.setGroupingSeparator('.');
            // frm.setDecimalFormatSymbols(frmatRp);
            // final String crFormat=frm.format(hg);
            final DecimalFormat dm = new DecimalFormat("###,###");
            holder.diskon = produkList.get(i).getDiskon();
            holder.tv_harga.setText(dm.format(hg));

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Detail_Barang.class);
                intent.putExtra("kode",holder.tv_kode.getText().toString());
                intent.putExtra("desk",holder.desk.toString());
                intent.putExtra("barang",holder.tv_barang.getText().toString());
                intent.putExtra("harga",produkList.get(i).getHarga_barang().toString());
                intent.putExtra("diskon",holder.diskon.toString());
                intent.putExtra("hargaf",dm.format(hg));
                context.startActivity(intent);
            }
        });
        holder.beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Detail_Barang.class);
                intent.putExtra("kode",holder.tv_kode.getText().toString());
                intent.putExtra("desk",holder.desk.toString());
                intent.putExtra("barang",holder.tv_barang.getText().toString());
                intent.putExtra("harga",produkList.get(i).getHarga_barang().toString());
                intent.putExtra("diskon",holder.diskon.toString());
                intent.putExtra("hargaf",dm.format(hg));
                context.startActivity(intent);
            }
        });
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    private class ProdukHolder extends RecyclerView.ViewHolder {
        private TextView tv_kode,tv_barang,tv_harga,url;
        private ImageView img;
        private Button beli;
        private ImageButton download,share;
        private String desk,diskon,image_share,level;
        private User_info info=User_config.getmInstance(context).getUser();

        public ProdukHolder(View view) {
            super(view);
            tv_kode=(TextView) view.findViewById(R.id.kode);
            tv_barang=(TextView) view.findViewById(R.id.barang);
            tv_harga=(TextView) view.findViewById(R.id.harga);
            img=(ImageView) view.findViewById(R.id.image);
            beli=(Button) view.findViewById(R.id.Beli);
            download=(ImageButton) view.findViewById(R.id.down);
            share=(ImageButton) view.findViewById(R.id.share);
            url=(TextView) view.findViewById(R.id.link_image);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareImage();
                }
            });
            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadImage();
                }
            });
        }

        private void downloadImage() {
            final Dialog dialog=new Dialog(context);
            dialog.setContentView(R.layout.dialog_loading);
            dialog.show();
            Glide.with(context).asBitmap().load(url.getText().toString()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    File sdcard=Environment.getExternalStorageDirectory();
                    File dirk=new File(sdcard+"/GMK");
                    if(!dirk.exists()) {
                        dirk.mkdirs();
                    }
                    File image_ku=new File(sdcard+"/GMK/"+"GMK"+System.currentTimeMillis()+".jpg");
                    File dir=image_ku.getParentFile();
                    try{
                        if(!dir.mkdirs()&&(!dir.exists()||!dir.isDirectory())){
                            new IOException("Tidak Bisa Disimpan "+ image_ku);
                        }
                        FileOutputStream s=new FileOutputStream(image_ku);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,s);
                        s.flush();
                        s.close();
                        ContentValues contentValues=new ContentValues();
                        contentValues.put(MediaStore.Images.Media.MIME_TYPE,"image/*");
                        contentValues.put(MediaStore.MediaColumns.DATA,image_ku.getAbsolutePath());
                        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
                        dialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Gambar Berhasil Di Download Cari Di Galeri atau Folder GMK Memori Anda")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setTitle("info");
                        alertDialog.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }

        private void ShareImage() {
            final Dialog dialog=new Dialog(context);
            dialog.setContentView(R.layout.dialog_loading);
            dialog.show();
            final  String data="Kode Barang : "+tv_kode.getText()+"\n"+"Barang : "+tv_barang.getText()+"\n"+"Harga : "+tv_harga.getText();
            Glide.with(context).asBitmap().load(url.getText().toString()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    File sdcard=Environment.getExternalStorageDirectory();
                    File dirk=new File(sdcard+"/GMK");
                    if(!dirk.exists()) {
                        dirk.mkdirs();
                    }
                    File image_ku=new File(sdcard+"/GMK/"+"GMK"+System.currentTimeMillis()+".jpg");
                    File dir=image_ku.getParentFile();
                    try{
                        if(!dir.mkdirs()&&(!dir.exists()||!dir.isDirectory())){
                            new IOException("Tidak Bisa Disimpan "+ image_ku);
                        }
                        FileOutputStream s=new FileOutputStream(image_ku);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,s);
                        s.flush();
                        s.close();
                        ContentValues contentValues=new ContentValues();
                        contentValues.put(MediaStore.Images.Media.MIME_TYPE,"image/*");
                        contentValues.put(MediaStore.MediaColumns.DATA,image_ku.getAbsolutePath());
                        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(image_ku));
                    intent.putExtra(Intent.EXTRA_TEXT,data);
                    dialog.dismiss();
                    context.startActivity(Intent.createChooser(intent,"Kirim Via"));
                }
            });
        }
    }
    public void add(Json_Produk json_produk){
        produkList.add(json_produk);
        notifyItemInserted(produkList.size());
    }
    public  void addall(List<Json_Produk> jsonProduks){
        for(Json_Produk produk:jsonProduks){
            add(produk);
        }
    }
}
