package vandy.mooc;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * User: outzider
 * Date: 4/21/15
 * Time: 5:16 PM
 */
public class TaskFragment extends Fragment {

    interface TaskCallbacks {

        Uri onPreExecute();

        void onProgressChange(int value);
    }

    private TaskCallbacks mParentActivity;
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mParentActivity = (TaskCallbacks) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        new DownloadAsyncTask().execute(mParentActivity.onPreExecute());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mParentActivity = null;
    }


    private class DownloadAsyncTask extends AsyncTask<Uri, Integer, Uri> {

        @Override
        protected Uri doInBackground(Uri... params) {
            Log.d(TAG, "Async Task downloading image");
            return Utils.downloadImage(getActivity().getApplicationContext(), params[0]);
        }

        @Override
        protected void onPostExecute(Uri uri) {
            mParentActivity.onProgressChange(50);
            new ApplyFilterAsyncTask().execute(uri);
        }
    }

    private class ApplyFilterAsyncTask extends AsyncTask<Uri, Integer, Uri> {

        @Override
        protected Uri doInBackground(Uri... params) {
            Log.d(TAG, "Async Task applying gray scale filter to image");
            return Utils.grayScaleFilter(getActivity().getApplicationContext(), params[0]);
        }

        @Override
        protected void onPostExecute(Uri uri) {
            Intent intent = new Intent();
            intent.putExtra("RESULT", uri.toString());
            mParentActivity.onProgressChange(100);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }
}
