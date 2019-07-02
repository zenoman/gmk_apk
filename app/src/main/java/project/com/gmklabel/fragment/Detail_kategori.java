package project.com.tastore_laris.fragment;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

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

public class Detail_kategori extends AppCompatActivity {
    private RecyclerView rv;
    private List<Json_Produk> produkList;
    private TextView kategori;
    private Toolbar toolbar;
    private Adapter_produk adapter_produk;
    private GridLayoutManager manager;
    private int total=0,page=1;
    private env db=new env();
    private String kode;
    private ShimmerFrameLayout loading;
    private RelativeLayout empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kategori);
        rv=(RecyclerView) findViewById(R.id.rv_k);
        kode=getIntent().getStringExtra("kode");
        loading=(ShimmerFrameLayout) findViewById(R.id.shimmer);
        empty=(RelativeLayout) findViewById(R.id.empty);
        kategori=(TextView) findViewById(R.id.kategori);
        kategori.setText(getIntent().getStringExtra("kategori"));
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter_produk=new Adapter_produk(Detail_kategori.this);
        manager=new GridLayoutManager(Detail_kategori.this,1,GridLayoutManager.VERTICAL,false);
        rv.setAdapter(adapter_produk);
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());
        showDetail(kode);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(manager.findLastCompletelyVisibleItemPosition()==manager.getItemCount()-1){
                    if(total>manager.getItemCount()){
                        page+=1;
                        loadNextPage(kode);
                    }else{
                       // Toast.makeText(Detail_kategori.this, "Barang Terakhir", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        loading.stopShimmer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loading.startShimmer();
    }

    private void loadNextPage(String kode) {
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Produk> call=model.viewdetailK(kode,page);
        call.enqueue(new Callback<Respon_Produk>() {
            @Override
            public void onResponse(Call<Respon_Produk> call, Response<Respon_Produk> response) {
                if(response.isSuccessful()){
                    produkList=response.body().getData();
                    adapter_produk.addall(produkList);

                }
            }

            @Override
            public void onFailure(Call<Respon_Produk> call, Throwable throwable) {

            }
        });
    }

    private void showDetail(String kode) {
        loading.startShimmer();
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Produk> call=model.viewdetailK(kode,page);
        call.enqueue(new Callback<Respon_Produk>() {
            @Override
            public void onResponse(Call<Respon_Produk> call, Response<Respon_Produk> response) {
                if(response.isSuccessful()){
                    loading.stopShimmer();
                    loading.setVisibility(View.GONE);
                    produkList=response.body().getData();
                    total=response.body().getTotal();
                    adapter_produk.addall(produkList);
                    if(adapter_produk.getItemCount()==0){
                        empty.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Respon_Produk> call, Throwable throwable) {

            }
        });
    }
}
