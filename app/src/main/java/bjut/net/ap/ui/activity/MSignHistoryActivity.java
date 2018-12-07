package bjut.net.ap.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import bjut.net.ap.adapter.MSignRecyclerAdapter;
import bjut.net.ap.adapter.SignRecyclerAdapter;
import bjut.net.ap.config.GlobalConfig;
import bjut.net.ap.config.URLConfig;
import bjut.net.ap.model.MeetingSign;
import bjut.net.ap.model.Tidings;
import bjut.net.ap.ui.base.BaseActivity;
import bjut.net.ap.utils.ScreenUtils;
import bjut.net.ap.utils.SharedPreferencesUtil;
import butterknife.ButterKnife;

import static bjut.net.ap.utils.FastJsonTools.createJsonBean;
import static bjut.net.ap.utils.FastJsonTools.createJsonToListBean;

public class MSignHistoryActivity extends BaseActivity {

    private ScreenUtils utils;
    private SharedPreferencesUtil spUtils;
    RecyclerView mRecyclerView;
    private MSignRecyclerAdapter mAdapter;
    private List<MeetingSign> mSignList = new ArrayList();//用来接受服务器返回的SignList 历史签到信息集合
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_history);
        initTitle("活动签到信息");
        //绑定该布局
        ButterKnife.bind(this);
        //实例化utils和spUtils
        utils = new ScreenUtils(this);
        spUtils = new SharedPreferencesUtil(this);
        mRecyclerView =  findViewById(R.id.id_recyclerview_history);
        loadData();
    }
    private void loadData() {
        // TODO submit data to server...
        RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.GETMSIGN);
        params.setCharset("UTF-8");
        params.addBodyParameter("sno", GlobalConfig.getUSERNO());
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.Is_the_search));
        pd.show();
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Log.i("ATWENXIN", "查询活动签到历史" );
                    Log.i("zhangvalue", "传输回来的result:" + result);
                    Tidings tidings = createJsonBean(result, Tidings.class);
                   // ScreenUtils.showToast(tidings.getMsg());
                    if (GlobalConfig.SUCCESS_FLAG.equals(tidings.getStatus())) {
                        List<MeetingSign> newData = createJsonToListBean(tidings.getT().toString(), MeetingSign.class);
                        mSignList.addAll(newData);
                        //获取数据成功后，设置适配器
                        if (mSignList != null) {
                            mAdapter = new MSignRecyclerAdapter(getApplicationContext(), mSignList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            mRecyclerView.setLayoutManager(linearLayoutManager);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            utils.createConfirmDialog("提示", "没有查询到相关的签到信息", "知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }  //failure说明查询成功只是没有签到历史
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
    public static void newInstance(Context context) {
        Intent intent = new Intent(context, MSignHistoryActivity.class);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
