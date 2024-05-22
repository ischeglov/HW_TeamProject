package ru.netology.javaqadiplom;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class CreditAccountTest {

    @BeforeAll
    static void setUp() {
        System.out.println("Тесты запущены");
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Тесты выполнены");
    }

    @DisplayName("Выброс исключений для заданных параметров при создании кредитного счета")
    @Test
    public void shouldExceptionCreateCreditAccount() {
        assertThrows(IllegalArgumentException.class, () -> new CreditAccount(1000, 5000, -20));
        assertThrows(IllegalArgumentException.class, () -> new CreditAccount(1000, 5000, 0));
//        assertThrows(IllegalArgumentException.class, () -> new CreditAccount(-1000, 5000, 20));
        assertThrows(IllegalArgumentException.class, () -> new CreditAccount(1000, -5000, 20));
    }

    @DisplayName("Пополнение кредитного счета на положительную указанную сумму")
    @Test
    public void shouldAddToPositiveBalance() {
        CreditAccount account = new CreditAccount(
                1000,
                5_000,
                15
        );

        assertTrue(account.add(3000));
        assertEquals(4_000, account.getBalance());
    }

    @DisplayName("Пополнение кредитного счета на отрицательную указанную сумму")
    @Test
    public void shouldAddToNegativeBalance() {
        CreditAccount account = new CreditAccount(
                1000,
                5000,
                15
        );

        assertFalse(account.add(-3000));
        assertEquals(1000, account.getBalance());
    }

    @DisplayName("Пополнение кредитного счета на '0'")
    @Test
    public void shouldAddToZeroBalance() {
        CreditAccount account = new CreditAccount(
                1000,
                5000,
                15
        );

        assertFalse(account.add(0));
        assertEquals(1000, account.getBalance());
    }

    @DisplayName("Покупка на положительную указанную сумму в пределах кредитного лимита")
    @Test
    public void shouldPurchasePayToWithinLimit() {
        CreditAccount account = new CreditAccount(
                1000,
                5000,
                15
        );

        assertTrue(account.pay(5000));
        assertEquals(-4000, account.getBalance());
    }

    @DisplayName("Покупка на положительную указанную сумму за пределами кредитного лимита")
    @Test
    public void shouldPurchasePayToOverLimit() {
        CreditAccount account = new CreditAccount(
                1000,
                5000,
                15
        );

        assertFalse(account.pay(7000));
        assertEquals(1000, account.getBalance());
    }

    @DisplayName("Покупка на положительную указанную сумму с кредитным лимитом равным нулю")
    @Test
    public void shouldPurchasePayToWithinZeroLimit() {
        CreditAccount account = new CreditAccount(
                1000,
                0,
                15
        );

        assertTrue(account.pay(1000));
        assertEquals(0, account.getBalance());
    }

    @DisplayName("Расчет процентов на отрицательный и положительный баланс кредитного счета")
    @ParameterizedTest
    @CsvSource({
            "-200,5000,15,-30",
            "200,5000,15,0",
            "200,5000,10,0",
            "0,5000,10,0"
    })
    public void shouldCountPercent(int initialBalance, int creditLimit, int rate, int expected) {
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);

        int actual = account.yearChange();

        assertEquals(expected, actual);
    }
}
