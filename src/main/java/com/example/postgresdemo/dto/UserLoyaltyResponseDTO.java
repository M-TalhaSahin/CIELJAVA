package com.example.postgresdemo.dto;

import com.example.postgresdemo.model.CoffeeSale;
import com.example.postgresdemo.model.UserLoyalty;

public class UserLoyaltyResponseDTO {
    private int progressBar;
    private int realCoffeeCount;
    private int usableFree;
    private int usedFree;

    public static UserLoyaltyResponseDTO mapToResponseDTO(UserLoyalty userLoyalty) {
        UserLoyaltyResponseDTO responseDTO = new UserLoyaltyResponseDTO();
        responseDTO.setProgressBar(userLoyalty.getProgressBar());
        responseDTO.setRealCoffeeCount(userLoyalty.getRealCoffeeCount());
        responseDTO.setUsableFree(userLoyalty.getUsableFree());
        responseDTO.setUsedFree(userLoyalty.getUsedFree());
        return responseDTO;
    }

    public int getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(int progressBar) {
        this.progressBar = progressBar;
    }

    public int getRealCoffeeCount() {
        return realCoffeeCount;
    }

    public void setRealCoffeeCount(int realCoffeeCount) {
        this.realCoffeeCount = realCoffeeCount;
    }

    public int getUsableFree() {
        return usableFree;
    }

    public void setUsableFree(int usableFree) {
        this.usableFree = usableFree;
    }

    public int getUsedFree() {
        return usedFree;
    }

    public void setUsedFree(int usedFree) {
        this.usedFree = usedFree;
    }

}
