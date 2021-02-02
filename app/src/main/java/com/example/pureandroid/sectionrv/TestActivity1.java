package com.example.pureandroid.sectionrv;

import android.app.Activity;
import android.os.Bundle;

import com.example.pureandroid.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * fork from https://gist.github.com/gabrielemariotti/e81e126227f8a4bb339c
 * A SectionedGridRecyclerViewAdapter: use this class to realize a simple sectioned grid `RecyclerView.Adapter`.
 */
public class TestActivity1 extends Activity {

    RecyclerView mRecyclerView;
    SimpleAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_1);
        //Your RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.test_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        //Your RecyclerView.Adapter
        mAdapter = new SimpleAdapter(this);

        //This is the code to provide a sectioned grid
        List<SectionedGridRecyclerViewAdapter.Section> sections =
                new ArrayList<SectionedGridRecyclerViewAdapter.Section>();

        //Sections
        sections.add(new SectionedGridRecyclerViewAdapter.Section(0, "Section 0"));
        sections.add(new SectionedGridRecyclerViewAdapter.Section(5, "Section 1"));
//        sections.add(new SectionedGridRecyclerViewAdapter.Section(12, "Section 3"));
//        sections.add(new SectionedGridRecyclerViewAdapter.Section(14, "Section 4"));
//        sections.add(new SectionedGridRecyclerViewAdapter.Section(20, "Section 5"));

//        sections.add(new SectionedGridRecyclerViewAdapter.Section(0, "Section 1"));
//        sections.add(new SectionedGridRecyclerViewAdapter.Section(5, "Section 2"));
//        sections.add(new SectionedGridRecyclerViewAdapter.Section(12, "Section 3"));
//        sections.add(new SectionedGridRecyclerViewAdapter.Section(14, "Section 4"));
//        sections.add(new SectionedGridRecyclerViewAdapter.Section(20, "Section 5"));

        //Add your adapter to the sectionAdapter
        SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
        SectionedGridRecyclerViewAdapter mSectionedAdapter = new
                SectionedGridRecyclerViewAdapter(this, R.layout.section, R.id.section_text, mRecyclerView, mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        mRecyclerView.setAdapter(mSectionedAdapter);
    }
}
