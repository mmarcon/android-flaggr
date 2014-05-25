package me.marcon.flaggr.togglr;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import me.marcon.flaggr.receiver.FlaggrReceiver;

import java.util.List;


public class TogglrActivity extends Activity {

    private static final String TAG = TogglrActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_togglr);
        List<ResolveInfo> availableApps = scan();
        for(ResolveInfo info : availableApps) {
            CharSequence label = info.loadLabel(getPackageManager());
            Log.d(TAG, label.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.togglr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<ResolveInfo> scan() {
        Intent flaggrIntent = new Intent();
        flaggrIntent.setAction(FlaggrReceiver.FLAGGR_RECEIVER_DEFAULT_INTENT_ACTION);
        PackageManager packageManager = getPackageManager();
        return packageManager.queryBroadcastReceivers(flaggrIntent, PackageManager.MATCH_DEFAULT_ONLY);
    }

}
