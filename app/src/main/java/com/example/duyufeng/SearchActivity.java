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

// TODO: 这是搜索界面。需要完成doMySearch()函数。
public class SearchActivity extends AppCompatActivity {

    private NewsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
        adapter.notifyDataSetChanged();
    }

    void doMySearch(String query) {
        myApplication list;
        list = (myApplication)getApplication();

        switch (list.navId) {

            case R.id.navigation_graph:
                // TODO: 图谱搜索
                Toast.makeText(this, "Query graph: " + query, Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(this, EntityActivity.class);
                intent.putExtra(String.valueOf(R.string.search_word), query);

                startActivity(intent);


                break;


            case R.id.navigation_news:
            default:
                // TODO: list是我们的新闻列表。用list来搜索。
                Toast.makeText(this, "Query: "+query + "; 现有新闻数=" + list.list.size(), Toast.LENGTH_SHORT).show();

                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new NewsAdapter(list.list, query);
                recyclerView.setAdapter(adapter);

                /*
                // 暂不支持上拉加载功能
                recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
                    @Override
                    public void onLoadMore() {
                        adapter.setLoadState(adapter.LOADING);

                        if (dataViewModel.getNewsItems().getValue().size() < 44444) {
                            // 模拟获取网络数据，延时1s
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // 这里是更多数据
                                            dataViewModel.loadMoreNewsItems();
                                            adapter.setLoadState(adapter.LOADING_COMPLETE);
                                        }
                                    });
                                }
                            }, 1000);
                        } else {
                            // 显示加载到底的提示
                            adapter.setLoadState(adapter.LOADING_END);
                        }
                        myApplication list = (myApplication)(Objects.requireNonNull(getActivity()).getApplication());
                        list.list = dataViewModel.getNewsItems().getValue();
                    }
                });
                 */

                break;





        }

    }

    public void toDetailView(View view) {
        // Do something in response to button

        myApplication list = (myApplication) getApplication();
        NewsItem item = ((NewsItemLayout)(view.getParent().getParent().getParent().getParent())).item;
        list.detailNews = item.getNews();
        startActivity(new Intent(this, DetailNewsActivity.class));
    }
}
