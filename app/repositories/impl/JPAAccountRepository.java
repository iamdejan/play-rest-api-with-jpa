package repositories.impl;

import factories.AccountFactory;
import models.Account;
import play.db.jpa.JPAApi;
import repositories.AccountRepository;
import repositories.DatabaseExecutionContext;

import javax.inject.Inject;
import java.util.List;
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
        return supplyAsync(() ->
            jpaApi.withTransaction(entityManager -> {
                Account account = AccountFactory.createAccount();
                entityManager.persist(account);
                return account;
            }), databaseExecutionContext);
    }

    @Override
    public CompletionStage<Account> rechargeAccount(int accountId, long rechargeAmount) {
        return supplyAsync(() ->
            jpaApi.withTransaction(entityManager -> {
                String query = String.format("update Account set balance = balance + %d where id = %d", rechargeAmount, accountId);
                System.out.println("Recharge account query string: " + query);
                entityManager.createQuery(query).executeUpdate();
                Account account = entityManager.createQuery("select a from Account a where a.id = " + accountId, Account.class).getSingleResult();
                return account;
            }), databaseExecutionContext);
    }

    @Override
    public CompletionStage<List<Account>> getAllAccounts() {
        return supplyAsync(() ->
            jpaApi.withTransaction(entityManager -> {
                String query = String.format("select a from Account a");
                System.out.println("Get all accounts query string: " + query);
                return entityManager.createQuery(query, Account.class).getResultList();
            }), databaseExecutionContext);
    }
}
