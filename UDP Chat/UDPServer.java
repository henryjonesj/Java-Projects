import java.io.*;
import java.net.*;
public class UDPServer extends Thread
{
	public static void main(String args[]) throws Exception
	{
		DatagramSocket ds=new DatagramSocket(6000);
		DatagramPacket dp1, dp2;
		byte[] b=new byte[1024];
		String S;
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		//synchronized{
		do
		{
			dp1=new DatagramPacket(b, b.length);
			ds.receive(dp1);

			b=dp1.getData();
			//try{	wait();	}
			//catch(InterruptedException e){}

			S=new String(b);
			System.out.println("\nMessage Received : "+S);
			
			System.out.println("\nEnter message : ");
			S=br.readLine();

			b=S.getBytes();
			dp2=new DatagramPacket(b, S.length(), InetAddress.getLocalHost(), 6000);

			ds.send(dp2);
			//notify();
			System.out.println("\nEnd");
		}while(!(S.equals("exit")));
		//}
	}
}