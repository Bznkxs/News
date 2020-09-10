package com.example.duyufeng.ui.graph;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.duyufeng.Graph;
import com.example.duyufeng.NodeInfo;
import com.example.duyufeng.NodeShowPair;
import org.json.JSONException;

import java.io.IOException;

public class GraphViewModel extends ViewModel implements Loader.Test {
    MutableLiveData<Graph> graphData = new MutableLiveData<>();


    public void setGraph(String label) {
        Loader loader = new Loader(label, this);
        loader.start();
    }

    public LiveData<Graph> getGraph() {
        return graphData;
    }

    public void LoadComplete(Graph graph) {
        graphData.postValue(graph);
    }

}

class Loader extends Thread {
    Test model;
    String label;
    public interface Test {
        void LoadComplete(Graph graph);
    }
    public Loader(String label, GraphViewModel model) {
        this.label = label;
        this.model = model;
    }
    @Override
    public void run() {
        Graph graph = null;
        try {
            graph = new Graph(label);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (model != null) {
            model.LoadComplete(graph);
        }
    }
}

