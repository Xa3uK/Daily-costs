package org.fishbone.dailycosts.services;

import static java.util.Objects.isNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.fishbone.dailycosts.models.Revenue;
import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.repositories.BalanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BalanceService {

    BalanceRepository balanceRepository;

    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public List<Revenue> findAll(){
        return balanceRepository.findAll();
    }

    public List<Revenue> findRevenueByUserId(int userId){
        return balanceRepository.findByUserId(userId);
    }

    public List<Revenue> findRevenueByUserIdSortedByDate(int userId){
        return balanceRepository.findAllByUserIdOrderByDateDesc(userId);
    }

    public void deleteRevenueById(int id){
        balanceRepository.deleteById(id);
    }

    @Transactional
    public void save(Revenue revenue, User user){
        revenue.setUser(user);
        balanceRepository.save(revenue);
    }
}
