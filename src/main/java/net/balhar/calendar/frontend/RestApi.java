package net.balhar.calendar.frontend;

import net.balhar.calendar.frontend.annotation.AnnotationProxy;
import net.balhar.jsonapi.Document;
import net.balhar.jsonapi.Identifiable;
import net.balhar.jsonapi.hash.HashDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Rest implementation for Api, which should be able to work on top of interfaces specifying DSL for this domain.
 */
@Controller
@RequestMapping(value = "/events")
public class RestApi<ITEM extends Identifiable> implements Api<ITEM> {
    private String basePath;
    // This module will have to understand the apis. Use annotations on methods to provide necessary info.
    private AnnotationProxy proxy;

    @Autowired
    public RestApi(String basePath, AnnotationProxy proxy) {
        this.basePath = basePath;
        this.proxy = proxy;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Document> collection(Configuration configuration) throws InvocationTargetException,
            IllegalAccessException {
        Collection<ITEM> retrieved = (Collection<ITEM>) proxy.collection(configuration);

        Document response = new HashDocument(retrieved);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Document> create(ITEM toCreate) {
        ITEM created = (ITEM) proxy.create(toCreate);

        Document response = new HashDocument(created);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<Document> single(
            @PathVariable String uuid
    ) {
        assertResourceExists(uuid);

        ITEM retrieved = (ITEM) proxy.single(uuid);

        Document response = new HashDocument(retrieved);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
    public ResponseEntity<Document> update(String uuid, ITEM updatedData) {
        assertResourceExists(uuid);

        ITEM updated = (ITEM) proxy.update(uuid, updatedData);

        Document response = new HashDocument(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    public ResponseEntity delete(String uuid) {
        proxy.delete(uuid);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private void assertResourceExists(String uuid) {
        if (proxy.single(uuid) == null) {
            throw new ResourceNotFound();
        }
    }
}
