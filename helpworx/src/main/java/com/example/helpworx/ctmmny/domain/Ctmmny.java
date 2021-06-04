package com.example.helpworx.ctmmny.domain;

import com.example.helpworx.users.domain.Users;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ctmmny {

    @Id
    @Column(name = "CTMMNY_CD")
    private String id;

    @Column(name = "CTMMNY_NM")
    private String ctmmnyNm;

    @Column(name="CTMMNY_DSCR")
    private String ctmmnyDscr;

    @NotNull
    @Column(name = "IS_USED")
    private Boolean isUsed;

    @Column(name = "CONT_BEGIN_DATE")
    private LocalDateTime contBeginDate;

    @Column(name = "CONT_END_DATE")
    private LocalDateTime contEndDate;

    @OneToMany(mappedBy = "ctmmny", fetch = FetchType.LAZY)
    private List<CtmmnySys> ctmmnySysList;

    @OneToMany(mappedBy = "ctmmny",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Users> usersList;

}
