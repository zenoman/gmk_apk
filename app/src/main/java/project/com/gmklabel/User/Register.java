package project.com.gmklabel.User;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kaopiz.kprogresshud.KProgressHUD;

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
private SignInButton btngp;
private GoogleSignInClient googleSignInClient;
private static final int RC_SIGN_IN = 9001;
private KProgressHUD dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Pleasw", "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        Signdaftar(account);
    }

    private void Signdaftar(GoogleSignInAccount account) {
//        progress
        dialog= KProgressHUD.create(Register.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Tunggu Sebentar")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        String usern,pas,emal,telp;
        usern=account.getEmail();
        pas=account.getDisplayName();
        emal=account.getEmail();
        telp="08xx";
        dialog.show();
        Model model= db.getRespo().create(Model.class);
        Call<Respon_User> call=model.register(usern,pas,emal,telp);
        call.enqueue(new Callback<Respon_User>() {
            @Override
            public void onResponse(Call<Respon_User> call, Response<Respon_User> response) {
                String msg=response.body().getMsg();
               if(response.isSuccessful()){
                dialog.dismiss();
                   startActivity(new Intent(Register.this,Login.class));
                   finish();
               }
            }

            @Override
            public void onFailure(Call<Respon_User> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(Register.this,"Periksa Jaringan Anda",Toast.LENGTH_SHORT).show();
            }
        });
    }

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
        btngp=(SignInButton) findViewById(R.id.bsign);
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
// Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);

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
        btngp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
    }
    private void signin() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onBackPressed() {

    }

    private void daftarUser() {
        String usern,pas,emal,telp;
        usern=user.getText().toString();
        pas="12345";
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
                    Toast.makeText(Register.this,"Login dengan Email anda Dan Password : 1234 ",Toast.LENGTH_SHORT).show();
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
