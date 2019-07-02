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

import project.com.tastore_laris.Konstruktor.Json_Csize;
import project.com.tastore_laris.Model.RecyclerViewClickListener;
import project.com.tastore_laris.R;

public class Adapter_Csize extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Json_Csize> csizeList=new ArrayList<>();
    private RecyclerViewClickListener clickListener;
    Context context;

    public Adapter_Csize( Context context ,RecyclerViewClickListener clickListener) {
        this.context = context;
        this.csizeList = csizeList;
        this.clickListener=clickListener;
    }

    public void clearFilter(List<Json_Csize> json_csize){
        csizeList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_cv,viewGroup,false);
        return  new SizeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final SizeHolder holder=(SizeHolder) viewHolder;
        holder.btnsize.setText(csizeList.get(i).getWarna());
        holder.ids=csizeList.get(i).getIdbarang();
        holder.size=csizeList.get(i).getWarna();
        holder.stok=csizeList.get(i).getStok();
        holder.btnsize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.SizeClick(holder.ids,holder.size,holder.stok);
            }
        });
    }

    @Override
    public int getItemCount() {
        return csizeList.size();
    }

    private class SizeHolder extends RecyclerView.ViewHolder {
        Button btnsize;
        String ids,size,stok;
        public SizeHolder(View view) {
            super(view);
            btnsize=(Button) view.findViewById(R.id.btn);

        }
    }
    public void add(Json_Csize json_csize){
        csizeList.add(json_csize);
        notifyItemInserted(csizeList.size());
    }
    public void addAll(List<Json_Csize> csizeList){
        for(Json_Csize json_csize:csizeList){
            add(json_csize);
        }
    }
}
