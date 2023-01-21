package birzeit.edu.labandroidfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import pl.droidsonroids.gif.GifImageView;


/**
 * this is The Splash Screen
 **/
public class MainActivity extends AppCompatActivity {

    private Animation animation1;
    private GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        animation1 = AnimationUtils.loadAnimation(this,R.anim.animation1);

        gifImageView = findViewById(R.id.gifImageView);
        gifImageView.setImageResource(R.drawable.airplane);
        gifImageView.startAnimation(animation1);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}