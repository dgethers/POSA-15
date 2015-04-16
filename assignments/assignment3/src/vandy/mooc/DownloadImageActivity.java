package vandy.mooc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class DownloadImageActivity extends LifecycleLoggingActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Uri data = getIntent().getData();


        Thread downloader = new Thread(new Runnable() {

            @Override
            public void run() {
                Uri downloadImage = Utils.downloadImage(getApplicationContext(), data);
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
