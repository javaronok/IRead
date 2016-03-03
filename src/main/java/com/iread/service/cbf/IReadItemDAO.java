package com.iread.service.cbf;

import it.unimi.dsi.fastutil.longs.LongSet;
import org.grouplens.lenskit.collections.LongUtils;
import org.lenskit.data.dao.ItemDAO;

import java.util.Collection;

/**
 * Created by Angelina on 28.02.2016.
 */
public class IReadItemDAO implements ItemDAO {
    private final Collection<Long> items;

    public IReadItemDAO(Collection<Long> items) {
        this.items = items;
    }

    @Override
    public LongSet getItemIds() {
        return LongUtils.packedSet(items);
    }
}
