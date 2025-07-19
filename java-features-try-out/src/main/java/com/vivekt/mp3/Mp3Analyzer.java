package com.vivekt.mp3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.*;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;

public class Mp3Analyzer {

    private static final Map<String, List<File>> hashMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        File parentDir = new File("C:\\ent\\backup\\"); // Change to your path

        if (!parentDir.exists() || !parentDir.isDirectory()) {
            System.out.println("Invalid directory!");
            return;
        }

        List<File> mp3Files = new ArrayList<>();
        findMp3Files(parentDir, mp3Files);

        System.out.println("Found " + mp3Files.size() + " MP3 files.\n");

        for (File file : mp3Files) {
            printMetadata(file);

            String hash = computeFileHash(file);
            hashMap.computeIfAbsent(hash, k -> new ArrayList<>()).add(file);
        }

        System.out.println("\n--- DUPLICATE FILES ---");
        hashMap.values().stream()
                .filter(list -> list.size() > 1)
                .forEach(duplicates -> {
                    System.out.println("\nDuplicate group:");
                    duplicates.forEach(f -> System.out.println(" - " + f.getAbsolutePath()));
                });
    }

    private static void findMp3Files(File dir, List<File> result) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                findMp3Files(file, result);
            } else if (file.getName().toLowerCase().endsWith(".mp3")) {
                result.add(file);
            }
        }
    }

    private static void printMetadata(File file) {
        try {
            AudioFile audioFile = AudioFileIO.read(file);
            Tag tag = audioFile.getTag();

            System.out.println("File: " + file.getAbsolutePath());
            System.out.println("Size: " + (file.length() / 1024) + " KB");

            if (tag != null) {
                System.out.println("Title : " + tag.getFirst(org.jaudiotagger.tag.FieldKey.TITLE));
                System.out.println("Artist: " + tag.getFirst(org.jaudiotagger.tag.FieldKey.ARTIST));
                System.out.println("Album : " + tag.getFirst(org.jaudiotagger.tag.FieldKey.ALBUM));
            } else {
                System.out.println("No metadata available.");
            }

            System.out.println();
        } catch (Exception e) {
            System.out.println("Error reading metadata: " + file.getName());
        }
    }

    private static String computeFileHash(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] buffer = new byte[4096];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                md.update(buffer, 0, read);
            }

            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();

        } catch (IOException | java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to hash file: " + file.getName(), e);
        }
    }
}
