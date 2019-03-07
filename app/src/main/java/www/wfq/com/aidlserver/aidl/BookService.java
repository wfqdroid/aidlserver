package www.wfq.com.aidlserver.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.*;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookService extends Service {

    private List<Book> books = new CopyOnWriteArrayList<>();

    private RemoteCallbackList<IOnNewBookArrivedListener> mListeners = new RemoteCallbackList<>();

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private IBookController.Stub stub = new IBookController.Stub() {

        @Override
        public List<Book> findAllBook() throws RemoteException {
            // 这里会造成客户端的ANR的发生，原因：
            // 客户端调用远程服务的方法，被调用的方法运行在服务端的Binder线程池中，同时客户端的线程会被挂起，这个时候如果服务端的方法是耗时的方法的话
            // 就会导致客户端阻塞，如果这个客户端的线程是UI线程的话，就会导致ANR的发生，由于客户端的onServiceConnected方法运行在UI线程中，
            // 所以不可以在这里调用服务端的耗时的方法
            // 另外服务端的方法本身运行在Binder线程池中，所以服务端本身就可以执行耗时的操作，这个时候切记不要在服务端方法中开线程
//            SystemClock.sleep(5000);
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (null != book) books.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {

//            if(!mListeners.contains(listener)){
//                mListeners.add(listener);
//            }else{
//                Log.e("WFQ", "接口早已经添加");
//            }

            mListeners.register(listener);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {

//            if(mListeners.contains(listener)){
//                mListeners.remove(listener);
//            }else{
//                Log.e("WFQ", "没有这个接口，怎么移除？");
//            }

            mListeners.unregister(listener);
            Log.e("WFQ", "注销成功" + mListeners.getRegisteredCallbackCount());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        for (int i = 0; i < 5; i++) {
            books.add(new Book("book" + i));
        }
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
//        int check = checkCallingOrSelfPermission("com.wfq.server_client.permission.ACCESS_BOOK_SERVICE");
//        if(check == PackageManager.PERMISSION_DENIED){
//            return null;
//        }
//        Log.e("WFQ", "服务端：onBind调用");
        return stub;
    }


    private void onNewBookArrived(Book book) throws RemoteException{
        Log.e("WFQ", "得到一本新书" + book.getName());
        books.add(book);

//        for (IOnNewBookArrivedListener mListener : mListeners) {
//            mListener.onNewBookArrived(book);
//        }

        int N = mListeners.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener broadcastItem = mListeners.getBroadcastItem(i);
            broadcastItem.onNewBookArrived(book);

        }

        mListeners.finishBroadcast();

    }



    private class ServiceWorker implements Runnable{
        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()){
                try {
                    Thread.sleep(5000);
                    Book newbook = new Book("安卓开发艺术");
                    onNewBookArrived(newbook);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
