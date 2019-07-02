package project.com.gmklabel.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import project.com.gmklabel.Config.env;
import project.com.gmklabel.Konstruktor.Json_Produk;
import project.com.gmklabel.R;
import project.com.gmklabel.fragment.Detail_Barang;
import project.com.gmklabel.fragment.zoom_image;

public class Adapter_Image extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
private List<Json_Produk> gambarList=new ArrayList<>();
 Detail_Barang context;
private env db=new env();

    public Adapter_Image(Context context) {
        this.context = (Detail_Barang) context;
        this.gambarList=gambarList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_gambar,viewGroup,false);
        return new GambarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,final int i) {
        final GambarHolder holde=(GambarHolder) viewHolder;
        holde.tvnam.setText(gambarList.get(i).getNama());
        holde.btn_gzoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Bundle bundle=new Bundle();
             String gambar=db.barang_url+gambarList.get(i).getNama();
             bundle.putString("image",gambar);
             zoom_image imgz=new zoom_image();
             imgz.setArguments(bundle);
             imgz.show(holde.fm,"zoom");
                Dialog dialog=new Dialog(context);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });
        Glide.with(context).load(db.barang_url+gambarList.get(i).getNama()).into(holde.img);
    }

    @Override
    public int getItemCount() {
        return gambarList.size();
    }

    private class GambarHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvnam;
        ImageButton btn_gzoom;
        FragmentManager fm;
        public GambarHolder(View view) {
            super(view);
            img=(ImageView) view.findViewById(R.id.image);
            fm=context.getSupportFragmentManager();
            tvnam=(TextView) view.findViewById(R.id.nama);
            btn_gzoom=(ImageButton) view.findViewById(R.id.btn_zoom);
        }
    }
    public void add(Json_Produk json_gambar){
        gambarList.add(json_gambar);
        notifyItemInserted(gambarList.size());
    }
    public void addAll(List<Json_Produk> json_gambars){
        for(Json_Produk json_gambar:json_gambars){
            add(json_gambar);
        }
    }
}
