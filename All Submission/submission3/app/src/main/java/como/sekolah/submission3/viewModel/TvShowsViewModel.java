package como.sekolah.submission3.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import como.sekolah.submission3.model.TvShows;
import cz.msebera.android.httpclient.Header;

public class TvShowsViewModel extends ViewModel {

    private static final String API_KEY = "62c32f94e7a678d91092722de67b27c9";
    private MutableLiveData<ArrayList<TvShows>> listTvshow = new MutableLiveData<>();

    public LiveData<ArrayList<TvShows>> getvshow() {
        return listTvshow;
    }

    public void setTvshow(final String tvshow) {


        final ArrayList<TvShows> listItems = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+ API_KEY +"&language=en-US";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++){
                        JSONObject tvShows = list.getJSONObject(i);
                        TvShows tvShowsItems = new TvShows(tvShows);
                        listItems.add(tvShowsItems);
                    }

                    listTvshow.postValue(listItems);


                } catch (Exception e) {
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