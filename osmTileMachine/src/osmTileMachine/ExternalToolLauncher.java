package osmTileMachine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.sql.Time;
import java.util.ArrayList;
import java.io.*;

public class ExternalToolLauncher {

	private ArrayList<String> argumentList;
	private Configuration sessionConfiguration;
	private int exitValue;

	public ExternalToolLauncher(Configuration conf){
		sessionConfiguration = conf;
		argumentList = new ArrayList<String>();
		exitValue = -999;
	}

	public void addArgument(String a){
		argumentList.add(a);
	}
	public void setCommand(String command) throws Exception{
		if (argumentList.isEmpty()){		
			argumentList.add(command);
		}
		else{
			throw new Exception("setCommand must be called before addArgument");
		}
	}
	public int ExitValue(){
		return exitValue;
	}


	public void run()
	{
		String[] args = argumentList.toArray(new String[argumentList.size()]);
		try
		{            

			ProcessBuilder pb = new java.lang.ProcessBuilder(argumentList);
			pb.directory(new File(sessionConfiguration.getWorkingDirectory()));
			pb.redirectErrorStream(true);

			String argumentsPrintFriendly = "";
			for (int i = 0; i<args.length;i++)
			{
				argumentsPrintFriendly = argumentsPrintFriendly + " " + args[i];
			}
			MessagePrinter.debug(sessionConfiguration, "ExternalToolLauncher: Calling external tool: " + argumentsPrintFriendly);


			Process proc = pb.start();

			// any output?
			StreamGobbler outputGobbler = new 
					StreamGobbler(proc.getInputStream(),args[0], sessionConfiguration.getDebugOutput());

			outputGobbler.start();


			// any error???
			int exitVal = proc.waitFor();
			outputGobbler.join();
			MessagePrinter.debug(sessionConfiguration, "ExternalToolLauncher: Process exited with return code: " + exitVal);
			exitValue = exitVal;
		} catch (Throwable t)
		{
			MessagePrinter.debug(sessionConfiguration, "ExternalToolLauncher exception! Stack trace:" + t.getStackTrace().toString());
//			t.printStackTrace();
		}
	}
	
	public void runAndKill(double TimeoutSeconds)
	{
		String[] args = argumentList.toArray(new String[argumentList.size()]);
		try
		{            

			ProcessBuilder pb = new java.lang.ProcessBuilder(argumentList);
			pb.directory(new File(sessionConfiguration.getWorkingDirectory()));
			pb.redirectErrorStream(true);

			String argumentsPrintFriendly = "";
			for (int i = 0; i<args.length;i++)
			{
				argumentsPrintFriendly = argumentsPrintFriendly + " " + args[i];
			}
			MessagePrinter.debug(sessionConfiguration, "ExternalToolLauncher: Calling external tool: " + argumentsPrintFriendly);


			Process proc = pb.start();

			// any output?
			StreamGobbler outputGobbler = new 
					StreamGobbler(proc.getInputStream(),args[0], sessionConfiguration.getDebugOutput());

			outputGobbler.start();

			TimeConsumer.sleepSeconds(TimeoutSeconds);
			
			proc.destroy();
			proc.waitFor();
			
			outputGobbler.join();
			MessagePrinter.debug(sessionConfiguration, "ExternalToolLauncher: Process killed after " + TimeoutSeconds + " seconds.");
		} catch (Throwable t)
		{
			MessagePrinter.debug(sessionConfiguration, "ExternalToolLauncher exception!");
			t.printStackTrace();
		}
	}
}


class StreamGobbler extends Thread
{
	InputStream is;
	String type;
	Configuration sessionConfiguration;
	String appName;
	Boolean debugOutput;

	StreamGobbler(InputStream is, String n, Boolean d)
	{
		this.is = is;
		appName = n;
		debugOutput = d;

	}

	public void run()
	{
		try
		{
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line=null;
			while ( (line = br.readLine()) != null)
				if (debugOutput) 
				{
					if (debugOutput) System.out.println("DEBUG: ExternalToolLauncher: " + appName + " output: " + line);    

					//MessagePrinter.debug(sessionConfiguration, "ExternalToolLauncher: "+ appName + " output:" +line);
				}
		} catch (IOException ioe)
		{
			ioe.printStackTrace();  
		}
	}
}
