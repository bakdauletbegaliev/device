package device;

public class RadioDevice implements Device {
    @Override
    public String applySettings(int volume) {
        return "RADIO | power=ON | volume=" + volume;
    }
}
