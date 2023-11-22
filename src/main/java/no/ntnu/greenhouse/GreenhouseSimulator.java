package no.ntnu.greenhouse;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import no.ntnu.gui.greenhouse.AddNodeActionHandler;
import no.ntnu.listeners.greenhouse.NodeStateListener;
import no.ntnu.tools.Logger;

/**
 * Application entrypoint - a simulator for a greenhouse.
 */
public class GreenhouseSimulator {
    private static final int PORT_NUMBER = 1234;
    private final Map<Integer, SensorActuatorNode> nodes = new HashMap<>();

    private final List<PeriodicSwitch> periodicSwitches = new LinkedList<>();
    private final boolean fake;

    /**
     * Create a greenhouse simulator.
     *
     * @param fake When true, simulate a fake periodic events instead of creating
     *             socket communication
     */
    public GreenhouseSimulator(boolean fake) {
        this.fake = fake;
        new AddNodeActionHandler(this);
    }

    /**
     * Initialise the greenhouse but don't start the simulation just yet.
     */
    public void initialize() {
        createNode(1, 2, 1, 0, 0);
        createNode(1, 0, 0, 2, 1);
        createNode(2, 0, 0, 0, 0);
        Logger.info("Greenhouse initialized");
    }

    public void createNode(int temperature, int humidity, int windows, int fans, int heaters) {
        SensorActuatorNode node = DeviceFactory.createNode(
                temperature, humidity, windows, fans, heaters);
        nodes.put(node.getId(), node);
    }

    /**
     * Start a simulation of a greenhouse - all the sensor and actuator nodes inside it.
     */
    public void start() {
        initiateCommunication();
        for (SensorActuatorNode node : nodes.values()) {
            System.out.println("daokdwaldadmmwa" + nodes.get(2).getId());
            node.start();
        }
        for (PeriodicSwitch periodicSwitch : periodicSwitches) {
            periodicSwitch.start();
        }

        Logger.info("Simulator started");
    }

    private void initiateCommunication() {
        if (fake) {
            initiateFakePeriodicSwitches();
        } else {
            initiateRealCommunication();
        }
    }

    public void addNode(SensorActuatorNode newNode) {
        System.out.println("Added a new NODEEEE");
        System.out.println(newNode.getId());
        System.out.println(newNode.getSensors());
        System.out.println(nodes.values());
        System.out.println("WHY not all nodes closed?");
        nodes.put(newNode.getId(), newNode);
    }

    private ServerSocket initiateRealCommunication() {
            ServerSocket listeningSocket = null;
            try {
                listeningSocket = new ServerSocket(PORT_NUMBER);
            } catch (IOException e) {
                System.err.println("Could not open server socket: " + e.getMessage());
            }
            return listeningSocket;
        }

    private void initiateFakePeriodicSwitches() {
        periodicSwitches.add(new PeriodicSwitch("Window DJ", nodes.get(1), 2, 20000));
        periodicSwitches.add(new PeriodicSwitch("Heater DJ", nodes.get(2), 7, 8000));
    }

    /**
     * Stop the simulation of the greenhouse - all the nodes in it.
     */
    public void stop() {
        stopCommunication();
        for (SensorActuatorNode node : nodes.values()) {
            System.out.println("STOPPING NODE " + node.getId());
            node.stop();
        }
    }

    private void stopCommunication() {
        if (fake) {
            for (PeriodicSwitch periodicSwitch : periodicSwitches) {
                periodicSwitch.stop();
            }
        } else {
            // TODO - here you stop the TCP/UDP communication
        }
    }

    /**
     * Add a listener for notification of node staring and stopping.
     *
     * @param listener The listener which will receive notifications
     */
    public void subscribeToLifecycleUpdates(NodeStateListener listener) {
        for (SensorActuatorNode node : nodes.values()) {
            node.addStateListener(listener);
        }
    }
}
