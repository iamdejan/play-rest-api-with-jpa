package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.AccountRepository;
import utility.ResponseUtility;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class AccountController extends Controller {
    private final AccountRepository accountRepository;
    private final HttpExecutionContext executionContext;

    @Inject
    public AccountController(AccountRepository accountRepository, HttpExecutionContext executionContext) {
        this.accountRepository = accountRepository;
        this.executionContext = executionContext;
    }

    public CompletionStage<Result> createAccount() {
        return accountRepository.createAccount().thenApplyAsync(account -> {
            JsonNode jsonNode = Json.toJson(account);
            return created(ResponseUtility.createResponse(jsonNode, true));
        }, executionContext.current());
    }
}
