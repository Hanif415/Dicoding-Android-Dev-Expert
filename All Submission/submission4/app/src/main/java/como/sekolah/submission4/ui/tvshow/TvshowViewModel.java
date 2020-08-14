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

import como.sekolah.submission4.entity.TvShows;
import cz.msebera.android.httpclient.Header;

public class TvshowViewModel extends ViewModel {

    private static final String API_KEY = "62c32f94e7a678d91092722de67b27c9";
    private MutableLiveData<ArrayList<TvShows>> listTvShow = new MutableLiveData<>();

    public LiveData<ArrayList<TvShows>> getTvShow (){
        return listTvShow;
    }

    public void setTvShow(final String tvShow) {
        final ArrayList <TvShows> listItem = new ArrayList<>();

        String url ="https://private-5d1b4-themoviedbmock.apiary-mock.com/3/discover/tv?api_key="+ API_KEY +"&language=en-US";
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
                        TvShows tvShowsItems = new TvShows(tvShows);
                        listItem.add(tvShowsItems);
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