//package com.chans.myapplication;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Message;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.widget.ImageView;
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//
///**
// * description :
// * Created by Chan RenHeNet
// * 2016/1/8
// */
//public class MainActivity3 extends Activity {
//
//    private static final String LOG_TAG = "MainActivity";
//    private ImageHandler handler = new ImageHandler(new WeakReference<>(this));
//    private ViewPager viewPager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main3);
//        //初始化iewPager的内容
//        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        ImageView view1 = (ImageView) inflater.inflate(R.layout.item, null);
//        ImageView view2 = (ImageView) inflater.inflate(R.layout.item, null);
//        ImageView view3 = (ImageView) inflater.inflate(R.layout.item, null);
//        view1.setImageResource(R.drawable.ics);
//        view2.setImageResource(R.drawable.jellybean);
//        view3.setImageResource(R.drawable.kitkat);
//        ArrayList<ImageView> views = new ArrayList<ImageView>();
//        views.add(view1);
//        views.add(view2);
//        views.add(view3);
//        viewPager.setAdapter(new ImageAdapter(views));
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            //配合Adapter的currentItem字段进行设置。
//            @Override
//            public void onPageSelected(int arg0) {
//                handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, arg0, 0));
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//            }
//
//            //覆写该方法实现轮播效果的暂停和恢复
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//                switch (arg0) {
//                    case ViewPager.SCROLL_STATE_DRAGGING:
//                        handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
//                        break;
//                    case ViewPager.SCROLL_STATE_IDLE:
//                        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//        viewPager.setCurrentItem(Integer.MAX_VALUE/2);//默认在中间，使用户看不到边界
//        //开始轮播效果
//        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
//    }//end of onCreate
//}//end of MainActivity