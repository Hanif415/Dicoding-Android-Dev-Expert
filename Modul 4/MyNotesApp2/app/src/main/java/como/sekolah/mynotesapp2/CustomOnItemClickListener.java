package como.sekolah.mynotesapp2;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener {

    private int position;
    private OnItemClickCallback onItemClickCallback;

    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View view) {
        onItemClickCallback.OnItemClicked(view, position);
    }

    public interface OnItemClickCallback {
        void OnItemClicked(View view, int position);
    }
}
