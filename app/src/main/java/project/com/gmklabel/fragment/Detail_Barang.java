package project.com.gmklabel.fragment;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import project.com.gmklabel.Config.env;
import project.com.gmklabel.Konstruktor.Json_Csize;
import project.com.gmklabel.Konstruktor.Json_Cvarian;
import project.com.gmklabel.Konstruktor.Json_Produk;
import project.com.gmklabel.Konstruktor.Json_rinci_poduk;
import project.com.gmklabel.Konstruktor.User_info;
import project.com.gmklabel.Model.Model;
import project.com.gmklabel.Model.RecyclerViewClickListener;
import project.com.gmklabel.Model.Respon_Csize;
import project.com.gmklabel.Model.Respon_Cvarian;
import project.com.gmklabel.Model.Respon_Produk;
import project.com.gmklabel.R;
import project.com.gmklabel.User.User_config;
import project.com.gmklabel.adapter.Adapter_Csize;
import project.com.gmklabel.adapter.Adapter_Cvarian;
import project.com.gmklabel.adapter.Adapter_Image;
import project.com.gmklabel.adapter.Adapter_Image_small;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_Barang extends AppCompatActivity implements RecyclerViewClickListener {
private LinearLayout ly_csize;
private RecyclerView rv_imageu,rv_image,rv_varian,rv_size;
private Adapter_Image adapter_image;
private Adapter_Image_small adapterImageSmall;
private Adapter_Cvarian adapterCvarian;
private Adapter_Csize adapterCsize;
private GridLayoutManager manageru,managermini,managervarian,managerCsize;
private String STOK,idwr;
private TextView Barang,harga,desk,jdlb,stok,tv_kodev,tv_var,tv_ids,tv_size;
private String idw,sdesk,skode,hargaf,hargan,barang,diskon;
private List<Json_Produk> gambarList=new ArrayList<>();
private List<Json_Produk> miniList=new ArrayList<>();
private List<Json_rinci_poduk> warnaList=new ArrayList<>();
private List<Json_Cvarian> cvarianList;
private List<Json_Csize> csizeList;
private MaterialSpinner spinner;
private env db=new env();
private ShimmerFrameLayout loading_gambar;
private Toolbar toolbar;
private ElegantNumberButton qty;
private Button checkout;
private User_info user_info;
private RecyclerViewClickListener clickListener,clickVar;
private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__barang);
        desk=(TextView) findViewById(R.id.deskripsi);
        harga=(TextView) findViewById(R.id.Harga);
        Barang=(TextView) findViewById(R.id.barang);
        rv_imageu=(RecyclerView) findViewById(R.id.rv_image);
        loading_gambar=(ShimmerFrameLayout) findViewById(R.id.load_gambar);

        ly_csize=(LinearLayout) findViewById(R.id.ly_csize);
        tv_kodev=(TextView) findViewById(R.id.kodev);
        tv_var=(TextView) findViewById(R.id.cvr);

        tv_ids=(TextView) findViewById(R.id.kodesz);
        tv_size=(TextView) findViewById(R.id.size);

        jdlb=(TextView) findViewById(R.id.jdlbarang);
        stok=(TextView) findViewById(R.id.stok);
        qty=(ElegantNumberButton) findViewById(R.id.qty);

        checkout=(Button) findViewById(R.id.check);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User_config.getmInstance(Detail_Barang.this).isLogedIn()) {
                    String jml = qty.getNumber().toString();
                    simpanPesan(jml);
                }else{
                    Toast.makeText(Detail_Barang.this, "Anda Harus Login Dulu", Toast.LENGTH_SHORT).show();
                }
            }
        });


        adapter_image=new Adapter_Image(this);
        manageru=new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false);
        rv_imageu.setLayoutManager(manageru);
        rv_imageu.setAdapter(adapter_image);
        rv_imageu.setItemAnimator(new DefaultItemAnimator());

        rv_image=(RecyclerView) findViewById(R.id.rv_detail_image);
        adapterImageSmall=new Adapter_Image_small(Detail_Barang.this,(RecyclerViewClickListener) this);
        managermini=new GridLayoutManager(this,5,GridLayoutManager.VERTICAL,false);
        rv_image.setLayoutManager(managermini);
        rv_image.setAdapter(adapterImageSmall);
        rv_image.setItemAnimator(new DefaultItemAnimator());

        rv_varian=(RecyclerView) findViewById(R.id.rv_varian);
        adapterCvarian=new Adapter_Cvarian(Detail_Barang.this,(RecyclerViewClickListener) this);
        managervarian=new GridLayoutManager(this,5,GridLayoutManager.VERTICAL,false);
        rv_varian.setLayoutManager(managervarian);
        rv_varian.setAdapter(adapterCvarian);
        rv_varian.setItemAnimator(new DefaultItemAnimator());

        rv_size=(RecyclerView) findViewById(R.id.rv_csize);
        adapterCsize=new Adapter_Csize(Detail_Barang.this,(RecyclerViewClickListener) this);
        managerCsize=new GridLayoutManager(Detail_Barang.this,4,GridLayoutManager.VERTICAL,false);
        rv_size.setLayoutManager(managerCsize);
        rv_size.setAdapter(adapterCsize);
        rv_size.setItemAnimator(new DefaultItemAnimator());

        sdesk=getIntent().getStringExtra("desk");
        skode=getIntent().getStringExtra("kode");
        hargaf=getIntent().getStringExtra("hargaf");
        barang=getIntent().getStringExtra("barang");
        hargan=getIntent().getStringExtra("harga");
        diskon=getIntent().getStringExtra("diskon");
        desk.setText(Html.fromHtml(sdesk));
        harga.setText(hargaf);
        Barang.setText(barang);
        jdlb.setText(barang);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        showImageU(skode);
        showImageMini(skode);
        showVarian();
       //showCsize("BRG00001","02");
        //showWarana(skode);

    }

    private void showVarian() {
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Cvarian> call=model.showCvarian(skode);
        call.enqueue(new Callback<Respon_Cvarian>() {
            @Override
            public void onResponse(Call<Respon_Cvarian> call, Response<Respon_Cvarian> response) {
                if(response.isSuccessful()){
                    cvarianList=response.body().getData();
                    adapterCvarian.addAll(cvarianList);
                }
            }

            @Override
            public void onFailure(Call<Respon_Cvarian> call, Throwable t) {

            }
        });
    }


    private void simpanPesan(final String jml) {
        user_info=User_config.getmInstance(Detail_Barang.this).getUser();
        final Dialog dialog=new Dialog(Detail_Barang.this);
        dialog.setContentView(R.layout.dialog_loading);
        if(tv_var.getText().toString().equals("")){
            Toast.makeText(this, "Anda Belum Memilih Varian", Toast.LENGTH_SHORT).show();
        }else if(tv_size.getText().toString().equals("")){
            Toast.makeText(this, "Anda Belum Memilih Size/Ukuran", Toast.LENGTH_SHORT).show();
        }
        else{
            final int fqty=Integer.valueOf(stok.getText().toString())-Integer.valueOf(jml);
            int jmlq=Integer.valueOf(jml);
            int fstok=Integer.valueOf(stok.getText().toString());
            if(fstok<jmlq){
                Toast.makeText(this, "Jumlah Belanja Melebihi Stok", Toast.LENGTH_SHORT).show();
            }else{
                dialog.show();
                String dbarang=Barang.getText().toString()+"-"+ tv_var.getText().toString()+"-"+ tv_size.getText().toString();
                Model model=db.getRespo().create(Model.class);
                Call<Respon_Produk> call=model.orderBarang(tv_kodev.getText().toString(),tv_ids.getText().toString(),user_info.getId(),skode,dbarang,hargan,jml,diskon);
                call.enqueue(new Callback<Respon_Produk>() {
                    @Override
                    public void onResponse(Call<Respon_Produk> call, Response<Respon_Produk> response) {
                        String pesan=response.body().getPesan();
                        String st=response.body().getStatus();
                        if(response.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(Detail_Barang.this,pesan,Toast.LENGTH_SHORT).show();
                            if(st.equals("1")){
                                stok.setText(String.valueOf(fqty));
                            }
                        }else{
                            Toast.makeText(Detail_Barang.this,pesan,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Respon_Produk> call, Throwable throwable) {
                        Toast.makeText(Detail_Barang.this,"Check Koneksi Anda",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }      
      
    }

 //  private void showWarana(String skode) {
 //      Model model=db.getRespo().create(Model.class);
 //      Call<Respon_Produk> call=model.viewWarna(skode);
 //      call.enqueue(new Callback<Respon_Produk>() {
 //          @Override
 //          public void onResponse(Call<Respon_Produk> call, Response<Respon_Produk> response) {

 //              List<String> listW=new ArrayList<>();
 //              if(response.isSuccessful()){
 //                  warnaList=response.body().getData_barang();
 //                  for(int i=0;i<warnaList.size();i++){
 //                      listW.add(warnaList.get(i).getWarna());

 //                  }
 //                  ArrayAdapter<String> adapter=new ArrayAdapter<String>(Detail_Barang.this,android.R.layout.simple_spinner_item,listW);
 //                  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 //                  spinner.setAdapter(adapter);

 //              }
 //          }

 //          @Override
 //          public void onFailure(Call<Respon_Produk> call, Throwable throwable) {
 //              Toast.makeText(Detail_Barang.this,"Periksa Jaringan Internet Anda",Toast.LENGTH_SHORT).show();
 //          }
 //      });
 //  }

    @Override
    protected void onResume() {
        super.onResume();
        loading_gambar.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        loading_gambar.stopShimmer();
    }

    private void showImageMini(String skode) {
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Produk> call=model.viewgmb(skode);
        call.enqueue(new Callback<Respon_Produk>() {
            @Override
            public void onResponse(Call<Respon_Produk> call, Response<Respon_Produk> response) {
                if(response.isSuccessful()){
                    miniList=response.body().getData();
                    adapterImageSmall.addAll(miniList);
                }
            }

            @Override
            public void onFailure(Call<Respon_Produk> call, Throwable throwable) {
                Toast.makeText(Detail_Barang.this,"Periksa Jaringan Internet Anda",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showImageU(String skode) {
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Produk> call=model.viewgmb(skode);
        call.enqueue(new Callback<Respon_Produk>() {
            @Override
            public void onResponse(Call<Respon_Produk> call, Response<Respon_Produk> response) {
                if(response.isSuccessful()){
                    loading_gambar.setVisibility(View.GONE);
                    loading_gambar.stopShimmer();
                    gambarList=response.body().getData();
                    adapter_image.addAll(gambarList);
                }else{
                    Toast.makeText(Detail_Barang.this,"Periksa Jaringan Internet Anda",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Respon_Produk> call, Throwable throwable) {
                Toast.makeText(Detail_Barang.this,"Periksa Jaringan Internet Anda",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void recyclerViewListClicked( int position) {
       // Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
        rv_imageu.smoothScrollToPosition(position);
    }

    @Override
    public void SizeClick(String id,String size,String stock) {
        tv_ids.setText(id);
        tv_size.setText(size);
        stok.setText(stock);

    }

    @Override
    public void onclickVarian(String id, String kodev,String warna) {
        tv_kodev.setText(kodev);
        tv_var.setText(warna);
        adapterCsize.clearFilter(csizeList);
        showCsize(id,kodev);
        //check
        tv_ids.setText("");
        tv_size.setText("");
        stok.setText("");
        //Toast.makeText(Detail_Barang.this,id+kodev , Toast.LENGTH_SHORT).show();
    }

    private void showCsize(String id, String kodev) {
        Model model=db.getRespo().create(Model.class);
        Call<Respon_Csize> call=model.showSize(id,kodev);
        call.enqueue(new Callback<Respon_Csize>() {
            @Override
            public void onResponse(Call<Respon_Csize> call, Response<Respon_Csize> response) {
                if(response.isSuccessful()){
                    csizeList=response.body().getData();
                    adapterCsize.addAll(csizeList);
                }
            }

            @Override
            public void onFailure(Call<Respon_Csize> call, Throwable t) {
                Log.e("Error",String.valueOf(t));
            }
        });
    }
}
