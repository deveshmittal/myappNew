package com.example.deveshmittal.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deveshmittal.myapplication.common.RecyclerViewItem;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deveshmittal on 02/11/15.
 */
public class HomeFragment extends Fragment {
    RecyclerView mRecyclerView;
    MyAdapter adapter;
    RecyclerView.RecycledViewPool mRecycledViewPool;
    ProgressDialog pDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecycledViewPool = new RecyclerView.RecycledViewPool();
        adapter = new MyAdapter(getActivity(), mRecycledViewPool, (MainActivity)getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setRecycledViewPool(mRecycledViewPool);
        mRecyclerView.setAdapter(adapter);
        Log.d("TAG", "onViewCreated : ");
        pDialog =ProgressDialog.show(getActivity(), "Loading..", "Please wait", true, false);

        Ion.with(this).load("http://dev.mygola.com/cats").asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                pDialog.hide();
                if (result == null || result.length() == 0) {
                    Toast.makeText(getActivity(), "No response from server", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("TAG", "Result is : " + result);

                final List<RecyclerViewItem> items = new ArrayList<>();
                try {
                    JSONArray data = new JSONArray(result);
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        String name = item.getString("name");
                        ViewType viewType;
                        if (name.equalsIgnoreCase("cat")) {
                            viewType = ViewType.getViewType(0);

                        } else {
                            viewType = ViewType.getViewType(1);
                        }
                        items.add(viewType.getItem(item));
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                adapter.setData(items);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
