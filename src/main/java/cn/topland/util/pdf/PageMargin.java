package cn.topland.util.pdf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 页边距，单位mm
 */
@Setter
@Getter
@NoArgsConstructor
public class PageMargin {

    private Double top;

    private Double bottom;

    private Double left;

    private Double right;

    public PageMargin(Double top, Double bottom, Double left, Double right) {

        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }
}