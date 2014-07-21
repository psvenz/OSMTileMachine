package osmTileMachine;

import java.util.ArrayList;

public class ActionList {

	private ArrayList<Action> theActionList;
	private int maxSize;

	public ActionList(){
		theActionList = new ArrayList<Action>();
		maxSize = 0;
	}

	public void append(ActionList b)
	{
		while (b.actionsLeft())
		{
			addItem(b.getNextAction());
		}
	}
	
	public void addItem(Action a){
		theActionList.add(a);
		if (maxSize < size())
			{
				maxSize = size();
			}
	}
	
	public int size(){
		return theActionList.size();
	}

	public int originalSize(){
		return maxSize;
	}

	public boolean actionsLeft(){
		return theActionList.size() > 0;
	}
	public Action getNextAction(){
		return theActionList.remove(0);
	}

	public String getListInHumanReadableFormat(){
		String s = "Action list:";
		s = s + System.getProperty("line.separator");
		for (int i=0;i< theActionList.size();i++)
		{
			s = s + "Action #" + (i+1) + ": ";
			s = s + theActionList.get(i).getActionInHumanReadableFormat();
			s = s + System.getProperty("line.separator");
		}
		return s;
	}
}
