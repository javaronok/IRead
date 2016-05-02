package com.iread.service.cbf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Angelina on 28.02.2016.
 */
public class ItemTags {
    Long itemId;
    List<String> tags = new ArrayList<String>();

    public ItemTags(Long itemId, List<String> tags) {
        this.itemId = itemId;
        this.tags = tags;
    }

    public Long getItemId() {
        return itemId;
    }

    public List<String> getTags() {
        return tags;
    }

    public static ItemTags createItemTags(Long item, String... tags) {
        return createItemTags(item, Arrays.asList(tags));
    }

    public static ItemTags createItemTags(Long item, List<String> tags) {
        return new ItemTags(item, tags);
    }
}
