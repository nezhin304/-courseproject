import com.bank.dao.BankAccountDAOImpl;
import com.bank.dao.CardDAOImpl;
import com.bank.dao.CustomerDAOImpl;
import com.bank.dao.TransactionDAOImpl;
import com.bank.entity.BankAccount;
import com.bank.entity.Card;
import com.bank.entity.Customer;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;

public class CardDAOTest {

    @Test
    public void insertAndSelectEntityTest() {

        CustomerDAOImpl.getInstance().deleteAll();

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccount("968574124");
        bankAccount.setDeposit(new BigDecimal(100000.0));
        bankAccount.setCredit(new BigDecimal(0.0));
        bankAccount.setState(true);

        Card card = new Card();
        card.setNumber("1234 5678 9101 1122");
        card.setBankAccount(bankAccount);

        Customer customer = new Customer();
        customer.setName("Vasja");
        customer.setSurname("Pjatochkin");
        customer.setPhone("380501112233");
        customer.setCard(card);
        card.setCustomer(customer);

        CardDAOImpl.getInstance().deleteCard(card);
        BankAccountDAOImpl.getInstance().deleteAccount(card.getBankAccount());

        CardDAOImpl.getInstance().save(card);

        Collection<Card> cards = CardDAOImpl.getInstance().getAll();
        Card cardResult = cards.stream()
                .filter(c -> c.getNumber().equals("1234567891011122")).findFirst().get();

        assertEquals("1234567891011122", cardResult.getNumber());
        assertEquals("Vasja", cardResult.getCustomer().getName());
        assertEquals("Pjatochkin", cardResult.getCustomer().getSurname());
        assertEquals("380501112233", cardResult.getCustomer().getPhone());
        assertEquals("968574124", cardResult.getBankAccount().getAccount());
        assertEquals(0, new BigDecimal(100000.0).compareTo(cardResult.getBankAccount().getDeposit()));
        assertEquals(0, new BigDecimal(0.0).compareTo(cardResult.getBankAccount().getCredit()));

        TransactionDAOImpl.getInstance().getMoney(card, new BigDecimal(110000.0));

        Collection<Card> cards1 = CardDAOImpl.getInstance().getAll();
        Card cardResult1 = cards1.stream()
                .filter(c -> c.getNumber().equals("1234567891011122")).findFirst().get();

        assertEquals("1234567891011122", cardResult1.getNumber());
        assertEquals("Vasja", cardResult1.getCustomer().getName());
        assertEquals("Pjatochkin", cardResult1.getCustomer().getSurname());
        assertEquals("380501112233", cardResult1.getCustomer().getPhone());
        assertEquals("968574124", cardResult1.getBankAccount().getAccount());
        assertEquals(0, new BigDecimal(0.0).compareTo(cardResult1.getBankAccount().getDeposit()));
        assertEquals(0, new BigDecimal(10000.0).compareTo(cardResult1.getBankAccount().getCredit()));

        TransactionDAOImpl.getInstance().addMoney(card, new BigDecimal(11000.0));

        Collection<Card> cards2 = CardDAOImpl.getInstance().getAll();
        Card cardResult2 = cards2.stream()
                .filter(c -> c.getNumber().equals("1234567891011122")).findFirst().get();

        assertEquals("1234567891011122", cardResult2.getNumber());
        assertEquals("Vasja", cardResult2.getCustomer().getName());
        assertEquals("Pjatochkin", cardResult2.getCustomer().getSurname());
        assertEquals("380501112233", cardResult2.getCustomer().getPhone());
        assertEquals("968574124", cardResult2.getBankAccount().getAccount());
        assertEquals(0, new BigDecimal(1000.0).compareTo(cardResult2.getBankAccount().getDeposit()));
        assertEquals(0, new BigDecimal(0.0).compareTo(cardResult2.getBankAccount().getCredit()));



    }
}
