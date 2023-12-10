package no.ntnu.run;

import no.ntnu.controlpanel.CommunicationChannel;
import no.ntnu.controlpanel.ControlPanelLogic;
import no.ntnu.controlpanel.ControlPanelSocket;
import no.ntnu.gui.controlpanel.ControlPanelApplication;
import no.ntnu.tools.Logger;

/**
 * Starter class for the control panel.
 * Note: we could launch the Application class directly, but then we would have issues with the
 * debugger (JavaFX modules not found)
 */
public class ControlPanelStarter {

    public static final String SERVER_HOST = "localhost";

    private final boolean fake;
    private ControlPanelSocket socket;

    private ControlPanelLogic logic;

    public ControlPanelStarter(boolean fake) {
        this.fake = fake;
    }

    /**
     * Entrypoint for the application.
     *
     * @param args Command line arguments, only the first one of them used: when it is "fake",
     *             emulate fake events, when it is either something else or not present,
     *             use real socket communication.
     */
    public static void main(String[] args) {
        boolean fake = false;
        if (args.length == 1 && "fake".equals(args[0])) {
            fake = true;
            Logger.info("Using FAKE events");
        }
        ControlPanelStarter starter = new ControlPanelStarter(fake);
        starter.start();
    }

    /**
     * Starts the controlPanelApplication.
     */

    private void start() {
        this.logic = new ControlPanelLogic();
        CommunicationChannel channel = initiateCommunication(logic, fake);
        ControlPanelApplication.startApp(logic, channel);
        stopCommunication();
    }

    /**
     * Initiates the communication between the controlPanel and the server.
     * @param logic The logic of the controlPanel.
     * @param fake If the communication should be fake or not.
     * @return The communicationChannel.
     */

    private CommunicationChannel initiateCommunication(ControlPanelLogic logic, boolean fake) {
        CommunicationChannel channel;
        if (!fake) {
            channel = initiateSocketCommunication(logic);
        } else {
            channel = getCommunicationChannel();
            System.out.println("Fake communication not supported");
        }
        return channel;
    }

    /**
     * Gets the communicationChannel.
     * @return The communicationChannel.
     */

    private CommunicationChannel getCommunicationChannel() {
        return this.logic.getCommunicationChannel();
    }

    /**
     * Initiates the socket communication of a connected controlPanel.
     * @param logic The logic of the controlPanel.
     * @return The communicationChannel.
     */


    /**
     * Initiates the socket communication of a connected controlPanel.
     * @param logic The logic of the controlPanel.
     * @return The communicationChannel.
     */
    private CommunicationChannel initiateSocketCommunication(ControlPanelLogic logic) {
        socket = new ControlPanelSocket(logic);
        logic.setCommunicationChannel(socket);
        return socket;
    }

    /**
     * Stops the communication of a connected controlPanel.
     */
    private void stopCommunication(){
        socket.close();
    }
}