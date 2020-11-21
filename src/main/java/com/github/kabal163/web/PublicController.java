package com.github.kabal163.web;

import com.github.kabal163.balance.BalanceSnapshot;
import com.github.kabal163.transfer.ImmutableTransfer;
import com.github.kabal163.wallet.ImmutableWallet;
import com.github.kabal163.wallet.WalletCommandService;
import com.github.kabal163.balance.BalanceQueryService;
import com.github.kabal163.wallet.DepositCommand;
import com.github.kabal163.web.dto.BalanceHistoryRequest;
import com.github.kabal163.web.dto.DepositRequest;
import com.github.kabal163.web.dto.DepositResponse;
import com.github.kabal163.web.dto.WebResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static com.github.kabal163.web.PublicController.ROOT_PATH;

@Slf4j
@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
@ApiOperation(value = "Controller provides ability to receive Bitcoins and see income history")
public class PublicController {

    public static final String ROOT_PATH = "/public/wallet";

    private final WalletCommandService walletCommandService;
    private final BalanceQueryService balanceQueryService;

    @PostMapping("/deposit")
    public WebResponse<DepositResponse> deposit(@RequestBody @Valid DepositRequest request) {
        DepositCommand command = DepositCommand.builder()
                .transferTimestamp(request.getDateTime())
                .amount(request.getAmount())
                .build();
        ImmutableTransfer result = walletCommandService.deposit(command);
        return WebResponse.succeeded(DepositResponse.newInstance(result));
    }

    @GetMapping("/history")
    public List<BalanceSnapshot> getWalletHistory(@RequestBody @Valid BalanceHistoryRequest request) {
        return balanceQueryService.getBalanceHistory(request.getStart(), request.getEnd());
    }
}
