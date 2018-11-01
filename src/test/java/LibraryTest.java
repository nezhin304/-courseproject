import com.bank.dao.BankAccountDAOImpl;
import com.bank.dao.CardDAOImpl;
import com.bank.dao.TransactionDAO;
import com.bank.dao.TransactionDAOImpl;
import com.bank.entity.BankAccount;
import com.bank.entity.Card;
import com.bank.entity.Customer;
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

//        Customer customer = new Customer();
//        customer.setName("Vasja");
//        customer.setSurname("Pjatochkin");
//        customer.setPhone("380501112233");
//        customer.setCard(card);
//        card.setCustomer(customer);
//
//        CardDAOImpl.getInstance().save(card);

        BigDecimal deposit = BankAccountDAOImpl.getInstance().getBalance(card).getDeposit();
        BigDecimal credit = BankAccountDAOImpl.getInstance().getBalance(card).getCredit();

        TransactionDAOImpl.getInstance().addMoney(card, new BigDecimal(1000));
//        TransactionDAOImpl.getInstance().getMoney(card, new BigDecimal(5000));


        BigDecimal depositNew = BankAccountDAOImpl.getInstance().getBalance(card).getDeposit();
        BigDecimal creditNew = BankAccountDAOImpl.getInstance().getBalance(card).getCredit();

        log.info(deposit.toString() + " " + credit);
        log.info(depositNew.toString() + " " + creditNew);

    }
}
