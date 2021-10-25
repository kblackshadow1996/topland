package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "directus_users")
public class DirectusUsers extends UuidEntity {

    private String email;

    private String password;

    @OneToOne
    @JoinColumn(name = "role")
    private DirectusRoles role;
}