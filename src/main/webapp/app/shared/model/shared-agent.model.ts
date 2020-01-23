import { ISshCredentials } from 'app/shared/model/ssh-credentials.model';
import { AgentState } from 'app/shared/model/enumerations/agent-state.model';
import { BootstrapMethod } from 'app/shared/model/enumerations/bootstrap-method.model';
import { Os } from 'app/shared/model/enumerations/os.model';

export interface ISharedAgent {
  id?: number;
  name?: string;
  description?: string;
  noOfExecutors?: number;
  label?: string;
  workspace?: string;
  dnsName?: string;
  agentState?: AgentState;
  bootstrapMetod?: BootstrapMethod;
  os?: Os;
  sshPort?: number;
  jvmOptions?: string;
  sshCredentials?: ISshCredentials;
}

export class SharedAgent implements ISharedAgent {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public noOfExecutors?: number,
    public label?: string,
    public workspace?: string,
    public dnsName?: string,
    public agentState?: AgentState,
    public bootstrapMetod?: BootstrapMethod,
    public os?: Os,
    public sshPort?: number,
    public jvmOptions?: string,
    public sshCredentials?: ISshCredentials
  ) {}
}
