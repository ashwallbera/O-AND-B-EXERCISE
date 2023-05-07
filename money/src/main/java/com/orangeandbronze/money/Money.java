package com.orangeandbronze.money;

import java.util.Objects;

public class Money {
    private final int dollar;
    private final int cents;
    private final Currency currency;

    /**
     * @param currency  must not be null
     * @param dollar  must not be < 0
     * @param cents  must not be < 0
    * */
    public Money(Currency currency, int dollar, int cents){
        notNull(currency);
        notNegative(dollar);
        notNegative(cents);

        this.dollar = dollar;
        this.cents = cents;
        this.currency = currency;
    }

    /**
     * @param other currency must equal to current object
    * */

    public Money add(Money other){
        if(!isEqualCurrency(other.currency))
            throw new CurrencyException("currency must be the same");
        return new Money(currency,this.dollar+other.dollar,this.cents+other.cents);
    }

   private Boolean isEqualCurrency(Currency other){
        return this.currency.equals(other);
   }
    private void notNull(Currency currency){
        if (currency == null)
            throw new NullPointerException("currency cannot be null");

    }
    private void notNegative(int number){
        if (number < 0)
            throw new IllegalArgumentException("number must not be negative");
    }

    @Override
    public String toString() {
        String centsSring = cents < 10 ? "0"+cents: ""+cents;
        return this.currency+" "+dollar+"."+centsSring;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;
        return dollar == money.dollar && cents == money.cents && currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dollar, cents, currency);
    }
}
