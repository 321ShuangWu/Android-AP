package bjut.net.ap.ui.activity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import bjut.net.ap.R;
import bjut.net.ap.ui.base.BaseActivity;
import bjut.net.ap.ui.fragment.PersonalFragment;
import bjut.net.ap.ui.fragment.SignFragment;
import bjut.net.ap.ui.view.AddPopWindow;
import bjut.net.ap.utils.Exit;
import bjut.net.ap.utils.ScreenUtils;
import bjut.net.ap.utils.SharedPreferencesUtil;

public class MainActivity extends BaseActivity implements
        OnCheckedChangeListener {
    private ImageView iv_add;
   /* private ImageButton ib_person;*/
    private RadioGroup rg;
    public static RadioButton rb,rb1, rb2;
    private TextView tv_title;
    private final String titles[] = {"个人主页","课程签到","会议签到" };
    ScreenUtils utils;
    SharedPreferencesUtil spUtil;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        utils = new ScreenUtils( this );
        spUtil = new SharedPreferencesUtil( this );
        tv_title = findViewById( R.id.ivTitleName );
        initContentMenu();
        setUpMenu();
    }

    private void initContentMenu() {
        iv_add = findViewById( R.id.iv_add );
       /* ib_person = findViewById( R.id.ib_person);*/
        // 点击右边显示
        iv_add.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddPopWindow addPopWindow = new AddPopWindow( MainActivity.this );
                addPopWindow.showPopupWindow( iv_add );
            }

        } );
       /* ib_person.setOnClickListener(  new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeFragment(new PersonalFragment() );
                tv_title.setText( titles[0] );
                Log.e("LIWENXIN", "--------个人已经到达这里-------" );
            }
        });*/
    }


    private void setUpMenu() {

        rg = findViewById( R.id.rg );
        rb = findViewById( R.id.rb );
        rb1 = findViewById( R.id.rb1 );
        rb2 = findViewById( R.id.rb2 );
        rg.setOnCheckedChangeListener( this );
        rb1.setChecked( true );
        setTitle( titles[0] );
    }

/*

 */
    public void changeFragment(Fragment targetFragment) {
        // resideMenu.clearIgnoredViewList();
        Bundle bundle = new Bundle();
        String optionSignType = tv_title.getText().toString();
        bundle.putString("optionSignType", optionSignType);
        targetFragment.setArguments( bundle );
        getSupportFragmentManager().beginTransaction()
                .replace( R.id.container, targetFragment, "fragment" )
                .setTransitionStyle( FragmentTransaction.TRANSIT_FRAGMENT_FADE )
                .commit();
    }
    // 监听手机上的BACK键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 判断菜单是否关闭
            utils.createConfirmDialog( 0, "您确定要退出吗", null, "退出", "取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Exit.getInstance1();
                        }
                    }, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    } );
            return true;
        }
        return super.onKeyDown( keyCode, event );
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (rb.getId() == checkedId) {

            changeFragment( new PersonalFragment());
            Log.e("LIWENXIN", "--------个人主页已经到达这里-------" );
            tv_title.setText( titles[0] );
        }else if (rb1.getId() == checkedId) {
            tv_title.setText( titles[1] );
            Fragment sign = new SignFragment();
            Bundle bundle = new Bundle();
            String optionSignType = tv_title.getText().toString();
            Log.e("LIWENXIN", "--------课程签到已经到达这里-------"+optionSignType );
            bundle.putString("optionSignType", optionSignType);
            changeFragment( new SignFragment());
            tv_title.setText( titles[1] );

        }
        else if (rb2.getId() == checkedId) {
            tv_title.setText( titles[2] );
            Fragment sign = new SignFragment();
            Bundle bundle = new Bundle();
            String optionSignType = tv_title.getText().toString();
            Log.e("LIWENXIN", "--------会议签到已经到达这里-------"+optionSignType );
            bundle.putString("optionSignType", optionSignType);
            sign.setArguments( bundle );
            changeFragment( sign );
        }
    }

}

