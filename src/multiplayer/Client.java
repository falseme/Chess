package multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import event.SquareMouse;
import ui.Square;
import ui.Table;

/**
* Used to make a multiplayer (LAN) game between two players.
*
* @author Fabricio Tomás <a href="https://github.com/Fabricio-Tomas">github-profile</a>
*/
public class Client implements Runnable {

	/** The port that the client will use to send or receive data. */
	public static final int port = 9895;
	/** The second port that the other client will use to send or receive data. */
	public static final int port2 = Client.port + 1;
	private int sendPort, receivePort;
	private String other;

	/**True if it is this client turn to move or false if not*/
	public boolean turn = false;

	public boolean connected = false;

	private ServerWindow window = null; // a window where to display error, warning or information messages.

	/**
	* Creates a client and connects it to another by the given host. <br>
	* The client created with this constructor will have the black pieces. <br>
	* Uses the port1 to send data and the port2 to receive data. <br>
	* Also opens a ServerWindow to show information about the connection state.
	*
	* @param host The IPV4 connection of the other player.
	* @see ServerWindow
	*/
	public Client(String host) {

		connect(null,0);

		window = new ServerWindow(this);

		other = host;

		sendPort = Client.port;
		receivePort = Client.port2;

		window.writeLine(sendPort + " -send port");
		window.writeLine(receivePort + " -receive port");

		try {

			Socket socket = new Socket(host, sendPort);

			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

			output.writeObject(InetAddress.getLocalHost().getHostAddress());

			output.close();
			socket.close();

			receiveData();
			SquareMouse.switchDefaultTurn();

		} catch (IOException e) {

			e.printStackTrace();

			window.writeLine("----------");
			window.writeLine("ERROR");
			window.writeLine(e.toString());
			window.writeLine("----------");

		}

	}

	/**
	* Creates a client without a connection. It waits to another player to connect. <br>
	* The client created with this constructor will have the white pieces. <br>
	* Uses the port2 to send data and the port1 to receive data. <br>
	* Also opens a ServerWindow to shoe information about the connection state.
	*
	* @see ServerWindow
	*/
	public Client() {

		connect(null,0);

		turn = true;

		sendPort = Client.port2;
		receivePort = Client.port;

		window = new ServerWindow(this);

		window.writeLine(sendPort + " -send port");
		window.writeLine(receivePort + " -receive port");

		try {
			window.writeLine("Tu dirección IP: " + InetAddress.getLocalHost().getHostAddress());
			window.writeLine("Esperando jugador...");
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					ServerSocket server = new ServerSocket(receivePort);

					Socket socket = server.accept();

					ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

					String otherip = (String) input.readObject();

					other = otherip;

					input.close();
					socket.close();
					server.close();

					receiveData();

				} catch (Exception e) {

					e.printStackTrace();

					window.writeLine("----------");
					window.writeLine("ERROR");
					window.writeLine(e.toString());
					window.writeLine("----------");

				}

			}

		});

		thread.start();

	}

	/**
	* using the port1 or port2 creates a ServerSocket and receive data from the other player about the pieces positions. <br>
	* If it can't, shows an error message in the serverWindow.<br>
	* All of this is done in a new Thread using the {@link #run()} method from the Runnable interface.
	*
	* @see ServerWindow
	*/
	protected void receiveData() {

		window.writeLine("Conectado a: " + other);

		Thread receive = new Thread(this, "Receive table data");
		receive.start();

	}

	/**
	* Creates a new Thread and try to send data about the pieces positions to the other player using the port1 and port2. <br>
	* If it can't, shows and error message in the serverWindow.
	*
	* @see ServerWindow
	*/
	public void sendData() {

		Thread send = new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					Socket socket = new Socket(other, sendPort);

					ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

					output.writeObject(Table.getTable());
					turn = !turn;

					output.close();
					socket.close();

				} catch (IOException e) {

					e.printStackTrace();

					window.writeLine("----------");
					window.writeLine("ERROR");
					window.writeLine(e.toString());
					window.writeLine("----------");

				}

			}

		});

		send.start();

	}

	@Override
	public void run() {

		try {

			ServerSocket server = new ServerSocket(receivePort);

			boolean working = true;

			while (working) {

				Socket socket = server.accept();

				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

				Square[][] newtable = (Square[][]) input.readObject();
				Table.replaceTable(newtable);
				turn = !turn;

				window.writeLine("Movimiento del contario: " + Table.lastMove);

				input.close();
				socket.close();

			}

			server.close();

		} catch (Exception e) {

			e.printStackTrace();

			window.writeLine("----------");
			window.writeLine("ERROR");
			window.writeLine(e.toString());
			window.writeLine("----------");

		}

	}

	public void connect(String host, int port) {

		connected = true;

	}

	public void disconnect() {

		other = null;
		turn = false;
		window = null;

	}

}
