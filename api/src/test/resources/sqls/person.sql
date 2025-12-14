-- Inserting a person with a valid CPF and a birth date
INSERT INTO public.person
(id, birth_date, cpf, name)
VALUES('9ab1a3db-00fb-4864-b2d5-f1f362fb7bfd', '1990-01-01', '13260581936', 'Person CPF Válido');

-- Inserting a person with an invalid CPF and a birth date
INSERT INTO public.person
(id, birth_date, cpf, name)
VALUES('761fdf54-d775-44f0-a5de-a1f6aee818ae', '1990-01-01', '11111111111', 'Person CPF Inválido');

INSERT INTO public.person
(id, birth_date, cpf, name)
VALUES('e2a1b8ab-1cde-4be4-8d4e-7a4f9f6c6a9a', '1995-05-20', '98765432100', 'Jane Smith');
