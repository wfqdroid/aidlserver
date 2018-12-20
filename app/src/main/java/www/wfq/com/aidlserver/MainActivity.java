package www.wfq.com.aidlserver;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import www.wfq.com.aidlserver.aidl.Book;
import www.wfq.com.aidlserver.aidl.BookService;
import www.wfq.com.aidlserver.aidl.IBookController;

import java.util.List;

public class MainActivity extends AppCompatActivity{

}


//        AppCompatActivity {
//
//    private Button findBooks,addBook;
//    private Boolean isConnected;
//    private IBookController controller;
//    private List<Book> bookList;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        findBooks = findViewById(R.id.btn_get);
//        addBook = findViewById(R.id.btn_add);
//        bindService();
//        findBooks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    bookList = controller.findAllBook();
//                    print();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        addBook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Book book = new Book("客户端新增一本书");
//                    controller.addBook(book);
//                    print();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//
//    }
//
//    private void print(){
//        for (Book book : bookList) {
//            Log.e("WFQ", "value is: " + book);
//        }
//
//    }
//
//    private ServiceConnection connection = new ServiceConnection(){
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            isConnected = true;
//            controller = IBookController.Stub.asInterface(iBinder);
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            isConnected = false;
//        }
//    };
//
//    private void bindService(){
//        Intent intent = new Intent(this,BookService.class);
//        bindService(intent,connection,BIND_AUTO_CREATE);
//    }
//    private void unBindService(){
//        if(isConnected) unbindService(connection);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unBindService();
//    }
//}
