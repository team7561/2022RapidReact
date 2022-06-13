[Table of Contents](#table-of-contents)
- [RSL](#rsl)
- [RoboRIO](#roborio)
- [OpenMesh Radio](#openmesh-radio)
- [SparkMax](#sparkmax)
- [PDP](#pdp)
- [VRM](#vrm)
- [PCM](#pcm)
  - [Status LED](#status-led)
- [Raspberry-Pi](#raspberry-pi)
- [Falcon-500](#falcon-500)
- [Talon-SRX](#talon-srx)
- [Victor SPX](#victor-spx)

# RSL
| LED  | Meaning |
|--------|------|
| On Solid    | Robot disabled   |
| Blinking | Robot Enabled |
| Off     | Robot Off |
# RoboRIO

| LED  | Meaning |
|--------|------|
| Power  | <span style="color:green">Green</span> - Power is good<br><span style="color:orange">Amber</span> - Brownout protection tripped, outputs disabled<br><span style="color:red">Red</span> - Power fault, check user rails for short circuit |
| Status | On while the controller is booting, then should turn off<br>2 blinks - Software error, reimage roboRIO<br>3 blinks - Safe Mode, restart roboRIO, reimage if not resolved<br>4 blinks - Software crashed twice without rebooting, reboot roboRIO, reimage if not resolved<br>Constant flash or stays solid on - Unrecoverable error |
| Radio  | Not used |
| Comm   | Off - No Communication<br><span style="color:red">Red Solid</span> - Communication with DS, but no user code running<br><span style="color:red">Red Blinking</span> - E-stop triggered<br><span style="color:green">Green Solid</span> - Good communications with DS |
| Mode   | Off - Disabled<br><span style="color:green">Green</span> - Teleop Enabled<br><span style="color:orange">Amber</span> - Autonomous Enabled<br><span style="color:red">Red</span> - Test Enabled |
| RSL    | Same as RSL |
# OpenMesh Radio
| LED  | Meaning |
|--------|------|
| Power    | Blue - On or Powering up<br>Blue Blinking - Powering Up                                                 |
| Eth Link | Blue - Link Up<br>Blue Blinking - Traffic Present                                                       |
| Wifi     | Off - Bridge mode, Unlinked<br>Red - AP, Unlinked<br>Yellow - AP, Linked<br>Green - Bridge mode, Linked |
# SparkMax

dfgdfgdf
# PDP
| LED  | Meaning |
|--------|------|
| Status  | <span style="color:green">Green Strobe</span> - Power is good - Robot Enabled<br><span style="color:green">Green Blinking</span> - Power is good - Robot Disabled<br><span style="color:orange">Amber</span> - Sticky Fault<br><span style="color:red">Red</span> - No CAN Comms|
# VRM
LED's lit bright green when functioning properly.
# PCM
## Status LED
| Colour  | Meaning |
|--------|------|
| <span style="color:green">Green</span>    | Strobe - No Fault Robot Enabled<br>Slow - Sticky Fault    |
| <span style="color:orange">Orange</span> | Slow Blink - Sticky Fault |
| <span style="color:red">Red</span>     | Slow Bink - No CAN Comms or Solenoid Fault (blinks Solenoid index<br>Long blink - Compressor Fault|
# Raspberry-Pi
# Falcon-500 
# Talon-SRX
# Victor SPX



https://docs.wpilib.org/en/2020/docs/hardware/hardware-basics/status-lights-ref.html