import java.net.*;
import java.io.*;
public class udpse1
{
	static byte buf[] = new byte[1024];
	public static void main(String args[])throws IOException
	{
		DatagramSocket ds= new DatagramSocket(8001);
		DatagramPacket dp = new DatagramPacket(buf,buf.length);
		InetAddress i=InetAddress.getLocalHost();
		boolean v=true;
		String [] dname=new String[5];
		String [] ipad=new String[5];
		dname[0]="www.google1.com";
		ipad[0]="10.0.0.10";
		dname[1]="www.yahoo1.com";
		ipad[1]="10.0.0.11";
		dname[2]="www.gmail1.com";
		ipad[2]="10.0.0.12";
		dname[3]="www.face1.com";
		ipad[3]="10.0.0.13";
		dname[4]="www.book1.com";
		ipad[4]="10.0.0.14";
		System.out.println("Server is Running...");
		do
		{
			ds.receive(dp);
			String str=new String(dp.getData(), 0,dp.getLength());
			if(str.equals("quit"))
			{
				System.out.println("Server1 Exits.....");
				v=false;
			}
			else
			{
				int j=0,k=0;
				System.out.println("Domain name from Server:"+str);
				for(j=0;j<5;j++)
				{
					if(str.equals(dname[j]))
					{	
						System.out.println("IPaddress is found:"+ipad[j]);
						buf=ipad[j].getBytes();
						ds.send(new DatagramPacket(buf,ipad[j].length(), i, 6001));
						v=true;
					}
					else
					{	
						k++;
					}
				}
				if((k!=0)&&(k==5))
				{
					System.out.println("Domain not found");
					String p=new String("x");
					buf=p.getBytes();
					ds.send(new DatagramPacket(buf,p.length(), i, 6001));
				}
			}
		}while(v);
				
	}
}
			