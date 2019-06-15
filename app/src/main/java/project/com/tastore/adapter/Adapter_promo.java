package project.com.tastore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import project.com.tastore.Config.env;
import project.com.tastore.Konstruktor.Json_Promo;
import project.com.tastore.R;

public class Adapter_promo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private List<Json_Promo> promoList=new ArrayList<>();
    private env db=new env();
    public Adapter_promo(Context context) {
        this.context = context;
        this.promoList=promoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_promo,viewGroup,false);
        return new PromoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PromoHolder holder=(PromoHolder) viewHolder;
        holder.tv_judul.setText(promoList.get(i).getJudul());
        Glide.with(context).load(db.slider_url+promoList.get(i).getFoto()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return promoList.size();
    }

    private class PromoHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv_judul;
        public PromoHolder(View view) {
            super(view);
            tv_judul=(TextView) view.findViewById(R.id.judul);
            img=(ImageView) view.findViewById(R.id.image);
        }
    }
    public void add(Json_Promo promo){
        promoList.add(promo);
        notifyItemInserted(promoList.size());
    }
    public void addall(List<Json_Promo> json_promos){
            for(Json_Promo promo:json_promos){
                    add(promo);
            }
    }
}
