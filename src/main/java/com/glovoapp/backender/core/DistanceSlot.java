package com.glovoapp.backender.core;

import lombok.AllArgsConstructor;
import org.springframework.util.Assert;

@AllArgsConstructor
public class DistanceSlot {

    private Double min;
    private Double max;

    // FIXME Adding <code>Assert.notNull()</code> and create test case scenario as well.
    public boolean between(Double distance) {
        Assert.notNull(distance, "distance argument is required; it must not be null");
        if (max == null) {
            max = Double.MAX_VALUE;
        }
        return distance >= min && distance < max;
    }

}
