package org.fishbone.dailycosts.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.fishbone.dailycosts.dto.PurchaseDTO;
import org.fishbone.dailycosts.models.Purchase;
import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.repositories.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;
    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        purchaseService = new PurchaseService(purchaseRepository, new ModelMapper());
    }

    @Test
    void testSavePurchaseWithAllFields() {
        PurchaseDTO purchaseDTO = new PurchaseDTO("Milk", "Food", "12.5", "2022-12-12");
        User user = new User();

        purchaseService.save(purchaseDTO, user);
        Purchase purchase = purchaseService.modelMapper.map(purchaseDTO, Purchase.class);
        purchase.setUser(user);

        ArgumentCaptor<Purchase> purchaseArgumentCaptor = ArgumentCaptor.forClass(Purchase.class);
        verify(purchaseRepository).save(purchaseArgumentCaptor.capture());

        Purchase capturedPurchase = purchaseArgumentCaptor.getValue();

        assertThat(capturedPurchase).isEqualTo(purchase);
    }

    @Test
    void testSavePurchaseAndSetCurrentDateIfDateIsEmpty() {
        PurchaseDTO purchaseDTO = new PurchaseDTO("Milk", "Food", "12.5", null);
        User user = new User();
        Purchase purchase = purchaseService.modelMapper.map(purchaseDTO, Purchase.class);
        purchase.setUser(user);
        purchase.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        purchaseService.save(purchaseDTO, user);
        ArgumentCaptor<Purchase> purchaseArgumentCaptor = ArgumentCaptor.forClass(Purchase.class);
        verify(purchaseRepository).save(purchaseArgumentCaptor.capture());

        Purchase capturedPurchase = purchaseArgumentCaptor.getValue();

        assertThat(capturedPurchase).isEqualTo(purchase);
        assertNotNull(capturedPurchase.getDate());
    }

    @Test
    void testDeletePurchaseById() {
        int id = 5;

        purchaseService.deletePurchaseById(id);

        verify(purchaseRepository).deleteById(id);
    }

    @Test
    void testFindPurchasesByUserId() {
        int id = 5;

       List<Purchase> purchaseList = purchaseService.findPurchaseByUserId(id);

        verify(purchaseRepository).findByUserId(id);

        assertNotNull(purchaseList);
    }

    @Test
    void testFindPurchaseByFilterWithCategory() throws ParseException {
        String from = "2022-12-12";
        String to = "2022-12-13";
        String category = "Food";
        int id = 1;

        Date fromDate = new SimpleDateFormat("yy-MM-dd").parse(from);
        Date toDate = new SimpleDateFormat("yy-MM-dd").parse(to);

        purchaseService.findPurchaseByFilter(from, to, category, id);
        verify(purchaseRepository)
            .findAllByDateBetweenAndProductCategoryContainsAndUserId(fromDate, toDate, category, id);
    }

    @Test
    void testFindPurchaseByFilterIfCategoryIsEmpty() throws ParseException {
        String from = "2022-12-12";
        String to = "2022-12-13";
        String defaultCategory = "All";
        int id = 1;

        Date fromDate = new SimpleDateFormat("yy-MM-dd").parse(from);
        Date toDate = new SimpleDateFormat("yy-MM-dd").parse(to);

        purchaseService.findPurchaseByFilter(from, to, defaultCategory, id);
        verify(purchaseRepository)
            .findAllByDateBetweenAndUserId(fromDate, toDate, id);
    }
}