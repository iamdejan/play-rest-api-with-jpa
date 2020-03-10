package repositories.impl;

import factories.AccountFactory;
import models.Account;
import play.db.jpa.JPAApi;
import repositories.AccountRepository;
import repositories.DatabaseExecutionContext;

import javax.inject.Inject;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JPAAccountRepository implements AccountRepository {
    private final JPAApi jpaApi;
    private final DatabaseExecutionContext databaseExecutionContext;

    @Inject
    public JPAAccountRepository(JPAApi jpaApi, DatabaseExecutionContext databaseExecutionContext) {
        this.jpaApi = jpaApi;
        this.databaseExecutionContext = databaseExecutionContext;
    }

    @Override
    public CompletionStage<Account> createAccount() {
        Account account = AccountFactory.createAccount();
        return supplyAsync(() ->
            jpaApi.withTransaction(entityManager -> {
                entityManager.persist(account);
                return account;
            }), databaseExecutionContext);
    }

    @Override
    public CompletionStage<Account> rechargeAccount(int accountId, long rechargeAmount) {
        return null;
    }

    @Override
    public CompletionStage<Set<Account>> getAllAccounts() {
        return null;
    }
}
