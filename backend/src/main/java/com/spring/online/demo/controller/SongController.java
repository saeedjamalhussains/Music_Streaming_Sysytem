package com.spring.online.demo.controller;

import com.spring.online.demo.models.Song;
import com.spring.online.demo.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "*")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadSong(@RequestParam("songName") String songName,
            @RequestParam("mp3File") MultipartFile mp3File,
            @RequestParam("albumImage") MultipartFile albumImage,
            @RequestParam("artist") String artist,
            @RequestParam("movie") String movie) {
        try {
            Song song = songService.storeSong(songName, mp3File, albumImage, artist, movie);
            return ResponseEntity.ok(song);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<java.util.List<Song>> getAllSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }
}
