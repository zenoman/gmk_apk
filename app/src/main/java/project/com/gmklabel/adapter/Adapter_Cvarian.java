package project.com.tastore_laris.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import project.com.tastore_laris.Konstruktor.Json_Cvarian;
import project.com.tastore_laris.Model.RecyclerViewClickListener;
import project.com.tastore_laris.R;

public class Adapter_Cvarian extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Json_Cvarian> list=new ArrayList<>();
    private static RecyclerViewClickListener clickListener;

    public Adapter_Cvarian(Context context,RecyclerViewClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.clickListener=clickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_cv,viewGroup,false);
        return new HolderVarian(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,final int i) {
        final HolderVarian holder=(HolderVarian) viewHolder;
        holder.btn.setText(list.get(i).getVarian());
        holder.lid=list.get(i).getKode();
        holder.kdv=list.get(i).getKode_v();
        holder.warna=list.get(i).getVarian();
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onclickVarian(holder.lid,holder.kdv,holder.warna);
               // Toast.makeText(context, holder.kdv, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class HolderVarian extends RecyclerView.ViewHolder {
        Button btn;
        String lid,kdv,warna;
        public HolderVarian(View view) {
            super(view);
            btn=(Button) view.findViewById(R.id.btn);
        }
    }
    public void  add(Json_Cvarian json_cvarian){
        list.add(json_cvarian);
        notifyItemInserted(list.size());
    }
    public void addAll(List<Json_Cvarian> cvarianList){
        for(Json_Cvarian json_cvarian:cvarianList){
            add(json_cvarian);
        }
    }
}
