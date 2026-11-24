package com.vjtech.gastoshogar.domain.enums;

public enum TipoMembresia {
    MONTH,
    YEAR;

    public static TipoMembresia from(String value) {
        if (value == null) {
            return null;
        }
        try {
            return TipoMembresia.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de membresía inválido: " + value + ". Valores permitidos: MONTH, YEAR");
        }
    }

    public int mesesDeDuracion() {
        return this == MONTH ? 1 : 12;
    }
}