package vandy.mooc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * An Activity that downloads an image, stores it in a local file on
 * the local device, and returns a Uri to the image file.
 */
public class DownloadImageActivity extends Activity {
    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG = getClass().getSimpleName();

    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., UI layout and
     * some class scope variable initialization.
     *
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Always call super class for necessary
        // initialization/implementation.
        super.onCreate(savedInstanceState);

        // Get the URL associated with the Intent data.
        final Uri data = getIntent().getData();
        Log.d(TAG, "onCreate->data: " + data);

        // Download the image in the background, create an Intent that
        // contains the path to the image file, and set this as the
        // result of the Activity.

        Thread downloader = new Thread(new Runnable() {

            @Override
            public void run() {
                Uri downloadImage = DownloadUtils.downloadImage(getApplicationContext(), data);
                Message msgObj = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("URI", downloadImage.toString());
                msgObj.setData(bundle);
                handler.sendMessage(msgObj);
            }

            private final Handler handler = new Handler(getMainLooper()) {

                public void handleMessage(Message msg) {
                    Intent intent = new Intent();
                    intent.putExtra("RESULT", msg.getData().getString("URI"));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            };
        });

        downloader.start();
    }


}
