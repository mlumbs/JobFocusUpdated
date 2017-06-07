package jobfocus.developx.onfleeck.co.za.jobfocus;

import android.app.ProgressDialog;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by DevelopX on 2017-03-04.
 */

public class AppWeb extends WebViewClient {

    private ProgressBar pb;

    public AppWeb(ProgressBar bar){
        this.pb =bar;
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //return super.shouldOverrideUrlLoading(view, url);
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url){
        super.onPageFinished(view,url);
        pb.setVisibility(View.GONE);

    }

}
