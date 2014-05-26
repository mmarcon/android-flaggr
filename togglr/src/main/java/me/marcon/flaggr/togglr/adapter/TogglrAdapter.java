package me.marcon.flaggr.togglr.adapter;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import me.marcon.flaggr.togglr.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmarcon on 5/26/14.
 */
public class TogglrAdapter extends ArrayAdapter<ResolveInfo> {

    private static final int LAYOUT_ID = R.layout.item_resolve_info;

    private final LayoutInflater mInflater;

    public TogglrAdapter(Context context, List<ResolveInfo> resolveInfos) {
        super(context, LAYOUT_ID, resolveInfos);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResolveInfoHolder holder;

        if(row == null) {
            row = mInflater.inflate(LAYOUT_ID, null);
            holder = new ResolveInfoHolder();
            holder.appName = (TextView) row.findViewById(R.id.appName);
            row.setTag(holder);
        } else {
            holder = (ResolveInfoHolder) row.getTag();
        }

        ResolveInfo resolveInfo = getItem(position);
        holder.appName.setText(resolveInfo.loadLabel(getContext().getPackageManager()));

        return row;
    }

    private static final class ResolveInfoHolder {
        TextView appName;
    }
}
