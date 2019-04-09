package rxjava

import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import util.Printer
import java.util.concurrent.TimeUnit

/**
 * 过滤操作符
 */
class RxOperatorFilter {

    fun startTest() {
//        fltFilter()
//        fltOfType()
//        fltTakeLast()
//        fltTakeLastTime()
//        fltLast()
//        fltSkipCount()
//        fltSkipTime()
//        fltSkipLast()
//        fltSkipLastTime()
//        fltTake()
//        fltTakeTime()
//        fltFirst()
//        fltElementAt()
//        fltSample()
//        fltThrottleFirst()
//        fltDebounce()
//        fltTimeoutTime()
//        fltTimeoutObservable()
//        fltTimeoutFunc()
//        fltTimeoutFuncAndObservable()
//        fltDistinct()
//        fltDistinctKey()
//        fltDistinctUntilChanged()
//        fltDistinctUntilChangedKey()
//        fltIgnoreElements()
    }


    /**
     * Filter操作符
     * 使用你指定的一个谓词函数测试数据项，
     * 只有通过测试的数据才会被发射
     */
    fun fltFilter() {
        Observable.range(1, 10)
                .filter { it > 5 }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * OfType操作符
     * filter操作符的一个特殊形式。
     * 它过滤一个Observable只返回指定类型的数据。
     */
    fun fltOfType() {
        Observable.just(1, 2, 3, "4", "5", "6")
                .ofType(String::class.java)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * TakeLast操作符
     * 使用TakeLast操作符修改原始Observable，
     * 你可以只发射Observable'发射的后N项数据，
     * 忽略前面的数据。
     */
    fun fltTakeLast() {
        Observable.range(1, 10)
                .takeLast(4)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * TakeLast.t操作符
     * 还有一个takeLast变体接受一个时长而不是数量参数。
     * 它会发射在原始Observable的生命周期内最后一段时间内发射的数据。
     * 时长和时间单位通过参数指定。
     *
     * 注意：
     * 这会延迟原始Observable发射的任何数据项，直到它全部完成。
     */
    fun fltTakeLastTime() {
        Observable.interval(200, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(30)
                .takeLast(1000, TimeUnit.MILLISECONDS)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Last操作符
     * 如果你只对Observable发射的最后一项数据，
     * 或者满足某个条件的最后一项数据感兴趣，你可以使用Last操作符。
     * 在某些实现中，Last没有实现为一个返回Observable的过滤操作符，
     * 而是实现为一个在当时就发射原始Observable指定数据项的阻塞函数。
     * 在这些实现中，如果你想要的是一个过滤操作符，最好使用TakeLast(1)
     */
    fun fltLast() {
        Observable.range(1, 10)
                .last(0)
                .subscribe({
                    Printer.printJavaInfo("ReceiveValue->$it")
                }, {
                    Printer.printJavaInfo("Error->$it")
                })
    }


    /**
     * Skip操作符
     * 使用Skip操作符，你可以忽略Observable'发射的前N项数据，
     * 只保留之后的数据。
     */
    fun fltSkipCount() {
        Observable.range(1, 10)
                .skip(5)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Skip操作符
     * skip的这个变体接受一个时长而不是数量参数。
     * 它会丢弃原始Observable开始的那段时间发射的数据，
     * 时长和时间单位通过参数指定。
     */
    fun fltSkipTime() {
        Observable.interval(100, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(30)
                .skip(1000, TimeUnit.MILLISECONDS, Schedulers.newThread())
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * SkipLast操作符
     * 使用SkipLast操作符修改原始Observable，
     * 你可以忽略Observable'发射的后N项数据，
     * 只保留前面的数据。
     */
    fun fltSkipLast() {
        Observable.range(1, 10)
                .skipLast(2)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * SkipLast操作符
     * 接受一个时长而不是数量参数。
     * 它会丢弃在原始Observable的生命周期内最后一段时间内发射的数据。
     * 时长和时间单位通过参数指定。
     */
    fun fltSkipLastTime() {
        Observable.interval(200, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(30)
                .skipLast(1000, TimeUnit.MILLISECONDS, Schedulers.newThread())
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Take操作符
     * 使用Take操作符让你可以修改Observable的行为，
     * 只返回前面的N项数据，然后发射完成通知，忽略剩余的数据。
     */
    fun fltTake() {
        Observable.range(1, 10)
                .take(5)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Take操作符
     * 接受一个时长而不是数量参数。
     * 它会丢发射Observable开始的那段时间发射的数据，
     * 时长和时间单位通过参数指定。
     */
    fun fltTakeTime() {
        Observable.interval(100, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(1000, TimeUnit.MILLISECONDS)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * First操作符
     * 如果你只对Observable发射的第一项数据，
     * 或者满足某个条件的第一项数据感兴趣，
     * 你可以使用First操作符。
     */
    fun fltFirst() {
        Observable.range(1, 10)
                .first(5)
                .subscribe({
                    Printer.printJavaInfo("ReceiveValue->$it")
                }, {
                    Printer.printJavaInfo("Error->$it")
                })
    }

    /**
     * ElementAt操作符
     * 获取原始Observable发射的数据序列指定索引位置的数据项，
     * 然后当做自己的唯一数据发射。
     */
    fun fltElementAt() {
        Observable.range(1, 10)
                .elementAt(50, 100)
                .subscribe({
                    Printer.printJavaInfo("ReceiveValue->$it")
                }, {
                    Printer.printJavaInfo("Error->$it")
                })
    }

    /**
     * Sample操作符
     * 定时查看一个Observable，然后发射自上次采样以来它最近发射的数据。
     *
     * 注意：
     * 如果自上次采样以来，原始Observable没有发射任何数据，
     * 这个操作返回的Observable在那段时间内也不会发射任何数据。
     */
    fun fltSample() {
        Observable.interval(100, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(100)
                .sample(1000, TimeUnit.MILLISECONDS)
                //Also
//                .sample(Observable.interval(1000, TimeUnit.MILLISECONDS))
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Throttle操作符
     * 发送采样周期内第一个或者最后一个数据
     */
    fun fltThrottleFirst() {
        Observable.interval(100, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(100)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                //Also
//                .throttleLast(1000, TimeUnit.MILLISECONDS)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }


    /**
     * Debounce操作符
     * 过滤掉发射速率过快的数据项。
     * RxJava将这个操作符实现为throttleWithTimeout和debounce。
     *
     * 注意：这个操作符会会接着最后一项数据发射原始Observable的onCompleted通知，
     * 即使这个通知发生在你指定的时间窗口内（从最后一项数据的发射算起）。
     * 也就是说，onCompleted通知不会触发限流。
     */
    fun fltDebounce() {
        Observable
                .create<Int> {
                    it.onNext(1)
                    Thread.sleep(100)//发送1后过了100ms就发送了2，未超时400ms，1丢弃，重新计时
                    it.onNext(2)
                    Thread.sleep(400)//发送2之后过了400ms，则2保留，重新计时
                    it.onNext(3)
                    Thread.sleep(300)//发送3后过了300ms就发送了4，未超时400ms，3丢弃，重新计时
                    it.onNext(4)
                    Thread.sleep(500)//发送4之后过了400ms，则4保留，重新计时
                    it.onNext(5)
                    Thread.sleep(150)//发送5后过了150ms就发送了6，未超时400ms，5丢弃，重新计时
                    it.onNext(6)
                    Thread.sleep(350)//发送6后过了350ms就发送了7，未超时400ms，6丢弃，重新计时
                    it.onNext(7)
                    Thread.sleep(600)//发送7之后过了600ms，则7保留，重新计时
                    it.onComplete()//所以最后保留 2,4,7 丢弃 1,3,5,6
                }
                .debounce(400, TimeUnit.MILLISECONDS, Schedulers.newThread())
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Timeout操作符 timeout(long,TimeUnit)
     * 对原始Observable的一个镜像，
     * 如果过了一个指定的时长仍没有发射数据，
     * 它会发一个错误通知
     */
    fun fltTimeoutTime() {
        Observable
                .create<Int> {
                    it.onNext(1)
                    Thread.sleep(100)//发送1后过了100ms就发送了2，未超时400ms，1丢弃，重新计时
                    it.onNext(2)
                    Thread.sleep(400)//发送2之后过了400ms，则2保留，重新计时
                    it.onNext(3)
                    Thread.sleep(300)//发送3后过了300ms就发送了4，未超时400ms，3丢弃，重新计时
                    it.onNext(4)
                    Thread.sleep(500)//发送4之后过了400ms，则4保留，重新计时
                    it.onNext(5)
                    Thread.sleep(150)//发送5后过了150ms就发送了6，未超时400ms，5丢弃，重新计时
                    it.onNext(6)
                    Thread.sleep(350)//发送6后过了350ms就发送了7，未超时400ms，6丢弃，重新计时
                    it.onNext(7)
                    Thread.sleep(600)//发送7之后过了600ms，则7保留，重新计时
                    it.onComplete()//所以最后保留 2,4,7 丢弃 1,3,5,6
                }
                .timeout(450, TimeUnit.MILLISECONDS)
                .subscribe({
                    Printer.printJavaInfo("ReceiveValue->$it")
                }, {
                    Printer.printJavaInfo("Error->$it")
                })
    }

    /**
     * Timeout操作符 timeout(long,TimeUnit,Observable)
     * 这个版本的timeout在超时时会切换到使用一个你指定的备用的Observable，
     * 而不是发错误通知。它也默认在computation调度器上执行。
     */
    fun fltTimeoutObservable() {
        Observable
                .create<Int> {
                    it.onNext(1)
                    Thread.sleep(100)//发送1后过了100ms就发送了2，未超时400ms，1丢弃，重新计时
                    it.onNext(2)
                    Thread.sleep(400)//发送2之后过了400ms，则2保留，重新计时
                    it.onNext(3)
                    Thread.sleep(300)//发送3后过了300ms就发送了4，未超时400ms，3丢弃，重新计时
                    it.onNext(4)
                    Thread.sleep(500)//发送4之后过了400ms，则4保留，重新计时
                    it.onNext(5)
                    Thread.sleep(150)//发送5后过了150ms就发送了6，未超时400ms，5丢弃，重新计时
                    it.onNext(6)
                    Thread.sleep(350)//发送6后过了350ms就发送了7，未超时400ms，6丢弃，重新计时
                    it.onNext(7)
                    Thread.sleep(600)//发送7之后过了600ms，则7保留，重新计时
                    it.onComplete()//所以最后保留 2,4,7 丢弃 1,3,5,6
                }
                .timeout(450, TimeUnit.MILLISECONDS, Observable.create {
                    it.onNext(1000)
                })
                .subscribe({
                    Printer.printJavaInfo("ReceiveValue->$it")
                }, {
                    Printer.printJavaInfo("Error->$it")
                })
    }

    /**
     * Timeout操作符 timeout(Func1)
     * 这个版本的timeout在超时时会切换到使用一个你指定的备用的Observable，
     * 而不是发错误通知。它也默认在computation调度器上执行。
     */
    fun fltTimeoutFunc() {
        Observable
                .create<Int> {
                    it.onNext(1)
                    Thread.sleep(100)//发送1后过了100ms就发送了2，未超时400ms，1丢弃，重新计时
                    it.onNext(2)
                    Thread.sleep(400)//发送2之后过了400ms，则2保留，重新计时
                    it.onNext(3)
                    Thread.sleep(300)//发送3后过了300ms就发送了4，未超时400ms，3丢弃，重新计时
                    it.onNext(4)
                    Thread.sleep(500)//发送4之后过了400ms，则4保留，重新计时
                    it.onNext(5)
                    Thread.sleep(150)//发送5后过了150ms就发送了6，未超时400ms，5丢弃，重新计时
                    it.onNext(6)
                    Thread.sleep(350)//发送6后过了350ms就发送了7，未超时400ms，6丢弃，重新计时
                    it.onNext(7)
                    Thread.sleep(600)//发送7之后过了600ms，则7保留，重新计时
                    it.onComplete()//所以最后保留 2,4,7 丢弃 1,3,5,6
                }
                .timeout {
                    Observable.just(1)
                            .delay(450, TimeUnit.MILLISECONDS)
                }
                .subscribe({
                    Printer.printJavaInfo("ReceiveValue->$it")
                }, {
                    Printer.printJavaInfo("Error->$it")
                })
    }

    /**
     * Timeout操作符 timeout(Func1,Observable)
     * 这个版本的timeout同时指定超时时长和备用的Observable。
     * 它默认在immediate调度器上执行
     */
    fun fltTimeoutFuncAndObservable() {
        Observable
                .create<Int> {
                    it.onNext(1)
                    Thread.sleep(100)//发送1后过了100ms就发送了2，未超时400ms，1丢弃，重新计时
                    it.onNext(2)
                    Thread.sleep(400)//发送2之后过了400ms，则2保留，重新计时
                    it.onNext(3)
                    Thread.sleep(300)//发送3后过了300ms就发送了4，未超时400ms，3丢弃，重新计时
                    it.onNext(4)
                    Thread.sleep(500)//发送4之后过了400ms，则4保留，重新计时
                    it.onNext(5)
                    Thread.sleep(150)//发送5后过了150ms就发送了6，未超时400ms，5丢弃，重新计时
                    it.onNext(6)
                    Thread.sleep(350)//发送6后过了350ms就发送了7，未超时400ms，6丢弃，重新计时
                    it.onNext(7)
                    Thread.sleep(600)//发送7之后过了600ms，则7保留，重新计时
                    it.onComplete()//所以最后保留 2,4,7 丢弃 1,3,5,6
                }
                .timeout(Function<Int, Observable<Int>> { Observable.just(100).delay(350, TimeUnit.MILLISECONDS) }, Observable.just(1000))
                .subscribe({
                    Printer.printJavaInfo("ReceiveValue->$it")
                }, {
                    Printer.printJavaInfo("Error->$it")
                })
    }

    /**
     * Distinct操作符 distinct()
     * 只允许还没有发射过的数据项通过。
     * 在某些实现中，有一些变体允许你调整判定两个数据不同(distinct)的标准。
     * 还有一些实现只比较一项数据和它的直接前驱，因此只会从序列中过滤掉连续重复的数据。
     */
    fun fltDistinct() {
        Observable.just(1, 2, 3, 4, 1, 2, 3, 4)
                .distinct()
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Distinct操作符 distinct(Func1)
     * 接受一个函数。这个函数根据原始Observable发射的数据项产生一个Key，
     * 然后，比较这些Key而不是数据本身，来判定两个数据是否是不同的。
     */
    fun fltDistinctKey() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .distinct { it % 2 }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * DistinctUntilChanged操作符  distinctUntilChanged()
     * 只判定一个数据和它的直接前驱是否是不同的。
     */
    fun fltDistinctUntilChanged() {
        Observable.just(1, 1, 2, 2, 2, 3, 4, 5, 5, 6)
                .distinctUntilChanged()
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * DistinctUntilChanged操作符  distinctUntilChanged(Func1)
     * 只判定一个数据和它的直接前驱是否是不同的。
     */
    fun fltDistinctUntilChangedKey() {
        Observable.just(1, 1, 2, 4, 2, 3, 4, 5, 5, 6)
                .distinctUntilChanged { t -> t % 2 }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * IgnoreElements操作符
     * IgnoreElements操作符抑制原始Observable发射的所有数据，
     * 只允许它的终止通知（onError或onCompleted）通过。
     */
    fun fltIgnoreElements() {
        Observable.just(1, 2, 3, 4)
                .ignoreElements()
                .subscribe(object : CompletableObserver {

                    override fun onSubscribe(d: Disposable) {
                        Printer.printJavaInfo("onSubscribe")
                    }

                    override fun onError(e: Throwable) {
                        Printer.printJavaInfo("Error->$e")
                    }

                    override fun onComplete() {
                        Printer.printJavaInfo("Complete")
                    }
                })
    }


    companion object {
        val instance = lazy { RxOperatorFilter() }.value
    }
}