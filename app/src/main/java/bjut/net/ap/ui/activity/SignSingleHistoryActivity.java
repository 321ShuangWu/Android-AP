package bjut.net.ap.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import bjut.net.ap.R;
import bjut.net.ap.adapter.SignRecyclerAdapter;
import bjut.net.ap.config.GlobalConfig;
import bjut.net.ap.config.URLConfig;
import bjut.net.ap.model.Sign;
import bjut.net.ap.model.Tidings;
import bjut.net.ap.ui.base.BaseActivity;
import bjut.net.ap.ui.view.MyDecoration;
import bjut.net.ap.utils.FastJsonTools;
import bjut.net.ap.utils.ScreenUtils;
import bjut.net.ap.utils.SharedPreferencesUtil;
import butterknife.ButterKnife;

import static bjut.net.ap.utils.FastJsonTools.createJsonBean;
import static bjut.net.ap.utils.FastJsonTools.createJsonToListBean;

public class SignSingleHistoryActivity extends BaseActivity {

    private ScreenUtils utils;
    private SharedPreferencesUtil spUtils;
    RecyclerView mRecyclerView;
    private SignRecyclerAdapter mAdapter;
    private List<Sign> mSignList = new ArrayList<Sign>();//用来接受服务器返回的SignList 历史签到信息集合
    public static int  courseid;   //课程id
    public static String  coursename="";   //课程名称
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_history);

        //绑定该布局
        ButterKnife.bind(this);
        //实例化utils和spUtils
        utils = new ScreenUtils(this);
        spUtils = new SharedPreferencesUtil(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview_history);
        initData();
        initTitle(coursename+"的历史签到");
        loadData();

    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {//判断获取的是否为空，如果不为空
            courseid= extras.getInt("courseid");
            coursename=extras.getString("coursename");
        }
    }

    private void loadData() {
        // TODO submit data to server...
        RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.GETSINGLESIGN);
        Sign signInfo = new Sign(GlobalConfig.getUSERNO(),courseid);
        // 2.用FastJsonTools转化为json数据格式
        String json = FastJsonTools.createJsonString(signInfo);
        params.addBodyParameter("json", json);
        params.setAsJsonContent(true);
        params.setBodyContent(json);
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.Is_the_search));
        pd.show();
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Log.i("zhangvalue", "传输回来的result:" + result);
                    Log.i("LIWENXIN", "传输回来的result:" + result);
                    Tidings tidings = createJsonBean(result, Tidings.class);
                    ScreenUtils.showToast(tidings.getMsg());
                    //SUCESS的时候说明了查到成功而且有数据
                    if (GlobalConfig.SUCCESS_FLAG.equals(tidings.getStatus())) {
                        List<Sign> newData = createJsonToListBean(tidings.getT().toString(), Sign.class);
                        mSignList.addAll(newData);
                        //获取数据成功后，设置适配器
                        if (mSignList != null) {
                        //当刷新出来有课程信息将此时的时间保存起来，为后面时间过期做准备
                            spUtils.writeData(GlobalConfig.COURSEID,courseid);
                            mAdapter = new SignRecyclerAdapter(getApplicationContext(), mSignList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            mRecyclerView.setLayoutManager(linearLayoutManager);
                            mRecyclerView.setAdapter(mAdapter);
                            //这句就是添加我们自定义的分隔线
                            mRecyclerView.addItemDecoration(new MyDecoration(getApplication(), MyDecoration.VERTICAL_LIST));
                        } else {
                            utils.createConfirmDialog("提示", "没有查询到该课程的签到信息", "知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                    //failure说明查询成功只是没有签到历史
                    else if (GlobalConfig.FAILURE_FLAG.equals(tidings.getStatus())) {
                        utils.createConfirmDialog("签到历史提示", tidings.getMsg(), "知道了", new DialogInterface.OnClickListener() {
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
                utils.createConfirmDialog("提示", "服务器又开小差了>_<", "知道了", new DialogInterface.OnClickListener() {
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
