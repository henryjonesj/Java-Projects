import java.net.*;
import java.io.*;

public class DNSServer1
	{
	static byte buf[] = new byte[1024];
	

	public static void main(String args[])throws IOException
		{
		DatagramSocket ds= new DatagramSocket(8001);
		DatagramPacket dp = new DatagramPacket(buf,buf.length);
		InetAddress i=InetAddress.getLocalHost();
		
		boolean v=true;
		String [] dname=new String[10];
		String [] ipaddr=new String[10];
	
		for(int ij=0;ij<10;ij++)
			{
			dname[ij]="www.host1"+ij+".com";		// www.host10.com  120.90.0.0
			ipaddr[ij]="120.90.0."+ij;
			}

		System.out.println("Server Running...");
	
		do
			{
			ds.receive(dp);
			String str=new String(dp.getData(), 0,dp.getLength());

			if(str.equals("quit"))
				{
				System.out.println("Server1 Exits...");
				v=false;
				}

			else
				{
				int j=0,k=0;
				System.out.println("Domain name from Server:"+str);
				for(j=0;j<10;j++)
					{
					if(str.equals(dname[j]))
						{	
						System.out.println("IPaddress is found:"+ipaddr[j]);
						buf=ipaddr[j].getBytes();
						ds.send(new DatagramPacket(buf,ipaddr[j].length(), i, 6001));
						v=true;
						}
					else
						{	
						k++;
						}
					}

				if(k==10)
					{
					System.out.println("Domain not found");
					String p=new String("NOT FOUND");
					buf=p.getBytes();
					ds.send(new DatagramPacket(buf,p.length(), i, 6001));
					}
				}
			}while(v);
				
		}
	}
			