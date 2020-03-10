package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repositories.AccountRepository;
import utility.ResponseUtility;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

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

    public CompletionStage<Result> rechargeAccount(Http.Request request) {
        try {
            JsonNode requestBody = request.body().asJson();
            int accountId = requestBody.get("accountId").intValue();
            long rechargeAmount = requestBody.get("rechargeAmount").longValue();
            return accountRepository.rechargeAccount(accountId, rechargeAmount).thenApplyAsync(account -> {
                JsonNode jsonNode = Json.toJson(account);
                return ok(ResponseUtility.createResponse(jsonNode, true));
            }, executionContext.current());
        } catch (Exception e) {
            return supplyAsync(() -> {
                String errorMessage = e.getLocalizedMessage();
                return internalServerError(ResponseUtility.createResponse(errorMessage, false));
            }, executionContext.current());
        }
    }

    public CompletionStage<Result> getAllAccounts() {
        try {
            return accountRepository.getAllAccounts().thenApplyAsync(accounts -> {
                JsonNode jsonNode = Json.toJson(accounts);
                return ok(ResponseUtility.createResponse(jsonNode, true));
            });
        } catch (Exception e) {
            return supplyAsync(() -> {
                return internalServerError(ResponseUtility.createResponse(null, false));
            });
        }
    }
}
