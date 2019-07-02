package project.com.tastore_laris.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import project.com.tastore_laris.Config.env;
import project.com.tastore_laris.Konstruktor.Json_Trans;
import project.com.tastore_laris.Konstruktor.User_info;
import project.com.tastore_laris.Model.Model;
import project.com.tastore_laris.Model.Respon_Trans;
import project.com.tastore_laris.R;
import project.com.tastore_laris.User.User_config;
import project.com.tastore_laris.adapter.Adapter_Trans;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Transaksi extends Fragment {
private List<Json_Trans> transList;
private ShimmerFrameLayout loadTrans;
private Adapter_Trans adapter_trans;
private GridLayoutManager manager;
private RecyclerView rv;
private env db=new env();
private User_info info;
private RelativeLayout lyempty;
    public Transaksi() {
        // Required empty public constructor
    }


    public static Transaksi newInstance() {
        return new Transaksi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        loadTrans.startShimmer();

    }

    @Override
    public void onPause() {
        super.onPause();
        loadTrans.stopShimmer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context context=getActivity();
        View view= inflater.inflate(R.layout.fragment_transaksi, container, false);
        rv=(RecyclerView) view.findViewById(R.id.rv);
        lyempty=(RelativeLayout) view.findViewById(R.id.empty);
        loadTrans=(ShimmerFrameLayout) view.findViewById(R.id.shimmer);
        loadTrans.setVisibility(View.GONE);
        adapter_trans=new Adapter_Trans(getContext());
        manager=new GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false);
        rv.setAdapter(adapter_trans);
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());
        if(User_config.getmInstance(getActivity()).isLogedIn()) {
            showTransaksi();
        }else {
            lyempty.setVisibility(View.VISIBLE);
        }
        return  view;

    }

    private void showTransaksi() {
        loadTrans.setVisibility(View.VISIBLE);
        loadTrans.startShimmer();
        info= User_config.getmInstance(getActivity()).getUser();
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Trans> call=model.vTrans(info.getId());
        call.enqueue(new Callback<Respon_Trans>() {
            @Override
            public void onResponse(Call<Respon_Trans> call, Response<Respon_Trans> response) {
                if(response.isSuccessful()){
                    loadTrans.setVisibility(View.GONE);
                    loadTrans.stopShimmer();
                    transList=response.body().getData();
                    adapter_trans.addAll(transList);
                    if(adapter_trans.getItemCount()==0){
                        lyempty.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Respon_Trans> call, Throwable t) {
                Toast.makeText(getContext(), String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
