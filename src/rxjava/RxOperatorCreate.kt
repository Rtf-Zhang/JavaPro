package rxjava

import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import util.Printer
import java.util.concurrent.TimeUnit

class RxOperatorCreate {

    fun startTest() {
        createObservable()
//        createObservableJust()
//        createObservableAll()
//        createObservableFrom()
//        createObservableDefer()
//        createObservableEmpty()
//        createObservableNever()
//        createObservableError()
//        createObservableInterval()
//        createObservableRange()
//        createObservableRepeat()
//        createObservableTimer()
//        createObservableRepeatWhen()
//        createObservableAmb()
    }

    //Create a simple observable.
    fun createObservable(): Observable<String> {
        return object : Observable<String>() {
            override fun subscribeActual(observer: Observer<in String>?) {
                observer?.onNext("value1")
                observer?.onNext("value2")
                observer?.onComplete()
            }
        }
    }

    /**
     * Just操作符依次发射队列数据
     */
    fun createObservableJust() {
        Observable.just("value1", "value2", "value3")
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * All操作符依次判断队列数据是否满足条件，
     * 如果原始Observable正常终止并且每一项数据都满足条件，就返回true；
     * 如果原始Observable的任何一项数据不满足条件,就返回False
     */
    fun createObservableAll() {
        Observable.just(1, 2, 3, 4)
                .all { t -> t > 3 }
                .subscribe({
                    Printer.printJavaInfo("ReceiveValue->$it")
                }, {
                    Printer.printJavaInfo("Error")
                })
    }

    /**
     * From操作符将操作符转换成Observable
     * 当你使用Observable时，
     * 如果你要处理的数据都可以转换成展现为Observables，
     * 而不是需要混合使用Observables和其它类型的数据，
     * 会非常方便。这让你在数据流的整个生命周期中，
     * 可以使用一组统一的操作符来管理它们
     */
    fun createObservableFrom() {
        val stringArray = mutableListOf<String>()
        stringArray.add("value1")
        stringArray.add("value2")
        Observable.fromArray(stringArray)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Defer操作符会一直等待直到有观察者订阅它，
     * 然后它使用Observable工厂方法生成一个Observable。
     * 它对每个观察者都这样做，
     * 因此尽管每个订阅者都以为自己订阅的是同一个Observable，
     * 事实上每个订阅者获取的是它们自己的单独的数据序列
     * defer方法默认不在任何特定的调度器上执行
     */
    fun createObservableDefer() {
        Observable.defer { Observable.just(1, 2, 3, 4) }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * 创建一个不发射任何数据但是正常终止的Observable
     * 操作符默认不在任何特定的调度器上执行
     */
    fun createObservableEmpty() {
        Observable.empty<String>()
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * 创建一个不发射数据也不终止的Observable
     * 操作符默认不在任何特定的调度器上执行
     */
    fun createObservableNever() {
        Observable.never<String>()
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * 创建一个不发射数据以一个错误终止的Observable
     * 操作符默认不在任何特定的调度器上执行
     */
    fun createObservableError() {
        Observable.error<String>(Throwable("ErrorObservable!"))
                .subscribe({

                }, {
                    Printer.printJavaInfo("Error")
                })

    }

    /**
     * Interval操作符
     * 创建一个按固定时间间隔发射整数序列的Observable
     */
    fun createObservableInterval() {
        Observable.interval(1, 1, TimeUnit.SECONDS, Schedulers.trampoline())
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Range操作符
     * 发射一个范围内的有序整数序列，你可以指定范围的起始和长度。
     * 它接受两个参数，一个是范围的起始值，一个是范围的数据的数目。
     * 如果你将第二个参数设为0，将导致Observable不发射任何数据
     * （如果设置为负数，会抛异常）
     */
    fun createObservableRange() {
        Observable.range(0, 10)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Repeat操作符
     * 不是创建一个Observable，而是重复发射原始Observable的数据序列
     * 通过repeat(n)指定重复次数。
     */
    fun createObservableRepeat() {
        Observable.just(1, 2)
                .repeat(3)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Timer操作符
     * timer返回一个Observable，它在延迟一段给定的时间后发射一个简单的数字0。
     */
    fun createObservableTimer() {
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * RepeatWhen操作符
     */
    fun createObservableRepeatWhen() {
        Observable.just(1, 2, 3)
                .repeatWhen { Observable.just(4, 5, 6) }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    fun createObservableAmb() {
        val observableSources = mutableListOf<ObservableSource<String>>()
        observableSources.add(ObservableSource {
            it.onNext("Observer1:Emit:Value1")
            it.onNext("Observer1:Emit:Value2")
            it.onComplete()
        })
        observableSources.add(ObservableSource {
            it.onNext("Observer2:Emit:Value1")
            it.onNext("Observer2:Emit:Value2")
            it.onComplete()
        })
        observableSources.add(ObservableSource {
            it.onNext("Observer3:Emit:Value1")
            it.onNext("Observer3:Emit:Value2")
            it.onComplete()
        })
        Observable.amb(observableSources)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    companion object {
        val instance = lazy { RxOperatorCreate() }.value
    }
}