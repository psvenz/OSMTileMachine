package osmTileMachine;

import java.util.ArrayList;

public class ActionList {

	private ArrayList<Action> theActionList;

	public ActionList(){
		theActionList = new ArrayList<Action>();
	}

	public void addItem(Action a){
		theActionList.add(a);
	}
	
	public int size(){
		return theActionList.size();
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
