package pacman;
import javax.microedition.lcdui.*;
public class Menu extends List implements CommandListener 
{

	private pacman parent = null;



    	private GameCanvas canvas_;

	private int started = 0;



	private Display disp_;

	private Command exitGameCommand = new Command("EXIT", Command.EXIT, 1);

	private Command aboutCommand = new Command("Help", Command.EXIT, 1);

	private Command restartCommand = new Command("New Game", Command.SCREEN, 1); 

	private Command continueCommand = new Command("Continue", Command.SCREEN, 1); 


	private Command exitCommand = new Command("Pause", Command.SCREEN, 1); 

 





	public Menu(Display d, pacman parent)

	{

		super("PACMAN", List.IMPLICIT);

		disp_ = d;

		init(parent);

	}

	

	public void init(pacman parent)

	{

		this.parent = parent;

		if (this.parent.gameOver == 0)

			this.append("Welcome to Pacman\n\nclick Menu to start", null);

		else if (this.parent.gameOver ==1 )

		{

			System.out.println("gameover");

			this.append("Game Over\n\nclick Menu to start", null);

		}



		this.addCommand(exitGameCommand);

		this.addCommand(aboutCommand);



		this.addCommand(restartCommand); 

		this.addCommand(continueCommand); 

		//this.addCommand(overCommand); 

		this.setCommandListener(this); 

        

	}

	



	

	public void show()

	{

		disp_.setCurrent(this);

	}

	

	public void commandAction (Command c, Displayable d) 

	{ 

		if (d == this) 

		{ 

			if (c == restartCommand || c == List.SELECT_COMMAND) 

			{ 

				//new game

				canvas_ = new GameCanvas(parent, disp_);

        			canvas_.addCommand(exitCommand);

        			canvas_.setCommandListener(this);

				started = 1;

				canvas_.show();

			} 

			if (c==exitGameCommand || c==List.SELECT_COMMAND)

			{

				//EXIT

				parent.notifyDestroyed();



			}

			if (c==continueCommand || c==List.SELECT_COMMAND)

			{

				//continue

				if (started == 1)

					canvas_.show();

			}



			

		}

		if (d == canvas_) 

		{

			if (c==exitCommand || c == List.SELECT_COMMAND) 

			{	

				this.delete(0);

				this.parent.gameOver = 0;

				init(parent);

				canvas_.destroyFrameTrigger();

				this.show();



			}

		}

	} 



}

