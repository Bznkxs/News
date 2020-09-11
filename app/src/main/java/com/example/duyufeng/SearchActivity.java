package com.example.duyufeng;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// TODO: 这是搜索界面。需要完成doMySearch()函数。
public class SearchActivity extends AppCompatActivity {

    private NewsAdapter adapter;

    myApplication list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        list = (myApplication)getApplication();
        // set toolbar working
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar abar = getSupportActionBar();
        abar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    com.example.duyufeng.MySuggestionProvider.AUTHORITY, com.example.duyufeng.MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            doMySearch(query);
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchtime_actionbar, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (list.navId == R.id.navigation_news) {
            adapter.notifyDataSetChanged();
        }


    }

    void doMySearch(String query) {


        switch (list.navId) {

            case R.id.navigation_graph:
                // TODO: 图谱搜索
                //Toast.makeText(this, "Query graph: " + query, Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(this, EntityActivity.class);
                intent.putExtra(String.valueOf(R.string.search_word), query);

                startActivity(intent);


                break;


            case R.id.navigation_news:
            default:

                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                LinkedList<News> nl = new LinkedList<News>(Arrays.asList(list.provider.search(query)));
                nl.addAll(Arrays.asList(list.providerPaper.search(query)));
                adapter = new NewsAdapter(new LinkedList<News>(nl));
                recyclerView.setAdapter(adapter);

                break;
        }
    }

    public void toDetailView(View view) {
        // Do something in response to button

        myApplication list = (myApplication) getApplication();
        News item = ((NewsItemLayout)(view.getParent().getParent().getParent().getParent())).item;
        try {
            item.saveContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.detailNews = item;
        startActivity(new Intent(this, DetailNewsActivity.class));
    }
}
