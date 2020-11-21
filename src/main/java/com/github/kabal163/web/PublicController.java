package com.github.kabal163.web;

import com.github.kabal163.entity.immutable.ImmutableTransfer;
import com.github.kabal163.service.WalletCommandService;
import com.github.kabal163.service.command.DepositCommand;
import com.github.kabal163.web.dto.DepositRequest;
import com.github.kabal163.web.dto.DepositResponse;
import com.github.kabal163.web.dto.WebResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.github.kabal163.web.PublicController.ROOT_PATH;

@Slf4j
@Validated
@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
@ApiOperation(value = "Controller provides ability to receive Bitcoins and see income history")
public class PublicController {

    public static final String ROOT_PATH = "/public/wallet";

    private final WalletCommandService walletCommandService;

    @PostMapping("/deposit")
    public WebResponse<DepositResponse> deposit(@RequestBody @Valid DepositRequest request) {
        DepositCommand command = DepositCommand.builder()
                .transferTimestamp(request.getDateTime())
                .amount(request.getAmount())
                .build();
        ImmutableTransfer result = walletCommandService.deposit(command);
        return WebResponse.succeeded(DepositResponse.newInstance(result));
    }
}
