package me.marcon.flaggr.togglr;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import me.marcon.flaggr.receiver.FlaggrReceiver;
import me.marcon.flaggr.togglr.adapter.TogglrAdapter;

import java.util.List;


public class TogglrActivity extends Activity {

    private static final String TAG = TogglrActivity.class.getCanonicalName();

    private ListView mListView;
    private TogglrAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_togglr);

        mListView = (ListView) findViewById(R.id.togglrList);

        List<ResolveInfo> availableApps = scan();
        mAdapter = new TogglrAdapter(this, availableApps);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new ItemClickListener());
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
        return packageManager.queryBroadcastReceivers(flaggrIntent, PackageManager.GET_INTENT_FILTERS);
    }

    private CharSequence getApplicationName(ResolveInfo resolveInfo) {
        return resolveInfo.loadLabel(getPackageManager());
    }

    private class ItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final ResolveInfo info = mAdapter.getItem(position);
            final Dialog dialog = new Dialog(TogglrActivity.this);
            dialog.setContentView(R.layout.togglr_dialog);
            dialog.setTitle(getApplicationName(info));

            final TextView flagNameTextView = (TextView) dialog.findViewById(R.id.flagName);
            final ToggleButton flagValueBtn = (ToggleButton) dialog.findViewById(R.id.flagValue);
            final Button okBtn = (Button) dialog.findViewById(R.id.ok_btn);
            final Button cancelBtn = (Button) dialog.findViewById(R.id.cancel_btn);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String flagName = flagNameTextView.getText().toString();
                    boolean flagValue = flagValueBtn.isChecked();
                    Intent flaggrIntent = new Intent();
                    flaggrIntent.setPackage(info.resolvePackageName);
                    flaggrIntent.setAction(FlaggrReceiver.FLAGGR_RECEIVER_DEFAULT_INTENT_ACTION);
                    flaggrIntent.putExtra(FlaggrReceiver.FLAGGR_RECEIVER_FLAG_KEY_INTENT_EXTRA, flagName);
                    flaggrIntent.putExtra(FlaggrReceiver.FLAGGR_RECEIVER_FLAG_VALUE_INTENT_EXTRA, flagValue);
                    sendBroadcast(flaggrIntent);
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

}
