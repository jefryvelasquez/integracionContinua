package com.glovoapp.backender;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = API.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class APIIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return welcome message from the configuration properties")
    public void shouldReturnWelcomeMessage() throws Exception {
        this.mockMvc.perform(get("/")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Hello from backend!"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return ALL orders available")
    public void shouldReturnAllOrders() throws Exception {
        this.mockMvc.perform(get("/orders")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(9)));
    }

    @Test
    @DisplayName("Should return ALL orders grouped by 500m slots, prioritizing for VIP, FOOD and DISTANCE when the " +
            "courier is using MOTORCYCLE and has GLOVO BOX")
    public void integrationScenario1() throws Exception {
        this.mockMvc.perform(get("/orders/courier-1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(9)))
                .andExpect(jsonPath("$.[0].id", is("order-2")))
                .andExpect(jsonPath("$.[0].description", equalToIgnoringCase("Barbecue")))

                .andExpect(jsonPath("$.[1].id", is("order-6")))
                .andExpect(jsonPath("$.[1].description", equalToIgnoringCase("suitcase")))

                .andExpect(jsonPath("$.[2].id", is("order-1")))
                .andExpect(jsonPath("$.[2].description", equalToIgnoringCase("I want a pizza cut into very small slices")))

                .andExpect(jsonPath("$.[3].id", is("order-4")))
                .andExpect(jsonPath("$.[3].description", equalToIgnoringCase("flamingo")))

                .andExpect(jsonPath("$.[4].id", is("order-3")))
                .andExpect(jsonPath("$.[4].description", equalToIgnoringCase("Keys")))

                .andExpect(jsonPath("$.[5].id", is("order-7")))
                .andExpect(jsonPath("$.[5].description", equalToIgnoringCase("burrito")))

                .andExpect(jsonPath("$.[6].id", is("order-8")))
                .andExpect(jsonPath("$.[6].description", equalToIgnoringCase("cake")))

                .andExpect(jsonPath("$.[7].id", is("order-5")))
                .andExpect(jsonPath("$.[7].description", equalToIgnoringCase("playstation")))

                .andExpect(jsonPath("$.[8].id", is("order-9")))
                .andExpect(jsonPath("$.[8].description", equalToIgnoringCase("important documents")));
    }

    @Test
    @DisplayName("Should return orders grouped by 500m slots, prioritizing for VIP, FOOD, DISTANCE, filtering orders " +
            "containing the words CAKE, PIZZA, FLAMINGO and also orders far from 5km when the courier is using BICYCLE " +
            "and doesn't have GLOVO BOX")
    public void integrationScenario2() throws Exception {
        this.mockMvc.perform(get("/orders/courier-2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$.[0].id", is("order-2")))
                .andExpect(jsonPath("$.[0].description", equalToIgnoringCase("Barbecue")))

                .andExpect(jsonPath("$.[1].id", is("order-6")))
                .andExpect(jsonPath("$.[1].description", equalToIgnoringCase("suitcase")))

                .andExpect(jsonPath("$.[2].id", is("order-3")))
                .andExpect(jsonPath("$.[2].description", equalToIgnoringCase("Keys")))

                .andExpect(jsonPath("$.[3].id", is("order-7")))
                .andExpect(jsonPath("$.[3].description", equalToIgnoringCase("burrito")))

                .andExpect(jsonPath("$.[4].id", is("order-5")))
                .andExpect(jsonPath("$.[4].description", equalToIgnoringCase("playstation")));
    }

    @Test
    @DisplayName("Should return orders grouped by 500m slots, prioritizing for VIP, FOOD, DISTANCE, filtering orders " +
            "containing the words CAKE, PIZZA, FLAMINGO when the courier is using MOTORCYCLE and doesn't have GLOVO BOX")
    public void integrationScenario3() throws Exception {
        this.mockMvc.perform(get("/orders/courier-4")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(6)))
                .andExpect(jsonPath("$.[0].id", is("order-2")))
                .andExpect(jsonPath("$.[0].description", equalToIgnoringCase("Barbecue")))

                .andExpect(jsonPath("$.[1].id", is("order-6")))
                .andExpect(jsonPath("$.[1].description", equalToIgnoringCase("suitcase")))

                .andExpect(jsonPath("$.[2].id", is("order-3")))
                .andExpect(jsonPath("$.[2].description", equalToIgnoringCase("Keys")))

                .andExpect(jsonPath("$.[3].id", is("order-7")))
                .andExpect(jsonPath("$.[3].description", equalToIgnoringCase("burrito")))

                .andExpect(jsonPath("$.[4].id", is("order-5")))
                .andExpect(jsonPath("$.[4].description", equalToIgnoringCase("playstation")))

                .andExpect(jsonPath("$.[5].id", is("order-9")))
                .andExpect(jsonPath("$.[5].description", equalToIgnoringCase("important documents")));
    }

    @Test
    @DisplayName("Should return HTTP 404 when courier doesn't exist")
    public void integrationScenario4() throws Exception {
        this.mockMvc.perform(get("/orders/courier-9")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
