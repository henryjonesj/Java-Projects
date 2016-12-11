package arith;
import java.rmi.*;

public class ArithImpl implements ArithInt
{
	public int add(int a, int b)throws RemoteException
	{
		return a+b;
	}
	
	
	public int sub(int a, int b)throws RemoteException
	{
		return a-b;
	}
}