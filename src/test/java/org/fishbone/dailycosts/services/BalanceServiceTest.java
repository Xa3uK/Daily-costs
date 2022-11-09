package org.fishbone.dailycosts.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.fishbone.dailycosts.models.Revenue;
import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.repositories.BalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    BalanceRepository balanceRepository;
    BalanceService balanceService;

    @BeforeEach
    void setUp() {
        balanceService = new BalanceService(balanceRepository);
    }

    @Test
    void testFindAll() {
        List<Revenue> revenueList = balanceService.findAll();

        verify(balanceRepository).findAll();

        assertNotNull(revenueList);
    }

    @Test
    void testFindRevenueByUserId() {
        int userId = 1;

        List<Revenue> revenueList = balanceService.findRevenueByUserId(userId);
        verify(balanceRepository).findByUserId(userId);

        assertNotNull(revenueList);
    }

    @Test
    void testDeleteRevenueById() {
        int id = 1;
        balanceService.deleteRevenueById(id);

        verify(balanceRepository).deleteById(id);
    }

    @Test
    void testSaveRevenue() {
        Revenue revenue = new Revenue();
        revenue.setAmount(1000.0);
        revenue.setRevenueType("Salary");
        User user = new User();

        balanceService.save(revenue, user);

        ArgumentCaptor<Revenue> revenueArgumentCaptor = ArgumentCaptor.forClass(Revenue.class);
        verify(balanceRepository).save(revenueArgumentCaptor.capture());
        Revenue capturedRevenue = revenueArgumentCaptor.getValue();

        assertThat(capturedRevenue).isEqualTo(revenue);
        assertNotNull(capturedRevenue.getDate());
        assertNotNull(capturedRevenue.getUser());
    }
}