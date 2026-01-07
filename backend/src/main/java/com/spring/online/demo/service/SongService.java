package com.spring.online.demo.service;

import com.spring.online.demo.DAO.SongRepository;
import com.spring.online.demo.models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class SongService {

    private final Path fileStorageLocation;

    @Autowired
    private SongRepository songRepository;

    public SongService() {
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(this.fileStorageLocation.resolve("songs"));
            Files.createDirectories(this.fileStorageLocation.resolve("images"));
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public Song storeSong(String songName, MultipartFile mp3File, MultipartFile albumImage, String artist,
            String movie) {
        String mp3FileName = StringUtils.cleanPath(mp3File.getOriginalFilename());
        String imageFileName = StringUtils.cleanPath(albumImage.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (mp3FileName.contains("..") || imageFileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + mp3FileName);
            }

            // Generate unique filenames to avoid conflicts
            String uniqueMp3Name = UUID.randomUUID().toString() + "_" + mp3FileName;
            String uniqueImageName = UUID.randomUUID().toString() + "_" + imageFileName;

            Path targetMp3Location = this.fileStorageLocation.resolve("songs").resolve(uniqueMp3Name);
            Path targetImageLocation = this.fileStorageLocation.resolve("images").resolve(uniqueImageName);

            Files.copy(mp3File.getInputStream(), targetMp3Location, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(albumImage.getInputStream(), targetImageLocation, StandardCopyOption.REPLACE_EXISTING);

            // Store relative paths or full URLs depending on how you want to serve them
            // For now, storing relative paths like /uploads/songs/filename.mp3
            String songUrl = "/uploads/songs/" + uniqueMp3Name;
            String imageUrl = "/uploads/images/" + uniqueImageName;

            Song song = new Song(songName, songUrl, imageUrl, artist, movie);
            return songRepository.save(song);

        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + mp3FileName + ". Please try again!", ex);
        }
    }

    public java.util.List<Song> getAllSongs() {
        return songRepository.findAll();
    }
}
