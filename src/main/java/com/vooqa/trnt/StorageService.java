package com.vooqa.trnt;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {

    private final Path path;

    public StorageService() {
        path = Paths.get("trnt-files");
    }

    public void store(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, path.resolve(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void encode() {
        Path torrentPath = Paths.get("F:\\opera\\Taboo.torrent");
        try {
            /*InputStream inputStream = Files.newInputStream(torrentPath);
            Bencoder encoder = new Bencoder(inputStream);
            Map<String, Object> torrentModelMap = encoder.parse();
            TorrentFile torrentFile = new TorrentFile(torrentModelMap);
            System.out.println(1);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
