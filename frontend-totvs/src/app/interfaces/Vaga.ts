import { Candidato } from "./Candidato";

export interface Vaga {
  id: string;
  name: string;
  status: string;
  candidatos: Array<Candidato>;
}

export interface VagaPost {
  name: string;
  status: VagaStatusEnum;
}

export interface VagaPut {
  id: string;
  name: string;
  status: VagaStatusEnum;
}

export enum VagaStatusEnum {
  OPEN = 'OPEN',
  CLOSED = 'CLOSED',
}