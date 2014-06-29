package osmTileMachine;

public class TimeConsumer {

	public static void sleepSeconds (double d)
	{
		try {
			Thread.sleep((long) (1000*d));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// if it doesn't work: what to do?!
			e.printStackTrace();
		}
	}
}
