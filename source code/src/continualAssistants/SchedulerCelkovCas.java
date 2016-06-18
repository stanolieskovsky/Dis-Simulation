package continualAssistants;

import OSPABA.*;
import Objekty.Konstanty;
import simulation.*;
import agents.*;

//meta! id="127"
public class SchedulerCelkovCas extends Scheduler
{
	public SchedulerCelkovCas(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="AgentOkolia", id="128", type="Start"
	public void processStart(MessageForm message)
	{
            message.setCode(Mc.finish);
            hold(Konstanty.dlzkaSimulacie, message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.start:
			processStart(message);
		break;
                    case Mc.finish:
			assistantFinished(message);
		break;
                    
		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentOkolia myAgent()
	{
		return (AgentOkolia)super.myAgent();
	}

}
