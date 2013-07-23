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

}
