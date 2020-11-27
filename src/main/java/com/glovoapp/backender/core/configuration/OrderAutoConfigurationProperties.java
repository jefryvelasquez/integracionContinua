package com.glovoapp.backender.core.configuration;

import lombok.Data;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@ConfigurationProperties(prefix = "backender.order")
@Validated
@Data
public class OrderAutoConfigurationProperties {

    @NotNull
    @PositiveOrZero
    private Double furtherDistance;

    @NotEmpty
    private List<String> specialWords;

    @NotEmpty
    private List<String> sorters;

    @Valid
    private Prioritise prioritise = new Prioritise(); // TODO Check if it's instantiate is really need it.

    @Setter
    private static class Prioritise {

        @PositiveOrZero
        @NotNull
        private Double slotDistance;

        @PositiveOrZero
        @NotNull
        private Double maxDistance;
    }
}
