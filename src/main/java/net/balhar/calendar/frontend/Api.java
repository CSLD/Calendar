package net.balhar.calendar.frontend;

import net.balhar.jsonapi.Document;
import org.springframework.http.ResponseEntity;

/**
 * It is adapter which provides functionality to expose everything the service does as a simple endpoint with resources
 * and hateoas like information defined in jsonAPI project.
 */
public interface Api<ITEM> {
    /**
     * Expose collection of items with possibility to specify, which should be retrieved. Also some options for
     * filtering
     * are possible.
     *
     * @param configuration Configuration of restrictions on retrieved items.
     * @return Json Api Document representation of the items.
     */
    ResponseEntity<Document> collection(Configuration configuration);

    /**
     * Creates passed in item. In case of user not having correct rights return 403, If the same item already exists
     * return 400. In case of validation error return 400 with additional information about validation errors.
     *
     * @param toCreate Item to be created.
     * @return Document containing created item.
     */
    ResponseEntity<Document> create(ITEM toCreate);

    /**
     * It allows updating resource with given uuid. If there is no such resource return 404, if the user doesn't have
     * rights return 404. If validation fails return 400 with additional information about why it failed.
     * This method works as a PATCH allowing you to modify any part of the resource by changing the data you send.
     *
     * @param uuid        Uuid of the item to be modified.
     * @param updatedData Data to be updated on item with given uuid.
     * @return Document containing updated item.
     */
    ResponseEntity<Document> update(String uuid, ITEM updatedData);

    /**
     * It deletes resource with given uuid. If such resource doesn't exist, return 404. If the user doesn't have rights
     * return 403. In correct case return 204.
     *
     * @param uuid Uuid of the item to be deleted.
     */
    ResponseEntity delete(String uuid);
}
