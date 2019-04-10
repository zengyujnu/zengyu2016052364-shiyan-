package com.jinandaxue.zy.songs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class ListViewFragment extends Fragment {
    private SongsActivity.ListViewAdapter listViewAdapter;
    public ListViewFragment(SongsActivity.ListViewAdapter theListViewAdapter) {
        listViewAdapter=theListViewAdapter;
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //String name = this.getArguments().getString("title");
        View contentView = inflater.inflate(R.layout.fragment_list_view, container, false);
        ListView listViewGoodsPrice= (ListView) contentView.findViewById(R.id.listwiew_songs);

        listViewGoodsPrice.setAdapter(listViewAdapter);
        listViewGoodsPrice.setOnItemClickListener(new mListViewItemClick2());

        registerForContextMenu(listViewGoodsPrice);
        return contentView;
        //return webView;
    }
    class mListViewItemClick2 implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(getActivity()
                    ,"您选择的项目是："+i
                    , Toast.LENGTH_SHORT).show();
        }
    }
}
