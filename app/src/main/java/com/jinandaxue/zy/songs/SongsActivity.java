package com.jinandaxue.zy.songs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class SongsActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_ITEM = 10;
    private ArrayList<Songs> songsCollection;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("操作");
        menu.add(0, 1, 0, "添加");
        menu.add(0, 2, 0, "删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        AdapterView.AdapterContextMenuInfo itemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 1:
                Intent intent=new Intent(SongsActivity.this,AddItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name","天蓝蓝");
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
                break;
            case 2:
                theListAdapter.removeItem(itemInfo.position);
                theListAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
    public class ListViewAdapter extends BaseAdapter {
        ArrayList<View> itemViews;

        public ListViewAdapter(ArrayList<Songs> songsCollection) {
            itemViews = new ArrayList<View>(songsCollection.size());

            for (int i=0; i<songsCollection.size(); ++i){
                itemViews.add(makeItemView(songsCollection.get(i).getName()
                        ,"时间："+songsCollection.get(i).getDay()
                        ,songsCollection.get(i).getPictureId())
                );
            }

        }

        public void addItem(String itemTitle){
            Songs songs=new Songs();
            songs.setName(itemTitle);
            songs.setDay(new Date());
            songs.setPictureId(R.drawable.a4);
            songs.setPrice(1);
            songsCollection.add(songs);
            SongsCollectionOperater operater=new SongsCollectionOperater();
            operater.save(SongsActivity.this.getBaseContext(), songsCollection);

            View view=makeItemView(itemTitle
                    ,""+songs.getDay()
                    ,songs.getPictureId());
            itemViews.add(view);
        }
        public void removeItem(int positon){
            itemViews.remove(positon);
            songsCollection.remove(positon);
            SongsCollectionOperater operater=new SongsCollectionOperater();
            operater.save(SongsActivity.this.getBaseContext(), songsCollection);
        }
        public int getCount() {
            return itemViews.size();
        }

        public View getItem(int position) {
            return itemViews.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        private View makeItemView(String strTitle, String strText, int resId) {
            LayoutInflater inflater = (LayoutInflater)SongsActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.list_view_item_songs_price, null);

            // 通过findViewById()方法实例R.layout.item内各组件
            TextView title = (TextView)itemView.findViewById(R.id.itemTitle);
            title.setText(strTitle);
            TextView text = (TextView)itemView.findViewById(R.id.itemText);
            text.setText(strText);
            ImageView image = (ImageView)itemView.findViewById(R.id.itemImage);
            image.setImageResource(resId);

            return itemView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //if (convertView == null)
                return itemViews.get(position);
            //return convertView;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ADD_ITEM:
                if (resultCode == RESULT_OK){
                    String name = data.getStringExtra("name");
                    theListAdapter.addItem(name);
                    theListAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
    ListViewAdapter theListAdapter;

    private TabLayout tablayout;
    private ViewPager viewPager;
    //数据源
    private String[] titles = {"地图", "歌曲", "新闻"};
    //View listViewContainer;
    //ListView listViewGoodsPrice;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_songs_price);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SongsActivity.this,AddItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name","");
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
            }
        });
       SongsCollectionOperater operater=new SongsCollectionOperater();
       songsCollection =operater.load(getBaseContext());
       if(songsCollection ==null) {
           songsCollection = new ArrayList<Songs>();
       }
       Songs good=new Songs();
       good.setName("");
       good.setPictureId(R.drawable.a4);
       good.setDay(new Date());
       good.setPrice(1);
       songsCollection.add(good);
       theListAdapter= new ListViewAdapter(songsCollection);


       LayoutInflater inflater = (LayoutInflater)SongsActivity.this
               .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       //View itemView = inflater.inflate(R.layout.content_goods_price, null);

       tablayout = (TabLayout) findViewById(R.id.tablayout);
       viewPager = (ViewPager) findViewById(R.id.viewpager);

       MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

       viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
       viewPager.setAdapter(myPagerAdapter);

       tablayout.setupWithViewPager(viewPager);

       //每条之间的分割线
       LinearLayout linearLayout = (LinearLayout) tablayout.getChildAt(0);

       linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

       linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
               R.drawable.layout_divider_vertical));
    }

    private MapViewFragment mapViewFragment=null;
    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0)
            {
                mapViewFragment= new MapViewFragment();
                return mapViewFragment;
            }
            if(position==1)
            {
                ListViewFragment fragment= new ListViewFragment(theListAdapter);
                return fragment;
            }
            else {
                WebViewFragment webViewFragment = new WebViewFragment();
                return webViewFragment;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }

    class mListViewItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(SongsActivity.this
                    ,"您选择的项目是："+((TextView)view).getText()
                    , Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if(mapViewFragment!=null)mapViewFragment.MDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if(mapViewFragment!=null)mapViewFragment.MResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if(mapViewFragment!=null)mapViewFragment.MPause();
    }
}
