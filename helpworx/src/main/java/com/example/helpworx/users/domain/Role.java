package com.example.helpworx.users.domain;

import lombok.Getter;
import javax.persistence.*;

@Entity
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_ID")
    private Long id;

    @Column(name = "ROLE_NM")
    private String roleNm;

//    @OneToMany(mappedBy = "role")
//    private List<UserRoleMap> members = new ArrayList<>();

}
