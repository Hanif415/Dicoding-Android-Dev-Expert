package como.sekolah.submission4.ui.searchview.movie;

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
import como.sekolah.submission4.entity.Movies;
import cz.msebera.android.httpclient.Header;

public class SearchMovieModel extends ViewModel {

    private MutableLiveData<ArrayList<Movies>> listMovie = new MutableLiveData<>();

    public void setMovie(final String movie, String title) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movies> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+ BuildConfig.API_KEY +"&language=en-US&query=" + title;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        Movies moviesItems = new Movies(movie);
                        listItems.add(moviesItems);
                    }

                    listMovie.postValue(listItems);

                } catch (JSONException e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Exception", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    public LiveData<ArrayList<Movies>> getMovie() {
        return listMovie;
    }
}
