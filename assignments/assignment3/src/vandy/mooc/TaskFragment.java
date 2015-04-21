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

        void onProgressUpdate(int percent);

        void onCancelled();

        void onPostExecute();
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
            //TODO: Possbile handling of multiple URIs passed in.
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Log.e(TAG, e.toString());
            }
            Uri downloadedImage = Utils.downloadImage(getActivity().getApplicationContext(), params[0]);
            return downloadedImage;
        }


        @Override
        protected void onPostExecute(Uri uri) {
            Log.d(TAG, "onPostExecute in DownloadAsyncTask");
            new ApplyFilterAsyncTask().execute(uri);
        }
    }

    private class ApplyFilterAsyncTask extends AsyncTask<Uri, Integer, Uri> {

        @Override
        protected Uri doInBackground(Uri... params) {
            Uri grayScaledImage = Utils.grayScaleFilter(getActivity().getApplicationContext(), params[0]);
            return grayScaledImage;
        }

        @Override
        protected void onPostExecute(Uri uri) {
            Log.d(TAG, "onPostExecute in ApplyFilterAsyncTask");
            Intent intent = new Intent();
            Log.d(TAG, "result is: " + uri.toString());
            intent.putExtra("RESULT", uri.toString());
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }


}
