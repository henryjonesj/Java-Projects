import java.io.*;
import java.net.*;
class Server implements Runnable
{
	DatagramSocket ds;
	DatagramPacket dp1, dp2;
	byte[] b;
	String S;
	BufferedReader br;

	public Server()throws Exception
	{
		b=new byte[1024];
		br=new BufferedReader(new InputStreamReader(System.in));
		ds=new DatagramSocket(6000);
		new Thread(this, "Server").start();
	}
	

	public void run()
	{
		do
		{
			try{
			dp1=new DatagramPacket(b, b.length);
			ds.receive(dp1);
			b=dp1.getData();
			S=new String(b);
			System.out.println("\nMessage Received : "+S);
			notify();
			S=br.readLine();
			System.out.println("\nEnter message : ");
			b=S.getBytes();
			dp2=new DatagramPacket(b, S.length(), InetAddress.getLocalHost(), 6000);
			ds.send(dp2);
			try{	wait();	}
			catch(InterruptedException e){}
			System.out.println("\nEnd");
			}
			catch(Exception e){}
		}while(!(S.equals("exit")));
	}
}
public class UDPServer1 extends Thread
{
	public static void main(String args[]) throws Exception
	{
		new Server();
	}
}