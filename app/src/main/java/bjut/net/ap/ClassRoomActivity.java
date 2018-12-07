package bjut.net.ap;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;

import bjut.net.ap.adapter.SignRecyclerAdapter;
import bjut.net.ap.config.GlobalConfig;
import bjut.net.ap.config.URLConfig;
import bjut.net.ap.model.Sign;
import bjut.net.ap.model.Tidings;
import bjut.net.ap.tool.CreateDialog;
import bjut.net.ap.ui.activity.MainActivity;
import bjut.net.ap.ui.base.BaseActivity;
import bjut.net.ap.ui.fragment.SignFragment;
import bjut.net.ap.ui.view.AddPopWindow2;
import bjut.net.ap.utils.DataUtils;
import bjut.net.ap.utils.DateUtils;
import bjut.net.ap.utils.FastJsonTools;
import bjut.net.ap.utils.ScreenUtils;
import bjut.net.ap.utils.SharedPreferencesUtil;
import bjut.net.ap.utils.TextUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

import static bjut.net.ap.utils.FastJsonTools.createJsonBean;
import static bjut.net.ap.utils.FastJsonTools.createJsonToListBean;

public class ClassRoomActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    @BindView(R.id.tv_coursename)
    TextView tv_coursename;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_begintime)
    TextView tv_begintime;
    @BindView(R.id.tv_teachername)
    TextView tv_teachername;

    @BindView(R.id.sign)
    Button sign;
    @BindView(R.id.chat)
    Button chat;
    @BindView(R.id.expand_button)
    Button expand_button;

    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;


    public static String coursename;   //课程名
    public static String begintime;    //上课时间
    //TODO 接受到下课时间用来判断是否继续进行抢占flag
    public static String week;      //上课的第几周
    public static String location;     //地点
    public static String teachername;   //教师名
    public static String beginweek;   //开始周
    public static String endweek;   //结束周
    public static int courseid;   //课程id
    //TODO 用来标记返回回来的最后一次签到记录
    public static String Lsno;
    public static String Lsname;
    public static String Lsigntime;
    public static int Lcourseid;
    public static String macAddress;


    public static String  UIN;

    private ScreenUtils utils;
    private SharedPreferencesUtil spUtils;
    RecyclerView mRecyclerView;
    private SignRecyclerAdapter mAdapter;
    private List<Sign> mSignList = new ArrayList<Sign>();//用来接受服务器返回的SignList 历史签到信息集合
    private CreateDialog createDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);
        ButterKnife.bind(this);
        //ImageView imageView = findViewById(R.id.image);
        initTitle("您好：" + GlobalConfig.getUSERNAME());
        //实例化utils和spUtils
        utils = new ScreenUtils(this);
        spUtils = new SharedPreferencesUtil(this);
        mRecyclerView = findViewById(R.id.id_recyclerview_history);
        initImageResource();
        initData();
        setData();
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        chat.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                /*   Intent i =new Intent(ClassRoomActivity.this,AboutActivity.class);
                startActivity(i);*/
                //目前聊天模块尚未开放
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(ClassRoomActivity.this);
                //    设置Title的内容
                builder.setTitle("温馨提示");
                //    设置Content来显示一个信息
                builder.setMessage("聊天模块尚在测试中，尚未开放");
                //    设置一个PositiveButton
                builder.setPositiveButton("知道了", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
//                        Toast.makeText(ClassRoomActivity.this, "positive: " + which, Toast.LENGTH_SHORT).show();
                    }
                });
              /*  //    设置一个NegativeButton
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(ClassRoomActivity.this, "negative: " + which, Toast.LENGTH_SHORT).show();
                    }
                });*/
                /*//    设置一个NeutralButton
                builder.setNeutralButton("忽略", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(ClassRoomActivity.this, "neutral: " + which, Toast.LENGTH_SHORT).show();
                    }
                });*/
                //    显示出该对话框
                builder.show();

            }
        });
        expand_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            }
        });
    }
    private void initImageResource(){
        // 初始化布局 View视图
        initViews();
        // Model数据
        initImageData();
        // Controller 控制器
        initAdapter();
        // 开启轮询
        new Thread(){
            public void run() {
                isRunning = true;
                while(isRunning){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println("设置当前位置: " + viewPager.getCurrentItem());
                            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        }
                    });
                }
            }
        }.start();
    }
    private void initViews() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(this);// 设置页面更新监听
        ll_point_container = findViewById(R.id.ll_point_container);
        tv_desc = findViewById(R.id.tv_desc);
    }

    private void initImageData() {
        // 图片资源id数组
        imageResIds = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};

        // 文本描述
        contentDescs = new String[]{
                "北京工业大学",
                "信息安全",
                "Objective—C语法基础",
                "物联网",
                "智慧城市导论"
        };
        // 初始化要展示的5个ImageView
        imageViewList = new ArrayList<>();
        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imageResIds[1]);
            imageViewList.add(imageView);
            // 加小白点, 指示器
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.selector_bg_point);
            layoutParams = new LinearLayout.LayoutParams(5, 5);
            if(i != 0)
                layoutParams.leftMargin = 20;
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }

    }
    private void initAdapter() {
        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;
        // 设置适配器
        viewPager.setAdapter(new MyAdapter());
        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        viewPager.setCurrentItem(5000000); // 设置到某个位置
    }


    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 3. 指定复用的判断逻辑, 固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
//			System.out.println("isViewFromObject: "+(view == object));
            // 当划到新的条目, 又返回来, view是否可以被复用.
            // 返回判断规则
            return view == object;
        }

        // 1. 返回要显示的条目内容, 创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.println("instantiateItem初始化: " + position);
            // container: 容器: ViewPager
            // position: 当前要显示条目的位置 0 -> 4

//			newPosition = position % 5
            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            container.addView(imageView);
            // b. 把View对象返回给框架, 适配器
            return imageView; // 必须重写, 否则报异常
        }

        // 2. 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // object 要销毁的对象
            System.out.println("destroyItem销毁: " + position);
            container.removeView((View)object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // 滚动时调用
    }
    @Override
    public void onPageSelected(int position) {
        // 新的条目被选中时调用
        System.out.println("onPageSelected: " + position);
        int newPosition = position % imageViewList.size();

        //设置文本
        tv_desc.setText(contentDescs[newPosition]);
//		for (int i = 0; i < ll_point_container.getChildCount(); i++) {
//			View childAt = ll_point_container.getChildAt(position);
//			childAt.setEnabled(position == i);
//		}
        // 把之前的禁用, 把最新的启用, 更新指示器
        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);
        // 记录之前的位置
        previousSelectedPosition  = newPosition;
    }
    @Override
    public void onPageScrollStateChanged(int state) {
        // 滚动状态变化时调用
    }
    private void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //判断获取的是否为空，如果不为空
            coursename = extras.getString("coursename");
            begintime = extras.getString("begintime");
            week = extras.getString("week");
            location = extras.getString("location");
            teachername = extras.getString("teachername");
            courseid = extras.getInt("courseid");
            beginweek = extras.getString("beginweek");
            endweek = extras.getString("endweek");
        }
        final ImageView iv_add_classroom = findViewById(R.id.iv_add_classroom);
        // 点击右边显示
        iv_add_classroom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddPopWindow2 addPopWindow2 = new AddPopWindow2(ClassRoomActivity.this);
                addPopWindow2.getId(courseid);
                addPopWindow2.getName(coursename);
                addPopWindow2.showPopupWindow2(iv_add_classroom);
            }

        });
    }
    /**
     * 初始化课程详情
     */
    private void setData() {
        sign.setText("签到");
        tv_coursename.setText("课程：" + coursename);
        tv_teachername.setText("教师：" + teachername);
        tv_location.setText("教室：" + location);
        tv_begintime.setText("上课时间："+"周"+ week+" "+begintime);
    }

    public void submit() {
        // TODO submit data to server...
        if (TextUtils.isEmpty(GlobalConfig.getUSERNO())) {
            //TODO 需要对学号进行校验，保证是合法用户
            utils.ToastInfo("学号不能为空 ");
        } else if (TextUtils.isEmpty(GlobalConfig.getUSERNAME())) {
            utils.ToastInfo("姓名不能为空");
        } else {
            ifEnterSign();
        }
    }
    public String getNewMac() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Permission Not Granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            WifiManager wifimanage = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiinfo = wifimanage.getConnectionInfo();
            macAddress = wifiinfo.getBSSID();//获取当前连接网络的mac地址;
        }
        return macAddress;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted keep going status
                    Log.d("mac", "PERMISSION GRANTED");
                    WifiManager wifimanage = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
                    WifiInfo wifiinfo = wifimanage.getConnectionInfo();
                    macAddress = wifiinfo.getBSSID();//获取当前连接网络的mac地址;
                } else {
                    Log.d("mac", "PERMISSION DENIED");
                }
        }
    }
    /**
     * 判断
     * 如果是历史界面提示界面过期，返回首页；
     * 如果不是历史界面请求服务器询问签到信息
     */
    private void ifEnterSign()
    {
        getNewMac();
        Log.e("LIWENXIN", "--------macAddress-------" + macAddress);
        for(int i = 0;i<GlobalConfig.APMACGROUP.size();i++)
        {
            if (GlobalConfig.APMACGROUP.get(i).trim().equals(macAddress))
            {
                //flag = 1;
                final ProgressDialog pd = new ProgressDialog(this);//进度对话框
                pd.setMessage(getResources().getString(R.string.Is_the_sign_on));
                pd.show();
                RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.SIGNON);
                final String signtime = DateUtils.getNowDateTime();
                //拼接传递给服务器的唯一标识
                UIN = "imei:"+GlobalConfig.getUIMEI()+":"+"androidID:"+GlobalConfig.getUANDROIDID()+":"+"serialNumber:"+
                        GlobalConfig.getUSERIALNUMBER()+":"+"insID:"+GlobalConfig.getUINSID()+":"+"DeviceID:"+GlobalConfig.getUDEVICEID();
                Log.e("ATWENXIN", "传递给服务器的UIN:" + UIN);
                // 1,先将传递的数据放入到 Sign对象中
                /*Sign signinfo = new Sign(courseid, GlobalConfig.getUSERNO(), GlobalConfig.getUSERNAME(), GlobalConfig.getUIMEI(), signtime, coursename,
                        teachername, begintime, DataUtils.getRequestSign(GlobalConfig.getUSERNO()),location);*/
                Sign signinfo = new Sign(courseid, GlobalConfig.getUSERNO(), GlobalConfig.getUSERNAME(),UIN, signtime, coursename,
                        teachername, begintime, DataUtils.getRequestSign(GlobalConfig.getUSERNO()),location);
                Log.i("zhangvalue", "服务器返回的课程信息:"+courseid +location);
                // 2.用FastJsonTools转化为json数据格式
                String json = FastJsonTools.createJsonString(signinfo);
                params.addBodyParameter("json", json);
                params.setAsJsonContent(true);
                params.setBodyContent(json);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("zhangvalue", "result返回来的信息：" + result);
                        final Tidings tidings = createJsonBean(result, Tidings.class);
                        Log.i("zhangvalue", "返回的状态:" + tidings.getStatus());
                        if (GlobalConfig.SUCCESS_FLAG.equals(tidings.getStatus())) {
                            utils.createConfirmDialog("成功签到", tidings.getMsg(), "好的", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    GlobalConfig.SUCCOURSE = coursename;
                                    GlobalConfig.SUCLOCATION = location;
                                    spUtils.writeData(GlobalConfig.SUCC,coursename);
                                    spUtils.writeData(GlobalConfig.SUCL,location);
                                }
                            });
                        } else if (GlobalConfig.ERROR_FLAG.equals(tidings.getStatus())) {
                            //重复签到
                            utils.createConfirmDialog("重复签到", tidings.getMsg(), "好的", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    GlobalConfig.SUCCOURSE = coursename;
                                    GlobalConfig.SUCLOCATION = location;
                                    spUtils.writeData(GlobalConfig.SUCC,coursename);
                                    spUtils.writeData(GlobalConfig.SUCL,location);
                                }
                            });
                        } else if (GlobalConfig.FAILURE_FLAG.equals(tidings.getStatus())) {
                            Log.i("zhangvalue", "LIWENXIN签到冲突返回的状态:" + tidings.getStatus());
                            Sign newData = createJsonBean(tidings.getT().toString(), Sign.class);
                            final String lasttime = newData.getSigntime();
                            final int courseid = newData.getCourseid();
                            final String lastsno= newData.getSno();
                            final String lastname= newData.getSname();
                            final String lastcoursename=newData.getCoursename();
                            Log.i("zhangvalue", "进入了:" + tidings.getStatus() + "判断里面" + "获取到lasttime:" + lasttime + "courseid为：" + courseid);
                            //在这个分支表示这门课程和最近一次的签到历史的时间有重叠属于违规签到，需要撤回这次签到
                            utils.createConfirmDialog(
                                    0,
                                    "签到冲突：", tidings.getMsg(),
                                    "撤销签到", "不撤销"
                                    , new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //先撤回签到
                                            /*cancel(GlobalConfig.getUIMEI(), lasttime, courseid,lastsno,lastname,lastcoursename);*/
                                            cancel(UIN, lasttime, courseid,lastsno,lastname,lastcoursename);
                                            dialog.dismiss();
                                            Log.i("zhangvalue", "LIWENXIN签到成功:" + coursename+"!!!!! " + location);
                                            GlobalConfig.SUCCOURSE = coursename;
                                            GlobalConfig.SUCLOCATION = location;
                                            spUtils.writeData(GlobalConfig.SUCC,coursename);
                                            spUtils.writeData(GlobalConfig.SUCL,location);
                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                        }
                        Log.i("zhangvalue", "返回的状态:" + tidings.getStatus() + "comehere");
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ScreenUtils.showToast("网络错误" + ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
                        pd.dismiss();//取消加载
                    }
                });
                break;
            }
        }
       /* if(flag ==0)
        {
            utils.createConfirmDialog("提示", "课程信息已经过期",
                    "好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(ClassRoomActivity.this,MainActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    });
        }
        else{
            flag = 0;
        }*/
    }
    /**
     * 进行签到记录撤销
     */
    public void cancel(String imei, String signtime, int courseid,String lastsno,String lastname, String lastcoursename) {
        // TODO submit data to server...
        if (TextUtils.isEmpty(imei) && TextUtils.isEmpty(signtime)) {
            utils.ToastInfo("信息不能为空");
        } else {
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage(getResources().getString(R.string.Is_the_cancel));
            pd.show();
            RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.SIGNOFF);
            // 1,先将传递的数据放入到Sign对象中
            /*Sign signInfo = new Sign(lastsno,lastname,GlobalConfig.getUIMEI(), signtime,lastcoursename, courseid);*/
            Sign signInfo = new Sign(lastsno,lastname,UIN, signtime,lastcoursename, courseid);
            Log.i("zhangvalue", "取消签到记录的信息为：sign" + "用户的IMEI" + UIN + "签到的时间：" + signtime + "签到的课程id" + courseid);
            // 2.用FastJsonTools转化为json数据格式
            String json = FastJsonTools.createJsonString(signInfo);
            params.addBodyParameter("json", json);
            params.setAsJsonContent(true);
            params.setBodyContent(json);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i("zhangvalue", result);
                    Tidings tidings = createJsonBean(result, Tidings.class);
                    Log.i("zhangvalue", "返回的状态:" + tidings.getStatus());
                    if (GlobalConfig.SUCCESS_FLAG.equals(tidings.getStatus())) {
                        //包含撤销签到成功的符号，表示撤销签到成功
                        //给出撤销签到成功提示
                        utils.createConfirmDialog("提示", tidings.getMsg(), "知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                    } else {
                        utils.createConfirmDialog("提示", tidings.getMsg(), "知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                    }
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ScreenUtils.showToast("网络错误" + ex.getMessage());
                }
                @Override
                public void onCancelled(CancelledException cex) {

                }
                @Override
                public void onFinished() {
                    pd.dismiss();//取消加载
                }
            });
        }
    }

    //TODO 防止用户出现卸载重装的情况，需要服务器端数据来判断
    //TODO从服务器获取最近其次的签到信息
    public void getLatestSign() {
        // TODO submit data to server...
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.Is_the_search));
        pd.show();
        RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.GETLATESTSIGN);
        params.addBodyParameter("imei", GlobalConfig.getUIMEI());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("zhangvalue", "result 最近一条签到记录----返回来的信息：" + result);
                Tidings tidings = createJsonBean(result, Tidings.class);
                if (GlobalConfig.SUCCESS_FLAG.equals(tidings.getStatus())) {
                    //拿到最近一次签到记录
                    List<Sign> newData = createJsonToListBean(tidings.getT().toString(), Sign.class);
                    //TODO 当最近一次记录不为空时
                    if (newData != null) {
                        //返回回来只有一条数据去除其中的signtime
                        Lsigntime = newData.get(0).getSigntime();
                        Log.i("zhangvalue", "获取到的最近一次记录：" + Lsigntime);
                        Lsno = newData.get(0).getSno();
                        Lsname = newData.get(0).getSname();
                        Lcourseid = newData.get(0).getCourseid();
                        //给出返回最近一次签到记录成功提示
                        utils.createConfirmDialog("提示", tidings.getMsg(), "知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                    }

                } else {
                    utils.createConfirmDialog("提示", tidings.getMsg(), "知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ScreenUtils.showToast("网络错误" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                pd.dismiss();//取消加载
            }
        });
    }
}
