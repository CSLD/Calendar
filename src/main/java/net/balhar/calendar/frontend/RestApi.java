package net.balhar.calendar.frontend;

import net.balhar.jsonapi.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Rest implementation for Api, which should be able to work on top of interfaces specifying DSL for this domain.
 */
public class RestApi<ITEM> implements Api<ITEM> {
    private Provider serviceProvider;
    private String basePath;
    // This module will have to understand the apis. Use annotations on methods to provide necessary info.

    public RestApi(Provider serviceProvider, String basePath) {
        this.serviceProvider = serviceProvider;
        this.basePath = basePath;
        // Dynamically specify the paths based on the metaData, probably will seriously have to do this.
    }

    @Override
    public ResponseEntity<Document> collection(Configuration configuration) {
        // It looks for ResourceCollection annotation on service provider and calls such a function expecting a result.
        return null;
    }

    @Override
    public ResponseEntity<Document> create(ITEM toCreate) {
        // It looks for Create annotation on service provider and calls such a method expecting a result.
        return null;
    }

    @Override
    public ResponseEntity<Document> update(String uuid, ITEM updatedData) {
        // It is happening on one entity. Therefore it is necessary to be able to retrieve the entity and based on it
        // update the entity and persist it correctly.
        return null;
    }

    @Override
    public ResponseEntity delete(String uuid) {
        //It looks for Delete annotation and call such method with passed in parameter.
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
