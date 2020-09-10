package com.example.duyufeng;

// TODO
public class SimpleNews implements News  {
    String name, date, author, abst;
    String content;
    SimpleNews(String s) {
        name = "";
        date = "";
        author = "";
        abst = "";
        content = s;
    }

    SimpleNews() {
        name = "";
        date = "";
        author = "";
        abst = "";
        content = null;
    }

    public SimpleNews(String name, String date, String author, String abst, String s) {
        this.name = name;
        this.date = date;
        this.author = author;
        this.abst = abst;
        content = s;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getAuthor() {
        return author;
    }


    @Override
    public String getContent() {
        if (content == null) {

            try {
                Thread.sleep(300); // 模拟加载
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            content = "沃尔沃阿娇；啊；是的减肥\n";// some random string

        }
        return content;
    }

    @Override
    public void removeContent() {
        content = null;
    }

//    @Override
//    public String peekContent() {
//        return content;
//    }


    @Override
    public boolean isSaved() {
        return content!=null;
    }

    @Override
    public void saveContent()  {
        getContent();
    }

    @Override
    public String getAbstract() {
        return abst;
    }
}
