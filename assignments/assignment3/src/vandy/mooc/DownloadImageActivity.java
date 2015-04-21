package vandy.mooc;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;

public class DownloadImageActivity extends LifecycleLoggingActivity implements TaskFragment.TaskCallbacks {


    //TODO: handle tasks with orientation change
    private final String TAG = getClass().getSimpleName();
    private static final String TAG_TASK_FRAGMENT = "task_fragment";
    private TaskFragment mTaskFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getFragmentManager();
        mTaskFragment = (TaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);
        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new TaskFragment();
            fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
        }
    }


    @Override
    public Uri onPreExecute() {
        return getIntent().getData();
    }

    @Override
    public void onProgressUpdate(int percent) {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPostExecute() {

    }
}
