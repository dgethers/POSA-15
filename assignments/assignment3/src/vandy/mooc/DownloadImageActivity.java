package vandy.mooc;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class DownloadImageActivity extends LifecycleLoggingActivity {


    //TODO: handle tasks with orientation change
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Uri data = getIntent().getData();

        new DownloadAsyncTask().execute(data);
    }


    private class DownloadAsyncTask extends AsyncTask<Uri, Integer, Uri> {

        @Override
        protected Uri doInBackground(Uri... params) {
            //TODO: Possbile handling of multiple URIs passed in.
            Uri downloadedImage = Utils.downloadImage(getApplicationContext(), params[0]);
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
            Uri grayScaledImage = Utils.grayScaleFilter(getApplicationContext(), params[0]);
            return grayScaledImage;
        }

        @Override
        protected void onPostExecute(Uri uri) {
            Log.d(TAG, "onPostExecute in ApplyFilterAsyncTask");
            Intent intent = new Intent();
            Log.d(TAG, "result is: " + uri.toString());
            intent.putExtra("RESULT", uri.toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
