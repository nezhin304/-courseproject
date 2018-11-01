import com.bank.dao.BankAccountDAOImpl;
import com.bank.dao.TransactionDAOImpl;
import com.bank.entity.BankAccount;
import com.bank.entity.Card;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class LibraryTest {

    org.slf4j.Logger log = LoggerFactory.getLogger(LibraryTest.class);

    @Test public void testSomeLibraryMethod() {

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccount("968574124");
        bankAccount.setDeposit(new BigDecimal(100000.000000));
        bankAccount.setCredit(new BigDecimal(0.000000));
        bankAccount.setState(true);

        Card card = new Card();
        card.setNumber("1234 5678 9101 1122");
        card.setBankAccount(bankAccount);
//
//        Customer customer = new Customer();
//        customer.setName("Vasja");
//        customer.setSurname("Pjatochkin");
//        customer.setPhone("380501112233");
//        customer.setCard(card);
//        card.setCustomer(customer);
//
//        CardDAOImpl.getInstance().save(card);

        BankAccount bankAccount1 = BankAccountDAOImpl.getInstance().getBalance(card);

        log.info(bankAccount1.getDeposit().toString());
        log.info(bankAccount1.getCredit().toString());
    }
}
