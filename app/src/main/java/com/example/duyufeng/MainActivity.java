package com.example.duyufeng;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavDestination;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView = null;
    LinearLayoutManager layoutManager;
    Toolbar myToolbar;
    //TabLayout tabLayout;
    BottomNavigationView navView;
    TextView titleView;
    SearchView searchView;
    MenuInflater inflater;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflater = getMenuInflater();
        inflater.inflate(R.menu.searchtime_actionbar, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set toolbar working
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        titleView = findViewById(R.id.title);
        navView = findViewById(R.id.nav_view);

        Resources resource=getBaseContext().getResources();
        ColorStateList csl=(ColorStateList)resource.getColorStateList(R.color.navigation_menu_item_color);
        navView.setItemTextColor(csl);
        navView.getMenu().getItem(0).setChecked(true);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // 即：Up button "<-" will not be shown
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_news, R.id.navigation_trend, R.id.navigation_data, R.id.navigation_graph, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        // TODO: 这里我想让只有切换到新闻、图谱时才出现搜索按钮，但是不能直接控制searchbar
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
               // Drawable drawable = getDrawable(R.drawable.ic_search);
               // int alpha = 0;
                switch (destination.getId()) {
                    case R.id.navigation_news:
                        setTitle(R.string.title_news);
                //        alpha = 255;
                        break;
                    case R.id.navigation_data:
                        setTitle(R.string.title_data);

                        break;
                    case R.id.navigation_trend:
                        setTitle(R.string.title_trend);

                        break;
                    case R.id.navigation_graph:
                        setTitle(R.string.title_graph);
                //        alpha = 255;
                        break;
                    case R.id.navigation_settings:
                        setTitle(R.string.title_settings);
                        break;
                }
                myApplication app = (myApplication)getApplication();
                app.navId = destination.getId();
            }
        });

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }


    @Override
    public void setTitle(int resourceId) {
        super.setTitle(resourceId);
        titleView.setText(resourceId);
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
        View view1 = inflater.inflate(R.layout.bottomdialog, null);
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
