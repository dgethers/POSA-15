package vandy.mooc;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class DownloadImageActivity extends Activity implements TaskFragment.TaskCallbacks {


    private static final String TAG_TASK_FRAGMENT = "task_fragment";
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getFragmentManager();
        TaskFragment mTaskFragment = (TaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);
        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new TaskFragment();
            fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
        }
    }


    @Override
    public Uri onPreExecute() {
        Log.d(TAG, "Getting intent data from previous Activity");
        return getIntent().getData();
    }
}
