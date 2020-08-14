package como.sekolah.submission4.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import como.sekolah.submission4.db.DatabaseMovieContract;
import como.sekolah.submission4.db.DatabaseTVShowContract;
import como.sekolah.submission4.db.MovieHelper;
import como.sekolah.submission4.db.TVShowHelper;

import static como.sekolah.submission4.db.DatabaseMovieContract.AUTHORITY;
import static como.sekolah.submission4.db.DatabaseMovieContract.TABLE_NAME;

public class Provider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TVSHOW = 3;
    private static final int TVSHOW_ID = 4;
    private MovieHelper movieHelper;
    private TVShowHelper tvShowHelper;

    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://como.sekolah.submission4/movie
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIE);
        // content://como.sekolah.submission4/movie/id
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", MOVIE_ID);

        // content://como.sekolah.submission4/tv_show
        sUriMatcher.addURI(AUTHORITY, DatabaseTVShowContract.TABLE_NAME, TVSHOW);
        // content://como.sekolah.submission4/tv_show/id
        sUriMatcher.addURI(AUTHORITY, DatabaseTVShowContract.TABLE_NAME + "/#", TVSHOW_ID);
    }
    public Provider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        boolean isMovie = false;
        Uri contentUri;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                deleted = movieHelper.deleteById(uri.getLastPathSegment());
                isMovie = true;
                break;
            case TVSHOW_ID:
                deleted = tvShowHelper.deleteById(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
        }

        if (isMovie){
            contentUri = DatabaseMovieContract.MovieColumns.CONTENT_URI;
        } else {
            contentUri = DatabaseTVShowContract.TvShowColumns.CONTENT_URI;
        }

        getContext().getContentResolver().notifyChange(contentUri, null);

        return deleted;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri contentUri;
        long added;
        boolean isMovie = false;

        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added = movieHelper.insert(contentValues);
                isMovie = true;
                break;
            case TVSHOW:
                added = tvShowHelper.insert(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (isMovie){
            contentUri = DatabaseMovieContract.MovieColumns.CONTENT_URI;
        } else {
            contentUri = DatabaseTVShowContract.TvShowColumns.CONTENT_URI;
        }

        getContext().getContentResolver().notifyChange(contentUri, null);

        return Uri.parse(contentUri + "/" + added);
    }

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();
        tvShowHelper = TVShowHelper.getInstance(getContext());
        tvShowHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                cursor = movieHelper.queryAll();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryById(uri.getLastPathSegment());
                break;
            case TVSHOW:
                cursor = tvShowHelper.queryAll();
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
