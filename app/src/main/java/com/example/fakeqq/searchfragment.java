package com.example.fakeqq;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class searchfragment extends Fragment {
    private RecyclerView recyclerView;
    private SeachImageAdapter imageAdapter;
    private List<String> imageUrls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchfragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        imageUrls = new ArrayList<>();
        imageUrls.add("https://picx1.zhimg.com/v2-dcd896f8d15d94d62fbc4221c478ffc8_720w.jpg?source=172ae18b");
        imageUrls.add("https://pica.zhimg.com/v2-8491dfe71264ef23ae92971d51032957_720w.jpg?source=172ae18b");
        imageUrls.add("https://doubookpic.tinynews.org/0176d0f00b82d1335ffd7247a6991fb97ee59d569631985bbcf92433fc6621ae/s29455450.jpg");
        imageAdapter = new SeachImageAdapter(getContext(), imageUrls);
        recyclerView.setAdapter(imageAdapter);

        return view;
    }
}
