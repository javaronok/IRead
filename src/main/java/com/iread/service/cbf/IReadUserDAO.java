package com.iread.service.cbf;

import it.unimi.dsi.fastutil.longs.LongSet;
import org.grouplens.lenskit.collections.LongUtils;
import org.lenskit.data.dao.UserDAO;

import javax.inject.Inject;
import java.io.File;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Angelina on 28.02.2016.
 */
public class IReadUserDAO implements UserDAO {
    private Collection<Long> users;

    public IReadUserDAO(Collection<Long> users) {
        this.users = users;
    }

    @Override
    public LongSet getUserIds() {
        return LongUtils.packedSet(users);
    }
}
