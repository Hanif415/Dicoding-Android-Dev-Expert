package como.sekolah.submission4.ui.tvshow;

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

import como.sekolah.submission4.BuildConfig;
import como.sekolah.submission4.entity.TVShows;
import cz.msebera.android.httpclient.Header;

public class TvshowViewModel extends ViewModel {

    private MutableLiveData<ArrayList<TVShows>> listTvShow = new MutableLiveData<>();

    public LiveData<ArrayList<TVShows>> getTvShow (){
        return listTvShow;
    }

    public void setTvShow(final String tvShow) {
        final ArrayList <TVShows> listItem = new ArrayList<>();

        String url ="https://api.themoviedb.org/3/discover/tv?api_key="+ BuildConfig.API_KEY +"&language=en-US";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list= responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++){
                        JSONObject tvShows = list.getJSONObject(i);
                        TVShows TVShowsItems = new TVShows(tvShows);
                        listItem.add(TVShowsItems);
                    }
                    listTvShow.postValue(listItem);

                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Exception", error.getMessage());
            }
        });
    }
}