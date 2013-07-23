package osmTileMachine;

abstract class Action {
	public final int ACTIONTYPE_EXTRACTACTION = 1;
	public final int ACTIONTYPE_RENDERACTION = 2;
	
	abstract int getActionType();
	abstract void runAction(Configuration sessionConfiguration) throws Exception;
}
