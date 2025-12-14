export function formatNameLive(value: string): string {
    return value
      .replace(/[^a-zA-ZÀ-ÿ\s]/g, "")
      .replace(/\s+/g, " ")
      .split(" ")
      .map((word) =>
        word
          ? word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()
          : ""
      )
      .join(" ");
  }  
  
  export function isValidFullName(name: string): boolean {
    if (!name) return false;
  
    const parts = name
      .trim()
      .split(" ")
      .filter(Boolean);
  
    if (parts.length < 2) return false;
  
    return parts.every(
      (part) =>
        part[0] === part[0].toUpperCase() &&
        part.slice(1) === part.slice(1).toLowerCase()
    );
  }
  
  
  export function isValidCPF(cpf: string): boolean {
    cpf = cpf.replace(/\D/g, "");
    if (!cpf || cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false;
  
    let soma = 0;
    for (let i = 0; i < 9; i++) soma += parseInt(cpf[i]) * (10 - i);
    let resto = (soma * 10) % 11;
    if (resto === 10) resto = 0;
    if (resto !== parseInt(cpf[9])) return false;
  
    soma = 0;
    for (let i = 0; i < 10; i++) soma += parseInt(cpf[i]) * (11 - i);
    resto = (soma * 10) % 11;
    if (resto === 10) resto = 0;
  
    return resto === parseInt(cpf[10]);
  }
  
  export function isValidEmail(email: string): boolean {
    if (!email) return true;
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  }
  