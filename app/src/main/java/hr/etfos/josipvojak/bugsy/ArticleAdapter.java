package hr.etfos.josipvojak.bugsy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jvojak on 25.4.2016..
 */
public class ArticleAdapter extends BaseAdapter{
    Context ctx;
    ArrayList<Article> articleList;
    Bitmap image;
    boolean isReady = false;
    ProgressBar pbGetImagesProgress;
    ImageView ivArticlePictureURL;
    TextView tvArticleTitle, tvArticleDescription, tvArticleCategory;

    public ArticleAdapter(Context ctx, ArrayList<Article> articleList) {
        super();
        this.ctx = ctx;
        this.articleList = articleList;
    }

    @Override
    public int getCount() {
        return this.articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.articleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(ctx, R.layout.list_item_article, null);
        }
        Article current = articleList.get(position);
        ivArticlePictureURL   = (ImageView) convertView.findViewById(R.id.ivArticlePicture);
        tvArticleTitle = (TextView) convertView.findViewById(R.id.tvArticleTitle);
        tvArticleDescription  = (TextView) convertView.findViewById(R.id.tvArticleDescription);
        tvArticleCategory = (TextView) convertView.findViewById(R.id.tvArticleCategory);

        tvArticleTitle.setText(current.getMTitle());
        tvArticleDescription.setText(current.getMDescription());
        tvArticleCategory.setText(current.getMCategory());

        Picasso.with(ctx).load(current.getMPicture_URL()).into(ivArticlePictureURL);

        return convertView;
    }

}
