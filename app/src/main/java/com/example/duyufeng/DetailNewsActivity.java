package com.example.duyufeng;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailNewsActivity extends AppCompatActivity {
    News mNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar abar = getSupportActionBar();
        abar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mNews = intent.getParcelableExtra(String.valueOf(R.string.Item));
        ((TextView)findViewById(R.id.Title)).setText(mNews.getName());
        ((TextView)findViewById(R.id.affix)).setText(mNews.getAuthor() + "    " + mNews.getDate());
        // getNews
        // getContent可能需要一定时间，这里需要开多线程，进入等待状态

        ((TextView)findViewById(R.id.Content)).setText(mNews.getContent());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_actionbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            share(mNews.getName() + "\n" + mNews.getAuthor() + " " + mNews.getDate() + "\n" +
                    mNews.getContent() + "\n--------\n" + "获取更多疫情相关资讯，请安装疫情速报App！");
        }
        else { // 16908332
            Toast.makeText(this, String.valueOf(item.getItemId()), Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }


    /**
     * Android share */
    private void share(String shareContent) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);
        share_intent.setType("text/plain");
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "Share1");
        share_intent.putExtra(Intent.EXTRA_TEXT, shareContent);

        share_intent = Intent.createChooser(share_intent, "Share2");
        startActivity(share_intent);
    }
}
