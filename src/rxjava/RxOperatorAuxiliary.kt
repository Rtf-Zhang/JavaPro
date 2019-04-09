package rxjava

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import util.Printer
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class RxOperatorAuxiliary {

    fun startTest() {
//        auxMaterialize()
//        auxTimestamp()
//        auxSerialize()
//        auxSchedulers()
//        auxDo()
//        auxDelay()
//        auxTimeInterval()
//        auxUsing()
//        auxSingle()
    }

    /**
     * Materialize/Dematerialize操作符
     * Materialize将数据项和事件通知都当做数据项发射，Dematerialize刚好相反。
     */
    fun auxMaterialize() {
        Observable.just(1, 2, 3, 4).materialize()
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Timestamp操作符
     * 给Observable发射的数据项附加一个时间戳
     */
    fun auxTimestamp() {
        Observable.just(1, 2, 3)
                .timestamp()
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Serialize操作符
     * 强制一个Observable连续调用并保证行为正确
     */
    fun auxSerialize() {
        Observable.create<Int> {
            it.onNext(1)
            Thread.sleep(100)
            it.onNext(2)
            Thread.sleep(500)
            it.onNext(3)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
                .serialize()
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Cache操作符
     * 记住Observable发射的数据序列并发射相同的数据序列给后续的订阅者
     */
    fun auxCache() {
        Observable.just(1, 2, 3, 4)
                .cache()
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * ObserveOn/SubscribeOn操作符
     * PS:
     * ObserveOn->指定一个观察者在哪个调度器上观察Observable
     * SubscribeOn->指定Observable自身在哪个调度器上执行
     */
    fun auxSchedulers() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.trampoline())
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Do操作符
     * doOnEach 操作符让你可以注册一个回调,它产生的Observable每发射一项数据就会调用它一次。
     * DoOnNext Action不是接受一个Notification参数，而是接受发射的数据项
     * DoOnSubscribe 注册一个动作，当观察者订阅它生成的Observable它就会被调用。
     * DoOnComplete 注册一个动作，当完成时调用
     * DoFinally 最后调用
     */
    fun auxDo() {
        Observable.just(1, 2, 3, 4)
//                .doOnEach(object : Observer<Int> {
//                    override fun onComplete() {
//                        Printer.printJavaInfo("Each->onComplete")
//                    }
//
//                    override fun onSubscribe(d: Disposable) {
//                        Printer.printJavaInfo("Each->onSubscribe")
//                    }
//
//                    override fun onNext(t: Int) {
//                        Printer.printJavaInfo("Each->$t")
//                    }
//
//                    override fun onError(e: Throwable) {
//                        Printer.printJavaInfo("EachError->$e")
//                    }
//                })
                .doOnEach { Printer.printJavaInfo("DoOnEach->$it") }
                .doOnNext { Printer.printJavaInfo("DoOnNext->$it") }
                .doOnSubscribe { Printer.printJavaInfo("DoOnSubscribe->$it") }
                .doOnComplete { Printer.printJavaInfo("DoOnComplete->") }
                .doFinally { Printer.printJavaInfo("DoFinally->") }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Delay操作符
     * 延时发送
     */
    fun auxDelay() {
        Observable.just(0, 1, 2, 3, 4)
//                .delay(1, TimeUnit.SECONDS)
//                .delay { Observable.timer((it * 1000).toLong(), TimeUnit.MILLISECONDS) }
//                .delaySubscription(1000,TimeUnit.MILLISECONDS)
                .delaySubscription<Int> {
                    it.onNext(100)
                }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * TimeInterval操作符
     * 将一个发射数据的Observable转换为发射那些数据发射时间间隔的Observable
     */
    fun auxTimeInterval() {
        Observable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(5)
                .timeInterval()
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Using操作符
     * 让你可以指示Observable创建一个只在它的生命周期内存在的资源，
     * 当Observable终止时这个资源会被自动释放。
     */
    fun auxUsing() {
        Observable.using(
                //一个用于 创建一次性资源的工厂函数
                Callable<String> { "Resource" },
                //一个用于创建Observable的工厂函数，这个函数返回的Observable就是最终被观察的Observable
                Function<String, ObservableSource<Int>> { r ->
                    ObservableSource {
                        Printer.printJavaInfo("R->$r")
                        it.onNext(1)
                        it.onNext(2)
                        it.onComplete()
                    }
                },
                //一个用于释放资源的函数，当Func2返回的Observable执行完毕之后会被调用
                Consumer<String> { Printer.printJavaInfo("ReleaseResource") }
        )
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Single操作符
     * 强制发送一条数据，若超过一条则发送错误
     */
    fun auxSingle() {
        Observable.just(1)
                .single(100)
                .subscribe(
                        { Printer.printJavaInfo("ReceiveValue->$it") },
                        { Printer.printJavaInfo("Error->$it") })
    }


    companion object {
        val instance = lazy { RxOperatorAuxiliary() }.value
    }
}