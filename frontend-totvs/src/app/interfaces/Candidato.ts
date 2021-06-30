export interface Candidato {
  id: string;
  name: string;
  mail: string;
  phone: string;
  vaga: Record<string, string>;
  file: File;
}

interface File {
  id: string;
  filename: string;
}

export interface CandidatoPost {
  name: string;
  mail: string;
  phone: string;
  vaga?: Record<string, string>;
}

export interface CandidatoPut {
  id: string;
  name: string;
  mail: string;
  phone: string;
  vaga?: Record<string, string>;
}