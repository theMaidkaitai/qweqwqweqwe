package com.stack.app.models.user.DTO;

import com.stack.app.generalDTO.RolesDTO;

public record UserLoginDTO(String nick ,String email , String password, RolesDTO rolesDTO) {

//    public UserDTO(String nick, String password, String email, RolesDTO rolesDTO) {
//        this(null, nick , password, email, rolesDTO);
//    }



}
