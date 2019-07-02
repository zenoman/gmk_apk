package project.com.gmklabel.User;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import project.com.gmklabel.Config.env;
import project.com.gmklabel.Model.Model;
import project.com.gmklabel.Model.Respon_User;
import project.com.gmklabel.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
private TextView tv_login,kembali;
private EditText user,pass,email,telepon;
private Button daftar;
private env db=new env();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tv_login=(TextView) findViewById(R.id.login);
        kembali=(TextView) findViewById(R.id.back);
        user=(EditText) findViewById(R.id.username);
        pass=(EditText) findViewById(R.id.pass);
        email=(EditText) findViewById(R.id.email);
        telepon=(EditText) findViewById(R.id.phone);
        daftar=(Button) findViewById(R.id.reg);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daftarUser();
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
                finish();
            }
        });
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void daftarUser() {
        String usern,pas,emal,telp;
        usern=user.getText().toString();
        pas=pass.getText().toString();
        emal=email.getText().toString();
        telp=telepon.getText().toString();
        //dialog
        final Dialog dialog=new Dialog(Register.this);
        dialog.setContentView(R.layout.dialog_loading);
        //cek
        if(TextUtils.isEmpty(usern)){
            user.setError("Harap Dilengkapi");
            user.requestFocus();
            return ;
        }else if(TextUtils.isEmpty(pas)){
            pass.setError("Harap Dilengkapi");
            pass.requestFocus();
            return;
        }else if(TextUtils.isEmpty(emal)){
            email.setError("Harap Dilengkapi");
            email.requestFocus();
            return;
        }else if(TextUtils.isEmpty(telp)){
            telepon.setError("Harap Dilengkapi");
            telepon.requestFocus();
            return;
        }
        dialog.show();
        Model model= db.getRespo().create(Model.class);
        Call<Respon_User> call=model.register(usern,pas,emal,telp);
        call.enqueue(new Callback<Respon_User>() {
            @Override
            public void onResponse(Call<Respon_User> call, Response<Respon_User> response) {
                if(response.isSuccessful()){
                    dialog.dismiss();
                    String msg=response.body().getMsg();
                    Toast.makeText(Register.this,msg,Toast.LENGTH_SHORT).show();
                    //finish();
                }
            }

            @Override
            public void onFailure(Call<Respon_User> call, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(Register.this,"Periksa Jaringan Anda",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
