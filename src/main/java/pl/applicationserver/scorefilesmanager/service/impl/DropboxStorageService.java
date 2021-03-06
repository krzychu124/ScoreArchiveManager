package pl.applicationserver.scorefilesmanager.service.impl;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DeleteResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.applicationserver.scorefilesmanager.config.AppEnvConfig;
import pl.applicationserver.scorefilesmanager.domain.SAFileMetadata;
import pl.applicationserver.scorefilesmanager.service.StorageService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("serverStorage")
@PropertySource("classpath:application.properties")
public class DropboxStorageService implements StorageService{
//TODO logger
    private DbxClientV2 client;
    @Autowired
    public DropboxStorageService(AppEnvConfig appEnvConfig) {
        DbxRequestConfig config = new DbxRequestConfig("ExperimentalScoreArchive");
        client = new DbxClientV2(config, appEnvConfig.getDropboxToken());
    }

    @Override
    public String upload(MultipartFile file, String filePath) {
        byte[] fileBytes = new byte[0];
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileBytes.length != 0) {
            try (InputStream in = new ByteArrayInputStream(fileBytes)) {
                FileMetadata metadata = client.files().uploadBuilder(filePath).uploadAndFinish(in);
                return metadata.getPathDisplay();
            } catch (IOException | DbxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public byte[] download(String filePath) {
        try {
            DbxDownloader<FileMetadata> downloader = client.files().download(filePath);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            downloader.download(out);
            out.close();
            return out.toByteArray();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return new byte[0];
    }

    public List<String> getFileList(String path) {
        try {
            ListFolderResult listing = client.files().listFolder(path);
            return listing.getEntries().stream().map(Metadata::toStringMultiline).collect(Collectors.toList());
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean removePermanently(SAFileMetadata fileMetadata) {
        try {
            DeleteResult deleteResult = client.files().deleteV2(fileMetadata.getUrl());
            System.out.println(deleteResult.toString());
            return true;
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return false;
    }
}
