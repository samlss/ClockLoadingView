package com.iigo.clockloadingview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.iigo.library.ClockLoadingView;

public class MainActivity extends AppCompatActivity {
    private ClockLoadingView clockLoadingView1;
    private ClockLoadingView clockLoadingView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clockLoadingView1 = findViewById(R.id.clv_loading1);
        clockLoadingView2 = findViewById(R.id.clv_loading2);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing()){
            clockLoadingView1.release();
            clockLoadingView2.release();
        }
    }

    public void onStart(View view) {
        clockLoadingView1.start();
        clockLoadingView2.start();
    }

    public void onStop(View view) {
        clockLoadingView1.stop();
        clockLoadingView2.stop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //手动设置颜色
        //Manually setting the color
        clockLoadingView1.setCircleColor(getResources().getColor(R.color.green));
        clockLoadingView1.setCenterColor(getResources().getColor(R.color.yellow));
        clockLoadingView1.setHourHandColor(getResources().getColor(R.color.orange));
        clockLoadingView1.setMinHandColor(getResources().getColor(R.color.red));

        //动态改变大小
        //change size
//        ViewGroup.LayoutParams params = clockLoadingView1.getLayoutParams();
//        params.width = 50;
//        params.height = 50;
//        clockLoadingView1.setLayoutParams(params);

        return super.onKeyDown(keyCode, event);
    }
}
