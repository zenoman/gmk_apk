package project.com.tastore_laris.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import project.com.tastore_laris.Config.env;
import project.com.tastore_laris.Konstruktor.Json_kategori;
import project.com.tastore_laris.R;
import project.com.tastore_laris.fragment.Detail_kategori;

public class Adapter_kategori extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
private env db=new env();
private Context context;
private List<Json_kategori> kategoris=new ArrayList<>();
    public Adapter_kategori(Context context){
        this.context=context;
        this.kategoris=kategoris;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_kategori,viewGroup,false);
        return new Kategoriholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
      Kategoriholder holder=(Kategoriholder)viewHolder;
        holder.tv_id.setText(kategoris.get(i).getId());
        holder.tv_kategori.setText(kategoris.get(i).getKategori());
        Glide.with(context).load(db.kategori_url+kategoris.get(i).getGambar()).into(holder.imgv);
    }

    @Override
    public int getItemCount() {
        return kategoris.size();
    }

    private class Kategoriholder extends RecyclerView.ViewHolder {
        CircleImageView imgv;
        TextView tv_kategori,tv_id;
        public Kategoriholder(View view) {
            super(view);
            tv_id=(TextView) view.findViewById(R.id.idkategori);
            tv_kategori=(TextView) view.findViewById(R.id.kategori);
            imgv=(CircleImageView) view.findViewById(R.id.image);
            imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, Detail_kategori.class);
                    intent.putExtra("kode",tv_id.getText().toString());
                    intent.putExtra("kategori",tv_kategori.getText().toString());
                    context.startActivity(intent);
                }
            });

        }
    }

    public void add(Json_kategori kategori){
        kategoris.add(kategori);
        notifyItemInserted(kategoris.size());
    }
    public void addall(List<Json_kategori> json_kategoris){
        for(Json_kategori kategori:json_kategoris){
            add(kategori);
        }

    }

}
