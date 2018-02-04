/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.background.sync;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class WaterReminderFirebaseJobService  extends JobService {
    // TODODONE (3) WaterReminderFirebaseJobService should extend from JobService

    private AsyncTask mBackgroundTask;

    // TODODONE (4) Override onStartJob
    // TODODONE (5) By default, jobs are executed on the main thread, so make an anonymous class extending
    //  AsyncTask called mBackgroundTask.

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mBackgroundTask = new AsyncTask() {

            // TODODONE (6) Override doInBackground
            // TODODONE (7) Use ReminderTasks to execute the new charging reminder task you made, use
            // this service as the context (WaterReminderFirebaseJobService.this) and return null
            // when finished.



            // TODODONE (8) Override onPostExecute and called jobFinished. Pass the job parameters
            // and false to jobFinished. This will inform the JobManager that your job is done
            // and that you do not want to reschedule the job.

            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = WaterReminderFirebaseJobService.this;
                ReminderTasks.executeTask(context, ReminderTasks.ACTION_CHARGING_REMINDER);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(jobParameters, false);
            }
        };
        // TODODONE (9) Execute the AsyncTask
        // TODODONE (10) Return true

        mBackgroundTask.execute();
        return true;
    }

    // TODODONE (11) Override onStopJob
    // TODODONE (12) If mBackgroundTask is valid, cancel it
    // TODODONE (13) Return true to signify the job should be retried

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if(mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }


}
