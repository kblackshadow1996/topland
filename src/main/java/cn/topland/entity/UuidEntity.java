package cn.topland.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class UuidEntity {

    @Id
    protected String id = getRandomUuid();

    protected static String getRandomUuid() {

        return UUID.randomUUID().toString();
    }

    public boolean equals(Object object) {

        if (this == object) {

            return true;
        } else if (object == null) {

            return false;
        } else if (object instanceof UuidEntity) {

            UuidEntity entity = (UuidEntity) object;
            return this.getId().equals(entity.getId());
        } else {

            return false;
        }
    }

    public int hashCode() {

        return this.getId() == null
                ? super.hashCode()
                : this.getId().hashCode();
    }
}