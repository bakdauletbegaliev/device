package device;

public class ProjectorDevice implements Device {
    @Override
    public String applySettings(int volume) {
        return "PROJECTOR | power=ON | volume=" + volume;
    }
}
