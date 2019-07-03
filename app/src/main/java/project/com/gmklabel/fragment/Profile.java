package project.com.gmklabel.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import de.hdodenhof.circleimageview.CircleImageView;
import project.com.gmklabel.MainActivity;
import project.com.gmklabel.R;
import project.com.gmklabel.User.Login;
import project.com.gmklabel.User.Register;
import project.com.gmklabel.User.Setting_Profile;
import project.com.gmklabel.User.Setting_login;
import project.com.gmklabel.User.User_config;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
private Button btn_login,keluar,daftar;
private LinearLayout not_login,yes_login;
private TextView tv_profile,tv_ubahPass,tv_bantuan;
private Context context=getActivity();
private CircleImageView imgh;
private GoogleSignInClient googleSignInClient;
    public Profile() {
        // Required empty public constructor
    }
    public static Profile newInstance(){
        return  new Profile();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Context context=getActivity();
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        btn_login=(Button) view.findViewById(R.id.login);
        keluar=(Button) view.findViewById(R.id.logout);
        daftar=(Button) view.findViewById(R.id.daftar);
        not_login=(LinearLayout) view.findViewById(R.id.not_login);
        yes_login=(LinearLayout) view.findViewById(R.id.yes_login);
        tv_profile=(TextView) view.findViewById(R.id.ubahprofile);
        tv_ubahPass=(TextView) view.findViewById(R.id.ubahpass);
        tv_bantuan=(TextView) view.findViewById(R.id.bantuan);
        imgh=(CircleImageView) view.findViewById(R.id.image);

// Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
//        load gambar
        Glide.with(getContext()).load(account.getPhotoUrl()).into(imgh);

        if(User_config.getmInstance(context).isLogedIn()||account.getId()!=null){

        }else{
            not_login.setVisibility(View.VISIBLE);
            yes_login.setVisibility(View.GONE);
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Login.class);
                context.startActivity(intent);
                ((FragmentActivity) context).finish();
            }
        });
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Register.class));
            }
        });
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Signout From google
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        User_config.getmInstance(context).logout();
                        yes_login.setVisibility(View.GONE);
                        not_login.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .addToBackStack("4")
                        .replace(R.id.frame, Setting_Profile.newInstance())
                        .commit();
            }
        });
        tv_ubahPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .addToBackStack("4")
                        .replace(R.id.frame, Setting_login.newInstance())
                        .commit();
            }
        });
        tv_bantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return  view;

    }

}
