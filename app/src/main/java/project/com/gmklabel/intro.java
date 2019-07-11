package project.com.gmklabel;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

import project.com.gmklabel.Intro.intro_app;

public class intro extends AppIntro {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_intro);
//
//    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        super.init(savedInstanceState);
        addSlide(intro_app.newInstance(R.layout.intro_home));
        addSlide(intro_app.newInstance(R.layout.intro_reg));
        addSlide(intro_app.newInstance(R.layout.intro_profile));
        addSlide(intro_app.newInstance(R.layout.intro_help));
        addSlide(intro_app.newInstance(R.layout.intro_pesan));
        showStatusBar(true);
        showSkipButton(true);
        setVibrate(true);
        setVibrateIntensity(30);
        setDepthAnimation();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Toast.makeText(getApplicationContext(),"Skip", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}
