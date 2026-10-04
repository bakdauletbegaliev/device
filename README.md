# Assignment 3 - Bridge Pattern

**Student:** Bakdaulet Begaliyev    

## 1. Topic

This project demonstrates the **Bridge design pattern** using remote controls and devices.

There are two independent class hierarchies:

- Abstraction side: `Remote` -> `BasicRemote`, `QuietRemote`
- Implementation side: `Device` -> `TvDevice`, `RadioDevice`, `ProjectorDevice`

`BasicRemote` uses volume 30 and `QuietRemote` uses volume 5. A remote can change its device at runtime by calling `setImplementation(...)`.

## 2. Role map

| Bridge role | Class | Source path |
|---|---|---|
| Abstraction | `Remote` | `src/remote/Remote.java` |
| A1 | `BasicRemote` | `src/remote/BasicRemote.java` |
| A2 | `QuietRemote` | `src/remote/QuietRemote.java` |
| Implementor | `Device` | `src/device/Device.java` |
| I1 | `TvDevice` | `src/device/TvDevice.java` |
| I2 | `RadioDevice` | `src/device/RadioDevice.java` |
| I3 | `ProjectorDevice` | `src/device/ProjectorDevice.java` |
| Client | `Main` | `src/Main.java` |

Important locations:

- Bridge field: `Remote.implementation`
- Main operation: `Remote.execute()`
- Runtime replacement: `Remote.setImplementation(Device implementation)`
- Same-object proof: `Main.testRuntimeSwitch()`

## 3. Build and run

From the project root:

```bash
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out Main --demo
```

No IDE or external libraries are required.

## 4. Expected demo results

| Test | Combination/action | Expected result |
|---|---|---|
| T1 | BasicRemote + TvDevice | `TV | power=ON | volume=30` |
| T2 | BasicRemote + RadioDevice | `RADIO | power=ON | volume=30` |
| T3 | QuietRemote + TvDevice | `TV | power=ON | volume=5` |
| T4 | QuietRemote + RadioDevice | `RADIO | power=ON | volume=5` |
| T5 | Same BasicRemote switches TV -> Radio | same object is `true`, ID and volume stay unchanged |
| T6 | BasicRemote + ProjectorDevice | `PROJECTOR | power=ON | volume=30` |
| T7 | QuietRemote + ProjectorDevice | `PROJECTOR | power=ON | volume=5` |

Expected summary:

```text
SUMMARY: 7/7 PASS
```

## 5. How Bridge is used

`Remote` does not create or check concrete device classes. It stores a reference with the interface type `Device`. The `execute()` method delegates work to `implementation.applySettings(volumePreset)`.

Because of this, the remote hierarchy and device hierarchy can change independently. For example, `ProjectorDevice` was added without changing `Remote`, `BasicRemote`, `QuietRemote`, `Device`, `TvDevice`, or `RadioDevice`.

## 6. Extension step

The project was first completed with `TvDevice` and `RadioDevice`.

After that, `ProjectorDevice` was added and `Main` was updated with T6 and T7. The source difference is saved in `extension.diff`.

## 7. Bridge vs Adapter

Bridge is used here to separate two dimensions that are designed to vary independently: remote type and device type. Adapter has a different intent: it makes an existing incompatible interface usable by another class. This project is not adapting an old incompatible API; it is intentionally separating two class hierarchies.
