package www.wfq.com.aidlserver.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookService extends Service {

    private List<Book> books = new CopyOnWriteArrayList<>();
    private IBookController.Stub stub = new IBookController.Stub() {
        @Override
        public List<Book> findAllBook() throws RemoteException {
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (null != book) books.add(book);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        for (int i = 0; i < 5; i++) {
            books.add(new Book("book" + i));
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
