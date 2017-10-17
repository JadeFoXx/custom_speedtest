package ludwigsamuel.custom_speedtest.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ludwigsamuel.custom_speedtest.R;
import ludwigsamuel.custom_speedtest.util.Animator;
import ludwigsamuel.custom_speedtest.util.ParameterContainer;
import ludwigsamuel.custom_speedtest.util.Test;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class TestFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    public enum State {
        IDLE, TESTING_PING, TESTING_DOWNLOAD
    }

    private String title;
    private int page;
    private Activity parentActivity;
    private CardView statusCardView;
    private TextView statusLabel;

    private CardView graphCardViewContainer;
    private CardView barGraphCardViewContainer;
    private CardView statsCardViewContainer;

    private ProgressBar timeProgressBar;
    private ArcProgressBar arcProgressBarPing;
    private ArcProgressBar arcProgressBarSpeed;
    private TextView maximumDisplayTextView;
    private MaximumValueDisplay maximumValueDisplay;
    private BarGraph barGraph;
    private LastValueDisplay lastValueDisplayThread;
    private TextView threadCountDisplay;
    private LastValueDisplay lastValueDisplayWifi;
    private TextView wifiStrengthDisplay;
    private State state;


    public static TestFragment newInstance(int page, String title) {
        TestFragment testFragment = new TestFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        testFragment.setArguments(args);
        return testFragment;
    }

    public void setParentActivity(Activity activity) {
        this.parentActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = State.IDLE;
        page = getArguments().getInt("page");
        title = getArguments().getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, container, false);
        statusCardView = (CardView) view.findViewById(R.id.test_fragment_status_cardview);
        statusLabel = (TextView) view.findViewById(R.id.test_fragment_status_label);
        timeProgressBar = (ProgressBar) view.findViewById(R.id.main_progressbar_time);

        graphCardViewContainer = (CardView)view.findViewById(R.id.test_fragment_arcgraph_cardview);
        barGraphCardViewContainer = (CardView)view.findViewById(R.id.test_fragment_bargraph_cardview);
        statsCardViewContainer = (CardView)view.findViewById(R.id.test_fragment_stats_cardview);

        arcProgressBarPing = (ArcProgressBar) view.findViewById(R.id.test_fragment_arcprogessbar_ping);
        arcProgressBarSpeed = (ArcProgressBar) view.findViewById(R.id.test_fragment_arcprogessbar_speed);
        arcProgressBarSpeed.setMultiplier(0.001);
        arcProgressBarSpeed.setDisplayProgressDouble(true);
        maximumDisplayTextView = (TextView) view.findViewById(R.id.test_fragment_label_max);
        maximumValueDisplay = new MaximumValueDisplay(parentActivity, maximumDisplayTextView);
        maximumValueDisplay.setMultiplier(0.001);
        barGraph = (BarGraph)view.findViewById(R.id.test_fragment_bargraph);
        threadCountDisplay = (TextView)view.findViewById(R.id.test_fragment_stats_thread_value);
        lastValueDisplayThread = new LastValueDisplay(parentActivity, threadCountDisplay);
        wifiStrengthDisplay = (TextView)view.findViewById(R.id.test_fragment_stats_wifi_value);
        lastValueDisplayWifi = new LastValueDisplay(parentActivity, wifiStrengthDisplay);
        return view;
    }


    @Override
    public void onClick(View v) {

    }

    public void start(ParameterContainer parameterContainer) {
        arcProgressBarPing.reset();
        arcProgressBarSpeed.reset();
        maximumValueDisplay.reset();
        barGraph.reset();
        lastValueDisplayThread.reset();
        lastValueDisplayWifi.reset();
        parameterContainer.getPingtestParameters().getSampleContainer().registerDataContainer(arcProgressBarPing);
        parameterContainer.getSpeedtestParameters().getBandwidthSampleContainer().registerDataContainer(maximumValueDisplay);
        parameterContainer.getSpeedtestParameters().getBandwidthSampleContainer().registerDataContainer(barGraph);
        parameterContainer.getSpeedtestParameters().getBandwidthSampleContainer().registerDataContainer(arcProgressBarSpeed);
        parameterContainer.getSpeedtestParameters().getThreadSampleContainer().registerDataContainer(lastValueDisplayThread);
        parameterContainer.getSpeedtestParameters().getWifiSampleContainer().registerDataContainer(lastValueDisplayWifi);
        new AsyncTest().execute(parameterContainer);
    }

    private class AsyncTest extends AsyncTask<ParameterContainer, ParameterContainer, Void> {

        @Override
        protected void onPreExecute() {
            Log.d("AsyncTest", "Started");
            Animator.expand(statusCardView, 10);
            Animator.expand(statsCardViewContainer, 10);
        }

        @Override
        protected Void doInBackground(ParameterContainer... params) {
            Test test = new Test();
            ParameterContainer parameterContainer = params[0];
            if (parameterContainer.getPingtestParameters() != null) {
                state = State.TESTING_PING;
                publishProgress(parameterContainer);
                test.ping(parameterContainer.getPingtestParameters());
            }
            if (parameterContainer.getSpeedtestParameters() != null) {
                state = State.TESTING_DOWNLOAD;
                publishProgress(parameterContainer);
                test.speed(parameterContainer.getSpeedtestParameters());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(final ParameterContainer... values) {
            super.onProgressUpdate(values);
            switch (state) {
                case TESTING_PING:
                    statusLabel.setText("Testing Ping...");
                    timeProgressBar.setIndeterminate(true);
                    break;
                case TESTING_DOWNLOAD:
                    Animator.expand(barGraphCardViewContainer, 10);
                    statusLabel.setText("Testing Download...");
                    timeProgressBar.setIndeterminate(true);
                    break;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("AsyncTest", "Completed");
            state = State.IDLE;
            statusLabel.setText("Completed");
            timeProgressBar.setIndeterminate(false);
            Animator.collapse(statusCardView, 10);
        }
    }

}
