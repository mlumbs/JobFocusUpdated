package jobfocus.developx.onfleeck.co.za.jobfocus;

import android.support.v7.widget.RecyclerView;

/**
 * Created by DevelopX on 2016-04-14.
 */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if(dy > 0||dy <0) //check for scroll down
        {
            onHide();
        }

    }

    public abstract void onHide();

    //public abstract void onShow();
}
