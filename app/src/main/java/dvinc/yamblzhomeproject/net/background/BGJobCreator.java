package dvinc.yamblzhomeproject.net.background;
/*
 * Created by DV on Space 5 
 * 15.07.2017
 */

import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class BGJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case BGSyncJob.TAG:
                Log.v("BACKGROUND JOB", "BG JOB IS START");
                return new BGSyncJob();
            default:
                return null;
        }
    }
}
