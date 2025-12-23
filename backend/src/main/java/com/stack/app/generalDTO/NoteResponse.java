package com.stack.app.generalDTO;

public record NoteResponse(String title, Long user_id, Currency currency, Integer limit_on_day, Integer budget, Integer totalSpends) {
}
