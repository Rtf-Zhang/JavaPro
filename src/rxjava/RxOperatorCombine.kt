package rxjava

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import util.Printer
import java.util.concurrent.TimeUnit

class RxOperatorCombine {

    fun startTest() {
//        cbStartWith()
//        cbMerge()
//        cbZip()
//        combineLasest()
//        cbJoin()
//        cbSwitch()
    }

    /**
     * StartWith操作符
     * 在数据序列的开头插入指定的项
     */
    fun cbStartWith() {
        Observable.just(6, 7, 8)
            .startWith(0)//直接发送同类型数据
            .startWith {
                it.onNext(1)
                it.onComplete()
            }//发送前置Observable队列
            .startWith { it ->
                it.onNext(2)
                it.onComplete()
            }//发送前置Observer队列
            .startWith(mutableListOf(3, 4, 5))
            .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * MergeWith操作符
     * 合并多个Observables的发射物
     */
    fun cbMergeWith() {
        Observable.just(1, 2, 3)
            .mergeWith(Observable.just(5, 6))
            .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Merge操作符
     * 合并多个Observables的发射物
     */
    fun cbMerge() {
        val observable1 = Observable.create<Int> {
            Printer.printJavaInfo("O1->Send->1")
            it.onNext(1)
            Thread.sleep(100)
            Printer.printJavaInfo("O1->Send->2")
            it.onNext(2)
            Thread.sleep(200)
            Printer.printJavaInfo("O1->Send->3")
            it.onNext(3)
            Thread.sleep(300)
            Printer.printJavaInfo("O1->Send->4")
            it.onNext(4)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())


        val observable2 = Observable.create<Int> {
            Printer.printJavaInfo("O2->Send->5")
            it.onNext(5)
            Thread.sleep(200)
            Printer.printJavaInfo("O2->Send->6")
            it.onNext(6)
            Thread.sleep(300)
            Printer.printJavaInfo("O2->Send->7")
            it.onNext(7)
            Thread.sleep(400)
            Printer.printJavaInfo("O2->Send->8")
            it.onNext(8)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())

        Observable.merge(observable1, observable2)
            .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Zip操作符
     * 收集每一个Observable发射的数据，然后应用函数计算
     * 必须收集齐每一个Observable，因此每次发射会有等待收集过程
     * 最后发射的数据的个数为原始发射数据最少的那一个observable
     */
    fun cbZip() {
        val observable1 = Observable.create<Int> {
            Printer.printJavaInfo("O1->Send->1")
            it.onNext(1)
            Thread.sleep(100)
            Printer.printJavaInfo("O1->Send->2")
            it.onNext(2)
            Thread.sleep(200)
            Printer.printJavaInfo("O1->Send->3")
            it.onNext(3)
            Thread.sleep(300)
            Printer.printJavaInfo("O1->Send->4")
            it.onNext(4)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())


        val observable2 = Observable.create<Int> {
            Printer.printJavaInfo("O2->Send->5")
            it.onNext(5)
            Thread.sleep(200)
            Printer.printJavaInfo("O2->Send->6")
            it.onNext(6)
            Thread.sleep(300)
            Printer.printJavaInfo("O2->Send->7")
            it.onNext(7)
            Thread.sleep(400)
            Printer.printJavaInfo("O2->Send->8")
            it.onNext(8)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())

        val observable3 = Observable.create<Int> {
            Printer.printJavaInfo("O3->Send->9")
            it.onNext(9)
            Thread.sleep(300)
            Printer.printJavaInfo("O3->Send->10")
            it.onNext(10)
            Thread.sleep(400)
            Printer.printJavaInfo("O3->Send->11")
            it.onNext(11)
            Thread.sleep(500)
            Printer.printJavaInfo("O3->Send->12")
            it.onNext(12)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())

        Observable.zip(
            observable1,
            observable2,
            observable3,
            Function3<Int, Int, Int, Int> { t1, t2, t3 -> t1 + t2 + t3 })
            .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }


    /**
     * CombineLatest操作符
     * 两个Observables  O1 And O2
     * 当O1或者O2发送了数据，则去与另外一个O的最近的数据进行函数运算
     * 并且将计算结果发送,只有第一次计算可能存在等待，
     * 后续只要收到任意方数据则去与另一方计算
     */
    fun combineLasest() {

        val observable1 = Observable.create<Int> {
            Printer.printJavaInfo("O1->Send->1")
            it.onNext(1)
            Thread.sleep(100)
            Printer.printJavaInfo("O1->Send->2")
            it.onNext(2)
            Thread.sleep(200)
            Printer.printJavaInfo("O1->Send->3")
            it.onNext(3)
            Thread.sleep(300)
            Printer.printJavaInfo("O1->Send->4")
            it.onNext(4)
            Thread.sleep(400)
//            it.onComplete()
        }.subscribeOn(Schedulers.newThread())


        val observable2 = Observable.create<Int> {
            Printer.printJavaInfo("O2->Send->5")
            it.onNext(5)
            Thread.sleep(200)
            Printer.printJavaInfo("O2->Send->6")
            it.onNext(6)
            Thread.sleep(300)
            Printer.printJavaInfo("O2->Send->7")
            it.onNext(7)
            Thread.sleep(400)
            Printer.printJavaInfo("O2->Send->8")
            it.onNext(8)
            Thread.sleep(500)
//            it.onComplete()
        }.subscribeOn(Schedulers.newThread())

        val observable3 = Observable.create<Int> {
            Printer.printJavaInfo("O3->Send->9")
            it.onNext(9)
            Thread.sleep(300)
            Printer.printJavaInfo("O3->Send->10")
            it.onNext(10)
            Thread.sleep(400)
            Printer.printJavaInfo("O3->Send->11")
            it.onNext(11)
            Thread.sleep(500)
            Printer.printJavaInfo("O3->Send->12")
            it.onNext(12)
            Thread.sleep(600)
//            it.onComplete()
        }.subscribeOn(Schedulers.newThread())


//        Observable.combineLatest(observable1, observable2, BiFunction<Int, Int, Int> { t1, t2 -> t1 + t2 }
//        ).subscribe { Printer.printJavaInfo("ReceiveValue->$it") }

        //多个Observable
        Observable.combineLatest(
            observable1,
            observable2,
            observable3,
            Function3<Int, Int, Int, Int> { t1, t2, t3 -> t1 + t2 + t3 })
            .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     *
     */
    fun cbJoin() {

        val observable1 = Observable.create<Int> {
            Printer.printJavaInfo("O1->Send->1")
            it.onNext(1)
            Thread.sleep(100)
            Printer.printJavaInfo("O1->Send->2")
            it.onNext(2)
            Thread.sleep(200)
            Printer.printJavaInfo("O1->Send->3")
            it.onNext(3)
            Thread.sleep(300)
            Printer.printJavaInfo("O1->Send->4")
            it.onNext(4)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())


        val observable2 = Observable.create<Int> {
            Printer.printJavaInfo("O2->Send->5")
            it.onNext(5)
            Thread.sleep(200)
            Printer.printJavaInfo("O2->Send->6")
            it.onNext(6)
            Thread.sleep(300)
            Printer.printJavaInfo("O2->Send->7")
            it.onNext(7)
            Thread.sleep(400)
            Printer.printJavaInfo("O2->Send->8")
            it.onNext(8)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())

        observable1.join(
            //合并的Observable
            observable2,
            //定义源Observable数据有效时效
            Function<Int, ObservableSource<Long>> { Observable.timer(300, TimeUnit.MILLISECONDS) },
            //定义合并Observable数据有效时效
            Function<Int, ObservableSource<Long>> { Observable.timer(300, TimeUnit.MILLISECONDS) },
            //定义合并方式
            BiFunction<Int, Int, Int> { t1, t2 -> t1 + t2 })
            .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Switch操作符
     * 将一个发射多个Observables的Observable转换成另一个单独的Observable，
     * 后者发射那些Observables最近发射的数据项
     */
    fun cbSwitch() {
        val observable1 = Observable.create<Int> {
            Printer.printJavaInfo("O1->Send->1")
            it.onNext(1)
            Thread.sleep(100)
            Printer.printJavaInfo("O1->Send->2")
            it.onNext(2)
            Thread.sleep(200)
            Printer.printJavaInfo("O1->Send->3")
            it.onNext(3)
            Thread.sleep(300)
            Printer.printJavaInfo("O1->Send->4")
            it.onNext(4)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())

        val observable2 = Observable.create<Int> {
            Printer.printJavaInfo("O2->Send->5")
            it.onNext(5)
            Thread.sleep(200)
            Printer.printJavaInfo("O2->Send->6")
            it.onNext(6)
            Thread.sleep(300)
            Printer.printJavaInfo("O2->Send->7")
            it.onNext(7)
            Thread.sleep(400)
            Printer.printJavaInfo("O2->Send->8")
            it.onNext(8)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())

        Observable.switchOnNext(ObservableSource<ObservableSource<Int>> {
            it.onNext(observable1)
            Thread.sleep(2000)
            it.onNext(observable2)
        }).subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }


    companion object {
        val instance = lazy { RxOperatorCombine() }.value
    }
}