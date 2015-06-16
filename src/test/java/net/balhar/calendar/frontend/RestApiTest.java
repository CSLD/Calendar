package net.balhar.calendar.frontend;

import net.balhar.calendar.AcceptanceTest;
import net.balhar.calendar.frontend.annotation.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

interface Config {

}

/**
 * It tests inner workings of rest api.
 */
public class RestApiTest extends AcceptanceTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void collectionReturnsAllGraves() throws Exception {
        mockMvc.perform(get("/graveyards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data..uuid", containsInAnyOrder("uuid1", "uuid2")))
                .andExpect(jsonPath("$.data.type", containsInAnyOrder("Grave", "Grave")));
    }

    @Test
    public void returnSpecificExistingGrave() throws Exception {
        mockMvc.perform(get("/graveyards/uuid1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.uuid", is("uuid1")))
                .andExpect(jsonPath("$.data.type", is("Grave")));
    }

    @Test
    public void createNewGrave() throws Exception {
        mockMvc.perform(post("/graveyards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Stranger\"}")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.uuid", is("uuid3")));
    }

    @Test
    public void deleteExistingDebtForGrave() throws Exception {
        mockMvc.perform(delete("/graveyards/uuid1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateExistingDebt() throws Exception {
        mockMvc.perform(put("/grave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"updated\"}")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("updated")));
    }
}

@Name(value = "graveyards")
class Graveyard implements Provider {
    private List<Grave> gravesInDebt = new ArrayList<>();

    public Graveyard() {
        gravesInDebt.add(new Grave("uuid1", "Frank Lestrand"));
        gravesInDebt.add(new Grave("uuid2", "John Dee"));
    }

    @ResourceCollection
    public Collection<Grave> graves(Config configuration) {
        return gravesInDebt;
    }

    @Resource
    public Grave specificGrave(String uuid) {
        return gravesInDebt.get(0);
    }

    @Create
    public Grave create(Grave toCreate) {
        toCreate.setUuid("uuid3");
        return toCreate;
    }

    @Update
    public Grave update(String uuid, Grave toUpdate) {
        toUpdate.setName("updated");
        return toUpdate;
    }

    @Delete
    public void debtPayedFor(String uuid) {
    }
}

class Grave {
    private String uuid;
    private String name;

    public Grave(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public Grave() {
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }
}