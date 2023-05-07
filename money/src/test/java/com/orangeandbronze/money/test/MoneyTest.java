package com.orangeandbronze.money.test;

import com.orangeandbronze.money.CurrencyException;
import com.orangeandbronze.money.Money;
import com.orangeandbronze.money.SubtractOperationException;
import org.junit.jupiter.api.Test;

import static com.orangeandbronze.money.Currency.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyTest {

    @Test
    void toString_positive_greater_than_1_and_cents_greater_than_9(){
        //Given money where dollar is 1 and cents is 50 and currency is PHP
        Money money = new Money(PHP, 1,50);
        //When toString is called
        String actual = money.toString();
        //Then result
        assertEquals("PHP 1.50",actual);
    }

    @Test
    void toString_positive_greater_than_1_and_cents_less_that_10(){
        //Given dollar is 1, cents is 5, currency is PHP
        Money money = new Money(PHP, 1,5);
        //When toString is called
        String actual = money.toString();
        //Then result should be "PHP 1.05"
        assertEquals("PHP 1.05",actual);
    }

    @Test
    void toString_positive_values_no_cents_same_currency(){
        //Given PHP 3.00 and PHP 2.00
        Money m1 = new Money(PHP, 3,0);
        Money m2 = new Money(PHP, 2,0);

        //When toString is called
        Money actual = m1.add(m2);
        //Then result should be "PHP 5.00"
        assertEquals(new Money(PHP,5,0),actual);
    }

    @Test
    void add_positive_values_no_cents_not_same_currency(){
        //Given Currency USD 2.00 and PHP 3.00
        Money usd = new Money(USD, 2,0);
        Money php = new Money(PHP, 3,0);

        //When add method is called
        //Then result should throw CurrencyException
        assertThrows(CurrencyException.class,() -> usd.add(php));
    }

    @Test
    void add_negative_values_cents_and_dollar_same_currency(){
        //Given PHP -2.00 and PHP 3.-5
        //When constructor invoked
        //Then result should throw IllegalArgumentException

        assertThrows(IllegalArgumentException.class,
                () -> {new Money(PHP, -2,0);});
        assertThrows(IllegalArgumentException.class,
                () -> {new Money(PHP, 3,-5);});
    }

    @Test
    void subtract_positive_values_cents_and_dollar_other_money_less_than_current(){
        //Given PHP 2.00 and PHP 1.00
        Money current = new Money(PHP, 2,0);
        Money other = new Money(PHP, 1,0);
        //When subtract method called
        Money actual = current.subtract(other);
        //The result should be PHP 1.00
        assertEquals(new Money(PHP,1,0),actual);
    }

    @Test
    void subtract_positive_values_cents_and_dollar_other_money_greater_than_current(){
        //Given PHP 2.00 and PHP 3.00
        Money current = new Money(PHP, 2,0);
        Money other = new Money(PHP, 3,0);
        //When subtract method called
        //The result should be
        // SubtractOperationException
        assertThrows(SubtractOperationException.class, () ->{
            current.subtract(other);
        });

    }


}
