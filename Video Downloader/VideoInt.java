package video;

import java.rmi.*;

public interface VideoInt extends Remote
{

public byte[] getVideo(String name) throws RemoteException;
}