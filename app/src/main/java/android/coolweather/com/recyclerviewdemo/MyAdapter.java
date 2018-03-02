package android.coolweather.com.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mao on 2018/3/2.
 */

/*
* MyAdapter中存在两个ViewHolder泛型用他们的父类，若只存在一个ViewHolder则泛型直接用实现类即可
* */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //ITEM1   ITEM2  HEADER_ITEM  FOOTER_ITEM分别对应RecyclerView中的不同Item布局
    private static int ITEM1=1;
    private static int ITEM2=2;
    private static int HEADER_ITEM=0;
    private static int FOOTER_ITEM=3;

    private static int HEADER_NUM=1;
    private static int FOOTER_NUM=1;
    private ArrayList<String> list;
    private LayoutInflater inflater;
    public MyAdapter(Context mcontext, ArrayList<String> mlist){
        this.list=mlist;
        this.inflater=LayoutInflater.from(mcontext);
    }
    private OnClickListener onClickListener;
    public interface OnClickListener{
        public abstract void onClickListener(View view,int position);
        public abstract void onLongClickListener(View view,int position);
    }
    public void setOnClickLintener(OnClickListener monClickListener){
        this.onClickListener=monClickListener;
    }
    /*
    * RecyclerView的Item有多少布局类型一般就有多少对应的ViewHolder
    * */
    static class ViewHolderHeader extends RecyclerView.ViewHolder{

        public ViewHolderHeader(View itemView) {
            super(itemView);
        }
    }
    static class ViewHolderFooter extends RecyclerView.ViewHolder{

        public ViewHolderFooter(View itemView) {
            super(itemView);
        }
    }
    static class ViewHolder1 extends RecyclerView.ViewHolder{
        private TextView textView1;
        public ViewHolder1(View itemView) {
            super(itemView);
            textView1=(TextView)itemView.findViewById(R.id.item1_data);
        }
    }
    static class ViewHolder2 extends RecyclerView.ViewHolder{
        private TextView textView2;
        public ViewHolder2(View itemView) {
            super(itemView);
            textView2=(TextView)itemView.findViewById(R.id.item2_data);
        }
    }
    /*
    * return int型返回值决定了RecyclerView中position位置的Item布局是什么
    * 返回什么值是根据自己定义的规则
    * */
    @Override
    public int getItemViewType(int position) {
        if (HEADER_NUM!=0&&position<HEADER_NUM){
            //头部View
            return HEADER_ITEM;
        }
        else if (FOOTER_NUM!=0&&position>=(HEADER_NUM+list.size()))
        {
            //底部View
            return FOOTER_ITEM;
        }
        else {
           // 这里定义了如果是偶数就返回ITEM1 奇数就返回ITEM2
            return (position%2==0)?ITEM1:ITEM2;
        }
    }

    /*
    * return RecyclerView.ViewHolder
    * 通过getItemViewType()返回的值就是这里的viewType,通过它我们可以决定加载哪个Item布局文件
    * 并将其传递给对应ViewHolder的构造，将ViewHolder返回
    * */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==HEADER_ITEM){
            return new ViewHolderHeader(inflater.inflate(R.layout.header_view,parent,false));
        }
        else if (viewType==FOOTER_ITEM){
            return new ViewHolderFooter(inflater.inflate(R.layout.footer_view,parent,false));
        }
        else if (viewType==ITEM1){
            return new ViewHolder1(inflater.inflate(R.layout.recycler_item1,parent,false));
        }
        else if(viewType==ITEM2){
            return new ViewHolder2(inflater.inflate(R.layout.recycler_item2,parent,false));
        }
        return null;
    }

    /*
    * 将数据和界面进行绑定
    * */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClickListener(v,position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onClickListener.onLongClickListener(v,position);
                return true;
            }
        });

        if (holder instanceof ViewHolder1){
            ((ViewHolder1) holder).textView1.setText(list.get(position-1)+"item1");
        }
        else if (holder instanceof ViewHolder2){
            ((ViewHolder2) holder).textView2.setText(list.get(position-1)+"item2");
        }
    }

    /*
    * 返回RecyclerView中一共要加载的item数
    * */
    @Override
    public int getItemCount() {
        return list.size()+HEADER_NUM+FOOTER_NUM;
    }
    /*
    * 删除ITEM
    * */
    public void removeItem(int position){
        //只有数据部分才可以删除
        if (position>0&&position<(HEADER_NUM+list.size())){
            list.remove(position-1);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }
    }
}
