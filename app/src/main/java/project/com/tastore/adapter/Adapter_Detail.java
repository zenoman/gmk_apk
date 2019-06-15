package project.com.tastore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import project.com.tastore.Config.env;
import project.com.tastore.Konstruktor.Json_keranjang;
import project.com.tastore.MainActivity;
import project.com.tastore.R;
import project.com.tastore.fragment.Keranjang;

public class Adapter_Detail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Json_keranjang> list=new ArrayList<>();
    private MainActivity context;
    private env db=new env();
    private Keranjang keranjang=new Keranjang();
    private MainActivity ctk;

    public Adapter_Detail( Context context) {
        this.list = list;
        this.context = (MainActivity) context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_detail_trans,viewGroup,false);
        return new DetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DetailHolder holder=(DetailHolder) viewHolder;
        holder.tv_id.setText(list.get(i).getId());
        holder.tv_kode.setText(list.get(i).getKode_barang());
        holder.tv_barang.setText(list.get(i).getBarang());
        holder.tv_varian.setText(list.get(i).getWarna());
        holder.tv_qty.setText(list.get(i).getJumlah());
        holder.tv_harga.setText(list.get(i).getTotal());
        holder.tv_cvarian.setText(list.get(i).getVarian());
        holder.iduser=list.get(i).getIduser();
        Glide.with(context).load(env.barang_url+list.get(i).getNama()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class DetailHolder extends RecyclerView.ViewHolder {
        private TextView tv_id,tv_barang,tv_harga,tv_varian,tv_qty,tv_kode,tv_cvarian;
        private ImageButton del;
        private ImageView img;
        private String iduser;
        public DetailHolder(View view) {
            super(view);
            tv_id=(TextView) view.findViewById(R.id.id);
            tv_kode=(TextView) view.findViewById(R.id.kode);
            tv_barang=(TextView) view.findViewById(R.id.barang);
            tv_varian=(TextView) view.findViewById(R.id.varian);
            tv_qty=(TextView) view.findViewById(R.id.qty);
            tv_harga=(TextView) view.findViewById(R.id.harga);
            tv_cvarian=(TextView) view.findViewById(R.id.cvarian);
            img=(ImageView) view.findViewById(R.id.img);
        }
    }
    public  void add(Json_keranjang json_keranjang){
        list.add(json_keranjang);
        notifyItemInserted(list.size());
    }
    public void addAll(List<Json_keranjang> ls){
        for(Json_keranjang sjo:ls){
            add(sjo);
        }
    }
}
