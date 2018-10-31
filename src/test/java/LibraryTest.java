import com.bank.dao.CardDAOImpl;
import com.bank.entity.BankAccount;
import com.bank.entity.Card;
import com.bank.entity.Customer;
import org.junit.Test;

import java.math.BigDecimal;

public class LibraryTest {
    @Test public void testSomeLibraryMethod() {

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccount("968574123");
        bankAccount.setDeposit(new BigDecimal(100000.000000));
        bankAccount.setCredit(new BigDecimal(0.000000));

        Card card = new Card();
        card.setNumber("1234 5678 9101 1121");
        card.setBankAccount(bankAccount);

        Customer customer = new Customer();
        customer.setName("Vasja");
        customer.setSurname("Pjatochkin");
        customer.setPhone("380501112233");
        customer.setCard(card);
        card.setCustomer(customer);

        CardDAOImpl.getInstance().save(card);
    }
}
