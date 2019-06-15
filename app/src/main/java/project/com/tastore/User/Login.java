package project.com.tastore.User;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import project.com.tastore.Config.env;
import project.com.tastore.Konstruktor.User_info;
import project.com.tastore.MainActivity;
import project.com.tastore.Model.Model;
import project.com.tastore.Model.Respon_User;
import project.com.tastore.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity  {
private Button btn_login,btn_back;
private Toolbar toolbar;
private TextView tv_register;
private EditText username,password;
private CheckBox ck;
private env db=new env();
//private KProgressHUD dialog;
private List<User_info> infoList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        btn_login=(Button) findViewById(R.id.btn_login);
        btn_back=(Button) findViewById(R.id.btn_back);
        tv_register=(TextView) findViewById(R.id.daftar);
        ck=(CheckBox) findViewById(R.id.ck);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(Login.this,Register.class);
               startActivity(intent);
                finish();
            }
        });

        ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else{
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekLogin();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Login.this,MainActivity.class));
            }
        });
    }


    private void cekLogin() {

      // dialog=KProgressHUD.create(Login.this)
      //         .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
      //         .setLabel("Tunggu Sebentar")
      //         .setCancellable(true)
      //         .setAnimationSpeed(2)
      //         .setDimAmount(0.5f);
    final Dialog dialog=new Dialog(Login.this);
    dialog.setContentView(R.layout.dialog_loading);
    //db.show_dialog(Login.this,true);
        String user,pass;
        user=username.getText().toString();
        pass=password.getText().toString();

        if(TextUtils.isEmpty(user)){
            username.setError("Harap Dilengkapi");
            username.requestFocus();
            return;
        }else if(TextUtils.isEmpty(pass)){
            password.setError("Harap Dilengkapi");
            password.requestFocus();
            return;
        }

        dialog.show();

        Model model=db.getRespo().create(Model.class);
        Call<Respon_User> call=model.userProfile(user,pass);
        call.enqueue(new Callback<Respon_User>() {
            @Override
            public void onResponse(Call<Respon_User> call, Response<Respon_User> response) {
                String msg=response.body().getMsg();
                if(response.isSuccessful()){
                    String st=response.body().getStatus();
                    if(st.equals("1")){
                        infoList=response.body().getData();
                        for (int i=0;i<infoList.size();i++){
                            User_info info=new User_info(
                                   infoList.get(i).getId(),
                                   infoList.get(i).getUsername(),
                                   infoList.get(i).getPassword(),
                                   infoList.get(i).getEmail(),
                                   infoList.get(i).getTelp(),
                                   infoList.get(i).getNama(),
                                   infoList.get(i).getAlamat(),
                                   infoList.get(i).getKota(),
                                   infoList.get(i).getProvinsi(),
                                   infoList.get(i).getKodepos(),
                                   infoList.get(i).getLevel(),
                                   infoList.get(i).getKtp_gmb()
                            );
                            Toast.makeText(Login.this,msg,Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            User_config.getmInstance(Login.this).userLogin(info);
                           startActivity(new Intent(Login.this,MainActivity.class));
                            finish();
                        }
                    }else if(st.equals("0")){
                        dialog.dismiss();
                        Toast.makeText(Login.this,msg,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    dialog.dismiss();
                    Toast.makeText(Login.this,msg,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Respon_User> call, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(Login.this,"Periksa Jaringan Internet Anda",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
       // User_config.getmInstance(Login.this).Show_dialog(Login.this).dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();
       // dialog.dismiss();
    }


}