package project.com.tastore_laris.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import project.com.tastore_laris.Config.env;
import project.com.tastore_laris.Konstruktor.Json_Produk;
import project.com.tastore_laris.Model.Model;
import project.com.tastore_laris.Model.Respon_Produk;
import project.com.tastore_laris.R;
import project.com.tastore_laris.adapter.Adapter_produk;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cari_Barang extends AppCompatActivity {
    private List<Json_Produk> list;
    private RecyclerView rv;
    private RelativeLayout ly;
    private SearchView searchView;
    private GridLayoutManager manager;
    private Adapter_produk adapter_produk;
    private env db=new env();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari__barang);
        rv=(RecyclerView) findViewById(R.id.rv);
        ly=(RelativeLayout) findViewById(R.id.empty);
        adapter_produk=new Adapter_produk(Cari_Barang.this);
        manager=new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        rv.setAdapter(adapter_produk);
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());
        searchView=(SearchView) findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter_produk.ClearFilter(list);
                HasilCari(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


    }

    private void HasilCari(String s) {
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Produk> call=model.vCari(s);
        call.enqueue(new Callback<Respon_Produk>() {
            @Override
            public void onResponse(Call<Respon_Produk> call, Response<Respon_Produk> response) {
                if(response.isSuccessful()){
                    list=response.body().getData();
                    adapter_produk.addall(list);
                    if(adapter_produk.getItemCount()>=0){
                        ly.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(Cari_Barang.this, "Barang Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                        ly.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Respon_Produk> call, Throwable t) {
                Log.e("error",String.valueOf(t));
            }
        });
    }
}
