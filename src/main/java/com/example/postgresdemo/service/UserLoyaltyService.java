package com.example.postgresdemo.service;

import com.example.postgresdemo.exception.InvalidLoyaltyOperationException;

public interface UserLoyaltyService {
    void updateLoyaltyForSale(Long userId, boolean isFree) throws InvalidLoyaltyOperationException;
}
