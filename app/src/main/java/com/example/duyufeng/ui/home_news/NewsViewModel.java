package com.example.duyufeng.ui.home_news;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.duyufeng.*;

import java.util.LinkedList;

public class NewsViewModel extends ViewModel {

    private LinkedList<News> newsItemList;
    private MutableLiveData<LinkedList<News>> newsItems;


    public NewsProvider provider; // 需要赋值给它


    public NewsViewModel() { newsItemList = new LinkedList<>(); }
    public NewsViewModel(NewsViewModel model) {
        newsItemList = model.newsItemList;
        newsItems = model.newsItems;
    }
    public LiveData<LinkedList<News>> getNewsItems() {
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
        if (newsItemList == null)
            newsItemList = new LinkedList<>();
        try {
            provider.refreshNews(20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        newsItemList.addAll(provider.pool);
        newsItems.setValue(newsItemList); // 步骤2
    }

    // TODO: 在这里得到更多新闻列表。
    // 步骤1：更新newsItemList
    // 步骤2：更新newsItems（最后一句话）
    public void loadMoreNewsItems() {
        /*
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int n = 10;
        if (newsItemList == null) {
            newsItemList = new LinkedList<NewsItem>();
        }
        for (int i = 0; i < n; ++i) {
            newsItemList.add(new SimpleNewsItem(
                    new SimpleNews("MoreNews" + newsItemList.size(),
                            "2018-01-01",
                            "Bznkxs" + i,
                            "abst",
                            "Contents,..."
                    )
            ));
        }

         */
        try {
            provider.moreNews(20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        newsItemList.addAll(provider.pool);
        newsItems.setValue(newsItemList);
    }

}