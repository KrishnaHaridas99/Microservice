package com.eazybytes.cards.service;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;

public interface ICardsService {

    void createCard(String mobileNumber);

    CardsDto fetchCardDetails(String mobileNumber);

    boolean updateCardDetails(CardsDto cardsDto);

    boolean deleteCardDetails(String mobileNumber);
}
