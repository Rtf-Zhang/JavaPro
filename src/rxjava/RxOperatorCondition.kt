package rxjava

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.BiPredicate
import io.reactivex.schedulers.Schedulers
import util.Printer
import java.util.concurrent.TimeUnit

class RxOperatorCondition {

    fun startTest() {
//        conAll()
//        conAmb()
//        conContains()
//        conDefaultIfEmpty()
//        conSequenceEqual()
//        conSkipUntil()
//        conSkipWhile()
//        conTakeUntil()
//        conTakeWhile()
    }

    /**
     * All操作符
     * 判定是否Observable发射的所有数据都满足某个条件
     */
    fun conAll() {
        Observable.just(1, 2, 3, 4)
                .all { it > 2 }
                .subscribe(
                        { Printer.printJavaInfo("ReceiveValue->$it") },
                        { Printer.printJavaInfo("Error->$it") }
                )
    }

    /**
     * Amb操作符
     * 给定两个或多个Observables，
     * 它只发射首先发射数据或通知的那个Observable的所有数据
     */
    fun conAmb() {
        val o1 = Observable.just(1, 2, 3).delay(100, TimeUnit.MILLISECONDS)
        val o2 = Observable.just(4, 5, 6).delay(200, TimeUnit.MILLISECONDS)
        Observable.amb(mutableListOf<ObservableSource<Int>>(o1, o2))
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Contains操作符
     * 传一个指定的值，如果原始Observable发射了那个值，
     * 它返回的Observable将发射true，否则发射false。
     */
    fun conContains() {
        Observable.just(1, 2, 3, 4)
                .contains(4)
                .map { Printer.printJavaInfo("Map->$it") }
                .contains(5)
                .subscribe(
                        { Printer.printJavaInfo("ReceiveValue->$it") },
                        { Printer.printJavaInfo("Error->$it") }
                )
    }

    /**
     * DefaultIfEmpty操作符
     * 发射来自原始Observable的值，如果原始Observable没有发射任何值，就发射一个默认值
     */
    fun conDefaultIfEmpty() {
        Observable.empty<Int>()
                .defaultIfEmpty(0)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * SequenceEqual操作符
     * 判定两个Observables是否发射相同的数据序列。
     * 传递两个Observable给SequenceEqual操作符，它会比较两个Observable的发射物，
     * 如果两个序列是相同的（相同的数据，相同的顺序，相同的终止状态），
     * 它就发射true，否则发射false。
     * 还有一个版本接受第三个参数，可以传递一个函数用于比较两个数据项是否相同。
     */
    fun conSequenceEqual() {
        Observable.sequenceEqual(
                Observable.just(2, 3, 4),
                Observable.just(1, 2, 3),
                BiPredicate { t1, t2 -> t1 == t2 + 1 }
        )
                .subscribe(
                        { Printer.printJavaInfo("ReceiveValue->$it") },
                        { Printer.printJavaInfo("Error->$it") }
                )
    }

    /**
     * SkipUntil操作符
     * 丢弃原始Observable发射的数据，
     * 直到第二个Observable发射了一项数据
     */
    fun conSkipUntil() {
        Observable.interval(500, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(10)
                .skipUntil(Observable.just(1, 2).delay(2000, TimeUnit.MILLISECONDS))
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * SkipWhile操作符
     * 丢弃Observable发射的数据，直到一个指定的条件不成立
     * 订阅原始的Observable，但是忽略它的发射物，
     * 直到你指定的某个条件变为false的那一刻，它开始发射原始Observable。
     */
    fun conSkipWhile() {
        Observable.interval(500, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(10)
                .skipWhile { it < 5 }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * TakeUntil操作符
     * 当第二个Observable发射了一项数据或者终止时，
     * 丢弃原始Observable发射的任何数据
     */
    fun conTakeUntil() {
        Observable.interval(500, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(10)
                .takeUntil(Observable.timer(2000, TimeUnit.MILLISECONDS))
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * TakeWhile操作符
     * 发射Observable发射的数据，直到一个指定的条件不成立
     * 发射原始Observable，直到你指定的某个条件不成立的那一刻，
     * 它停止发射原始Observable，并终止自己的Observable。
     */
    fun conTakeWhile() {
        Observable.interval(500, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(10)
                .takeWhile { it < 5 }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    companion object {
        val instance = lazy { RxOperatorCondition() }.value
    }
}