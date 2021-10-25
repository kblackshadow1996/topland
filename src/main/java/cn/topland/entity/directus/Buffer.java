package cn.topland.entity.directus;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class Buffer implements Serializable {

    private String type = "buffer";

    private List<Byte> data;
}