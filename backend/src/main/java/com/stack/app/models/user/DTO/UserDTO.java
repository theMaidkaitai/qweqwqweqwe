package com.stack.app.models.user.DTO;

import com.stack.app.generalDTO.RolesDTO;

public record UserDTO(Long id,String nick, String password , String email, RolesDTO rolesDTO) {

//    public UserDTO(String nick, String password, String email, RolesDTO rolesDTO) {
//        this(null, nick , password, email, rolesDTO);
//    }



}
