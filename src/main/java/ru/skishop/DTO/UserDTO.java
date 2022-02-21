package ru.skishop.DTO;

import java.util.List;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Long> listRoleId;

    public List<Long> getListRoleId() {
        return listRoleId;
    }

    public void setListRoleId(List<Long> listRoleId) {
        this.listRoleId = listRoleId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
}