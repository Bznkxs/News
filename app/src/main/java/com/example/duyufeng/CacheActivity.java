package com.example.duyufeng;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class CacheActivity extends AppCompatActivity {

    NewsAdapter adapter;
    myApplication list;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);

        // set toolbar working
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar abar = getSupportActionBar();
        abar.setDisplayHomeAsUpEnabled(true);


        list = (myApplication) getApplication();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(list.list, NewsItem.Cached);
        recyclerView.setAdapter(adapter);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.cache_actionbar, menu);
        /*
        inflater.inflate(R.menu.searchtime_actionbar, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
         */
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_del) {
            final BottomSheetDialog dialog = new BottomSheetDialog(this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view1 = inflater.inflate(R.layout.dialog_bottom_deletecache, null);
            dialog.setContentView(view1);
            dialog.show();
            Button button = dialog.findViewById(R.id.btnCancel);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            Button button1 = dialog.findViewById(R.id.btnDelete);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (NewsItem i : adapter.getData()) {
                        i.getNews().removeContent();
                    }
                    adapter = new NewsAdapter(list.list, NewsItem.Cached);
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(view.getContext(), R.string.delete_complete, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        }
        else { // 16908332
            Toast.makeText(this, String.valueOf(item.getItemId()), Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }

    void clearHistory() {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                com.example.duyufeng.MySuggestionProvider.AUTHORITY, com.example.duyufeng.MySuggestionProvider.MODE);
        suggestions.clearHistory();
    }

    public void toDetailView(View view) {
        // Do something in response to button

        Intent intent = new Intent(this, DetailNewsActivity.class);
        NewsItem item = ((NewsItemLayout)(view.getParent().getParent().getParent().getParent())).item;
        intent.putExtra(String.valueOf(R.string.Item), item.getNews());
        startActivity(intent);
    }

    public void moreOptions(View view) {
        // Toast.makeText(view.getContext(),"You clicked `...'!", Toast.LENGTH_SHORT).show();
        final BottomSheetDialog dialog = new BottomSheetDialog(view.getContext());
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.dialog_bottom_newslist, null);
        dialog.setContentView(view1);
        dialog.show();
        Button button = dialog.findViewById(R.id.btnCancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
