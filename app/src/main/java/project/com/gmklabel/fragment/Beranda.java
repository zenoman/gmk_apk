package project.com.gmklabel.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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

import project.com.gmklabel.Config.env;
import project.com.gmklabel.Konstruktor.Json_Produk;
import project.com.gmklabel.Konstruktor.Json_Promo;
import project.com.gmklabel.Konstruktor.Json_kategori;
import project.com.gmklabel.MainActivity;
import project.com.gmklabel.Model.Model;
import project.com.gmklabel.Model.Respon_Kategori;
import project.com.gmklabel.Model.Respon_Produk;
import project.com.gmklabel.Model.Respon_Promo;
import project.com.gmklabel.R;
import project.com.gmklabel.adapter.Adapter_kategori;
import project.com.gmklabel.adapter.Adapter_produk;
import project.com.gmklabel.adapter.Adapter_promo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Beranda extends Fragment {
private RecyclerView rv_kat,rv_promo,rv_produk;
private GridLayoutManager manager_kat,manager_pro,manager_duk;
private Adapter_kategori adapter_kategori;
private Adapter_promo adapter_promo;
private Adapter_produk adapter_produk;
private List<Json_kategori> kategoriList;
private List<Json_Promo> promoList;
private List<Json_Produk> produkList;
private ShimmerFrameLayout load_kat,load_pro,load_duk;
private env db=new env();
private Context context=getActivity();
private int total=0,page=1;
    public Beranda() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Beranda newInstance() {

        return new Beranda();
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
        View view= inflater.inflate(R.layout.fragment_beranda, container, false);
            rv_kat=(RecyclerView) view.findViewById(R.id.rv_kategori);
            load_kat=(ShimmerFrameLayout) view.findViewById(R.id.load_kategori);
            load_pro=(ShimmerFrameLayout) view.findViewById(R.id.load_promo);

            manager_kat=new GridLayoutManager(context,4,GridLayoutManager.VERTICAL,false);
            adapter_kategori=new Adapter_kategori(context);
            rv_kat.setAdapter(adapter_kategori);
            rv_kat.setLayoutManager(manager_kat);
            rv_kat.setItemAnimator(new DefaultItemAnimator());

            rv_promo=(RecyclerView) view.findViewById(R.id.rv_promo);
            manager_pro=new GridLayoutManager(context,1,GridLayoutManager.HORIZONTAL,false);
            adapter_promo=new Adapter_promo(context);
            rv_promo.setAdapter(adapter_promo);
            rv_promo.setLayoutManager(manager_pro);
            rv_promo.setItemAnimator(new DefaultItemAnimator());
           // rv_promo.setNestedScrollingEnabled(false);

            rv_produk=(RecyclerView) view.findViewById(R.id.rv_rekom);
            manager_duk=new GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false);
            adapter_produk=new Adapter_produk(context);
            rv_produk.setAdapter(adapter_produk);
            rv_produk.setLayoutManager(manager_duk);
            rv_produk.setItemAnimator(new DefaultItemAnimator());
            rv_produk.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if(manager_duk.findLastCompletelyVisibleItemPosition()==manager_duk.getItemCount()-1){
                        if(total>manager_duk.getItemCount()){
                            page+=1;
                            loadNext();
                        }else{
                           // Toast.makeText(getContext(), "Barang Terakhir", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            showKategori();
            showPromo();
            showProduk(0);
        return  view;
    }

    private void loadNext() {
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Produk> call=model.viewEta(page);
        call.enqueue(new Callback<Respon_Produk>() {
            @Override
            public void onResponse(Call<Respon_Produk> call, Response<Respon_Produk> response) {
                if(response.isSuccessful()){
                    produkList=response.body().getData();
                    total=response.body().getTotal();
                    adapter_produk.addall(produkList);
                }
            }

            @Override
            public void onFailure(Call<Respon_Produk> call, Throwable throwable) {

            }
        });
    }

    private void showProduk(int i) {
        Model mdl=db.getRespo().create(Model.class);
        Call<Respon_Produk> call=mdl.viewEta(1);
        call.enqueue(new Callback<Respon_Produk>() {
            @Override
            public void onResponse(Call<Respon_Produk> call, Response<Respon_Produk> response) {
                if(response.isSuccessful()){
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
                if(MainActivity.aktif==false){
                    call.cancel();
                }
            }
        });
    }

    private void showPromo() {
        Model mdl=db.getRespo().create(Model.class);
        Call<Respon_Promo> call=mdl.viewPromo();
        call.enqueue(new Callback<Respon_Promo>() {
            @Override
            public void onResponse(Call<Respon_Promo> call, Response<Respon_Promo> response) {
                if(response.isSuccessful()){
                    load_pro.stopShimmer();
                    load_pro.setVisibility(View.GONE);
                    promoList=response.body().getData();
                    adapter_promo.addall(promoList);
                    runslider();
                }
                if(MainActivity.aktif==false){
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<Respon_Promo> call, Throwable throwable) {
                if(MainActivity.aktif==false){
                    call.cancel();
                }
            }
        });
    }

    private void runslider() {
        final int spds=10000;
        final Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            int count=0;
            boolean flag=true;
            @Override
            public void run() {
                if(count<adapter_promo.getItemCount()){
                    if(count==adapter_promo.getItemCount()-1){
                        flag=false;
                    }else if(count==0){
                        flag=true;
                    }
                    if(flag) count++;
                    else count--;
                    rv_promo.smoothScrollToPosition(count);
                    handler.postDelayed(this,spds);
                }
            }
        };
        handler.postDelayed(runnable,spds);
    }

    private void showKategori() {
        Model mdl=db.getRespo().create(Model.class);
        Call<Respon_Kategori> call=mdl.viewKategori();
        call.enqueue(new Callback<Respon_Kategori>() {
            @Override
            public void onResponse(Call<Respon_Kategori> call, Response<Respon_Kategori> response) {
                if(response.isSuccessful()) {
                    load_kat.stopShimmer();
                    load_kat.setVisibility(View.GONE);
                    kategoriList = response.body().getData();
                    adapter_kategori.addall(kategoriList);
                }
                if(MainActivity.aktif==false){
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<Respon_Kategori> call, Throwable t) {
                //Toast.makeText(getActivity(),String.valueOf(t),Toast.LENGTH_SHORT).show();
                if(MainActivity.aktif==false){
                    call.cancel();
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        load_kat.startShimmer();
        load_pro.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        load_kat.stopShimmer();
        load_pro.startShimmer();
    }
}
