package video;

import java.rmi.*;
import java.io.*;

public class VideoImpl implements VideoInt
{

byte[] buff;

public byte[] getVideo(String name) throws RemoteException
	{
	try{	
	File f=new File(name);

	FileInputStream fis=new FileInputStream("F:\\krishnaa.avi");	

	buff=new byte[(int)f.length()];

	fis.read(buff);

	fis.close();



	}catch(Exception e){System.out.println(e);}


	return buff;

	}



}