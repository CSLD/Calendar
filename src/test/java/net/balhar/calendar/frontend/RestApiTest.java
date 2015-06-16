package net.balhar.calendar.frontend;

import net.balhar.calendar.AcceptanceTest;
import net.balhar.calendar.frontend.annotation.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

interface Config {

}

/**
 * It tests inner workings of rest api.
 */
public class RestApiTest extends AcceptanceTest {
    private Graveyard catholicGraveyard;

    @Before
    public void setUp() {
        catholicGraveyard = new Graveyard();
    }

    @Test
    public void collectionReturnsAllGraves() {

    }
}

@Name(value = "events")
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
    public Grave specificGrave() {
        return gravesInDebt.get(0);
    }

    @Create
    public Grave create(Grave toCreate) {
        toCreate.setUuid("uuid3");
        return toCreate;
    }

    public Grave update(Grave toUpdate) {
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


