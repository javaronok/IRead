package com.iread.service.cbf;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

import java.util.*;

/**
 * Created by Dmitriy on 28.02.2016.
 */
public class IReadItemTagDAO extends IReadItemDAO implements ItemTagDAO {
    private transient volatile Long2ObjectMap<List<String>> tagCache;
    private transient volatile Set<String> vocabCache;

    public IReadItemTagDAO(Collection<Long> items, Collection<ItemTags> itags) {
        super(items);

        tagCache = new Long2ObjectOpenHashMap<List<String>>();
        ImmutableSet.Builder<String> vocabBuilder = ImmutableSet.builder();
        for (ItemTags tag : itags) {
            long mid = tag.getItemId();
            List<String> tags = tagCache.get(mid);
            if (tags == null) {
                tags = new ArrayList<String>();
                tagCache.put(mid, tags);
            }
            tags.addAll(tag.getTags());
            vocabBuilder.addAll(tag.getTags());
        }
        vocabCache = vocabBuilder.build();
    }

    public List<String> getItemTags(long item) {
        List<String> tags = tagCache.get(item);
        if (tags != null) {
            return Collections.unmodifiableList(tags);
        } else {
            return Collections.emptyList();
        }
    }

    public Set<String> getTagVocabulary() {
        return vocabCache;
    }

}
