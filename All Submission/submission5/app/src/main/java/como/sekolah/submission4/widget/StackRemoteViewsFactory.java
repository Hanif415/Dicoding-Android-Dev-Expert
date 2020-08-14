package como.sekolah.submission4.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import como.sekolah.submission4.R;
import como.sekolah.submission4.entity.ResultItem;

import static como.sekolah.submission4.db.DatabaseMovieContract.MovieColumns.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Cursor cursor;
    private final List<ResultItem> widgetItem = new ArrayList<>();

    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public RemoteViews getViewAt(int i) {
        ResultItem item = getItem(i);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w185/" + item.getPoster())
                    .into(500, 500)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        remoteViews.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavoriteBannerWidget.EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return remoteViews;
    }

    private ResultItem getItem(int position) {
        if (!cursor.moveToPosition(position)){
            throw new IllegalStateException("Position Not Valid!");
        }

        return new ResultItem(cursor);
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
