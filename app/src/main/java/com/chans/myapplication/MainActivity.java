package com.chans.myapplication;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chans.countDownTimerView.RushBuyCountDownTimerView;

import java.util.List;

public class MainActivity extends Activity {

    private TextView tv, tv2;
    private EditText edt;
    private Button btn, refresh, file;
    private AsyncQueryHandler asyncQuery;
    CustomDialog customDialog;

    private RushBuyCountDownTimerView timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        edt = (EditText) findViewById(R.id.edt);
        btn = (Button) findViewById(R.id.btn);
        refresh = (Button) findViewById(R.id.refresh);
        file = (Button) findViewById(R.id.file);

        timerView = (RushBuyCountDownTimerView) findViewById(R.id.timerView);
        // 设置时间(hour,min,sec)
        timerView.setTime(0, 0, 1, 19);
        // 开始倒计时
        timerView.start();

        timerView.setOverTime(new RushBuyCountDownTimerView.OverTimeInterface() {
            @Override
            public void overTime() {
                //结束回调
                timerView.setVisibility(View.GONE);
            }
        });

        System.out.println("classname===" + getClass().getName() + ";simple=" + getClass().getSimpleName());

        tv.setText("abcdefg z,啊不才的额发个在adc，啊不错的发工资fg。 ABCDSAD、不错的，阿房宫、fdafgA。");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpannableString str = new HighlightTextUtil().toHighlight(MainActivity.this, edt.getText().toString().trim(),
                        tv.getText().toString());
                tv2.setText(str);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customDialog.createDialog(MainActivity.this, "温馨提示", R.drawable.aa, null, "确定", "您的手机已欠费，请细交话费...");
            }
        });

        //点击之后的操作
        customDialog = new CustomDialog(MainActivity.this, new CustomDialog.MyDialogClickListener() {
            @Override
            public void onclick(int id) {
                if (id == CustomDialog.SURE_BUTTON) {
                    Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, SwipeToRefresh.class));
                } else {
                    Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                }
            }
        });

        file.setText("SHARE");
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenApp("cn.renhe.zanfuwu", MainActivity.this);
                //				startActivity(new Intent(MainActivity.this, FindFiles.class));
//                startActivityForResult(new Intent(MainActivity.this, FindFiles.class), 111);
            }
        });

        //测试获取手机通讯录的所有内容
        asyncQuery = new MyAsyncQueryHandler(this.getContentResolver());
        Uri uri = Uri.parse("content://com.android.contacts/data/phones");
        String[] projection = {"_id", "name_raw_contact_id", "display_name", "data1", "contact_last_updated_timestamp",
                "sort_key"};
//        		String condition = "contact_last_updated_timestamp>" + "0";
        String condition = "account_type = 'heliao'";
        asyncQuery.startQuery(0, null, uri, null, condition, null, "contact_last_updated_timestamp COLLATE LOCALIZED asc");
        /******查email******/
        //        Uri uri1 = Uri.parse("content://com.android.contacts/data/emails");
        //		asyncQuery.startQuery(0, null, uri1, projection, null, null, "contact_last_updated_timestamp COLLATE LOCALIZED asc");
        /********查地址********/
        //        Uri uri2 = Uri.parse("content://com.android.contacts/data/postals");
        //        String[] projection2 = {"raw_contact_id", "display_name", "data1",
        //                "contact_last_updated_timestamp", "sort_key"};
        //        asyncQuery.startQuery(0, null, uri2, projection2, null, null, null);
        //		startService(new Intent(this, myService.class));

        Intent i_getValue = getIntent();
        String action = i_getValue.getAction();

        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri1 = i_getValue.getData();
            if (uri1 != null) {
                System.out.println("uri = " + uri1);
                //				String name = uri.getQueryParameter("name");
                //				String age = uri.getQueryParameter("age");
            }
        }

    }

    public static void OpenApp(String packageName, Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "应用未安装", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://www.baidu.com");
            intent.setData(content_url);
            context.startActivity(intent);
            return;
        }
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);
        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String className = ri.activityInfo.name;
            className = "cn.renhe.zanfuwu.activity.LoginActivity";
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            intent.putExtra("params", "zanfuwu");
            intent.putExtra("externalApp", true);
            context.startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_settings);
        PlusActionProvider shareActionProvider = (PlusActionProvider) item.getActionProvider();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 查找手机通讯录
     */
    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        ContentResolver contentResolver;
        List<ContactUpLoadBean> list;

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
            this.contentResolver = cr;
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                int columnNumber = cursor.getColumnCount();
                for (int i = 0; i < columnNumber; i++) {
                    String temp = cursor.getColumnName(i);
                    System.out.println("listColumnNames" + i + "--\t" + temp);
                }
                //				cursor.moveToFirst();
                //				for (int i = 0; i < cursor.getCount(); i++) {
                //					cursor.moveToPosition(i);
                //					System.out.println(cursor.getString(1) + "last Time===" + cursor.getString(4));
                //				}
                cursor.close();
            }
            //				/***只传id ， 名字和号码**/
            //				list = new ArrayList<>();
            //				System.out.println("-----current-----" + System.currentTimeMillis());
            //				cursor.moveToFirst();
            //				for (int i = 0; i < cursor.getCount(); i++) {
            //					ContactUpLoadBean cv = new ContactUpLoadBean();
            //					cursor.moveToPosition(i);
            //					String[] numbers = new String[] { cursor.getString(3) };
            //					cv.setId(cursor.getString(1));
            //					cv.setName(cursor.getString(2));
            //					cv.setMobiles(numbers);
            //					System.out.println("-----last-----" + cursor.getString(1) + ":" + cursor.getString(4));
            //					list.add(cv);
            //				}
            //				//合并相同的id；
            //				for (int i = 0; i < list.size(); i++) {
            //					for (int j = list.size() - 1; j > i; j--)
            //						if (list.get(j).getId().equals(list.get(i).getId())) {
            //							String[] numbers = list.get(i).getMobiles();
            //							String[] numbers2 = list.get(j).getMobiles();
            //							list.get(i).setMobiles(ArrayUtils.addAll(numbers, numbers2));
            //							list.remove(j);
            //						}
            //				}
            //				cursor.close();
            //			} else {
            //			}
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String text = "requestCode---" + requestCode + "-------resultCode-------" + resultCode + "---data-----"
                + data.getExtras().getString("test");
        Toast.makeText(MainActivity.this, text, 0).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerView.stop();
    }
}
