package com.example.duyufeng.ui.home_news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.duyufeng.NewsItem;
import com.example.duyufeng.R;
import com.example.duyufeng.SimpleNews;
import com.example.duyufeng.SimpleNewsItem;

import java.util.LinkedList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private LinkedList<NewsItem> newsItemList;
    private MutableLiveData<LinkedList<NewsItem>> newsItems;

    public HomeViewModel() {}
    public HomeViewModel(HomeViewModel model) {
        newsItemList = model.newsItemList;
        newsItems = model.newsItems;
    }
    public LiveData<LinkedList<NewsItem>> getNewsItems() {
        if (newsItems == null) {
            newsItems = new MutableLiveData<>();
            loadNewsItems();
        }
        return newsItems;
    }

    public void loadNewsItems() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int n = 10;
        if (newsItemList == null) {
            newsItemList = new LinkedList<>();
        }
        for (int i = 0; i < n; ++i) {
            newsItemList.add(new SimpleNewsItem(
                    new SimpleNews("news" + newsItemList.size(),
                            "2018-01-01",
                            "Bznkxs" + i,
                            "abst",
                            "Contents,..."
                            )
            ));
        }
        newsItems.setValue(newsItemList);
    }

}