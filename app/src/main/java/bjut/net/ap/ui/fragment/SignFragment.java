package bjut.net.ap.ui.fragment;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import bjut.net.ap.ClassRoomActivity;
import bjut.net.ap.R;
import bjut.net.ap.adapter.MeetingRecyclerAdapter;
import bjut.net.ap.adapter.RecyclerAdapter;
import bjut.net.ap.config.GlobalConfig;
import bjut.net.ap.config.URLConfig;
import bjut.net.ap.model.Course;
import bjut.net.ap.model.Meeting;
import bjut.net.ap.model.MeetingSign;
import bjut.net.ap.tool.CreateDialog;
import bjut.net.ap.model.Tidings;
import bjut.net.ap.model.Version;
import bjut.net.ap.tool.DeviceUtils;
import bjut.net.ap.ui.base.BaseFragment;
import bjut.net.ap.ui.view.MyDecoration;
import bjut.net.ap.utils.AppUtils;
import bjut.net.ap.utils.DataUtils;
import bjut.net.ap.utils.DateUtils;
import bjut.net.ap.utils.FastJsonTools;
import bjut.net.ap.utils.InstallationID;
import bjut.net.ap.utils.NetUtils;
import bjut.net.ap.utils.NetworkUtility;
import bjut.net.ap.utils.ScreenUtils;
import bjut.net.ap.utils.SharedPreferencesUtil;
import bjut.net.ap.utils.SharedPrefsStrListUtil;
import bjut.net.ap.utils.TextUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import static android.content.Context.WIFI_SERVICE;
import static bjut.net.ap.ClassRoomActivity.begintime;
import static bjut.net.ap.utils.FastJsonTools.createJsonBean;
import static bjut.net.ap.utils.FastJsonTools.createJsonToListBean;
import static bjut.net.ap.utils.FastJsonTools.createJsonToListString;

public class SignFragment extends BaseFragment {
    @BindView(R.id.et_sno)
    EditText et_sno;
    @BindView(R.id.et_username)
    EditText et_username;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "zhangvalue";

    public static String IP;             //本机IP
    public static String MYIMEI;           //本机IMEI
    public static String NAME = "";          //连接WiFi名称
    public static String macAddress;     //连接WiFi的MAC
    String sno = "";//用来标记最新的学号
    String username = "";//用来标记最新的用户名
    public static String meetingname ;
    public static String teachername;
    public static String checkcode ;
    public static int meetingid;
    public static String endtime ;
    public static String courselocation;
    public static String location ;
    String signtime;
    String optionSignType = "课程签到";
    private ScreenUtils utils;
    private SharedPreferencesUtil spUtils ;
    public static SharedPrefsStrListUtil spListUtils;
    int flag = 0;
    SwipeRefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private MeetingRecyclerAdapter mMAdapter;
    private List<Course> mCoursesList = new ArrayList<>();//用来接受服务器返回courselist 集合
    private List<Meeting> mMeetingList = new ArrayList();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private CreateDialog createDialog;

    public static InstallationID InsID = new InstallationID();
    public static String insID;
    public static DeviceUtils Device = new DeviceUtils();
    public static String DeviceID;
    public SignFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SignFragment newInstance(String param1, String param2) {
        SignFragment fragment = new SignFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        DeviceID = Device.getUniqueId(getActivity());
        insID = InsID.id(getActivity());
        GlobalConfig.UINSID = insID;
        GlobalConfig.UDEVICEID = Device.id;
        GlobalConfig.UANDROIDID = Device.androidID;
        GlobalConfig.USERIALNUMBER = Device.serialNumber;
        spUtils = new SharedPreferencesUtil(getContext());

        spUtils.writeData(GlobalConfig.INSID, insID);
        spUtils.writeData(GlobalConfig.DEVICEID, DeviceID);
        spUtils.writeData(GlobalConfig.ANDROIDID, Device.androidID);
        spUtils.writeData(GlobalConfig.SERIALNUMBER, Device.serialNumber);
    }
    /**
     * 从服务器获取当前所在位置的apMac组
     */
    public void getApmac() {
        RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.GETAPGROUP);
        params.setCharset("UTF-8");
        params.addBodyParameter("mac", GlobalConfig.getMAC());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("LIWENXIN", "---------------service---Ap组组的获取----------------" + result);
                if (result != null) {
                    createJsonToListString(result);//解析数据
                }
                for (int x = 0; x < createJsonToListString(result).size(); x++) {
                    String s = createJsonToListString(result).get(x).toString();
                    GlobalConfig.APMACGROUP.add(s);
                    spListUtils.putStrListValue(getContext(), GlobalConfig.APGROUP, GlobalConfig.APMACGROUP);
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sign, container, false);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if(bundle!=null){
            optionSignType = bundle.getString("optionSignType");
            Log.i("ATWENXIN", "----------------签到类型是————————"+optionSignType);
        }
        ButterKnife.bind(this, view);
        utils = new ScreenUtils(getContext());

        Log.e("LIWENXIN", "---------------Ap组组的获取----------------" + spListUtils.getStrListValue(getContext(), GlobalConfig.APGROUP));
        getApmac();//请求Apmac组的数据并存储
        initData();//如果键值对里面已经存储了用户名和学号就自动填充
        Log.i("zhangvalue", "----------------到达了onCreateView");
        if(optionSignType.equals( "课程签到" )) {
            switch (NetworkUtility.getNetworkType( getContext() )) {
                case NetworkUtility.CONNECT_TYPE_WIFI: {
                    Toast.makeText( getContext(), "当前处于wifi状态：可用",
                            Toast.LENGTH_SHORT ).show();
                    //当链接上wifi的时候开始获取mac信息
                    NAME = NetworkUtility.GetWifiName( getActivity() );
                    utils.showLoading();
                    //获取mac的过程可能会阻塞，所以使用线程来获取
                    new MacAsyncTask().execute();
                    utils.cancleLoading();
                    Log.i( "zhangvalue", "----------------GetWifiName----------------" + NAME );
                }
                break;
                default:
                    //如果没有链接wifi的情况下就会去打开wifi
                    if (!NetworkUtility.WifiState( getActivity() )) {
                        utils.createConfirmDialog( "提示", "请授予开启wifi权限 并链接上wifi 保证课程正常获取", "授权开启",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        NetworkUtility.OpenWifi( getActivity() );
                                        dialog.dismiss();
                                    }
                                } );
                    } else {
                        utils.createConfirmDialog( "提示", "请链接上wifi 保证课程正常获取", "好的",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                } );
                    }
            }
            mRecyclerView = view.findViewById( R.id.id_recyclerview );
            mRefreshLayout = view.findViewById( R.id.layout_swipe_refresh );
            mRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
                public void onRefresh() {
                    //在wifi打开的情况下才开始判断是否链接bjut_wifi,在进行后续操作
                    if (NetworkUtility.WifiState( getActivity() )) {
                        //TODO 在刷新的时候先要重新获取mac
                        //获取mac的过程可能会阻塞，所以使用线程来获取
                        utils.showLoading();
                        new MacAsyncTask().execute();
                        getApmac();
                        utils.cancleLoading();
                        //在重新加载之前先清除之前的数据，以免重叠数据
                        mCoursesList.clear();
                        loadCourseData();//去服务器请求数据
                        if (mCoursesList.size() > 0) {//只有当拿到了数据才开始改变适配器里面的数据
                            mAdapter.notifyDataSetChanged();
                            mRefreshLayout.setRefreshing( false );
                        } else {//当没有数据就停止继续刷新
                            mRefreshLayout.setRefreshing( false );
                        }
                    } else {
                        //开启wifi
                        NetworkUtility.OpenWifi( getActivity() );
                    }
                }
            } );
            ButterKnife.bind( getContext(), view );
            initView();
            if (NetworkUtility.WifiState( getActivity() ) && NAME.equals( "\"bjut_wifi\"" )) {
                loadCourseData();
            }
        }else if(optionSignType.equals( "会议签到" ))
        {
            switch (NetworkUtility.getNetworkType( getContext() )) {
                case NetworkUtility.CONNECT_TYPE_WIFI: {
                    Toast.makeText( getContext(), "当前处于wifi状态：可用",
                            Toast.LENGTH_SHORT ).show();
                    NAME = NetworkUtility.GetWifiName( getActivity() );
                    utils.showLoading();
                    new MacAsyncTask().execute();//获取mac的过程可能会阻塞，所以使用线程来获取
                    utils.cancleLoading();
                }
                break;
                default:
                    //如果没有链接wifi的情况下就会去打开wifi
                    if (!NetworkUtility.WifiState( getActivity() )) {
                        utils.createConfirmDialog( "提示", "请授予开启wifi权限 并链接上wifi 保证课程正常获取", "授权开启",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        NetworkUtility.OpenWifi( getActivity() );
                                        dialog.dismiss();
                                    }
                                } );
                    } else {
                        utils.createConfirmDialog( "提示", "请链接上wifi 保证信息正常获取", "好的",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                } );
                    }
            }
            mRecyclerView = view.findViewById( R.id.id_recyclerview );
            mRefreshLayout = view.findViewById( R.id.layout_swipe_refresh );
            mRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
                public void onRefresh() {
                    //在wifi打开的情况下才开始判断是否链接bjut_wifi,在进行后续操作
                    if (NetworkUtility.WifiState( getActivity() )) {
                        //TODO 在刷新的时候先要重新获取mac
                        //获取mac的过程可能会阻塞，所以使用线程来获取
                        utils.showLoading();
                        new MacAsyncTask().execute();
                        utils.cancleLoading();
                        //在重新加载之前先清除之前的数据，以免重叠数据
                        mMeetingList.clear();
                        loadMeetingData();//去服务器请求数据
                        if (mMeetingList.size() > 0) {//只有当拿到了数据才开始改变适配器里面的数据
                            mMAdapter.notifyDataSetChanged();
                            mRefreshLayout.setRefreshing( false );
                        } else {//当没有数据就停止继续刷新
                            mRefreshLayout.setRefreshing( false );
                        }
                    } else {
                        //开启wifi
                        NetworkUtility.OpenWifi( getActivity() );
                    }
                }
            } );
            ButterKnife.bind( getContext(), view );
            initView();
            if (NetworkUtility.WifiState( getActivity() ) && NAME.equals( "\"bjut_wifi\"" )) {
                loadMeetingData();
            }
        }
        return view;
    }

    /**
     * 初始化用户名以及学号
     */
    private void initData() {
        if (!TextUtils.isEmpty(GlobalConfig.getUSERNO())) {
            et_sno.setText(GlobalConfig.getUSERNO());
        }
        if (!TextUtils.isEmpty(GlobalConfig.getUSERNAME())) {
            et_username.setText(GlobalConfig.getUSERNAME());
        }
    }

    /**
     * 用当前mac地址和mac组地址进行比较，判断能否进入课堂或者能否签到
     * 解决界面不退出造成的仍能查看课程详情和签到
     */
    private void ifEnterClassRoom(int position) {
        Log.e("ATWENXIN", "当前点击的位置"+position);
        new MacAsyncTask().execute();
        Log.e("ATWENXIN", "--------是否能进入教室获取到的macAddress-------" + macAddress);
        Log.e("ATWENXIN", "@@@@@GlobalConfig.APMACGROUP.size()@@@@@" + GlobalConfig.APMACGROUP.size());
        if (GlobalConfig.APMACGROUP.size() == 0) {

            Log.e("ATWENXIN", "@@@@@是否能进入教室获取到的macAddress组@@@@@" + macAddress);
            getApmac();
            utils.createConfirmDialog("提示", "抱歉请你移动位置重新下拉刷新",
                    "好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        } else {
            for (int i = 0; i < GlobalConfig.APMACGROUP.size(); i++) {
                Log.i("LIWENXIN", "macAddress" + GlobalConfig.APMACGROUP.get(i));
                if (GlobalConfig.APMACGROUP.get(i).trim().equals(macAddress)) {
                    Intent intent = new Intent(getContext(), ClassRoomActivity.class);
                    intent.putExtra("macAddress",macAddress);
                    intent.putExtra("coursename", mCoursesList.get(position - 1).getCoursename());
                    intent.putExtra("begintime", mCoursesList.get(position - 1).getBegintime());
                    intent.putExtra("week", mCoursesList.get(position - 1).getWeek());
                    Log.e("LIWENXIN", "----@@@@@@--起始周---@@@@@@@----" + mCoursesList.get(position - 1).getWeek());
                    intent.putExtra("beginweek", mCoursesList.get(position - 1).getBeginweek());
                    intent.putExtra("endweek", mCoursesList.get(position - 1).getEndweek());
                    intent.putExtra("location", mCoursesList.get(position - 1).getLocation());
                    intent.putExtra("courseid", mCoursesList.get(position - 1).getId());
                    intent.putExtra("teachername", mCoursesList.get(position - 1).getTeachername());
                    sno = et_sno.getText().toString();
                    username = et_username.getText().toString();
                    GlobalConfig.USERNO = sno;
                    GlobalConfig.USERNAME = username;//每次都存入最新的用户名，学号
                    spUtils.writeData(GlobalConfig.USNO, sno);
                    spUtils.writeData(GlobalConfig.UNAME, username);
                    startActivity(intent);
                }
            }
            Log.e("LIWENXIN", "--------flag-------" + flag);
        }
    }
    private void loadMeetingData() {
        RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.GETMEETING);
        params.setCharset("UTF-8");
        params.addBodyParameter("mac", GlobalConfig.getMAC());
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage(getResources().getString(R.string.Is_the_search));
        pd.show();
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Log.i("ATWENXIN", "￥￥￥￥￥会议传输回来的result￥￥￥￥:" + result);
                    Tidings tidings = createJsonBean(result, Tidings.class);//将json字符串转换为指定的bean对象
                    if (GlobalConfig.SUCCESS_FLAG.equals(tidings.getStatus())) {
                        List<Meeting> newData = createJsonToListBean(tidings.getT().toString(), Meeting.class);
                        mMeetingList.addAll(newData);
                        //获取数据成功后，设置适配器
                        if (mMeetingList != null) {
                            //TODO 每次刷新获取到课程信息的时候将当前时间存成全局变量为后续判断课程是否过期做判断
                            //将获取到的IMEI信息存放到静态变量之中
                            String nowtime = DateUtils.getNowDateTime();
                            GlobalConfig.UOLDTIME = nowtime;
                            spUtils.writeData(GlobalConfig.OLDTIME, nowtime);
                            mMAdapter = new MeetingRecyclerAdapter(getContext(), mMeetingList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            mRecyclerView.setLayoutManager(linearLayoutManager);
                            mRecyclerView.setAdapter(mMAdapter);
                            //为RecyclerView添加HeaderView
                            setMeetingHeaderView(mRecyclerView);
                            //这句就是添加我们自定义的分隔线
                            mRecyclerView.addItemDecoration(new MyDecoration(getContext(), MyDecoration.VERTICAL_LIST));
                            mMAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Log.i("ATWENXIN", "GlobalConfig.getUSERNAME():" + GlobalConfig.getUSERNAME());
                                    if (TextUtils.isEmpty(et_username.getText().toString())) {
                                        utils.createConfirmDialog("提示",
                                                "姓名不能为空",
                                                "好的", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                    } else if (TextUtils.isEmpty(et_sno.getText().toString())) {
                                        utils.createConfirmDialog("提示",
                                                "学号不能为空",
                                                "好的", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                    } else try {
                                       /* if (DateUtils.daysBetween(GlobalConfig.gerOLDTIME(), DateUtils.getNowDateTime()) > 12 || (et1 < now1 || (et1 == now1 && et2 < now2))) {
                                            utils.createConfirmDialog("提示",
                                                    "抱歉你点击的会议信息已经过期，请下拉刷新获取最新的会议信息",
                                                    "好的", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                        } else {*/
                                        GlobalConfig.USERNAME = et_username.getText().toString();
                                        GlobalConfig.USERNO = et_sno.getText().toString();
                                        meetingname =  mMeetingList.get( position-1 ).getMeetingname();
                                        teachername = mMeetingList.get( position-1 ).getTeachername();
                                        meetingid = mMeetingList.get( position-1 ).getId();
                                        checkcode = DataUtils.getRequestSign(GlobalConfig.getUSERNO());
                                        begintime = mMeetingList.get( position-1 ).getBegintime();
                                        endtime = mMeetingList.get( position-1 ).getEndtime();
                                        courselocation = mMeetingList.get(position-1).getCourselocation();
                                        location =  mMeetingList.get( position-1 ).getLocation();

                                        showEditDialog( view,meetingname, teachername,begintime,endtime,location);
                                       /* }*/
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            utils.createConfirmDialog("提示", "抱歉未找到相关信息\n1、请检查是否链接了bjut_wifi\n2、请你移动位置重新下拉刷新",
                                    "好的", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                        }
                    } else {
                        //返回的状态码不是success就认为是返回错误信息，就是数据没有查询过数据库就默认没有链接bjut_wifi
                        utils.createConfirmDialog("提示", "请你链接上wifi",
                                "好的", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                    }

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("zhangvalue", ex.getMessage());
                utils.createConfirmDialog("提示", "服务器又开小差了>_<\n1、请检查是否链接了bjut_wifi,并登录\n2、请你移动位置重新下拉刷新", "好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
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
    private void loadCourseData() {
        RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.GETCOURSE);
        params.setCharset("UTF-8");
        params.addBodyParameter("mac", GlobalConfig.getMAC());
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage(getResources().getString(R.string.Is_the_search));
        pd.show();
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Log.i("zhangvalue", "传输回来的result:" + result);
                    Tidings tidings = createJsonBean(result, Tidings.class);//将json字符串转换为指定的bean对象
                    if (GlobalConfig.SUCCESS_FLAG.equals(tidings.getStatus())) {
                 /*       //此处弹出来的msg为用户刷新出该课程的开始与否
                        ScreenUtils.showToast(tidings.getMsg());*/
                        List<Course> newData = createJsonToListBean(tidings.getT().toString(), Course.class);
                        mCoursesList.addAll(newData);
                        //获取数据成功后，设置适配器
                        if (mCoursesList != null) {
                            //TODO 每次刷新获取到课程信息的时候将当前时间存成全局变量为后续判断课程是否过期做判断
                            //将获取到的IMEI信息存放到静态变量之中
                            String nowtime = DateUtils.getNowDateTime();
                            GlobalConfig.UOLDTIME = nowtime;
                            spUtils.writeData(GlobalConfig.OLDTIME, nowtime);

                            mAdapter = new RecyclerAdapter(getContext(), mCoursesList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            mRecyclerView.setLayoutManager(linearLayoutManager);
                            mRecyclerView.setAdapter(mAdapter);
                            //为RecyclerView添加HeaderView
                            setCourseHeaderView( mRecyclerView );
                            //这句就是添加我们自定义的分隔线
                            mRecyclerView.addItemDecoration(new MyDecoration(getContext(), MyDecoration.VERTICAL_LIST));
                            mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Log.i("zhangvalue", "GlobalConfig.getUSERNAME():" + GlobalConfig.getUSERNAME());
                                    //TODO 获取到当前cell的课程时间(8:00-11:30)并将其结束时间存起来 以“-”来分割
                                    //获取数组中最后一个作为该节课的结束时间
                                    String endtime = mCoursesList.get(position - 1).getBegintime().split("-")[1];
                                    String[] endtime1 = endtime.split(":");//下课时间的具体几点几分
                                    int et1 = Integer.parseInt(endtime1[0]);
                                    int et2 = Integer.parseInt(endtime1[1]);
                                    String nowtime = DateUtils.getNowDateHM();//获取当前时间格式为8:30
                                    String[] nowtime1 = nowtime.split(":");
                                    int now1 = Integer.parseInt(nowtime1[0]);
                                    int now2 = Integer.parseInt(nowtime1[1]);
                                    if (TextUtils.isEmpty(et_username.getText().toString())) {
                                        utils.createConfirmDialog("提示",
                                                "姓名不能为空",
                                                "好的", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                    } else if (TextUtils.isEmpty(et_sno.getText().toString())) {
                                        utils.createConfirmDialog("提示",
                                                "学号不能为空",
                                                "好的", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                    } else try {

                                        if (DateUtils.daysBetween(GlobalConfig.gerOLDTIME(), DateUtils.getNowDateTime()) > 12 || (et1 < now1 || (et1 == now1 && et2 < now2))) {
                                            utils.createConfirmDialog("提示",
                                                    "抱歉你点击的课程信息已经过期，请下拉刷新获取最新的课程信息",
                                                    "好的", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                        } else {

                                            ifEnterClassRoom(position);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                }
                            });
                        } else {
                            utils.createConfirmDialog("提示", "抱歉未找到相关信息\n1、请检查是否链接了bjut_wifi\n2、请你移动位置重新下拉刷新",
                                    "好的", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                        }
                    } else {
                        //返回的状态码不是success就认为是返回错误信息，就是数据没有查询过数据库就默认没有链接bjut_wifi
                        utils.createConfirmDialog("提示", "请你链接上wifi并且登录",
                                "好的", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                    }

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("zhangvalue", ex.getMessage());
                utils.createConfirmDialog("提示", "服务器又开小差了>_<\n1、请检查是否链接了bjut_wifi,并登录\n2、请你移动位置重新下拉刷新", "好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
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


    private void setCourseHeaderView(RecyclerView view) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.header, view, false);
        mAdapter.setHeaderView(header);
    }

    private void setMeetingHeaderView(RecyclerView view) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.header, view, false);
        mMAdapter.setHeaderView(header);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binder.unbind();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void showEditDialog(View view,String meetingname,String organizer,String begintime,String endtime,String location) {
        createDialog = new CreateDialog( getActivity(), onClickListener,meetingname,organizer,begintime,endtime,location );
        createDialog.show();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_meetingsign:
                    macAddress = getNewMac();//获取被连接网络的mac;
                    for(int i = 0;i<GlobalConfig.APMACGROUP.size();i++)
                    {
                        if (GlobalConfig.APMACGROUP.get(i).trim().equals(macAddress))
                        {
                            final ProgressDialog pd = new ProgressDialog(getActivity());//进度对话框
                            pd.setMessage(getResources().getString(R.string.Is_the_sign_on));
                            pd.show();
                            RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.MSIGNON);
                            signtime = DateUtils.getNowDateTime();

                            // 1,先将传递的数据放入到 Sign对象中
                            MeetingSign signinfo = new MeetingSign( GlobalConfig.getUSERNO(), GlobalConfig.getUSERNAME(), GlobalConfig.getUIMEI(), signtime, meetingname,
                                    teachername,checkcode,meetingid, begintime,endtime,courselocation,location);
                            Log.i("zhangvalue", "服务器返回的会议信息:"  +location);

                            // 2.用FastJsonTools转化为json数据格式
                            String json = FastJsonTools.createJsonString(signinfo);
                            params.addBodyParameter("json", json);
                            params.setAsJsonContent(true);
                            params.setBodyContent(json);

                            x.http().post(params, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    final Tidings tidings = createJsonBean(result, Tidings.class);
                                    Log.i("ATWENXIN", "返回的状态:" + tidings.getStatus());
                                    if (GlobalConfig.SUCCESS_FLAG.equals(tidings.getStatus())) {
                                        utils.createConfirmDialog("成功签到", tidings.getMsg(), "好的", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                    } else if (GlobalConfig.ERROR_FLAG.equals(tidings.getStatus())) {
                                    } else if (GlobalConfig.FAILURE_FLAG.equals(tidings.getStatus())) {

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
                            break;
                        }
                    }
                    break;
                case R.id.btn_sign_cancel: cancel(GlobalConfig.getUSERNO(),GlobalConfig.getUIMEI(),location,signtime,checkcode,meetingid);
                    break;
                case R.id.btn_back:createDialog.cancel();
                    break;
            }
        }

    };

    private void cancel(String sno,String imei,String location,String signtime,String checkcode,int meetingid) {
        if (TextUtils.isEmpty(imei) && TextUtils.isEmpty(signtime)) {
            utils.ToastInfo("信息不能为空");
        } else {
            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setMessage(getResources().getString(R.string.Is_the_cancel));
            pd.show();
            RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.MSIGNOFF);
            // 1,先将传递的数据放入到Sign对象中
            MeetingSign signInfo = new MeetingSign(sno,location,GlobalConfig.getUIMEI(), signtime,checkcode, meetingid);
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

    public String getNewMac() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Permission Not Granted
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            WifiManager wifimanage = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiinfo = wifimanage.getConnectionInfo();
            macAddress = wifiinfo.getBSSID();//获取当前连接网络的mac地址;
            NAME = wifiinfo.getSSID();//获取被连接网络wifi的名称;
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
                    WifiManager wifimanage = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
                    WifiInfo wifiinfo = wifimanage.getConnectionInfo();
                    macAddress = wifiinfo.getBSSID();//获取当前连接网络的mac地址;
                    NAME = wifiinfo.getSSID();//获取被连接网络wifi的名称;
                } else {
                    Log.d("mac", "PERMISSION DENIED");
                }
        }
    }
    //异步加载获取mac地址
    public class MacAsyncTask extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            MYIMEI = NetworkUtility.getIMEI(getActivity());
            Log.e(TAG, "COME HERE------------imei！！！" + MYIMEI);
            macAddress = getNewMac();
                Log.e(TAG, "COME HERE------------macAddress！！！！" + macAddress);
                Log.e(TAG, "COME HERE------------NAME！！！！" + NAME);
            return MYIMEI;
        }

        @Override
        protected void onPostExecute(String unused) {
            // 取消加载提示
            utils.cancleLoading();
            //将获取到的IMEI信息存放到静态变量之中
            GlobalConfig.UIMEI = MYIMEI;
            GlobalConfig.WIFINAME = NAME;
            GlobalConfig.MACNAME = macAddress;
            //用户再次登录时，查看之前是否已经登录过一次了，但，可能存在过个用户在同一台手机上登录多次的情况，则只默认记住最后一次登录人的用户id
            spUtils.writeData(GlobalConfig.IMEI, MYIMEI);
            spUtils.writeData(GlobalConfig.MAC, macAddress);
            spUtils.writeData(GlobalConfig.WIFI, NAME);
        }
    }
    /**
     * 检查版本更新信息
     */
    private void checkversion() {
        RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.CHECKVERSION);
        params.setCharset("UTF-8");
        params.addBodyParameter("versioncode", String.valueOf(AppUtils.getAppVersionCode(getContext())));
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage(getResources().getString(R.string.Is_the_search));
        pd.show();
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Log.i("zhangvalue", "传输回来的result:" + result);
                    Tidings tidings = createJsonBean(result, Tidings.class);
//                    ScreenUtils.showToast(tidings.getMsg());//测试的时候可以展示，最后就将注释掉
                    //返回的状态为success
                    if (GlobalConfig.SUCCESS_FLAG.equals(tidings.getStatus())) {
                        Version newData = createJsonBean(tidings.getT().toString(), Version.class);
                        //获取数据成功后，判断是否为null
                        if (newData != null) {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("发现新版本")
                                    .setMessage(tidings.getMsg())
                                    .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (NetUtils.isWifi(getContext())) {
                                                //wifi下载
                                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setData(Uri.parse(URLConfig.DOAWNLOAD_URL));
                                                getContext().startActivity(intent);
                                                dialog.dismiss();
                                            } else {
                                                utils.createConfirmDialog(
                                                        0,
                                                        "提示：", "当前处于非wifi状态下是否继续下载？",
                                                        "确定继续下载", "下次再说吧"
                                                        , new DialogInterface.OnClickListener() {

                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                //下载
                                                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                                                intent.setData(Uri.parse(URLConfig.DOAWNLOAD_URL));
                                                                getContext().startActivity(intent);
                                                                dialog.dismiss();
                                                            }
                                                        }, new DialogInterface.OnClickListener() {

                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                            }
                                        }
                                    })
                                    .setNegativeButton("以后再说", null)
                                    .show();
                        } else {
                            utils.createConfirmDialog("提示", "目前已经是最新版本\n",
                                    "知道了", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                        }
                    }
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("zhangvalue", ex.getMessage());
                utils.createConfirmDialog("提示", "服务器又开小差了>_<\n请检查网络链接", "知道了",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
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
