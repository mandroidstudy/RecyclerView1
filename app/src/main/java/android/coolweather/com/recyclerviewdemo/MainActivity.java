package android.coolweather.com.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //准备数据
        initDate();
        final MyAdapter adapter=new MyAdapter(MainActivity.this,list);
        adapter.setOnClickLintener(new MyAdapter.OnClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                Toast.makeText(MainActivity.this,"您点击了"+position,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClickListener(View view, int position) {
                adapter.removeItem(position);
            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initDate() {
        list=new ArrayList<String>();
        for (int i=0;i<27;i++){
            list.add("我是数据"+i);
        }
    }
}
