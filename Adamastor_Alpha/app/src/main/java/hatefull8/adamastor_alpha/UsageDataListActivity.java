package hatefull8.adamastor_alpha;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.usage.UsageStatsManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import static android.app.PendingIntent.getActivity;

/**
 * Created by jlsilva94 on 08/11/17.
 */

public class UsageDataListActivity extends Activity {

    UsageStatsManager usageStatsManager;
    Map<String,UsageStats> usageMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usageStatsManager = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
        setContentView(R.layout.activity_usage_data);
        getUsageData();
        setStats();

    }

    public void getUsageData() {
        Calendar beginCal = Calendar.getInstance();
        beginCal.set(Calendar.MONTH, -1);
        long beginTime = beginCal.getTimeInMillis();

        Calendar endCal = Calendar.getInstance();
        long endTime = endCal.getTimeInMillis();

         UsageStatsManager usageStatsManager = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);

        usageMap = usageStatsManager.queryAndAggregateUsageStats(beginTime, endTime);

        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }

    public void setStats() {
        TextView tv =  ((TextView) findViewById(R.id.stats));
        for (UsageStats us: usageMap.values()) {
            tv.setText(tv.getText()+"\n"+us.getPackageName()+" -> "+us.);
        }
    }
}
