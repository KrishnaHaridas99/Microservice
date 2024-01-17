package com.eazybytes.cards.service.impl;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardsMapper;
import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    @Autowired
    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> cards = cardsRepository.findByMobileNumber(mobileNumber);
        if(cards.isPresent())
        {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber : "+mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    @Override
    public CardsDto fetchCardDetails(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).
                orElseThrow(() -> new ResourceNotFoundException("Cards","mobileNumber",mobileNumber));

        return CardsMapper.mapToCardsDto(cards, new CardsDto());
    }

    @Override
    public boolean updateCardDetails(CardsDto cardsDto) {
        boolean isUpdated = false;
        Cards cards = cardsRepository.findByMobileNumber(cardsDto.getMobileNumber()).
                orElseThrow(() -> new ResourceNotFoundException("Cards","Mobile Number",cardsDto.getMobileNumber()));
        if(cards != null)
        {
            Cards cardWithUpdatedData = CardsMapper.mapToCards(cardsDto , cards);
            cardsRepository.save(cardWithUpdatedData);
            isUpdated = true;
        }
        return isUpdated;
    }

    public Cards createNewCard(String mobileNumber)
    {
        Cards newCard = new Cards();
        long cardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(cardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public boolean deleteCardDetails(String mobileNumber) {
        boolean isDeleted = false;
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).
                orElseThrow(() -> new ResourceNotFoundException("Cards","Mobile Number",mobileNumber));
        if(cards != null)
        {
            cardsRepository.deleteById(cards.getCardId());
            isDeleted = true;
        }
        return isDeleted;
    }
}
