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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import project.com.tastore.Config.env;
import project.com.tastore.Konstruktor.Json_keranjang;
import project.com.tastore.Konstruktor.User_info;
import project.com.tastore.MainActivity;
import project.com.tastore.Model.Model;
import project.com.tastore.Model.Respon_Keranjang;
import project.com.tastore.R;
import project.com.tastore.User.User_config;
import project.com.tastore.fragment.Keranjang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_Keranjang extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Json_keranjang> list=new ArrayList<>();
    private MainActivity context;
    private env db=new env();
    private Keranjang keranjang=new Keranjang();
    private MainActivity ctk;
    public Adapter_Keranjang( Context context) {
        this.context = (MainActivity) context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_keranjang,viewGroup,false);
        return new KeranjangHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        KeranjangHolder holder=(KeranjangHolder) viewHolder;
        holder.idwarna=list.get(i).getIdwarna();
        holder.tv_id.setText(list.get(i).getId());
        holder.tv_kode.setText(list.get(i).getKode_barang());
        holder.tv_barang.setText(list.get(i).getBarang());
        holder.tv_varian.setText(list.get(i).getWarna());
        holder.tv_cvarian.setText(list.get(i).getVarian());
        holder.tv_qty.setText(list.get(i).getJumlah());
        holder.tv_harga.setText(list.get(i).getTotal());
        holder.iduser=list.get(i).getIduser();
        Glide.with(context).load(env.barang_url+list.get(i).getNama()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class KeranjangHolder extends RecyclerView.ViewHolder {
        private TextView tv_id,tv_barang,tv_harga,tv_varian,tv_qty,tv_kode,tv_cvarian;
        private ImageButton del;
        private ImageView img;
        private String iduser;
        private String idwarna;
        public KeranjangHolder(View view) {
            super(view);
            tv_id=(TextView) view.findViewById(R.id.id);
            tv_kode=(TextView) view.findViewById(R.id.kode);
            tv_barang=(TextView) view.findViewById(R.id.barang);
            tv_varian=(TextView) view.findViewById(R.id.varian);
            tv_qty=(TextView) view.findViewById(R.id.qty);
            tv_harga=(TextView) view.findViewById(R.id.harga);
            del=(ImageButton) view.findViewById(R.id.btn_hapus);
            tv_cvarian=(TextView) view.findViewById(R.id.cvarian);
            img=(ImageView) view.findViewById(R.id.img);
            final User_info info= User_config.getmInstance(context).getUser();
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hapusBarang();
                    context.getSupportFragmentManager().beginTransaction().replace(R.id.frame,Keranjang.newInstance()).commit();
                }
            });
        }

        private void hapusBarang() {
            Model model=db.getRespo().create(Model.class);
            Call<Respon_Keranjang> call=model.hapusK(tv_id.getText().toString(),idwarna,tv_qty.getText().toString());
            call.enqueue(new Callback<Respon_Keranjang>() {
                @Override
                public void onResponse(Call<Respon_Keranjang> call, Response<Respon_Keranjang> response) {
                    if(response.isSuccessful()){
                        String msg=response.body().getPesan();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Respon_Keranjang> call, Throwable throwable) {
                    Toast.makeText(context, String.valueOf(throwable), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void add(Json_keranjang keranjang){
        list.add(keranjang);
        notifyItemInserted(list.size());
    }
    public void addAll(List<Json_keranjang> keranjangList){
        for(Json_keranjang keranjang:keranjangList){
            add(keranjang);
        }
    }
}
