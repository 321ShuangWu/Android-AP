package bjut.net.ap.ui.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import bjut.net.ap.R;
import bjut.net.ap.ui.base.BaseActivity;
import bjut.net.ap.config.GlobalConfig;
import bjut.net.ap.config.URLConfig;
import bjut.net.ap.model.Ap;
import bjut.net.ap.model.Tidings;
import bjut.net.ap.utils.FastJsonTools;
import bjut.net.ap.utils.ScreenUtils;
import bjut.net.ap.utils.SharedPreferencesUtil;
import bjut.net.ap.utils.TextUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static bjut.net.ap.ui.fragment.SignFragment.macAddress;


public class SubmitApActivity extends BaseActivity {
    @BindView(R.id.et_location)
    EditText et_location;
    @BindView(R.id.bt_mac)
    Button bt_mac;
    @BindView( R.id.sp_school )
    Spinner sp;
    @BindView( R.id.sp2_building )
    Spinner sp2;
    ScreenUtils utils;
    SharedPreferencesUtil spUtil;
    public static String location;     //表示位置信息
    public static String building;
    private Context context;
    public static int parentid;
    private String[] school = new String[]{"北京工业大学", "清华大学", "北京大学"};
    private String[][] pandc = new String[][]{{"一教", "二教", "三教","四教","软件楼","交通楼","知行楼","礼堂","知行园"}, {"综合楼", "软件楼"}, {"环境设计学院","能源工程学院"}};
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_submit_ap );
        ButterKnife.bind( this );
        context = this;
        //spinner = findViewById( R.id.spinner );
        initTitle( "发现新Ap" );
        initLoction();
        utils = new ScreenUtils( this );
        spUtil = new SharedPreferencesUtil( this );
    }

    private void initLoction() {
        adapter = new ArrayAdapter( this, android.R.layout.simple_spinner_item, school );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        sp.setAdapter( adapter );
        sp.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View v, int position, long id) {
                int pos = sp.getSelectedItemPosition();
                adapter2 = new ArrayAdapter( context, android.R.layout.simple_spinner_item, pandc[pos] );
                sp2.setAdapter( adapter2 );
            }
            public void onNothingSelected(AdapterView arg0) {
            }
        });
        adapter2 = new ArrayAdapter( this, android.R.layout.simple_spinner_item, pandc );
        adapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        sp2.setAdapter( adapter2 );
        sp2.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                parentid = parent.getSelectedItemPosition();
              /*  String selected = parent.getItemAtPosition(position).toString();
                building = selected;*/
               parentid++;
                Log.i("LIWENXIN", "当前选中的项得数值:" + parentid);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("nothingSelect");
            }
        });
    }


    /**
     * mac地址上传到服务器端
     * 如果mac，如果mac和location同时存在与数据库
     * 就说明该mac地址所对应已经存在于数据库中就不添加
     *
     * @param view
     */
    @OnClick(R.id.bt_mac)
    public void uploadMac(View view) {
        location =et_location.getText().toString().trim();
        if (TextUtils.isEmpty(macAddress)) {
            utils.ToastInfo("macAddress不能为空");
        } else if (TextUtils.isEmpty(location)) {
            utils.ToastInfo("教室信息不能为空");
        }
        else {
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage(getResources().getString(R.string.Is_the_upload));
            pd.show();
            Log.i("LIWENXIN", "当前选中的项得数值:" + parentid);
            RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.UPLOADMAC);
            Ap apInfo = new Ap(macAddress, parentid,location);
            // 2.用FastJsonTools转化为json数据格式
            String json = FastJsonTools.createJsonString(apInfo);
            params.addBodyParameter("json", json);
            params.setAsJsonContent(true);
            params.setBodyContent(json);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i("zhangvalue", result);
                    Tidings tidings = FastJsonTools.createJsonBean(result, Tidings.class);
                    if (GlobalConfig.SUCCESS_FLAG.equals(tidings.getStatus())) {//包含上传成功的符号，表示上传成功
                        ScreenUtils.showToast(tidings.getMsg());//给出上传成功提示
                        utils.createConfirmDialog("提示", tidings.getMsg(), "好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                    } else {
                        utils.createConfirmDialog("提示", tidings.getMsg(), "好的", new DialogInterface.OnClickListener() {
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
}
