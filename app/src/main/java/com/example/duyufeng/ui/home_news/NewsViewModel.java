package com.example.duyufeng.ui.home_news;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.duyufeng.*;

import java.util.Arrays;
import java.util.LinkedList;

public class NewsViewModel extends ViewModel {

    private LinkedList<News> newsItemList, newsItemListPaper;
    private MutableLiveData<LinkedList<News>> newsItems, newsItemsPaper;

    public boolean firstrun = true, firstrunPaper = true;
    public boolean isFirstrun(String type) {
        if (type.equals("paper"))
            return firstrunPaper;
        return firstrun;
    }

    public NewsProvider provider,providerPaper; // 需要赋值给它

    LinkedList<News> _getNewsItemList(String type) {
        if (type.equals("paper"))
            return newsItemListPaper;
        return newsItemList;
    }

    MutableLiveData<LinkedList<News>> _getNewsItems(String type) {
        if (type.equals("paper"))
            return newsItemsPaper;
        return newsItems;
    }


    public NewsViewModel() { newsItemList = new LinkedList<>();newsItemListPaper = new LinkedList<>(); }
    public NewsViewModel(NewsViewModel model) {
        newsItemList = model.newsItemList;
        newsItems = model.newsItems;
        newsItemListPaper = model.newsItemListPaper;
        newsItemsPaper = model.newsItemsPaper;
    }
    public LiveData<LinkedList<News>> getNewsItems(String type) {
        if (type.equals("news")) {
            if (newsItems == null) {
                newsItems = new MutableLiveData<>();
                newsItemList.clear();
                newsItemList.addAll(provider.pool);
                newsItems.setValue(newsItemList);
                loadLatestNewsItems(type);
            }
            return newsItems;
        }
        else {
            if (newsItemsPaper == null) {
                newsItemsPaper = new MutableLiveData<>();
                newsItemListPaper.clear();
                newsItemListPaper.addAll(providerPaper.pool);
                newsItemsPaper.setValue(newsItemList);
                loadLatestNewsItems(type);
            }
            return newsItemsPaper;
        }

    }

    // TODO: 在这里得到最新新闻列表。
    // 步骤1：更新newsItemList
    // 步骤2：更新newsItems（最后一句话）

    public void loadLatestNewsItems(String type) {

        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (type.equals("news")) {
                    NewsProvider provider = NewsViewModel.this.provider;
                    if (newsItemList == null)
                        newsItemList = new LinkedList<>();
                    try {
                        provider.refreshNews(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    newsItemList.clear();
                    newsItemList.addAll(provider.pool);
                    firstrun = false;
                    newsItems.postValue(newsItemList); // 步骤2

                } else {
                    NewsProvider provider = NewsViewModel.this.providerPaper;
                    if (newsItemListPaper == null)
                        newsItemListPaper = new LinkedList<>();
                    try {
                        provider.refreshNews(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    newsItemListPaper.clear();
                    newsItemListPaper.addAll(provider.pool);
                    firstrunPaper = false;
                    newsItemsPaper.postValue(newsItemListPaper); // 步骤2
                }
            }
        });
        myThread.start();
    }

    // TODO: 在这里得到更多新闻列表。
    // 步骤1：更新newsItemList
    // 步骤2：更新newsItems（最后一句话）
    public void loadMoreNewsItems(String type) {
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (type.equals("news")) {
                    NewsProvider provider = NewsViewModel.this.provider;
                    if (newsItemList == null)
                        newsItemList = new LinkedList<>();
                    try {
                        provider.moreNews(20);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    newsItemList.clear();
                    newsItemList.addAll(provider.pool);
                    newsItems.postValue(newsItemList); // 步骤2
                } else {
                    NewsProvider provider = NewsViewModel.this.providerPaper;
                    if (newsItemListPaper == null)
                        newsItemListPaper = new LinkedList<>();
                    try {
                        provider = providerPaper;

                        provider.moreNews(20);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    newsItemListPaper.clear();
                    newsItemListPaper.addAll(provider.pool);
                    newsItemsPaper.postValue(newsItemListPaper); // 步骤2
                }
            }
        });
        myThread.start();
    }
}