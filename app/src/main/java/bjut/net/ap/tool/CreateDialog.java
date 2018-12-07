package bjut.net.ap.tool;


import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import bjut.net.ap.R;
import bjut.net.ap.ui.fragment.SignFragment;

import static bjut.net.ap.R2.id.textview;

public class CreateDialog extends Dialog {
    /**
     * 上下文对象 *
     */
    Activity context;

    private Button btn_meetingsign;
    private Button btn_sign_cancel;
    private Button btn_back;

    public TextView text_meetingname;
    public TextView text_organizer;
    public TextView text_mbegintime;
    public TextView text_mendtime;
    public TextView text_mlocation;

    public String meetingname;
    public String organizer;
    public String begintime;
    public String endtime;
    public String location;

    private View.OnClickListener mClickListener;

    public CreateDialog(Activity context) {
        super(context);
        this.context = context;
    }

    public CreateDialog(Activity context, View.OnClickListener clickListener,String meetingname,String organizer,String begintime,String endtime,String location) {
        super(context);
        this.context = context;
        this.mClickListener = clickListener;
        this.meetingname = meetingname;
        this.organizer = organizer;
        this.begintime = begintime;
        this.endtime = endtime;
        this.location = location;
    }
    public CreateDialog(Activity context, int theme, View.OnClickListener clickListener) {
        super(context, theme);
        this.context = context;
        this.mClickListener = clickListener;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.create_meeting_dialog);
        text_meetingname = findViewById( R.id.text_meetingname);
        text_organizer =  findViewById(R.id.text_organizer);
        text_mbegintime =  findViewById(R.id.text_meeting_begin_time);
        text_mendtime =  findViewById(R.id.text_meeting_end_time);
        text_mlocation =  findViewById(R.id.text_meetinglocation);
        this.text_meetingname.setText( "名称 ："+ meetingname );
        this.text_organizer.setText( "发起人|组织 ："+ organizer );
        this.text_mbegintime.setText( "起始时间 ："+ begintime );
        this.text_mendtime.setText( "结束时间 ："+ endtime );
        this.text_mlocation.setText("地点 ："+ location );
        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = this.getWindow();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);
        // 根据id在布局中找到控件对象
        btn_meetingsign = findViewById(R.id.btn_meetingsign);
        btn_sign_cancel =  findViewById(R.id.btn_sign_cancel);
        btn_back =  findViewById(R.id.btn_back);
        // 为按钮绑定点击事件监听器
        btn_meetingsign.setOnClickListener(mClickListener);
        btn_sign_cancel.setOnClickListener(mClickListener);
        btn_back.setOnClickListener(mClickListener);
        this.setCancelable(true);
    }
    public void setText(String meetingname,String organizer,String begintime,String endtime,String location)
    {
        text_meetingname.setText( "会议名称" );
       /* text_meetingname.setText( meetingname );
        text_organizer.setText( organizer );
        ;*/
    }
}


