import java.io.*;
import java.net.*;
class Client implements Runnable
{
	DatagramSocket ds;
	DatagramPacket dp1, dp2;
	byte[] b;
	String S;
	BufferedReader br;

	public Client()throws Exception
	{
		b=new byte[1024];
		ds=new DatagramSocket(6000);
		br=new BufferedReader(new InputStreamReader(System.in));
		new Thread(this, "Client").start();
	}

	public void run()
	{
	do
	{
		try{
		System.out.println("\nEnter message : ");
		S=br.readLine();
		b=S.getBytes();
		dp1=new DatagramPacket(b, S.length(), InetAddress.getLocalHost(), 6000);			
		ds.send(dp1);

		try{	wait();	}
		catch(InterruptedException e){}

		dp2=new DatagramPacket(b, b.length);
		ds.receive(dp1);
		b=dp2.getData();
		S=new String(b);
		System.out.println("\nMessage Received : "+S);
		notify();
		}
		catch(Exception e){}
	}while(!(S.equals("exit")));

	System.out.println("\nEnd");
	}
}


public class UDPClient1 extends Thread
{
	public static void main(String args[]) throws Exception
	{
		new Client();
	}
}