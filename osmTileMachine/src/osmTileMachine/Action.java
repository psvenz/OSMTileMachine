package osmTileMachine;

abstract class Action {
	public final int ACTIONTYPE_EXTRACTACTION = 1;
	public final int ACTIONTYPE_RENDERACTION = 2;
	public final int ACTIONTYPE_DATAUPDATEACTION = 3;
	public final int ACTIONTYPE_DOWNLOADACTION = 4;

	abstract int getActionType();
	abstract void runAction(Configuration sessionConfiguration) throws Exception;
	abstract String getActionInHumanReadableFormat(); 
}

