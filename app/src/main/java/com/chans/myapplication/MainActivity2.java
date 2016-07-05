package com.chans.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.chans.verticalViewpage.VerticalLinearLayout;

/**
 * description :VerticalLinearLayout 测试案例
 * Created by Chans Renhenet
 * 2015/11/25
 */
public class MainActivity2 extends Activity {
    private VerticalLinearLayout mMianLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mMianLayout = (VerticalLinearLayout) findViewById(R.id.id_main_ly);
        mMianLayout.setOnPageChangeListener(new VerticalLinearLayout.OnPageChangeListener() {
            @Override
            public void onPageChange(int currentPage) {
//              mMianLayout.getChildAt(currentPage);
                Toast.makeText(MainActivity2.this, "第" + (currentPage + 1) + "页", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
