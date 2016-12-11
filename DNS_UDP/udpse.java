import java.io.*;
import java.net.*;
class udpse
{
	static byte buf[] = new byte[1024];
	static byte buf1[] = new byte[1024];	
	public static void main(String[] a) throws IOException
	{
		DatagramSocket ds= new DatagramSocket(6000);
		DatagramPacket dp = new DatagramPacket(buf,buf.length);
		InetAddress i=InetAddress.getLocalHost();
		DatagramSocket ds1= new DatagramSocket(6001);
		DatagramPacket dp1 = new DatagramPacket(buf1,buf1.length);
		InetAddress i1=InetAddress.getLocalHost();
		boolean v=true;
		String [] dname=new String[5];
		String [] ipad=new String[5];
		dname[0]="www.google.com";
		ipad[0]="10.0.0.0";
		dname[1]="www.yahoo.com";
		ipad[1]="10.0.0.1";
		dname[2]="www.gmail.com";
		ipad[2]="10.0.0.2";
		dname[3]="www.face.com";
		ipad[3]="10.0.0.3";
		dname[4]="www.book.com";
		ipad[4]="10.0.0.4";
		System.out.println("Server is Running.....");
		do
		{
			ds.receive(dp);
			String str = new String(dp.getData(), 0,dp.getLength());
			if(str.equals("quit"))
			{
				System.out.println("Server Exits.....");
				String g=new String("quit");
				buf=g.getBytes();
				ds1.send(new DatagramPacket(buf,g.length(),i1,8001));
				v=false;
			}
			else
			{
				int k=0,j=0;
				System.out.println("Domain name from Client: " +str);
				for(j=0;j<5;j++)
				{
					if(str.equals(dname[j]))
					{
						System.out.println("IPaddress is found:"+ipad[j]);
						buf=ipad[j].getBytes();
						ds.send(new DatagramPacket(buf,ipad[j].length(), i, 8000));
						System.out.println("IPaddress is sent to client");
						v=true;
					}
					else
					{	
						k++;
					}
				}
				if((k!=0)&&(k==5))
				{
					System.out.println("Request sent to another server");
					buf1=str.getBytes();
					ds1.send(new DatagramPacket(buf1,str.length(), i, 8001));
					ds1.receive(dp1);
					String ss=new String(dp1.getData(), 0,dp1.getLength());
					if(ss.equals("x"))
					{
						System.out.println("Domain name not found");
						String p=new String("x");
						buf=p.getBytes();
						ds.send(new DatagramPacket(buf,p.length(), i, 8000));
					}
					else
					{
						System.out.println("IPaddress from Server1:"+ss);
						buf=ss.getBytes();
						ds.send(new DatagramPacket(buf,ss.length(), i, 8000));
					}
				}
			}
		}while(v);
	}
}