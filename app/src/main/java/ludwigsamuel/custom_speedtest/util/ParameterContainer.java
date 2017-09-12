package ludwigsamuel.custom_speedtest.util;

/**
 * Created by Ludwig Samuel on 12-Nov-16.
 */

public class ParameterContainer {
    private SpeedtestParameters speedtestParameters;
    private PingtestParameters pingtestParameters;

    public SpeedtestParameters getSpeedtestParameters() {
        return speedtestParameters;
    }

    public void setSpeedtestParameters(SpeedtestParameters speedtestParameters) {
        this.speedtestParameters = speedtestParameters;
    }

    public PingtestParameters getPingtestParameters() {
        return pingtestParameters;
    }

    public void setPingtestParameters(PingtestParameters pingtestParameters) {
        this.pingtestParameters = pingtestParameters;
    }
}
