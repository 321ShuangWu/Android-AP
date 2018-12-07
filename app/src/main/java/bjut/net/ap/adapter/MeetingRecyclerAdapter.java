package bjut.net.ap.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import bjut.net.ap.R;
import bjut.net.ap.model.Meeting;

public class MeetingRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener {
    private static final String TAG = "ATWENXIN";
    private RecyclerAdapter.OnItemClickListener mOnItemClickListener = null;
    private LayoutInflater mInflater;
    private Context mContext;
    List<Meeting> mMeetingDatas;
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_NORMAL = 1;  //说明是不带有header和footer的
    //HeaderView
    public static View mHeaderView;
    //www.jianshu.com/p/991062d964cf
    //HeaderView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null ){
            return TYPE_NORMAL;
        }
        if (position == 0){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }
    public MeetingRecyclerAdapter(Context context, List<Meeting> datas) {
        this.mContext = context;
        this.mMeetingDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new MyViewHolder(mHeaderView);
        }
        View view = mInflater.inflate( R.layout.cell_classroom_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){//正常情况下没有header和footer
            if(holder instanceof MyViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
//                ((MyViewHolder) holder).tv.setText(mDatas.get(position-1));e
                holder.txt_name.setText("会议名称：" + mMeetingDatas.get(position-1).getMeetingname());
                holder.txt_teacher.setText("会议召集人：" + mMeetingDatas.get(position-1).getTeachername());
                holder.txt_location.setText("会议地点：" + mMeetingDatas.get(position-1).getLocation()+"            进入会议");
                //将position保存在itemView的Tag中，以便点击时进行获取
                holder.itemView.setTag(position);
                return;
            }
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        if(mHeaderView == null){
            return mMeetingDatas.size();
        }else {
            return mMeetingDatas.size() + 1;
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    public void setOnItemClickListener(RecyclerAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
/*}
class MyViewHolder extends ViewHolder {
    //    @BindView(R.id.txt_name)
    TextView txt_name;
    TextView txt_teacher;
    TextView txt_location;
    public MyViewHolder(View itemView) {
        super(itemView);
        //如果是headerview,直接返回
        if (itemView == RecyclerAdapter.mHeaderView){
            return;
        }
        txt_name = itemView.findViewById(R.id.txt_name);
        txt_teacher = itemView.findViewById(R.id.txt_teacher);
        txt_location = itemView.findViewById(R.id.txt_location);
    }*/
}

