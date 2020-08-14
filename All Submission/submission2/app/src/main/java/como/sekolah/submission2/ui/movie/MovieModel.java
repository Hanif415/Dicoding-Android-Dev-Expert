package como.sekolah.submission2.ui.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MovieModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is movie fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}