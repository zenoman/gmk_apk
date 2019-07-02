package project.com.tastore_laris.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import project.com.tastore_laris.Config.env;
import project.com.tastore_laris.Konstruktor.Json_Produk;
import project.com.tastore_laris.MainActivity;
import project.com.tastore_laris.Model.Model;
import project.com.tastore_laris.Model.Respon_Produk;
import project.com.tastore_laris.R;
import project.com.tastore_laris.adapter.Adapter_produk;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class katalog extends Fragment {
private RecyclerView rv_katalog;
private GridLayoutManager manager;
private Adapter_produk adapter_produk;
private List<Json_Produk> produkList;
private  int total=0,page=1;
private env db=new env();
private ShimmerFrameLayout load;

    public katalog() {
        // Required empty public constructor
    }


    public static katalog newInstance() {

        return new katalog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context context=getActivity();
        View view= inflater.inflate(R.layout.fragment_katalog, container, false);
        rv_katalog=(RecyclerView) view.findViewById(R.id.rv_katalog);
        load=(ShimmerFrameLayout) view.findViewById(R.id.shimmer);

        manager=new GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false);
        adapter_produk=new Adapter_produk(context);
        rv_katalog.setAdapter(adapter_produk);
        rv_katalog.setLayoutManager(manager);
        rv_katalog.setItemAnimator(new DefaultItemAnimator());

        showKatalog(1);
        rv_katalog.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(manager.findLastCompletelyVisibleItemPosition()==manager.getItemCount()-1){
                    if(total>manager.getItemCount()){
                        page+=1;
                        loadNextPage();
                    }else{
                      //  Toast.makeText(getContext(),"Barang Terakhir",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        load.stopShimmer();
    }

    @Override
    public void onResume() {
        super.onResume();
        load.startShimmer();
    }

    private void loadNextPage() {
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Produk> call=model.viewBarng(page);
        call.enqueue(new Callback<Respon_Produk>() {
            @Override
            public void onResponse(Call<Respon_Produk> call, Response<Respon_Produk> response) {
                if(response.isSuccessful()) {
                    produkList = response.body().getData();
                    total = response.body().getTotal();
                    adapter_produk.addall(produkList);
                }
                if(MainActivity.aktif==false){
                    call.cancel();
                }
            }


            @Override
            public void onFailure(Call<Respon_Produk> call, Throwable throwable) {

            }
        });
    }

    private void showKatalog(int i) {
        load.startShimmer();
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Produk> call=model.viewBarng(i);
        call.enqueue(new Callback<Respon_Produk>() {
            @Override
            public void onResponse(Call<Respon_Produk> call, Response<Respon_Produk> response) {
                if(response.isSuccessful()){
                    load.stopShimmer();
                    load.setVisibility(View.GONE);
                    produkList=response.body().getData();
                    total=response.body().getTotal();
                    adapter_produk.addall(produkList);
                }
                if(MainActivity.aktif==false){
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<Respon_Produk> call, Throwable throwable) {

            }
        });
    }

}
