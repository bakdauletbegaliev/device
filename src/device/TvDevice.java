package device;

public class TvDevice implements Device {
    @Override
    public String applySettings(int volume) {
        return "TV | power=ON | volume=" + volume;
    }
}
