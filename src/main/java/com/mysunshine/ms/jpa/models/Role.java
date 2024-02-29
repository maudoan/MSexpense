package com.mysunshine.ms.jpa.models;

import com.mysunshine.ms.constant.enums.ERole;
import com.mysunshine.ms.jpa.IdEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends IdEntity {
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}