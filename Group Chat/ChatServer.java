import java.net.*;
import java.io.*;

public class ChatServer implements Runnable
{ 
	ChatServerThread clients[] = new ChatServerThread[50];
	ServerSocket server = null;
	Thread thread = null;
	int clientCount = 0;

	public ChatServer(int port)
		{ 
		try
			{ 
			System.out.println("Binding to port " + port + ", please wait ...");
			server = new ServerSocket(port);
			System.out.println("Server started: " + server);
			start(); 
			}
			catch(Exception e)
				{ System.out.println("Can not bind to port " + port + ": " + e.getMessage()); }
		}
	

	public void start() 
		{ 
		if (thread == null)
			{ 
			thread = new Thread(this);
			thread.start();
			}
		}


	public void run()
		{ 
		while (thread != null)
			{ 
			try
				{ 
				System.out.println("Waiting for a client ...");
				addThread(server.accept()); 
				}
				catch(Exception e)
					{ System.out.println("Server accept error: " + e); stop(); }
			}
		}
	



	public void stop() 
		{ 
		if (thread != null)
			{ 
			thread.stop();
			thread = null;
			}
		}


	private int findClient(int ID)
		{ 
		for (int i = 0; i < clientCount; i++)
			{
			if (clients[i].getID() == ID)
				return i;
			}
		return -1;
		}


	public synchronized void handle(int ID, String input)
		{ 
		if (input.equals(".quit"))
			{ 
			clients[findClient(ID)].send(".quit");
			remove(ID); 
			}
		else
			{
			for (int i = 0; i < clientCount; i++)
				clients[i].send(ID + ": " + input);
			}
		}

	public synchronized void remove(int ID)
		{ 
		int pos = findClient(ID);
	
		if (pos >= 0)
			{ 
			ChatServerThread toTerminate = clients[pos];
			System.out.println("Removing client thread " + ID + " at " + pos);
			if (pos < clientCount-1)
				{
				for (int i = pos+1; i < clientCount; i++)
					clients[i-1] = clients[i];
				}
			clientCount--;

			try
				{ 	
				toTerminate.close(); 	
				}

			catch(IOException ioe)
				{ 	
				System.out.println("Error closing thread: " + ioe); 
				}
	
			toTerminate.stop(); 
			}
		}

	private void addThread(Socket socket)
		{ 	
		if (clientCount < clients.length)
			{ 
			System.out.println("Client accepted: " + socket);
			clients[clientCount] = new ChatServerThread(this, socket);
			try
				{ 
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++; 
				}
				catch(Exception e)
					{ System.out.println("opening error: " + e); } 
			}
		else
			System.out.println("Client refused: maximum " + clients.length + " reached.");
		}


	public static void main(String args[]) 
		{ 
		ChatServer server = null;
		if (args.length != 1)
			System.out.println(" java ChatServer port to be defined..");
		else
			server = new ChatServer(Integer.parseInt(args[0]));
		}

}


class ChatServerThread extends Thread
	{ 
	ChatServer server = null;
	Socket socket = null;
	int ID = -1;

	DataInputStream streamIn = null;
	DataOutputStream streamOut = null;
	

	public ChatServerThread(ChatServer server1, Socket socket1)
		{ 
		super();
		server = server1;
		socket = socket1;
		ID = socket.getPort();
		}

	public void send(String msg)
		{ 
		try
			{ 
			streamOut.writeUTF(msg);
			streamOut.flush();
			}
		catch(IOException ioe)
			{ 
			System.out.println(ID + " ERROR sending: " + ioe.getMessage());
			server.remove(ID);
			stop();
			}
		}


	public int getID()
		{ 	
		return ID;
		}
	

	public void run()
		{ 
		System.out.println("Server Thread " + ID + " running.");
		while (true)
			{ 
			try
				{ 
				server.handle(ID, streamIn.readUTF());
				}
				catch(IOException ioe)
					{ 
					System.out.println(ID + " ERROR reading: " + ioe.getMessage());
					server.remove(ID);
					stop();
					}
			}
		}

	public void open() throws IOException
		{ 
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		}

	public void close() throws IOException
		{ 
		if (socket != null) socket.close();
		if (streamIn != null) streamIn.close();
		if (streamOut != null) streamOut.close();
		}
	}
