package ru.netology.javaqadiplom;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SavingAccountTest {
    @BeforeAll
    static void setUp() {
        System.out.println("Тесты запущены");
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Тесты выполнены");
    }

    @Test

    public void shouldExceptionCreateSavingAccount() {
        assertThrows(IllegalArgumentException.class, () -> new SavingAccount(0, 5000, 1000, 10));
        assertThrows(IllegalArgumentException.class, () -> new SavingAccount(-100, 1000, 5000, 10));
        assertThrows(IllegalArgumentException.class, () -> new SavingAccount(0, 1000, -5000, 10));
        assertThrows(IllegalArgumentException.class, () -> new SavingAccount(0, -1000, 5000, 10));
        assertThrows(IllegalArgumentException.class, () -> new SavingAccount(1000, 1000, 5000, -10));
    }

    @Test
    public void shouldAddLessThanMaxBalance() {
        SavingAccount account = new SavingAccount(
                2_000,
                1_000,
                10_000,
                10
        );

        account.add(3_000);

        assertEquals(5_000, account.getBalance());
    }

    @Test
    public void shouldAddMoreMaxBalance() {
        SavingAccount account = new SavingAccount(
                2_000,
                1_000,
                10_000,
                10
        );

        account.add(20_000);

        assertEquals(2_000, account.getBalance());
    }

    @Test
    public void shouldAddNegativeBalance() {
        SavingAccount account = new SavingAccount(
                2_000,
                1_000,
                10_000,
                10
        );

        account.add(-3000);

        assertEquals(2_000, account.getBalance());
    }

    @Test
    public void shouldPurchasePayToWithinBalance() {
        SavingAccount account = new SavingAccount(
                5_000,
                1_000,
                10_000,
                10
        );

        account.pay(3_000);

        assertEquals(2_000, account.getBalance());
    }

    @Test
    public void shouldPayLessThanMinZero() {
        SavingAccount account = new SavingAccount(
                5_000,
                0,
                10_000,
                10
        );

        account.pay(6_000);

        assertEquals(5_000, account.getBalance());
    }

    @Test
    public void shouldPayLessThanMinBalance() {
        SavingAccount account = new SavingAccount(
                5_000,
                2_000,
                10_000,
                10
        );

        account.pay(4_000);

        assertEquals(5_000, account.getBalance());
    }

    @ParameterizedTest

    @CsvSource({
            "1000,1000,10000,10,100",
            "0, 0, 10000, 10, 0",
            "0, 0, 0, 0, 0",
            "200, 0, 10000, 15, 30"
    })
    public void shouldCountPercent(int initialBalance, int minBalance, int maxBalance, int rate, int expected) {
        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        int actual = account.yearChange();


        assertEquals(expected, actual);
    }
}
