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

public class Client implements Runnable {

	public static final int port = 9895;
	public static final int port2 = Client.port + 1;
	private int sendPort, receivePort;
	private String other;

	public boolean turn = false;

	ServerWindow window;

	public Client(String host) {

		window = new ServerWindow();
		window.setVisible(true);

		other = host;

		sendPort = Client.port;
		receivePort = Client.port2;

		window.writeLine(sendPort + " -send");
		window.writeLine(receivePort + " -receive");

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

		}

	}

	public Client() {

		turn = true;

		sendPort = Client.port2;
		receivePort = Client.port;

		window = new ServerWindow();
		window.setVisible(true);

		window.writeLine(sendPort + " -send");
		window.writeLine(receivePort + " -receive");

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

				} catch (IOException e) {

					e.printStackTrace();

				} catch (ClassNotFoundException e) {

					e.printStackTrace();

				}

			}

		});

		thread.start();

	}

	private void receiveData() {

		window.writeLine("Conectado a: " + other);

		Thread receive = new Thread(this, "Receive table data");
		receive.start();

	}

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
					window.writeLine(e.getMessage());
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

		} catch (IOException e) {

			e.printStackTrace();

			window.writeLine("----------");
			window.writeLine("ERROR");
			window.writeLine(e.getMessage());
			window.writeLine("----------");

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

			window.writeLine("----------");
			window.writeLine("ERROR");
			window.writeLine(e.getMessage());
			window.writeLine("----------");

		}

	}

}
