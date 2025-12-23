package com.stack.app.generalDTO;

public record AuthResponseTokenDTO(String token, Long id, String nick,  String email, RolesDTO roles) {
}
