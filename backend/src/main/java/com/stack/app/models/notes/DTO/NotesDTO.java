package com.stack.app.models.notes.DTO;

import com.stack.app.generalDTO.Currency;

public record NotesDTO(String title, Long user_id, Currency currency, Integer limit_on_day, Integer budget, Integer totalSpends) {
}
