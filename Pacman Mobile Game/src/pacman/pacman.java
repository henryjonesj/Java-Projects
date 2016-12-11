package pacman;

import javax.microedition.midlet.*;

import javax.microedition.lcdui.*;

public class pacman extends MIDlet implements CommandListener

{



  

    private GameCanvas canvas_;


	private Menu menu_ ;

	private Menu gameOvermenu_ ;



	public int gameOver;



    	public pacman() 

    	{

        	super();

        	Display disp = Display.getDisplay(this);



		menu_ = new Menu(disp, this);

        

		canvas_ = new GameCanvas(this, disp);

	}



   

    public void startApp() throws MIDletStateChangeException 

    {

        

		gameOver = 0;

		menu_.show();

	



    }

  


    public void pauseApp() 

    {



    }

	

	

	public void restartApp() throws MIDletStateChangeException

	{

		Display disp = Display.getDisplay(this);

		canvas_ = new GameCanvas(this, disp);

		

		canvas_.setCommandListener(this);

		


		gameOvermenu_=new Menu(disp,this);


		gameOvermenu_.show();





	}

	

   

    public void destroyApp(boolean bool)

    {


	}



    public void commandAction(Command c, Displayable s)

    {

    }





}

