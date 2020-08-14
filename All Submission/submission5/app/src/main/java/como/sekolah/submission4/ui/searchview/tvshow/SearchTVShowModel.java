package como.sekolah.submission4.ui.searchview.tvshow;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import como.sekolah.submission4.BuildConfig;
import como.sekolah.submission4.entity.TVShows;
import cz.msebera.android.httpclient.Header;

public class SearchTVShowModel extends ViewModel {

    private static final String API_KEY = "62c32f94e7a678d91092722de67b27c9";
    private MutableLiveData<ArrayList<TVShows>> listTVShow = new MutableLiveData<>();

    public void setTVShow(final String tvshow, String title) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVShows> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key="+ BuildConfig.API_KEY+"&language=en-US&query=" + title;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject tvShow = list.getJSONObject(i);
                        TVShows tvShowsItems = new TVShows(tvShow);
                        listItems.add(tvShowsItems);
                    }

                    listTVShow.setValue(listItems);

                } catch (JSONException e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<ArrayList<TVShows>> getTVShow(){
        return listTVShow;
    }
}
