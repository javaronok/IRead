package com.iread.model;

import javax.persistence.*;

@Entity
@Table(name = "sys_role")
public class SysRole {

    @Id
    @SequenceGenerator(name = "PKSysRole", sequenceName = "SYS_ROLE", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PKSysRole")
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "ROLE_NAME", length = 32, nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
