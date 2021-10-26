package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "attachment")
public class Attachment extends SimpleIdEntity {

    @OneToOne
    @JoinColumn(name = "file")
    private DirectusFiles file;

    @Transient
    private Long exception;

    @Transient
    private Long contract;

    @Transient
    private Long settlement;
}