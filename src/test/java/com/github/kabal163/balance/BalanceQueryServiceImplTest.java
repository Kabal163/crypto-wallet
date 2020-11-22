package com.github.kabal163.balance;

import com.github.kabal163.MotherObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BalanceQueryServiceImplTest {

    @Mock BalanceRepository repositoryMock;

    BalanceQueryServiceImpl balanceQueryService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        when(repositoryMock.getAggregatedHistory(any())).thenReturn(emptyList());

        balanceQueryService = new BalanceQueryServiceImpl(repositoryMock);
    }

    @Test
    void givenBalanceHistorySearchParamsIsNull_whenCallGetBalanceHistory_thenThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> balanceQueryService.getBalanceHistory(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null");
    }

    @Test
    void whenCallGetBalanceHistory_thenBalanceRepository_getAggregatedHistoryMethodMustBeCalledOnce() {
        BalanceHistorySearchParams params = MotherObject.getBalanceHistory().any().please();
        balanceQueryService.getBalanceHistory(params);

        verify(repositoryMock, times(1)).getAggregatedHistory(any());
    }

    @Test
    void whenCallGetBalanceHistory_thenBalanceHistorySearchParamsMustBePassedToBalanceRepository_getAggregatedHistoryMethod() {
        BalanceHistorySearchParams expected = MotherObject.getBalanceHistory().any().please();
        ArgumentCaptor<BalanceHistorySearchParams> captor = ArgumentCaptor.forClass(BalanceHistorySearchParams.class);
        when(repositoryMock.getAggregatedHistory(captor.capture())).thenReturn(emptyList());

        balanceQueryService.getBalanceHistory(expected);

        BalanceHistorySearchParams actual = captor.getValue();
        assertThat(actual).isEqualTo(expected);
    }
}