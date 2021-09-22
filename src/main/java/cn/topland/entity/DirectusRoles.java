package cn.topland.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * directus角色名称
 */
@Setter
@Getter
@Entity
@Table(name = "directus_roles")
public class DirectusRoles extends UuidEntity {

    private String name;
}