import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

import java.xml.rpc.*;
import java.rmi.*;

public class ArithMIDlet extends CommandListener, implements Runnable
{
Form form;
Display display;

TextField num1, num2, result;
Command add, sub, exit;

Thread thread;

int value;

public ArithMIDlet()
	{
	form=new Form("Arithmetic");
	display=Display.getDisplay(this);
	thread=new Thread(this);

	num1=new TextField("Number1", "", 10, TextField.ANY);
	num2=new TextField("Number2", "", 10, TextField.ANY);
	result=new TextField("Result", "", 10, TextField.ANY);

	add=new Command("Add", Command.SCREEN, 1);
	sub=new Command("Sub", Command.SCREEN, 2);
	exit=new Command("Exit", Command.SCREEN, 3);

	form.append(num1);
	form.append(num2);
	form.append(result);
	form.addCommand(add);
	form.addCommand(sub);
	form.addCommand(exit);

	form.setCommandListener(this);
	}

public void startApp()
	{
	display.setCurrent(form);
	}

public void pauseApp()
	{
	}

public destroyApp(boolean unconditional)
	{
	notifyDestroyed();
	}

public void commandAction(Command c, Displayable s)
	{
	if(c==add || c==sub)
		{
		
		if(c==add)
			value=1;
		else
			value=2;
		thread.start();
		}
	else
		{
		destroyApp(false);
		}
		
	}

public void run()
	{
	try
		{
		int a=Integer.parseInt(num1.getString());
		int b=Integer.parseInt(num2.getString());
		ArithInt_Stub stub=new ArithInt_Stub();
		
		if(value==1)
			{	
			int r=stub.add(a,b);
			result.setString(String.valueOf(r));
			}

		else 
			{	
			int r=stub.sub(a,b);
			result.setString(String.valueOf(r));
			}
		}
	catch(RemoteException re)
		{
		}
	}

}



