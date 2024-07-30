package com.example.postgresdemo.service.impl;

import com.example.postgresdemo.exception.InvalidLoyaltyOperationException;
import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.UserLoyalty;
import com.example.postgresdemo.repository.UserLoyaltyRepository;
import com.example.postgresdemo.service.UserLoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLoyaltyServiceImpl implements UserLoyaltyService {

    @Autowired
    private UserLoyaltyRepository userLoyaltyRepository;

    @Transactional
    @Override
    public void updateLoyaltyForSale(Long userId, boolean isFree) {
        int maxProgress = 5;

        UserLoyalty userLoyalty = userLoyaltyRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserLoyalty not found for userId " + userId));

        if (isFree && userLoyalty.getUsableFree() < 1) {
            throw new InvalidLoyaltyOperationException("No free coffees available!");
        }

        userLoyalty.setRealCoffeeCount(userLoyalty.getRealCoffeeCount() + 1);

        if (userLoyalty.getProgressBar() == maxProgress) {
            userLoyalty.setProgressBar(isFree ? 0 : 1);
        }
        else if (!isFree) userLoyalty.setProgressBar(userLoyalty.getProgressBar() + 1);

        if (isFree) {
            userLoyalty.setUsableFree(userLoyalty.getUsableFree() - 1);
            userLoyalty.setUsedFree(userLoyalty.getUsedFree() + 1);
        }
        else if (userLoyalty.getProgressBar() == maxProgress) {
            userLoyalty.setUsableFree(userLoyalty.getUsableFree() + 1);
        }

        userLoyaltyRepository.save(userLoyalty);
    }
}
