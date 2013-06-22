package osmTileMachine;

public class MessagePrinter {

	static void debug (Configuration sessionConfiguration, String s){
			if (sessionConfiguration.getDebugOutput()) System.out.println("DEBUG: " + s);
	}

	static void notify (Configuration sessionConfiguration, String s){
		System.out.println("INFO: " + s);
	}

	static void error (Configuration sessionConfiguration, String s){
		System.out.println("ERROR: " + s);
	}

	static void warning (Configuration sessionConfiguration, String s){
		System.out.println("WARNING: " + s);
	}
}
