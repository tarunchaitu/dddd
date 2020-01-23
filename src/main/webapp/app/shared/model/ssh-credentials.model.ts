import { ISharedAgent } from 'app/shared/model/shared-agent.model';

export interface ISshCredentials {
  id?: number;
  name?: string;
  username?: string;
  password?: string;
  sshKey?: string;
  sharedAgents?: ISharedAgent[];
}

export class SshCredentials implements ISshCredentials {
  constructor(
    public id?: number,
    public name?: string,
    public username?: string,
    public password?: string,
    public sshKey?: string,
    public sharedAgents?: ISharedAgent[]
  ) {}
}
