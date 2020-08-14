package como.sekolah.submission3.viewModel;

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

import como.sekolah.submission3.model.Movies;
import cz.msebera.android.httpclient.Header;

public class MoviesViewModel extends ViewModel {

//    api key
    private static final String API_KEY = "62c32f94e7a678d91092722de67b27c9";
//    mutable live data
    private MutableLiveData<ArrayList<Movies>>listMovie = new MutableLiveData<>();




//    proses pengambilan data lewat api
    public void setMovie(final String movie) {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movies> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
//                    script di bawah berfungsi untuk mengambil data dari api dengan berbentuk JSON
                    JSONArray list = responseObject.getJSONArray("results");
//                    maka variable list menjadi sebuah data yang isi nya data film yang tadi saya ambil


//                    script si bawah berfungsi untuk memasukan data yang di simpan di variable list ke dalam
                    for (int i = 0; i<list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        Movies moviesItems = new Movies(movie);
                        listItems.add(moviesItems);
                    }

                    listMovie.postValue(listItems);


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



    public LiveData<ArrayList<Movies>> getMovie() {
        return listMovie;
    }
}