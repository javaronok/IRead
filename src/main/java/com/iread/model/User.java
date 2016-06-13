package com.iread.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "i_user")
public class User implements Serializable, UserDetails {

    @Id
    @SequenceGenerator(name = "PKIUser", sequenceName = "SEQ_USER", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PKIUser")
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "USERNAME", length = 15, nullable = false)
    private String username;

    @Column(name = "PASSWORD", length = 100)
    private String password;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "enabled", columnDefinition = "smallint", length = 1)
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "ID")})
    private Set<SysRole> roles;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @JsonIgnore
    public boolean isAccountNonExpired() {
        return BooleanUtils.toBoolean(getEnabled());
    }

    @JsonIgnore
    public boolean isAccountNonLocked() {
        return BooleanUtils.toBoolean(getEnabled());
    }

    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return BooleanUtils.toBoolean(getEnabled());
    }

    public boolean isEnabled() {
        return BooleanUtils.toBoolean(getEnabled());
    }

    @Override
    public String toString() {
        return "User[name=\""+getUsername()+"\"]";
    }
}
