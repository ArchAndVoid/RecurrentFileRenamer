package com.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileRenamer {

    private static List<String> findFiles(Path path, String fileExtension)
            throws IOException {

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Путь должен быть папкой");
        }

        List<String> result;

        try (Stream<Path> walk = Files.walk(path)) {
            result = walk
                    .filter(p -> !Files.isDirectory(p))
                    .map(p -> p.toString().toLowerCase())
                    .filter(f -> f.contains(fileExtension))

                    .collect(Collectors.toList());
        }

        return result;
    }

    public Integer rename (String path, String format, String newFormat) throws IOException {
        List<String> files = findFiles(Paths.get(path), format);
        files.forEach(x -> System.out.println(x));
        int counter = 0;
        for (int i = 0; i<files.size(); i++){
            File file = new File(files.get(i));
            File newFile = new File(files.get(i).replace(format,newFormat));
            if (newFile.exists())
                throw new java.io.IOException("Файл существует");
            file.renameTo(newFile);
            counter++;
        }

        return counter;
    }
}
