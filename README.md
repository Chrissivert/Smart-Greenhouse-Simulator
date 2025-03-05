# Smart-Greenhouse-Simulator
![image](https://github.com/user-attachments/assets/6b37a320-58ad-4d54-8610-84f90c9a6bf7)
![image](https://github.com/user-attachments/assets/04e2891d-4f03-41e0-887c-82e3d00d2c63)
![image](https://github.com/user-attachments/assets/cb01d6c1-e369-4353-84a9-42fd1930cb39)





Created Autumn 2023

Created by:

- [Robin Hammer](https://github.com/robi-ha)
- [Brage Solem](https://github.com/BrageSolem)
- Chris Sivert

Course project for the
course [IDATA2304 Computer communication and network programming (2023)](https://www.ntnu.edu/studies/courses/IDATA2304/2023).

Project theme: a distributed smart greenhouse application, consisting of:

* Sensor-actuator nodes
* Visualization nodes

See protocol description in [protocol.md](protocol.md).

## Getting started

There are several runnable classes in the project.

To run the greenhouse part (with sensor/actuator nodes):


* Command line version: run the `main` method inside `CommandLineGreenhouse` class.
* GUI version: run the `main` method inside `GreenhouseGuiStarter` class. Note - if you run the
  `GreenhouseApplication` class directly, JavaFX will complain that it can't find necessary modules.

To run the control panel (only GUI-version is available): run the `main` method inside the
`ControlPanelStarter` class, the same note about JavaFX applies. Control panel can not be run without
the greenhouse part,can run but will close itself. Works with both the command line and GUI version of the greenhouse.
Multiple control panels can be run at the same time.

## Simulating events

The greenhouse part can be run in simulation mode, where it will update the sensor values, 
the updates are based upon witch actuators are turned on.
