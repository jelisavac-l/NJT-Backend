package com.jelisavacl.njt.dto;

import com.jelisavacl.njt.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;

    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail()
        );
    }

}