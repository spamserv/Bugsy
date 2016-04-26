package hr.etfos.josipvojak.bugsy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lvArticles;
    ArrayList<Article> myArticles;
    ArticleAdapter myArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        this.init();

        GetArticlesTask Task = new GetArticlesTask();
        Task.execute(Resources.XML_NEWS);

        lvArticles.setOnItemClickListener(this);
    }

    private void init() {
        this.lvArticles = (ListView) findViewById(R.id.lvArticles);
        this.myArticles = new ArrayList<>();
        myArrayAdapter = new ArticleAdapter(this, myArticles);
        lvArticles.setAdapter(myArrayAdapter);
    }

    private void refreshArticles(ArrayList<Article> articles)
    {
        this.myArticles.addAll(articles);
        this.myArrayAdapter.notifyDataSetChanged();
    }
/*
    private void sortByCategory(ArrayList<Article> articles, String category) {
        ArrayList<Article> temp = null;
        for(int i=0;i<articles.size();i++) {
            Article a = (Article) myArrayAdapter.getItem(i);
            if(a.getMCategory() == "Zabava")
                temp.add(a);
        }

        this.myArticles.addAll(articles);
        this.myArrayAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Article a = (Article) myArrayAdapter.getItem(position);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(a.getMLink()));
        startActivity(i);
    }

    public void onClickRefresh(View view) {
        myArrayAdapter = new ArticleAdapter(this, myArticles);
        lvArticles.setAdapter(myArrayAdapter);
        this.myArrayAdapter.notifyDataSetChanged();
    }


    private class GetArticlesTask extends AsyncTask<String, Void, ArrayList<Article>>
    {
        @Override
        protected ArrayList<Article> doInBackground(String... params) {
            ArrayList<Article> Articles = new ArrayList<>();

            String ArticlesXML = params[0];
            try {
                URL ArticlesURL = new URL(ArticlesXML);
                HttpURLConnection Connection =
                        (HttpURLConnection) ArticlesURL.openConnection();
                InputStream ArticlesStream = Connection.getInputStream();
                this.processInputStream(ArticlesStream, Articles);

            } catch (Exception e) {
                Log.d("Exception1234", e.getMessage());
                e.printStackTrace();
            }

            return Articles;
        }

        private void processInputStream(
                InputStream articlesStream, ArrayList<Article> articles)
                throws XmlPullParserException, IOException {

            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser articleParser = parserFactory.newPullParser();
            articleParser.setInput(articlesStream, null);

            Article NewArticle = null;
            int Event = articleParser.getEventType();
            while (Event != XmlPullParser.END_DOCUMENT)
            {
                String EventName = articleParser.getName();
                switch (Event)
                {
                    case XmlPullParser.START_TAG:
                        switch (EventName)
                        {
                            case ArticleTags.TAG_ITEM:
                                NewArticle = new Article();
                                break;
                            case ArticleTags.TAG_TITLE:
                                if (NewArticle != null) {
                                    NewArticle.setMTitle(articleParser.nextText());
                                }
                                break;
                            case ArticleTags.TAG_DESCRIPTION:
                                if (NewArticle != null) {
                                    NewArticle.setMDescription(articleParser.nextText());
                                }
                                break;
                            case ArticleTags.TAG_LINK:
                                if (NewArticle != null) {
                                    NewArticle.setMLink(articleParser.nextText());
                                }
                                break;
                            case ArticleTags.TAG_CATEGORY:
                                if (NewArticle != null) {
                                    NewArticle.setMCategory(articleParser.nextText());
                                }
                                break;
                            case ArticleTags.TAG_PICTURE_URL:
                                if (NewArticle != null) {
                                    String pictureURL = articleParser.getAttributeValue(null,"url");
                                    NewArticle.setMPicture_URL(pictureURL);
                                }
                            default:
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        switch (EventName)
                        {
                            case ArticleTags.TAG_ITEM:
                                articles.add(NewArticle);
                                break;
                        }
                        break;
                    default:
                        break;
                }
                Event = articleParser.next();
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Article> article) {
            refreshArticles(article);
        }
    }


}
