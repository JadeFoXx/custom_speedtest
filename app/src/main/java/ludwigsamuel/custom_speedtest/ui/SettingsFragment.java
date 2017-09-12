package ludwigsamuel.custom_speedtest.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.TabActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;

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
                    Animator.collapse(speedtestContainer, 5);
                    Animator.collapse(pingtestContainer, 5);
                }
                break;
            case PROFILE_CUSTOM:
                if(!init) {
                    Animator.expand(speedtestContainer, 5);
                    Animator.expand(pingtestContainer, 5);
                }
                downloadUrlEditText.setText(SpeedtestParameters.DEFAULT_FILE_URL);
                durationEditText.setText(Integer.toString(SpeedtestParameters.DEFAULT_DURATION));
                bufferSizeEditText.setText(Integer.toString(SpeedtestParameters.DEFAULT_BUFFER_SIZE));
                pingUrlEditText.setText(PingtestParameters.DEFAULT_PING_URL);
                break;
        }
    }

    public ParameterContainer getSelectedParameters() {
        PingtestParameters pingtestParameters;
        SpeedtestParameters speedtestParameters;
        if(profileSpinner.getSelectedItem().toString().equals(PROFILE_DEFAULT)) {
            pingtestParameters = PingtestParameters.getDefaultPingtestParameters();
            speedtestParameters = SpeedtestParameters.getDefaultSpeedtestParameters();
        } else {
            pingtestParameters = new PingtestParameters();
            if(pingUrlEditText.getText() != null && !pingUrlEditText.getText().toString().equals("")) {
                pingtestParameters.setPingURL(pingUrlEditText.getText().toString());
            } else {
                pingtestParameters.setPingURL(PingtestParameters.DEFAULT_PING_URL);
            }
            pingtestParameters.setPingcount(PingtestParameters.DEFAULT_PING_COUNT);

            speedtestParameters = new SpeedtestParameters();
            switch (threadingSpinner.getSelectedItem().toString()) {
                case THREADING_DEFAULT:
                    speedtestParameters.setAdaptive(false);
                    speedtestParameters.setThreadcount(1);
                    break;
                case THREADING_ADAPTIVE:
                    speedtestParameters.setAdaptive(true);
                    speedtestParameters.setMinThreadcount(1);
                    speedtestParameters.setMaxThreacount(100);
                    break;
                case THREADING_MULTI:
                    speedtestParameters.setAdaptive(false);
                    speedtestParameters.setThreadcount(5);
                    break;
            }

            switch (averageSpinner.getSelectedItem().toString()) {
                case AVERAGE_DEFAULT:
                    speedtestParameters.setAverageType(SpeedtestParameters.AVERAGE_STANDARD);
                    break;
                case AVERAGE_SPEEDTEST_DOT_NET:
                    speedtestParameters.setAverageType(SpeedtestParameters.AVERAGE_SPEEDTEST_DOT_NET);
                    break;
            }

            if(downloadUrlEditText.getText() != null && !downloadUrlEditText.getText().toString().equals("")) {
                speedtestParameters.setFileURL(downloadUrlEditText.getText().toString());
            } else {
                speedtestParameters.setFileURL(SpeedtestParameters.DEFAULT_FILE_URL);
            }

            if(durationEditText.getText() != null && !durationEditText.getText().toString().equals("")) {
                speedtestParameters.setDuration(Integer.valueOf(durationEditText.getText().toString()));
            } else {
                speedtestParameters.setDuration(SpeedtestParameters.DEFAULT_DURATION);
            }

            if(bufferSizeEditText.getText() != null && !bufferSizeEditText.getText().toString().equals("")) {
                speedtestParameters.setBuffersize(Integer.valueOf(bufferSizeEditText.getText().toString()));
            } else {
                speedtestParameters.setBuffersize(SpeedtestParameters.DEFAULT_BUFFER_SIZE);
            }

            speedtestParameters.setPollInterval(SpeedtestParameters.DEFAULT_POLLRATE);
            speedtestParameters.setAdaptionInterval(SpeedtestParameters.DEFAULT_PROBEINTERVAL);
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
}
