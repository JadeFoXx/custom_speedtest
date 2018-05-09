package ludwigsamuel.custom_speedtest.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import ludwigsamuel.custom_speedtest.R;
import ludwigsamuel.custom_speedtest.util.Animator;
import ludwigsamuel.custom_speedtest.util.ParameterContainer;
import ludwigsamuel.custom_speedtest.util.PingtestParameters;
import ludwigsamuel.custom_speedtest.util.SpeedtestParameters;

/**
 * Created by Ludwig Samuel on 11-Nov-16.
 */

public class SettingsFragment extends android.support.v4.app.Fragment implements View.OnClickListener, Spinner.OnItemSelectedListener{

    private final String PROFILE_DEFAULT = "Default";
    private final String PROFILE_CUSTOM = "Custom";
    private final String THREADING_DEFAULT = "Singlethread";
    private final String THREADING_MULTI = "Multithread";
    private final String THREADING_ADAPTIVE = "Adaptivethread";
    private final String AVERAGE_DEFAULT = "Standard";
    private final String AVERAGE_SPEEDTEST_DOT_NET = "Speedtest.net";

    private String title;
    private int page;
    private Activity parentActivity;
    private Spinner profileSpinner;
    private Spinner threadingSpinner;
    private Spinner averageSpinner;
    private EditText pingUrlEditText;
    private EditText downloadUrlEditText;
    private EditText durationEditText;
    private EditText bufferSizeEditText;

    private CardView profileContainer;
    private CardView speedtestContainer;
    private CardView pingtestContainer;



    public static SettingsFragment newInstance(int page, String title) {
        SettingsFragment settingsFragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        settingsFragment.setArguments(args);
        return settingsFragment;
    }

    public void setParentActivity(Activity activity) {
        this.parentActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("page");
        title = getArguments().getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        profileContainer = (CardView)view.findViewById(R.id.settings_fragment_profile_container);
        speedtestContainer = (CardView)view.findViewById(R.id.settings_fragment_speedtest_container);
        pingtestContainer = (CardView)view.findViewById(R.id.settings_fragment_pingtest_container);
        profileSpinner = (Spinner)view.findViewById(R.id.settings_fragment_spinner_profile);
        profileSpinner.setOnItemSelectedListener(this);
        threadingSpinner = (Spinner)view.findViewById(R.id.settings_fragment_spinner_threading_mode);
        averageSpinner = (Spinner)view.findViewById(R.id.settings_fragment_spinner_average);
        pingUrlEditText = (EditText)view.findViewById(R.id.settings_fragment_input_ping_url);
        downloadUrlEditText = (EditText)view.findViewById(R.id.settings_fragment_input_download_url);
        durationEditText = (EditText)view.findViewById(R.id.settings_fragment_input_download_duration);
        bufferSizeEditText = (EditText)view.findViewById(R.id.settings_fragment_input_buffersize);
        return view;
    }

    private void setProfile(String profile, boolean init) {
        switch (profile) {
            case PROFILE_DEFAULT:
                if(!init) {
                    Animator.collapse(speedtestContainer, 2);
                    Animator.collapse(pingtestContainer, 2);
                }
                break;
            case PROFILE_CUSTOM:
                downloadUrlEditText.setText(SpeedtestParameters.DEFAULT_FILE_URL);
                durationEditText.setText(Integer.toString(SpeedtestParameters.DEFAULT_DURATION));
                bufferSizeEditText.setText(Integer.toString(SpeedtestParameters.DEFAULT_BUFFER_SIZE));
                pingUrlEditText.setText(PingtestParameters.DEFAULT_PING_URL);
                if(!init) {
                    Animator.expand(speedtestContainer, 2);
                    Animator.expand(pingtestContainer, 2);
                }
                break;
        }
    }

    public ParameterContainer getSelectedParameters() {
        PingtestParameters pingtestParameters;
        SpeedtestParameters speedtestParameters;
        if(profileSpinner.getSelectedItem().toString().equals(PROFILE_DEFAULT)) {
            pingtestParameters = new PingtestParameters();
            speedtestParameters = new SpeedtestParameters();
        } else {
            pingtestParameters = new PingtestParameters();
            if(!isNullOrEmpty(pingUrlEditText)) {
                pingtestParameters.setPingURL(pingUrlEditText.getText().toString());
            }
            speedtestParameters = new SpeedtestParameters();
            switch (threadingSpinner.getSelectedItem().toString()) {
                case THREADING_DEFAULT:
                    speedtestParameters.setThreadingMode(SpeedtestParameters.ThreadingMode.SINGLE);
                    speedtestParameters.setThreadCount(SpeedtestParameters.DEFAULT_SINGLE_THREAD_COUNT);
                    break;
                case THREADING_ADAPTIVE:
                    speedtestParameters.setThreadingMode(SpeedtestParameters.ThreadingMode.ADAPTIVE);
                    speedtestParameters.setMinThreadCount(SpeedtestParameters.DEFAULT_MIN_THREAD_COUNT);
                    speedtestParameters.setMaxThreadCount(SpeedtestParameters.DEFAULT_MAX_THREAD_COUNT);
                    break;
                case THREADING_MULTI:
                    speedtestParameters.setThreadingMode(SpeedtestParameters.ThreadingMode.MULTI);
                    speedtestParameters.setThreadCount(SpeedtestParameters.DEFAULT_MULTI_THREAD_COUNT);
                    break;
            }

            switch (averageSpinner.getSelectedItem().toString()) {
                case AVERAGE_DEFAULT:
                    speedtestParameters.setAverageMode(SpeedtestParameters.AverageMode.STANDARD);
                    break;
                case AVERAGE_SPEEDTEST_DOT_NET:
                    speedtestParameters.setAverageMode(SpeedtestParameters.AverageMode.SPEEDTEST_DOT_NET);
                    break;
            }

            if(!isNullOrEmpty(downloadUrlEditText)) {
                speedtestParameters.setFileURL(downloadUrlEditText.getText().toString());
            }

            if(!isNullOrEmpty(durationEditText)) {
                speedtestParameters.setDuration(Integer.valueOf(durationEditText.getText().toString()));
            }

            if(!isNullOrEmpty(bufferSizeEditText)) {
                speedtestParameters.setBufferSize(Integer.valueOf(bufferSizeEditText.getText().toString()));
            }
        }
        ParameterContainer parameterContainer = new ParameterContainer();
        parameterContainer.setPingtestParameters(pingtestParameters);
        parameterContainer.setSpeedtestParameters(speedtestParameters);
        return parameterContainer;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(profileSpinner.getSelectedItem().toString().equals(PROFILE_DEFAULT)) {
            setProfile(PROFILE_DEFAULT, false);
        } else {
            setProfile(PROFILE_CUSTOM, false);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean isNullOrEmpty(EditText input) {
        return input.getText() == null || input.getText().toString().equals("");
    }
}
