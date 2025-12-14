package com.avaliacaopratica.api.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class Utilities {

    public static boolean isEmpty(Object value) {
        if (value == null)
            return true;

        if (value instanceof String)
            return ((String) value).trim().isEmpty();

        if (value instanceof Collection)
            return ((Collection<?>) value).isEmpty();

        if (value instanceof Map)
            return ((Map<?, ?>) value).isEmpty();

        if (value.getClass().isArray())
            return Arrays.asList(value).isEmpty();

        return false;
    }

    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }

    public static boolean isCPF(String CPF) {
        CPF = CPF.replaceAll("[^0-9]", "");

        if (CPF.length() != 11) {
            return false;
        }

        if (CPF.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (CPF.charAt(i) - '0') * (10 - i);
        }
        int primeiroDigitoVerificador = 11 - (soma % 11);
        if (primeiroDigitoVerificador == 10 || primeiroDigitoVerificador == 11) {
            primeiroDigitoVerificador = 0;
        }

        if (primeiroDigitoVerificador != (CPF.charAt(9) - '0')) {
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (CPF.charAt(i) - '0') * (11 - i);
        }
        int segundoDigitoVerificador = 11 - (soma % 11);
        if (segundoDigitoVerificador == 10 || segundoDigitoVerificador == 11) {
            segundoDigitoVerificador = 0;
        }

        if (segundoDigitoVerificador != (CPF.charAt(10) - '0')) {
            return false;
        }

        return true;
    }
}
