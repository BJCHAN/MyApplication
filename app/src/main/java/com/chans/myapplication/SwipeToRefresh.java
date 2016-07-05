package com.chans.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chans.SuperSwipeRefreshLayout.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * description :
 * Created by Chans Renhenet
 * 2015/9/1
 */
public class SwipeToRefresh extends Activity {

    //    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    //	private ListView activity_main_list;
    private CatNamesRecyclerViewAdapter mCatNamesRecyclerViewAdapter;
    private ListAdapter ListAdapter;
    List<String> mCatNames;

    private SuperSwipeRefreshLayout swipeRefreshLayout;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private ImageView image_load;

    private AnimationDrawable secondAnimation;
    private AnimationDrawable loadAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
//		activity_main_list = (ListView) findViewById(R.id.activity_main_list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        setupAdapter();

        // init SuperSwipeRefreshLayout
        swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setHeaderViewBackgroundColor(0xffffffff);
        swipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        swipeRefreshLayout.setTargetScrollWithLayout(true);
        swipeRefreshLayout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
                    @Override
                    public void onRefresh() {
                        secondAnimation.start();
                        textView.setText("正在刷新");
//                        imageView.setVisibility(View.GONE);
//                        progressBar.setVisibility(View.VISIBLE);
                        loadAnimation.start();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mCatNames != null)
                                    mCatNames.clear();
                                mCatNames.add("3");
                                mCatNames.add("3");
                                mCatNames.add("3");
                                mCatNames.add("3");
                                mCatNames.add("3");
                                mCatNames.add("3");
                                mCatNames.add("3");
                                mCatNames.add("3");
                                mCatNames.add("3");
                                mCatNames.add("3");
                                if (mCatNamesRecyclerViewAdapter != null)
                                    mCatNamesRecyclerViewAdapter.notifyDataSetChanged();
                                else {
                                    mCatNamesRecyclerViewAdapter = new CatNamesRecyclerViewAdapter(SwipeToRefresh.this, mCatNames);
                                    mRecyclerView.setAdapter(mCatNamesRecyclerViewAdapter);
                                }
                                secondAnimation.stop();
                                loadAnimation.stop();
                                swipeRefreshLayout.setRefreshing(false);
//                                progressBar.setVisibility(View.GONE);
                            }
                        }, 2000);
                    }

                    @Override
                    public void onPullDistance(int distance) {
                        // pull distance
                        System.out.println("distance==" + distance);
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        if (!enable) {
                            secondAnimation.stop();
                            loadAnimation.stop();
                        }
                        textView.setText(enable ? "松开刷新" : "下拉刷新");
//                        imageView.setVisibility(View.VISIBLE);
//                        imageView.setRotation(enable ? 180 : 0);
                    }
                });

//        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.gray, R.color.blue);
//
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        setupAdapter();
//                        if (mCatNames != null)
//                            mCatNames.clear();
//                        mCatNames.add("3");
//                        mCatNames.add("3");
//                        mCatNames.add("3");
//                        mCatNames.add("3");
//                        mCatNames.add("3");
//                        mCatNames.add("3");
//                        mCatNames.add("3");
//                        mCatNames.add("3");
//                        mCatNames.add("3");
//                        mCatNames.add("3");
//                        if (mCatNamesRecyclerViewAdapter != null)
//                            mCatNamesRecyclerViewAdapter.notifyDataSetChanged();
//                        else {
//                            mCatNamesRecyclerViewAdapter = new CatNamesRecyclerViewAdapter(SwipeToRefresh.this, mCatNames);
//                            mRecyclerView.setAdapter(mCatNamesRecyclerViewAdapter);
//                        }
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 2500);
//            }
//        });
    }

    private void setupAdapter() {
        mCatNames = new ArrayList<>();
        randomizeCatNames();
        mCatNamesRecyclerViewAdapter = new CatNamesRecyclerViewAdapter(this, mCatNames);
        mRecyclerView.setAdapter(mCatNamesRecyclerViewAdapter);
//		ListAdapter = new ListAdapter();
//		activity_main_list.setAdapter(ListAdapter);
    }

    public void randomizeCatNames() {
//        mCatNames = Arrays.asList(getCatNamesResource());
        for (int i = 0; i < 10; i++)
            mCatNames.add("" + i);
//        Collections.shuffle(mCatNames);
    }

    private String[] getCatNamesResource() {
        return this.getResources().getStringArray(R.array.cat_names);
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(swipeRefreshLayout.getContext())
                .inflate(R.layout.layout_head, null);
        progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
        progressBar.setVisibility(View.GONE);
        textView = (TextView) headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        textView.setVisibility(View.GONE);
        imageView = (ImageView) headerView.findViewById(R.id.image_view);
//        imageView.setVisibility(View.VISIBLE);
//        imageView.setImageResource(R.mipmap.down_arrow);
        imageView.setBackgroundResource(R.drawable.loading_step_animation);
        loadAnimation = (AnimationDrawable) imageView.getBackground();

        image_load = (ImageView) headerView.findViewById(R.id.image_load);
        //添加动画
        image_load.setBackgroundResource(R.drawable.second_step_animation);
        secondAnimation = (AnimationDrawable) image_load.getBackground();
        return headerView;
    }

    //简单适配
    class ListAdapter extends BaseAdapter {
        List<String> mCatNames;

        public ListAdapter() {
            randomizeCatNames();
        }

        public void randomizeCatNames() {
            mCatNames = Arrays.asList(getCatNamesResource());
            Collections.shuffle(mCatNames);
        }

        private String[] getCatNamesResource() {
            return SwipeToRefresh.this.getResources().getStringArray(R.array.cat_names);
        }

        @Override
        public int getCount() {
            return mCatNames.size();
        }

        @Override
        public Object getItem(int position) {
            return mCatNames.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(SwipeToRefresh.this).inflate(R.layout.list_item, parent, false);
                vh.icon = (TextView) convertView.findViewById(R.id.icon);
                vh.time = (TextView) convertView.findViewById(R.id.time);
                vh.type = (TextView) convertView.findViewById(R.id.type);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            vh.time.setText(mCatNames.get(position));

            return convertView;
        }

        class ViewHolder {
            TextView icon;
            TextView time;
            TextView type;
        }
    }

    //自定义下拉样式
    public class CustomView extends View {

        private Bitmap goods;
        private Bitmap people;
        private Bitmap peopleWithGoods;
        private int measuredWidth;
        private int measuredHeight;
        private float mCurrentProgress;
        private int mCurrentAlpha;
        private Paint mPaint;
        private Bitmap scaledPeople;
        private Bitmap scaledGoods;

        public CustomView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public CustomView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public CustomView(Context context) {
            super(context);
            init();
        }

        private void init() {
            //包裹bitmap
            goods = BitmapFactory.decodeResource(getResources(), R.mipmap.app_refresh_goods_0);
            //快递小哥bitmap
            people = BitmapFactory.decodeResource(getResources(), R.mipmap.app_refresh_people_0);
            //这是后面动画中的最后一张图片，拿这张图片的作用是用它的宽高来测量
            //我们这个自定义View的宽高
            peopleWithGoods = BitmapFactory.decodeResource(getResources(), R.mipmap.app_refresh_people_3);
            //来个画笔，我们注意到快递小哥和包裹都有一个渐变效果的，我们用
            //mPaint.setAlpha来实现这个渐变的效果
            mPaint = new Paint();
            //首先设置为完全透明
            mPaint.setAlpha(0);
        }

        /**
         * 测量方法
         *
         * @param widthMeasureSpec
         * @param heightMeasureSpec
         */
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        }

        //测量宽度
        private int measureWidth(int widthMeasureSpec) {
            int result = 0;
            int size = MeasureSpec.getSize(widthMeasureSpec);
            int mode = MeasureSpec.getMode(widthMeasureSpec);
            if (MeasureSpec.EXACTLY == mode) {
                result = size;
            } else {
                result = peopleWithGoods.getWidth();
                if (MeasureSpec.AT_MOST == mode) {
                    result = Math.min(result, size);
                }
            }
            return result;
        }

        //测量高度
        private int measureHeight(int heightMeasureSpec) {
            int result = 0;
            int size = MeasureSpec.getSize(heightMeasureSpec);
            int mode = MeasureSpec.getMode(heightMeasureSpec);
            if (MeasureSpec.EXACTLY == mode) {
                result = size;
            } else {
                result = peopleWithGoods.getHeight();
                if (MeasureSpec.AT_MOST == mode) {
                    result = Math.min(result, size);
                }
            }
            return result;
        }

        //在这里面拿到测量后的宽和高，w就是测量后的宽，h是测量后的高
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            measuredWidth = w;
            measuredHeight = h;
            //根据测量后的宽高来对快递小哥做一个缩放
            scaledPeople = Bitmap.createScaledBitmap(people, measuredWidth, measuredHeight, true);
            //根据测量后的宽高来对快递包裹做一个缩放
            scaledGoods = Bitmap.createScaledBitmap(goods, scaledPeople.getWidth() * 10 / 27, scaledPeople.getHeight() / 5, true);
        }

        /**
         * 绘制方法
         *
         * @param canvas
         */
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //由于包裹和快递小哥要分别来画，所以使用save和restore方法
            //save
            //画包裹
            //restore
            //save
            //画小哥
            //restore
            canvas.save();
            canvas.scale(mCurrentProgress, mCurrentProgress, measuredWidth - scaledGoods.getWidth() / 2, measuredHeight / 2);
            mPaint.setAlpha(mCurrentAlpha);
            canvas.drawBitmap(scaledGoods, measuredWidth - scaledGoods.getWidth(), measuredHeight / 2 - scaledGoods.getHeight() / 2, mPaint);
            canvas.restore();
            canvas.save();
            canvas.scale(mCurrentProgress, mCurrentProgress, 0, measuredHeight / 2);
            mPaint.setAlpha(mCurrentAlpha);
            canvas.drawBitmap(scaledPeople, 0, 0, mPaint);
            canvas.restore();
        }

        /**
         * 根据进度来对小哥和包裹进行缩放
         *
         * @param currentProgress
         */
        public void setCurrentProgress(float currentProgress) {
            this.mCurrentProgress = currentProgress;
            this.mCurrentAlpha = (int) (currentProgress * 255);
        }
    }
}
