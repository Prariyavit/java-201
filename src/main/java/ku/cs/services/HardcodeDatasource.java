package ku.cs.services;

import ku.cs.models.BookList;

public class HardcodeDatasource implements Datasource<BookList> {
    @Override
    public BookList readData() {
        BookList list = new BookList();
        list.addBook("1", "One piece", "Echiro Oda", 1997, 10);
        list.addBook("2","Barbie", "Barbie", 1959, 20);
        list.addBook("3", "tokyo revengers", "Ken Wakui", 2017, 30);
        return list;
    }

    @Override
    public void writeData(BookList data) {

    }
}
