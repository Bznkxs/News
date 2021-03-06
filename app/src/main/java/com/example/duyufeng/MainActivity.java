package com.example.duyufeng;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.SearchRecentSuggestions;
import android.view.*;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDestination;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duyufeng.ui.data.DataViewModel;
import com.example.duyufeng.ui.home_news.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView = null;
    LinearLayoutManager layoutManager;
    Toolbar myToolbar;
    //TabLayout tabLayout;
    BottomNavigationView navView;

    TextView titleView;
    SearchView searchView;
    MenuInflater inflater;
    NavController navController;
    int dId;

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

        menu.close();

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

        Resources resource = getBaseContext().getResources();
        ColorStateList csl = (ColorStateList)resource.getColorStateList(R.color.navigation_menu_item_color);
        navView.setItemTextColor(csl);
        navView.getMenu().getItem(0).setChecked(true);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // 即：Up button "<-" will not be shown
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_news, R.id.navigation_trend, R.id.navigation_data, R.id.navigation_graph, R.id.navigation_people)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        // TODO: 这里我想让只有切换到新闻、图谱时才出现搜索按钮，但是不能直接控制searchbar
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
               // Drawable drawable = getDrawable(R.drawable.ic_search);
               // int alpha = 0;
                dId = destination.getId();
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
                    case R.id.navigation_people:
                        setTitle(R.string.title_people);
                        break;
                }
                myApplication app = (myApplication)getApplication();
                app.navId = destination.getId();
            }
        });

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        // load data
        DataViewModel model = new ViewModelProvider(this).get(DataViewModel.class);
        try {
            model.refresh();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTitle(int resourceId) {
        super.setTitle(resourceId);
        titleView.setText(resourceId);
    }

    public void toDetailView(View view) {
        // Do something in response to button

        myApplication list = (myApplication) getApplication();
        list.detailNews = ((NewsItemLayout)(view.getParent().getParent().getParent().getParent())).item;
        try {
            list.detailNews.saveContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, DetailNewsActivity.class));
    }

    public void toPersonView(View view) {
        // Do something in response to button

        myApplication list = (myApplication) getApplication();
        list.personInfo = ((PeopleLayout)(view.getParent().getParent().getParent().getParent())).item;
        startActivity(new Intent(this, PersonActivity.class));
    }



    void clearHistory() {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                com.example.duyufeng.MySuggestionProvider.AUTHORITY, com.example.duyufeng.MySuggestionProvider.MODE);
        suggestions.clearHistory();
    }


    public void deleteCache() {
        // Toast.makeText(view.getContext(),"You clicked `...'!", Toast.LENGTH_SHORT).show();
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
                clearHistory();
                Toast.makeText(view.getContext(), R.string.delete_complete, Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });




    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delsearch:
                deleteCache();
                break;
            case R.id.action_viewcache:
                startActivity(new Intent(this, CacheActivity.class));
                break;
            case R.id.action_chgtab:
                ManageTabs();
                break;
        }
        return true;
    }

    public void ManageTabs() {
        // Toast.makeText(view.getContext(),"You clicked `...'!", Toast.LENGTH_SHORT).show();
        final Dialog dialog = new Dialog(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.dialog_manage_tabs, null);
        dialog.setContentView(view1);
        dialog.show();
        Button button = dialog.findViewById(R.id.btnConfirm);
        myApplication list = (myApplication) getApplication();
        final int[] w = {list.tabConfig};
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.tabConfig = w[0];
                dialog.dismiss();
                list.adapter.setConfiguration(list.tabConfig);
                list.adapter.notifyDataSetChanged();
            }
        });

        Button button1 = dialog.findViewById(R.id.btnCancel);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                w[0] = list.tabConfig;
                dialog.dismiss();

            }
        });

        CheckBox b1 = dialog.findViewById(R.id.checkBox);
        CheckBox b2 = dialog.findViewById(R.id.checkBox2);
        b1.setChecked(w[0] >= 2);
        b2.setChecked((w[0] & 1) == 1);
        View.OnClickListener listener =  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                w[0] = 0;
                if (b1.isChecked())
                    w[0] += 2;
                if (b2.isChecked())
                    w[0] += 1;

            }
        };
        b1.setOnClickListener(listener);
        b2.setOnClickListener(listener);
    }

}
