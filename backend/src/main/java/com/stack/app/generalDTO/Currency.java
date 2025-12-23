package com.stack.app.generalDTO;

public enum Currency {
    dollar("Dollar"),
    rub("Rub"),
    bitcoin("Bitcoin"),
    euro("Euro"),
    rupees("Rupees"),
    yuan("Yuan"),
    sterlings("Sterlings"),;

    private String value;
    Currency(String value) {
    }
}
