package com.ziplinegreen.vault.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User extends AuditModel{
    @Id
    @GeneratedValue
    private Long id;

//    @NotNull
    @Column(name ="USER_USERNAME")
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
