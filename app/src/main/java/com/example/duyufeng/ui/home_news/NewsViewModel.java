package com.example.duyufeng.ui.home_news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.duyufeng.NewsItem;
import com.example.duyufeng.NewsItemList;
import com.example.duyufeng.SimpleNews;
import com.example.duyufeng.SimpleNewsItem;

import java.util.LinkedList;

public class NewsViewModel extends ViewModel {
    // TODO: 这个的用法是xxx.list，可以当作LinkedList来使用。
    private NewsItemList newsItemList;
    private MutableLiveData<NewsItemList> newsItems;

    public NewsViewModel() {}
    public NewsViewModel(NewsViewModel model) {
        newsItemList = model.newsItemList;
        newsItems = model.newsItems;
    }
    public LiveData<NewsItemList> getNewsItems() {
        if (newsItems == null) {
            newsItems = new MutableLiveData<>();
            loadLatestNewsItems();
        }
        return newsItems;
    }

    // TODO: 在这里得到最新新闻列表。
    // 步骤1：更新newsItemList
    // 步骤2：更新newsItems（最后一句话）
    public void loadLatestNewsItems() {
        /*
        try {
            Thread.sleep(100); // 模拟加载时间，可以删掉
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */
        int n = 10;
        if (newsItemList == null) {
            newsItemList = new NewsItemList();
        }
        for (int i = 0; i < n; ++i) {
            newsItemList.list.add(new SimpleNewsItem(
                    new SimpleNews("news" + newsItemList.list.size(),
                            "2018-01-01",
                            "Bznkxs" + i,
                            "abst",
                            "Contents,..."
                            )
            ));
        }
        newsItems.setValue(newsItemList); // 步骤2
    }

    // TODO: 在这里得到更多新闻列表。
    // 步骤1：更新newsItemList
    // 步骤2：更新newsItems（最后一句话）
    public void loadMoreNewsItems() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int n = 10;
        if (newsItemList == null) {
            newsItemList = new NewsItemList();
        }
        for (int i = 0; i < n; ++i) {
            newsItemList.list.add(new SimpleNewsItem(
                    new SimpleNews("MoreNews" + newsItemList.list.size(),
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