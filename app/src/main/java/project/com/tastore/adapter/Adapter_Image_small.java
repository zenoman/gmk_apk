package project.com.tastore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import project.com.tastore.Config.env;
import project.com.tastore.Konstruktor.Json_Produk;
import project.com.tastore.Model.RecyclerViewClickListener;
import project.com.tastore.R;

public class Adapter_Image_small extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Json_Produk> list=new ArrayList<>();
    private Context context;
    private static RecyclerViewClickListener itemListener;
    private env db=new env();

    public Adapter_Image_small(Context context,RecyclerViewClickListener listener) {
        this.context = context;
        this.list=list;
        this.itemListener=listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_mini_image,viewGroup,false);
        return new MiniHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final MiniHolder holder=(MiniHolder) viewHolder;
        Glide.with(context).load(env.barang_url+list.get(i).getNama()).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               itemListener.recyclerViewListClicked(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private class MiniHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public MiniHolder(final View view) {
            super(view);
            img=(ImageView) view.findViewById(R.id.image);
        }



    }
    public void add(Json_Produk json_produk){
        list.add(json_produk);
        notifyItemInserted(list.size());
    }
    public void addAll(List<Json_Produk> json_produks){
        for(Json_Produk json_produk:json_produks){
            add(json_produk);
        }
    }
}
