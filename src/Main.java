import device.ProjectorDevice;
import device.RadioDevice;
import device.TvDevice;
import remote.BasicRemote;
import remote.QuietRemote;
import remote.Remote;

public class Main {
    private static int passed = 0;
    private static int total = 0;

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--demo")) {
            runDemo();
        } else {
            System.out.println("Run with: java -cp out Main --demo");
        }
    }

    private static void runDemo() {
        testResult("T1", "BasicRemote + TvDevice",
                new BasicRemote("B1", new TvDevice()),
                "TV | power=ON | volume=30");

        testResult("T2", "BasicRemote + RadioDevice",
                new BasicRemote("B2", new RadioDevice()),
                "RADIO | power=ON | volume=30");

        testResult("T3", "QuietRemote + TvDevice",
                new QuietRemote("Q1", new TvDevice()),
                "TV | power=ON | volume=5");

        testResult("T4", "QuietRemote + RadioDevice",
                new QuietRemote("Q2", new RadioDevice()),
                "RADIO | power=ON | volume=5");

        testRuntimeSwitch();

        testResult("T6", "BasicRemote + ProjectorDevice",
                new BasicRemote("B3", new ProjectorDevice()),
                "PROJECTOR | power=ON | volume=30");

        testResult("T7", "QuietRemote + ProjectorDevice",
                new QuietRemote("Q3", new ProjectorDevice()),
                "PROJECTOR | power=ON | volume=5");

        System.out.println("SUMMARY: " + passed + "/" + total + " PASS");
    }

    private static void testResult(String id, String classes, Remote remote, String expected) {
        total++;
        String actual = remote.execute();
        boolean ok = actual.equals(expected);

        if (ok) {
            passed++;
            System.out.println(id + " PASS | " + classes + " | result=" + actual);
        } else {
            System.out.println(id + " FAIL | " + classes + " | result=" + actual
                    + " | expected=" + expected);
        }
    }

    private static void testRuntimeSwitch() {
        total++;

        BasicRemote remote = new BasicRemote("SWITCH-1", new TvDevice());
        BasicRemote originalReference = remote;
        String originalId = remote.getId();
        int originalVolume = remote.getVolumePreset();

        String before = remote.execute();
        remote.setImplementation(new RadioDevice());
        String after = remote.execute();

        boolean sameObject = originalReference == remote;
        boolean stateUnchanged = originalId.equals(remote.getId())
                && originalVolume == remote.getVolumePreset();
        boolean resultsCorrect = before.equals("TV | power=ON | volume=30")
                && after.equals("RADIO | power=ON | volume=30");
        boolean ok = sameObject && stateUnchanged && resultsCorrect;

        if (ok) {
            passed++;
            System.out.println("T5 PASS | BasicRemote runtime switch | sameObject=" + sameObject
                    + " | stateUnchanged=" + stateUnchanged);
        } else {
            System.out.println("T5 FAIL | BasicRemote runtime switch | sameObject=" + sameObject
                    + " | stateUnchanged=" + stateUnchanged
                    + " | expected before=TV volume 30 and after=RADIO volume 30");
        }
        System.out.println(" before=" + before + " | after=" + after);
    }
}
