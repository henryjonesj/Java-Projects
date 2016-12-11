import java.io.*;
import java.net.*;
class tdnsc
{
	static byte buf[] = new byte[1024];
	static byte buf1[] = new byte[1024];
	public static void main(String[] a) throws IOException
	{
		DatagramSocket ds= new DatagramSocket(8000);
		DatagramPacket dp = new DatagramPacket(buf, buf.length);
		InetAddress i= InetAddress.getLocalHost();
		DatagramSocket ds1= new DatagramSocket(8001);
		DatagramPacket dp1= new DatagramPacket(buf1, buf1.length);
		InetAddress i1= InetAddress.getLocalHost();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean v=true;
		System.out.println("Client is Running.....");
		do
		{
			System.out.println("Enter the Domain Name(quit):");
			String str = new String(br.readLine());
			if(str.equals("quit"))
			{
				System.out.println("Client Exits...");
				buf = str.getBytes();
				ds.send(new DatagramPacket(buf,str.length(), i, 6000));
				ds1.send(new DatagramPacket(buf,str.length(), i, 6001));
				v=false;
			}
			else
			{
				buf = str.getBytes();
				ds.send(new DatagramPacket(buf,str.length(), i, 6000));
				ds.receive(dp);
				String str1 = new String(dp.getData(), 0,dp.getLength());
				if(str1.equals("x"))
				{
					System.out.println("IPAddress is not found in Server");
					System.out.println("Request sent to Server1");
					System.out.println("Waiting for reply "); 
					ds1.send(new DatagramPacket(buf,str.length(), i, 6001));
					ds1.receive(dp1);
					String ss = new String(dp1.getData(), 0,dp1.getLength());
					if(ss.equals("x"))
					{
						System.out.println("IPAddress not found");
					}
					else	
					{
						System.out.println("IPAddress from Server1: " +ss);
						v=true;
					}
				}
				else
				{
					System.out.println(" IPAddress from server:"+str1);
				}
			}	
		}while(v);
	}			
}