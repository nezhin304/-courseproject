import com.bank.dao.BankAccountDAOImpl;
import com.bank.dao.CustomerDAOImpl;
import com.bank.dao.TransactionDAOImpl;
import com.bank.entity.BankAccount;
import com.bank.entity.Card;
import com.bank.entity.Customer;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class CardDAOTest {

    @Test
    public void insertAndSelectEntityTest() {

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccount("968574129"); //968574124
        bankAccount.setDeposit(new BigDecimal(100000.0));
        bankAccount.setCredit(new BigDecimal(0.0));
        bankAccount.setState(true);

        Card card = new Card();
        card.setNumber("1234567891011128"); //1234567891011122
        card.setBankAccount(bankAccount);
        bankAccount.setCard(card);

        Customer customer = new Customer();
        customer.setName("Vasja");
        customer.setSurname("Pjatochkin");
        customer.setPhone("380501112233");
        customer.setCard(card);
        card.setCustomer(customer);

//        CustomerDAOImpl.getInstance().deleteCustomer(customer);
        BankAccountDAOImpl.getInstance().save(bankAccount);

//        Collection<Customer> customers = CustomerDAOImpl.getInstance().getAll();
//        Customer customerResult = customers.stream()
//                .filter(c -> c.getName().equals("Vasja")).findFirst().get();
//
//        assertEquals("Vasja", customerResult.getName());
//        assertEquals("Pjatochkin", customerResult.getSurname());
//        assertEquals("380501112233", customerResult.getPhone());
//
//        Card cardResult = customerResult.getCards().stream()
//                .filter(c -> c.getNumber().equals("1234567891011122")).findFirst().get();
//        assertEquals("1234567891011122", cardResult.getNumber());
//        assertEquals("968574124", cardResult.getBankAccount().getAccount());
//        assertEquals(0, new BigDecimal(100000.0).compareTo(cardResult.getBankAccount().getDeposit()));
//        assertEquals(0, new BigDecimal(0.0).compareTo(cardResult.getBankAccount().getCredit()));
//        assertEquals(Boolean.TRUE, cardResult.getBankAccount().getState());
//
//        TransactionDAOImpl.getInstance().getMoney(card, new BigDecimal(110000.0));
//
//        BankAccount bankAccount1 = BankAccountDAOImpl.getInstance().getBankAccount(card);
//
//        assertEquals("968574124", bankAccount1.getAccount());
//        assertEquals(0, new BigDecimal(0.0).compareTo(bankAccount1.getDeposit()));
//        assertEquals(0, new BigDecimal(10000.0).compareTo(bankAccount1.getCredit()));
//
//        TransactionDAOImpl.getInstance().addMoney(card, new BigDecimal(11000.0));
//
//        BankAccount bankAccount2 = BankAccountDAOImpl.getInstance().getBankAccount(card);
//
//        assertEquals("968574124", bankAccount2.getAccount());
//        assertEquals(0, new BigDecimal(1000.0).compareTo(bankAccount2.getDeposit()));
//        assertEquals(0, new BigDecimal(0.0).compareTo(bankAccount2.getCredit()));

    }
}
