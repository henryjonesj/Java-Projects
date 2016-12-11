import java.io.*;
import java.net.*;
class DNSServer
{
	static byte buf[] = new byte[1024];
	static byte buf1[] = new byte[1024];	

	public static void main(String[] a) throws IOException
		{
		DatagramSocket ds= new DatagramSocket(8000);
		DatagramPacket dp = new DatagramPacket(buf,buf.length);
	
		InetAddress i=InetAddress.getLocalHost();

		DatagramSocket ds1= new DatagramSocket(6001);
		DatagramPacket dp1 = new DatagramPacket(buf1,buf1.length);
	
		InetAddress i1=InetAddress.getLocalHost();
	
		boolean v=true;
	
		String [] dname=new String[10];
		String [] ipaddr=new String[10];

		for(int temp=0;temp<10;temp++)				// www.host0.com 110.90.0.0
			{
			dname[temp]="www.host"+temp+".com";
			ipaddr[temp]="110.90.0."+temp;
			}

		System.out.println("Server Running.....");
		do
			{
			ds.receive(dp);
			String str = new String(dp.getData(), 0,dp.getLength());

			if(str.equals("quit"))
				{
				System.out.println("Server Exitting...");
				String g=new String("quit");
				buf=g.getBytes();
				ds1.send(new DatagramPacket(buf,g.length(),i1,8001));
				v=false;
				}
	
			else
				{
				int k=0,j=0;
				System.out.println("Requested Domain name from Client: " +str);

				for(j=0;j<10;j++)
					{
					if(str.equals(dname[j]))
						{
						System.out.println("Found! :"+ipaddr[j]);
						buf=ipaddr[j].getBytes();
						ds.send(new DatagramPacket(buf,ipaddr[j].length(), i, 6000));
						System.out.println("Sent to client");
						v=true;
						}
					else
						{	
						k++;
						}
					}

				if(k==10)
					{
					System.out.println("Request sent to another server");

					buf1=str.getBytes();
					ds1.send(new DatagramPacket(buf1,str.length(), i, 8001));
					ds1.receive(dp1);
	
					String ss=new String(dp1.getData(), 0,dp1.getLength());
					if(ss.equals("NOT FOUND"))
						{
						System.out.println("Domain name not found");
						String p=new String("NOT FOUND");
						buf=p.getBytes();
						ds.send(new DatagramPacket(buf,p.length(), i, 6000));
						}
					else
						{
						System.out.println("IPaddress from Server1:"+ss);
						buf=ss.getBytes();
						ds.send(new DatagramPacket(buf,ss.length(), i, 6000));
						}
					}
				}
			}while(v);
		}
	}