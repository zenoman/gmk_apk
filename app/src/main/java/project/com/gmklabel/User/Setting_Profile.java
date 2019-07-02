package project.com.gmklabel.User;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import project.com.gmklabel.Config.env;
import project.com.gmklabel.Konstruktor.User_info;
import project.com.gmklabel.Model.Model;
import project.com.gmklabel.Model.Respon_User;
import project.com.gmklabel.R;
import project.com.gmklabel.fragment.Profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Setting_Profile extends Fragment {
private EditText nama,alamat,kota,pos,prov;
private Button btn_simpan,btn_kembali;
private env db=new env();
private User_info User=User_config.getmInstance(getContext()).getUser();
    public Setting_Profile() {
        // Required empty public constructor
    }


    public static Setting_Profile newInstance() {
      return new Setting_Profile();
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
        View view= inflater.inflate(R.layout.fragment_setting__profile, container, false);
        nama=(EditText) view.findViewById(R.id.nama);
        alamat=(EditText) view.findViewById(R.id.alamat);
        kota=(EditText) view.findViewById(R.id.kota);
        pos=(EditText) view.findViewById(R.id.pos);
        prov=(EditText) view.findViewById(R.id.provinsi);
        btn_kembali=(Button) view.findViewById(R.id.btn_kembali);
        btn_simpan=(Button) view.findViewById(R.id.btn_simpan);
        showUser();
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanProfile();
            }
        });
        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .addToBackStack("4")
                        .replace(R.id.frame, Profile.newInstance())
                        .commit();
            }
        });
        return view;
    }

    private void simpanProfile() {
        final String nm,al,kt,ps,pro,id,level;
        final Dialog dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_loading);
        id=User.getId();
        nm=nama.getText().toString();
        al=alamat.getText().toString();
        kt=kota.getText().toString();
        ps=pos.getText().toString();
        pro=prov.getText().toString();

        if(TextUtils.isEmpty(nm)){
            nama.setError("Harap diisi!");
            nama.requestFocus();
            return;
        }else if(TextUtils.isEmpty(al)){
            alamat.setError("Harap diisi!");
            alamat.requestFocus();
            return;
        }else if(TextUtils.isEmpty(kt)){
           kota.setError("Harap diisi!");
           kota.requestFocus();
            return;
        }else if(TextUtils.isEmpty(ps)){
           pos.setError("Harap diisi!");
           pos.requestFocus();
            return;
        }else if(TextUtils.isEmpty(pro)){
            prov.setError("Harap diisi!");
            prov.requestFocus();
            return;
        }
        dialog.show();
        Model model=db.getRespo().create(Model.class);
        Call<Respon_User> call=model.updateLeng(id,nm,al,kt,pro,ps);
        call.enqueue(new Callback<Respon_User>() {
            @Override
            public void onResponse(Call<Respon_User> call, Response<Respon_User> response) {
                if(response.isSuccessful()){
                    dialog.dismiss();
                    String msg=response.body().getMsg();
                    User_info info=new User_info(
                            User.getId(),
                            User.getUsername(),
                            User.getPassword(),
                            User.getEmail(),
                            User.getTelp(),
                            nama.getText().toString(),
                           alamat.getText().toString(),
                           kota.getText().toString(),
                           prov.getText().toString(),
                           pos.getText().toString(),
                           User.getLevel(),
                           User.getKtp_gmb()
                    );
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    User_config.getmInstance(getContext()).userLogin(info);
                }
            }

            @Override
            public void onFailure(Call<Respon_User> call, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(getContext(),"Periksa Koneksi Internet Anda",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUser() {
        nama.setText(User.getNama());
        alamat.setText(User.getAlamat());
        kota.setText(User.getKota());
        pos.setText(User.getKodepos());
        prov.setText(User.getProvinsi());
    }
        
}
