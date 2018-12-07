package bjut.net.ap.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import bjut.net.ap.R;
import bjut.net.ap.model.Course;
import bjut.net.ap.model.Meeting;

/**
 * Created by 张胜凡 on 2017/12/19 15:13.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener{
    private static final String TAG = "zhangvalue";
    private OnItemClickListener mOnItemClickListener = null;
    private LayoutInflater mInflater;
    private Context mContext;
    List<Course> mCoursesDatas;
    List<Meeting> mMettingDatas;
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

    /** 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    * */
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

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }


    public RecyclerAdapter(Context context, List<Course> datas) {
        this.mContext = context;
        this.mCoursesDatas = datas;
        mInflater = LayoutInflater.from(context);
    }


    /**
     * Data有多少条获取数据的数量
     * @return
     */
    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView

    @Override
    public int getItemCount() {
        if(mHeaderView == null){
            return mCoursesDatas.size();
        }else {
            return mCoursesDatas.size() + 1;
        }
    }

    /**
     * 创建ViewHolder
     *创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new MyViewHolder(mHeaderView);
        }
        View view = mInflater.inflate(R.layout.cell_classroom_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);

        return viewHolder;
    }

    /**
     *绑定View，这里是根据返回的这个position的类型，从而进行绑定的， HeaderView和FooterView, 就不同绑定了
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){//正常情况下没有header和footer
            if(holder instanceof MyViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
//                ((MyViewHolder) holder).tv.setText(mDatas.get(position-1));
                holder.txt_name.setText("课程名：" + mCoursesDatas.get(position-1).getCoursename());
                holder.txt_teacher.setText("授课教师：" + mCoursesDatas.get(position-1).getTeachername());
                holder.txt_location.setText("上课地点：" + mCoursesDatas.get(position-1).getLocation()+"        进入课堂");
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
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
//自定义的ViewHolder，持有每个Item的的所有界面元素
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
    }
}

