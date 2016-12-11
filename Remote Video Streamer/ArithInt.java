package arith;
import java.rmi.*;
public interface ArithInt extends Remote
{
  public int add(int a, int b) throws RemoteException;
  public int sub(int a, int b)throws RemoteException;
 } 