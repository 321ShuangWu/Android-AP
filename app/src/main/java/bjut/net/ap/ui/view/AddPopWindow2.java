package bjut.net.ap.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import bjut.net.ap.R;
import bjut.net.ap.config.GlobalConfig;
import bjut.net.ap.ui.activity.AboutActivity;
import bjut.net.ap.ui.activity.SignHistoryActivity;
import bjut.net.ap.ui.activity.SignSingleHistoryActivity;
import bjut.net.ap.utils.ScreenUtils;

import static android.content.Intent.getIntent;

/**
 * Created by Administrator on 2018/4/20.
 */

public class AddPopWindow2 extends PopupWindow {
    int courseid;
    String coursename;
    private View conentView;
    ScreenUtils utils;
    RelativeLayout re_layout1, re_layout2;
    public AddPopWindow2(final Activity context) {
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popupwindow_add2, null);
        utils = new ScreenUtils(context);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

		re_layout1 = conentView.findViewById(R.id.re_layout1);
        re_layout2=conentView.findViewById(R.id.re_layout2);

        re_layout1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,SignHistoryActivity.class));
                context.overridePendingTransition(R.anim.activity_move_in_start, R.anim.activity_move_out_start);
                AddPopWindow2.this.dismiss();

            }

        });
        re_layout2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplication(), SignSingleHistoryActivity.class);
                Log.v("zhangvalue", "服务器返回的课程信息:" +courseid+coursename);
                intent.putExtra("courseid", courseid);
                intent.putExtra("coursename", coursename);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.activity_move_in_start, R.anim.activity_move_out_start);
                AddPopWindow2.this.dismiss();

            }

        });
    }
    /**
     * 显示popupWindow
     */
    public void showPopupWindow2(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            //mPop.showAsDropDown(anchor, 0, 0);//设置显示PopupWindow的位置位于View的左下方，x,y表示坐标偏移量
            this.showAsDropDown(parent, 0, 30);

        } else {
            this.dismiss();
        }
    }

    public void getId(int id)
    {
        courseid = id;
    }

    public void getName(String name)
    {
        coursename = name;
    }

}



