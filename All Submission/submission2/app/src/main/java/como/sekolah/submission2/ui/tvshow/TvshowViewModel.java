package como.sekolah.submission2.ui.tvshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TvshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TvshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Tv Show fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}