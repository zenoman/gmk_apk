package project.com.tastore.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import project.com.tastore.Config.env;
import project.com.tastore.Konstruktor.Json_keranjang;
import project.com.tastore.MainActivity;
import project.com.tastore.Model.Model;
import project.com.tastore.Model.Respon_Keranjang;
import project.com.tastore.R;
import project.com.tastore.adapter.Adapter_Detail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Detail_transaksi extends DialogFragment {
    private List<Json_keranjang> list;
    private RecyclerView rv;
    private Button btnclose;
    private GridLayoutManager manager;
    private Adapter_Detail adapter;
    private MainActivity contex;
    private env db=new env();
    private String faktur;
    private ShimmerFrameLayout loading;
    public static Detail_transaksi newInstance() {
        // Required empty public constructor
        return new Detail_transaksi();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail_transaksi, container, false);
        rv=(RecyclerView) view.findViewById(R.id.rv);
        btnclose=(Button) view.findViewById(R.id.close);
        loading=(ShimmerFrameLayout) view.findViewById(R.id.shimmer);
        loading.startShimmer();
        adapter=new Adapter_Detail(getContext());
        manager=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        rv.setAdapter(adapter);
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());
        faktur=getArguments().getString("fk");
        showDetail(faktur);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loading.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        loading.stopShimmer();
    }
    private void showDetail(String fk) {
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Keranjang> call=model.DetailTrans(fk);
        call.enqueue(new Callback<Respon_Keranjang>() {
            @Override
            public void onResponse(Call<Respon_Keranjang> call, Response<Respon_Keranjang> response) {
                if(response.isSuccessful()){
                    loading.stopShimmer();
                    loading.setVisibility(View.GONE);
                    list=response.body().getData();
                    adapter.addAll(list);
                }
            }

            @Override
            public void onFailure(Call<Respon_Keranjang> call, Throwable t) {
                Log.e("Error",String.valueOf(t));
            }
        });
    }


}
