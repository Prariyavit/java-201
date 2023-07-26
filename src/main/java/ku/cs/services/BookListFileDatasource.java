package ku.cs.services;

import ku.cs.models.BookList;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class BookListFileDatasource implements Datasource<BookList> {
    private String directoryName;
    private String fileName;

    public BookListFileDatasource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public BookList readData() {
        BookList books = new BookList();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        BufferedReader buffer = new BufferedReader(inputStreamReader);

        try {
            String line;
            // Use while loop to read data line by line
            while ((line = buffer.readLine()) != null) {
                // If it is an empty line, skip it
                if (line.equals("")) continue;

                // Split the string by ","
                String[] data = line.split(",");
                // Read the data based on index and handle data types accordingly
                String id = data[0].trim();
                String title = data[1].trim();
                String author = data[2].trim();
                int year = Integer.parseInt(data[3].trim());
                int score = Integer.parseInt(data[4].trim());
                // Add the data to the list
                books.addBook(id, title, author, year, score);
            }

        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    @Override
    public void writeData(BookList data) {

    }
}
