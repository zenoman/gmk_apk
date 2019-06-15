package project.com.tastore.User;

import android.app.Dialog;
import android.content.Intent;
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

import project.com.tastore.Config.env;
import project.com.tastore.Konstruktor.User_info;
import project.com.tastore.Model.Model;
import project.com.tastore.Model.Respon_User;
import project.com.tastore.R;
import project.com.tastore.fragment.Profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Setting_login extends Fragment {
    private EditText username,password,telp,email;
    private Button btnSimpan,btnKembali;
    private env db=new env();
    private User_info user=User_config.getmInstance(getContext()).getUser();
    public Setting_login() {
        // Required empty public constructor
    }


    public static Setting_login newInstance() {

        return new Setting_login() ;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_setting_login, container, false);
        username=(EditText) view.findViewById(R.id.username);
        telp=(EditText) view.findViewById(R.id.telp);
        password=(EditText) view.findViewById(R.id.pass);
        email=(EditText) view.findViewById(R.id.email);
        btnSimpan=(Button) view.findViewById(R.id.btn_simpan);
        btnKembali=(Button) view.findViewById(R.id.btn_kembali);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateLogin();
            }
        });
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .addToBackStack("4")
                        .replace(R.id.frame, Profile.newInstance())
                        .commit();
            }
        });
        showUser();
        return view;
    }

    private void UpdateLogin() {
        final Dialog dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_loading);

        String us,ps,tl,em;
        us=username.getText().toString();
        ps=password.getText().toString();
        tl=telp.getText().toString();
        em=email.getText().toString();
        if(TextUtils.isEmpty(us)){
            username.setError("Harap Dilengkapi");
            username.requestFocus();
            return;
        }else if(TextUtils.isEmpty(tl)){
            telp.setError("Harap Dilengkapi");
            telp.requestFocus();
            return;
        }else if(TextUtils.isEmpty(em)){
           email.setError("Harap Dilengkapi");
           email.requestFocus();
            return;
        }
        dialog.show();
        Model model=db.getRespo().create(Model.class);
        if(ps.equals("")){
            Call<Respon_User> call=model.updateLogN(user.getId(),us,em,tl);
            call.enqueue(new Callback<Respon_User>() {
                @Override
                public void onResponse(Call<Respon_User> call, Response<Respon_User> response) {
                    if(response.isSuccessful()){
                        dialog.dismiss();
                        String msg=response.body().getMsg();
                        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                        getActivity().startActivity(new Intent(getContext(),Login.class));
                    }
                }

                @Override
                public void onFailure(Call<Respon_User> call, Throwable throwable) {
                    dialog.dismiss();
                    Toast.makeText(getContext(),"Periksa Jaringan Anda",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Call<Respon_User> call=model.updateLog(user.getId(),us,ps,em,tl);
            call.enqueue(new Callback<Respon_User>() {
                @Override
                public void onResponse(Call<Respon_User> call, Response<Respon_User> response) {
                    if(response.isSuccessful()){
                        dialog.dismiss();
                        String msg=response.body().getMsg();
                        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                        getActivity().startActivity(new Intent(getContext(),Login.class));
                    }
                }

                @Override
                public void onFailure(Call<Respon_User> call, Throwable throwable) {
                    dialog.dismiss();
                    Toast.makeText(getContext(),"Periksa Jaringan Anda",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void showUser() {
        username.setText(user.getUsername());
        telp.setText(user.getTelp());
        email.setText(user.getEmail());

    }


}
