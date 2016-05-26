package com.iread.config;

import com.iread.utils.AttachmentFileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class FileStorageConfig {
    @Autowired
    Environment env;

    @Bean
    public AttachmentFileStore attachmentFileStore() {
        AttachmentFileStore store = new AttachmentFileStore();
        store.setTmpPath(System.getProperty("java.io.tmpdir"));
        store.setDwhPath(env.getProperty("attachments.path"));
        return store;
    }
}
