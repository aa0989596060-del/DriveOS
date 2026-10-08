package com.driveos;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final List<ApplicationInfo> apps = new ArrayList<>();
    private PackageManager pm;
    private TextView timeText, dateText, statusText;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pm = getPackageManager();

        timeText = findViewById(R.id.timeText);
        dateText = findViewById(R.id.dateText);
        statusText = findViewById(R.id.statusText);

        findViewById(R.id.navButton).setOnClickListener(v -> openNavigation());
        findViewById(R.id.musicButton).setOnClickListener(v -> openMusic());
        findViewById(R.id.phoneButton).setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_DIAL)));
        findViewById(R.id.appsButton).setOnClickListener(v ->
                statusText.setText("Showing installed launchable apps"));

        loadApps();
        updateClock();
        enableImmersive();
    }

    private void loadApps() {
        apps.clear();
        Intent main = new Intent(Intent.ACTION_MAIN);
        main.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ApplicationInfo> list = new ArrayList<>();
        for (android.content.pm.ResolveInfo ri : pm.queryIntentActivities(main, 0)) {
            if (ri.activityInfo != null && ri.activityInfo.applicationInfo != null) {
                list.add(ri.activityInfo.applicationInfo);
            }
        }
        Collections.sort(list, (a,b) -> {
            String x = a.loadLabel(pm).toString();
            String y = b.loadLabel(pm).toString();
            return x.compareToIgnoreCase(y);
        });
        apps.addAll(list);

        GridView grid = findViewById(R.id.appGrid);
        grid.setAdapter(new AppAdapter());
        grid.setOnItemClickListener((parent, view, position, id) -> {
            ApplicationInfo info = apps.get(position);
            Intent launch = pm.getLaunchIntentForPackage(info.packageName);
            if (launch != null) {
                statusText.setText("Opening " + info.loadLabel(pm));
                startActivity(launch);
            }
        });
    }

    private void openNavigation() {
        try {
            Intent geo = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="));
            startActivity(geo);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps")));
        }
    }

    private void openMusic() {
        Intent media = new Intent(Intent.ACTION_MAIN);
        media.addCategory(Intent.CATEGORY_APP_MUSIC);
        try {
            startActivity(media);
        } catch (Exception e) {
            Intent chooser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://music.youtube.com"));
            startActivity(chooser);
        }
    }

    private void updateClock() {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat date = new SimpleDateFormat("EEEE, MMM d", Locale.getDefault());
        timeText.setText(time.format(new Date()));
        dateText.setText(date.format(new Date()) + "  •  DriveOS");
        timeText.postDelayed(this::updateClock, 30000);
    }

    private void enableImmersive() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    private class AppAdapter extends BaseAdapter {
        @Override public int getCount() { return apps.size(); }
        @Override public Object getItem(int position) { return apps.get(position); }
        @Override public long getItemId(int position) { return position; }

        @Override public View getView(int position, View convertView, android.view.ViewGroup parent) {
            LinearLayout box = new LinearLayout(MainActivity.this);
            box.setOrientation(LinearLayout.VERTICAL);
            box.setGravity(android.view.Gravity.CENTER);
            box.setPadding(8, 10, 8, 10);
            box.setBackgroundResource(R.drawable.bg_button);

            ImageView icon = new ImageView(MainActivity.this);
            icon.setImageDrawable(apps.get(position).loadIcon(pm));
            box.addView(icon, new LinearLayout.LayoutParams(56, 56));

            TextView name = new TextView(MainActivity.this);
            name.setText(apps.get(position).loadLabel(pm));
            name.setTextColor(Color.WHITE);
            name.setTextSize(13);
            name.setGravity(android.view.Gravity.CENTER);
            box.addView(name, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            return box;
        }
    }
}
