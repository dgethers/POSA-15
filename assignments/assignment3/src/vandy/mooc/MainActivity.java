package vandy.mooc;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends LifecycleLoggingActivity {

    private final String TAG = getClass().getSimpleName();


    private static final int DOWNLOAD_IMAGE_REQUEST = 1;

    private EditText mUrlEditText;

    private Uri mDefaultUrl = Uri.parse("http://www.dre.vanderbilt.edu/~schmidt/robot.png");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mUrlEditText = (EditText) findViewById(R.id.url);


    }

    public void downloadImage(View view) {
        hideKeyboard(this,
                mUrlEditText.getWindowToken());

        Uri url = getUrl();
        if (url == null) {
            Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show();
        } else {
            Intent imageDownloadIntent = makeDownloadImageIntent(url);
            startActivityForResult(imageDownloadIntent, DOWNLOAD_IMAGE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == DOWNLOAD_IMAGE_REQUEST) {
                Intent intent = makeGalleryIntent(data.getStringExtra("RESULT"));

                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "A problem occurred trying to download contents of URL", Toast.LENGTH_SHORT).show();
        }
    }

    private Intent makeGalleryIntent(String pathToImageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(pathToImageFile)), "image/*");

        return intent;
    }

    private Intent makeDownloadImageIntent(Uri url) {

        Intent downloadActivityIntent = new Intent(getApplicationContext(), DownloadImageActivity.class);
        downloadActivityIntent.setData(url);
        return downloadActivityIntent;

//        return new Intent(Intent.ACTION_WEB_SEARCH, url);
    }

    protected Uri getUrl() {
        Uri url = Uri.parse(mUrlEditText.getText().toString());

        String uri = url.toString();
        if (uri == null || uri.equals(""))
            url = mDefaultUrl;

        boolean isUriFormatCorrect = Patterns.WEB_URL.matcher(url.toString()).matches();

        if (isUriFormatCorrect)
            return url;
        else {
            return null;
        }
    }

    public void hideKeyboard(Activity activity,
                             IBinder windowToken) {
        InputMethodManager mgr =
                (InputMethodManager) activity.getSystemService
                        (Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }
}
