package vandy.mooc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public abstract class LifecycleLoggingActivity extends Activity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
		
        if(savedInstanceState != null) {
            Log.d(TAG,
                  "onCreate(): activity re-created from savedInstanceState");
						
        } else {
            Log.d(TAG,
                  "onCreate(): activity created anew");
        }
		
    }
	
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
	
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
	
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
	
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
	
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart() called");
    }
	
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
}
