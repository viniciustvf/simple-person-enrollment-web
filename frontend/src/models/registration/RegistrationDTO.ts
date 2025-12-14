export interface RegistrationRequestDTO {
    idCourse: number;
    cpf: string;
    nota: number;
  }
  
  export interface RegistrationResponseDTO {
    idRegistration: number;
    courseName: string;
    cpf: string;
    nota: number;
    dataInscricao: string;
  }
  