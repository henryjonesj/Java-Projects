package videostub;

import java.io.*;
import javax.xml.rpc.*;


public class VideoClient
{

public static Stub createProxy()
	{

	return (Stub)(new VideoService_Impl().getVideoIntPort());

	}


public static void main(String args[]) throws IOException
	{
	Stub stub=createProxy();

	VideoInt client=(VideoInt)stub;

	byte buff[]=client.getVideo("krishnaa.avi");

	File f=new File("wl.avi");

	FileOutputStream fos=new FileOutputStream(f);

	fos.write(buff);

	}




}