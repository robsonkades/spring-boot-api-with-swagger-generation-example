package com.pismo.payments.dtos;

public enum OperationType {
    SPOT_PURCHASE_RATE(1), PAY_IN_INSTALLMENTS(2), WITHDRAW(3), PAYMENT(4);

    private int value;

    OperationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
