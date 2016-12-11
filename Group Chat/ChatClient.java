import java.net.*;
import java.io.*;

public class ChatClient implements Runnable
	{ 
	Socket socket = null;
	Thread thread = null;
	DataInputStream console = null;
	DataOutputStream streamOut = null;
	ChatClientThread client = null;
	

	public ChatClient(String serverName, int serverPort)
		{ 
		System.out.println("Establishing connection..");
		try
			{ 
			socket = new Socket(serverName, serverPort);
			System.out.println("Connected: " + socket);
			start();
			}
			catch(UnknownHostException uhe)
				{ System.out.println("Host unknown: " + uhe.getMessage()); }
			catch(IOException ioe)
				{ System.out.println("Unexpected exception: " + ioe.getMessage()); }
		}


	public void run()
		{ 
		while (thread != null)
			{ 
			try
				{ 
				streamOut.writeUTF(console.readLine());
				streamOut.flush();
				}
				catch(IOException ioe)
					{ 
					System.out.println("Sending error: " + ioe.getMessage());
					stop();
					}
			}
		}


	public void handle(String msg)
		{ 
		if (msg.equals(".quit"))
			{ 
			System.out.println("DONE ...");
			stop();
			}
		else
			System.out.println(msg);
		}
	

	public void start() throws IOException
		{ 
		console = new DataInputStream(System.in);
		streamOut = new DataOutputStream(socket.getOutputStream());
		if (thread == null)
			{ 
			client = new ChatClientThread(this, socket);
			thread = new Thread(this);
			thread.start();
			}
		}


	public void stop()
		{ 
		if (thread != null)
			{ 
			thread.stop();
			thread = null;
			}
		try
			{ 
			if (console != null) console.close();
			if (streamOut != null) streamOut.close();
			if (socket != null) socket.close();
			}
			catch(IOException ioe)
				{ System.out.println("Error closing ..."); }
		

		client.close();
		client.stop();
		}


	public static void main(String args[])
		{ 
		ChatClient client = null;

		if (args.length != 2)
			System.out.println("Enter IPAddr(localhost) and portno of server");
		else
			client = new ChatClient(args[0], Integer.parseInt(args[1]));
		}
	}



class ChatClientThread extends Thread
	{ 
	Socket socket = null;
	ChatClient client = null;
	DataInputStream streamIn = null;
	ChatClientThread(ChatClient _client, Socket _socket)
		{ 
		client = _client;
		socket = _socket;
		open();
		start();
		}


	public void open()
		{ 
		try
			{ 
			streamIn = new DataInputStream(socket.getInputStream());
			}
			catch(IOException ioe)
				{ 
				System.out.println("Error getting input stream: " + ioe);
				client.stop();
				}
		}


	public void close()
		{ 
		try
			{ 
			if (streamIn != null) streamIn.close();
			}
			catch(IOException ioe)
				{ System.out.println("Error closing input stream: " + ioe);}
		}


	public void run()
		{ 
		while (true)
			{ 
			try
				{ 
				client.handle(streamIn.readUTF());
				}
				catch(IOException ioe)
					{ 
					System.out.println("Listening error: " + ioe.getMessage());
					client.stop();
					}
			}
		}

	}