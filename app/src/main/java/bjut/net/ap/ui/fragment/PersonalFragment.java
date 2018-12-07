package bjut.net.ap.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import bjut.net.ap.R;
import bjut.net.ap.config.GlobalConfig;
import bjut.net.ap.config.URLConfig;
import bjut.net.ap.model.Tidings;
import bjut.net.ap.model.Version;
import bjut.net.ap.ui.activity.AboutActivity;
import bjut.net.ap.ui.activity.FeedBackActivity;
import bjut.net.ap.utils.AppUtils;
import bjut.net.ap.utils.NetUtils;
import bjut.net.ap.utils.ScreenUtils;
import bjut.net.ap.utils.SharedPreferencesUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static bjut.net.ap.utils.FastJsonTools.createJsonBean;

public class PersonalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.ll_about_us)
    LinearLayout ll_about_usUs;
    @BindView(R.id.ll_feedback)
    LinearLayout ll_feedback;
    @BindView(R.id.ll_check)
    LinearLayout ll_check;
    //右侧的版本号
    @BindView(R.id.tv_right)
    TextView tv_right;


   /* @BindView(R.id.button1)
    Button button1;*/
    SharedPreferencesUtil spUtils;
    private ScreenUtils utils;
    View view;
    private int PERMISSION_FINE_LOCATION = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PersonalFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PersonalFragment newInstance(String param1, String param2) {
        PersonalFragment fragment = new PersonalFragment();
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
        utils = new ScreenUtils(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, view);
        utils = new ScreenUtils(getContext());
        //将右边版本号显示出来
        tv_right.setText(AppUtils.getAppVersionName(getContext()));
       /* button1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                Toast.makeText(getActivity(),macAddress,Toast.LENGTH_LONG).show();
            }
        });*/
        return view;
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
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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
        ButterKnife.bind(this, view).unbind();
    }

    /**
     * 设置点击事件
     */

    @OnClick({R.id.ll_about_us, R.id.ll_feedback, R.id.ll_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_about_us:
                AboutActivity.newInstance(getActivity());
                break;
            case R.id.ll_feedback:
                FeedBackActivity.newInstance(getActivity());
                break;
            case R.id.ll_check:
                // 强制检查更新,并添加额外回调用于处理进度框
//                UpdateUtils.checkUpdate(getActivity(), true);
                if (!NetUtils.isConnected(getContext())) {
                    // 无网络时
                    utils.createConfirmDialog("提示", ",当前无网络，请检查你的移动设备的网络连接\n",
                            "知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                }else{
                    checkversion();//有网络的时候开始发送更新请求
                }
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
                   //返回的状态为success
                    String status=tidings.getStatus();
                    if ((tidings.getStatus()+"").equals(GlobalConfig.SUCCESS_FLAG)) {
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
                                                        "温馨提示：", "当前处于非wifi状态下是否继续下载？",
                                                        "确定继续下载", "下次再说"
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
                        }
                    }else{//返回的状态如果不是success那么就是failure，说明版本没有被更新
                        utils.createConfirmDialog("温馨提示：", "目前已经是最新版本\n"+"版本号:"+AppUtils.getAppVersionName(getContext()),
                                "知道了", new DialogInterface.OnClickListener() {
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
                utils.createConfirmDialog("温馨提示：", "服务器又开小差了>_<\n请检查网络链接", "好的",
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


   /* *//**
     * app里面手动下载更新app
     *//*
    public void down() {
        // TODO submit data to server...
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage(getResources().getString(R.string.Is_the_search));
        pd.show();
        RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.DOAWNLOAD_APP);
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
                        //给出返回最近一次签到记录成功提示
                        utils.createConfirmDialog("提示", tidings.getMsg(), "朕知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                    }

                } else {
                    utils.createConfirmDialog("提示", tidings.getMsg(), "寡人知道了", new DialogInterface.OnClickListener() {
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

    }*/
}
