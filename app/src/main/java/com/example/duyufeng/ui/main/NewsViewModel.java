package com.example.duyufeng.ui.main;

import android.graphics.pdf.PdfDocument;
import androidx.arch.core.util.Function;
import androidx.lifecycle.*;
import com.example.duyufeng.ui.home_news.HomeViewModel;

public class NewsViewModel extends HomeViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    public NewsViewModel(HomeViewModel model) {
        super(model);
    }
    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<Integer> getIndex() {
        return mIndex;
    }

}