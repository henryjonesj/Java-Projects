import java.io.*;
import java.net.*;
class udpc
{
	static byte buf[] = new byte[1024];
	public static void main(String[] a) throws IOException
	{
		DatagramSocket ds= new DatagramSocket(8000);
		DatagramPacket dp = new DatagramPacket(buf, buf.length);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		InetAddress i= InetAddress.getLocalHost();
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
					System.out.println("IPAddress is not found");
				}
				else	
				{
					System.out.println("IPAddress from server: " +str1);
					v=true;
				}
			}	
		}while(v);
	}			
}