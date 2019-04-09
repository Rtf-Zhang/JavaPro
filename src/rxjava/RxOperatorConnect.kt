package rxjava

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import util.Printer
import java.util.concurrent.TimeUnit


class RxOperatorConnect {

    fun startTest() {
//        cntPublicConnect()
//        cntReplay()
        cntRefCount()
    }

    /**
     * Publish/Connect 操作符
     * Publish 将普通的Observable转换成ConnectableObservable
     * ConnectableObservable 只有调用Connect才会开始发送数据
     */
    fun cntPublicConnect() {
        val conOb = Observable.just(1, 2, 3, 4)
                .publish()
        conOb.subscribe { Printer.printJavaInfo("ReceiveValue1->$it") }
        Thread.sleep(1000)
        conOb.connect()
        //将不会收到数据，因为没有调用Connect方法
        conOb.subscribe { Printer.printJavaInfo("ReceiveValue2->$it") }
    }

    /**
     * Replay操作符
     * 保证所有的观察者收到相同的数据序列，
     * 即使它们在Observable开始发射数据之后才订阅
     */
    fun cntReplay() {
        val conOb = Observable.interval(500, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(5)
                //.replay()//发送完整的序列
                //.replay(3)//发送序列的数量最大为3,且为最后3个
                .replay(1500, TimeUnit.MILLISECONDS)//发送序列最后时长为t的时段内的数据
//                .replay { Observable.just(100,200,300).publish() }
        conOb.subscribe { Printer.printJavaInfo("ReceiveValue1->$it") }
        Thread.sleep(1000)
        conOb.connect()
        //将收到数据，因为Replay方法会保证所有订阅者收到相同数据序列
        conOb.subscribe { Printer.printJavaInfo("ReceiveValue2->$it") }
        conOb.subscribe { Printer.printJavaInfo("ReceiveValue3->$it") }
    }

    fun cntRefCount() {
        val conOb = Observable.interval(500, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(5)
                .publish()
//        conOb.refCount()
        conOb.subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    companion object {
        val instance = lazy { RxOperatorConnect() }.value
    }
}