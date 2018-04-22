package pl.applicationserver.scorefilesmanager.dto;

import java.util.List;

public interface UserInfoDTO {
    Long getId();
    String getName();
    String getSurname();
    String getEmail();
    List<SimpleRoleDTO> getRoles();
}
