package repositories;

import com.google.inject.ImplementedBy;
import models.Account;
import repositories.impl.JPAAccountRepository;

import java.util.Set;
import java.util.concurrent.CompletionStage;

@ImplementedBy(JPAAccountRepository.class)
public interface AccountRepository {
    CompletionStage<Account> createAccount();

    CompletionStage<Account> rechargeAccount(int accountId, long rechargeAmount);

    CompletionStage<Set<Account>> getAllAccounts();
}
