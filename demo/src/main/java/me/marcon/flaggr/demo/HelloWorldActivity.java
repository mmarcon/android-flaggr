package me.marcon.flaggr.demo;

import android.app.Activity;
import android.os.Bundle;
import me.marcon.flaggr.Flaggr;

public class HelloWorldActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Flaggr.with(this).isEnabled(R.bool._new_layout)) {
            setContentView(R.layout.new_main);
        } else {
            setContentView(R.layout.old_main);
        }
    }
}