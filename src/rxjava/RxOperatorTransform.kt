package rxjava

import io.reactivex.*
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import util.Printer
import java.util.concurrent.TimeUnit

class RxOperatorTransform {

    fun startTest() {
//        opeMap()
//        opeFlatMap()
//        opeConcatMap()
//        opeSwitchMap()
//        opeGroupBy()
//        opeScan()
//        opeBuffer()
//        opeBufferSkip()
//        opeBufferClosing()
//        opeBufferBoundary()
//        opeBufferOpeningClosing()
//        opeBufferTimespan()
//        opeBufferTimespanAndCount()
    }

    /**
     * Map操作符
     * 对原始Observable发射的每一项数据应用一个你选择的函数，
     * 然后返回一个发射这些结果的Observable。
     * RxJava将这个操作符实现为map函数。
     * 这个操作符默认不在任何特定的调度器上执行。
     */
    fun opeMap() {
        Observable.just(1, 2, 3, 4)
                .map { "map->$it" }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * FlatMap操作符
     * FlatMap操作符使用一个指定的函数对原始Observable发射的每一项数据执行变换操作，
     * 这个函数返回一个本身也发射数据的Observable，
     * 然后FlatMap合并这些Observables发射的数据，
     * 最后将合并后的结果当做它自己的数据序列发射。
     * PS:
     * 额外的int参数的一个变体，
     * 这个参数设置flatMap从原来的Observable映射Observables的最大同时订阅数。
     * 当达到这个限制时，它会等待其中一个终止然后再订阅另一个。
     */
    fun opeFlatMap() {
        Observable.just(1, 3, 6, 9)
                .flatMap({ t ->
                    ObservableSource<Int> {
                        it.onNext(t * 10)
                        it.onNext(t * 20)
                        it.onNext(t * 30)
                        it.onComplete()
                    }
                }, 2)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }


    /**
     * ConcatMap操作符
     * 类似于最简单版本的flatMap，
     * 但是它按次序连接而不是合并那些生成的Observables，
     * 然后产生自己的数据序列。
     */
    fun opeConcatMap() {
        Observable.just(1, 3, 6, 9)
                .concatMap {
                    ObservableSource<Int> {
                        var count = 0
                        while (count++ < 10) {
                            it.onNext(count)
                            if (count == 5) {
                                it.onComplete()
                            }
                        }
                    }
                }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }


    /**
     * SwitchMap操作符
     * RxJava还实现了switchMap操作符。它和flatMap很像，
     * 除了一点：当原始Observable发射一个新的数据（Observable）时，
     * 它将取消订阅并停止监视产生执之前那个数据的Observable，只监视当前这一个。
     */
    fun opeSwitchMap() {
        Observable.just(1, 3, 6, 9)
                .switchMap {
                    ObservableSource<Int> {
                        var count = 0
                        while (count++ < 10) {
                            it.onNext(count)
                            if (count == 5) {
                                it.onComplete()
                            }
                        }
                    }
                }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * GroupBy操作符
     * 将原始Observable分拆为一些Observables集合，
     * 它们中的每一个发射原始Observable数据序列的一个子序列。
     * 哪个数据项由哪一个Observable发射是由一个函数判定的，
     * 这个函数给每一项指定一个Key，Key相同的数据会被同一个Observable发射。
     *
     * 注意：groupBy将原始Observable分解为一个发射多个GroupedObservable的Observable，
     * 一旦有订阅，每个GroupedObservable就开始缓存数据。因此，如果你忽略这些GroupedObservable中的任何一个，
     * 这个缓存可能形成一个潜在的内存泄露。因此，如果你不想观察，也不要忽略GroupedObservable。
     * 你应该使用像take(0)这样会丢弃自己的缓存的操作符。
     */
    fun opeGroupBy() {
        Observable.just(1, 2, 3, 4).groupBy {
            if (it > 2) 1 else 0
        }.subscribe {
            if (it.key == 0) {
                it.subscribe { Printer.printJavaInfo("KEY-0-Value-$it") }
            } else {
                it.subscribe { Printer.printJavaInfo("KEY-1-Value-$it") }
            }
        }
    }

    /**
     * Scan操作符
     * Scan操作符对原始Observable发射的第一项数据应用一个函数，
     * 然后将那个函数的结果作为自己的第一项数据发射。
     * 它将函数的结果同第二项数据一起填充给这个函数来产生它自己的第二项数据。
     * 它持续进行这个过程来产生剩余的数据序列。
     * 这个操作符在某些情况下被叫做accumulator。
     */
    fun opeScan() {
        Observable.just(1, 2, 3, 4)
                .scan(100) { t1, t2 ->
                    t1 * t2
                }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Buffer操作符
     * 将一个Observable变换为另一个，原来的Observable正常发射数据，
     * 变换产生的Observable发射这些数据的缓存集合。
     * Buffer操作符在很多语言特定的实现中有很多种变体，
     * 它们在如何缓存这个问题上存在区别。
     *
     * 注意：
     * 如果原来的Observable发射了一个onError通知，
     * Buffer会立即传递这个通知，而不是首先发射缓存的数据，
     * 即使在这之前缓存中包含了原始Observable发射的数据。
     */
    fun opeBuffer() {
        Observable.range(0, 100)
                .buffer(10)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * buffer(count, skip)
     * 从原始Observable的第一项数据开始创建新的缓存，
     * 此后每当收到skip项数据，用count项数据填充缓存：
     * 开头的一项和后续的count-1项，它以列表(List)的形式发射缓存，
     * 取决于count和skip的值，这些缓存可能会有重叠部分（比如skip < count时），
     * 也可能会有间隙（比如skip > count时）。
     */
    fun opeBufferSkip() {
        Observable.range(1, 100)
                .buffer(5, 10)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * buffer(bufferClosingSelector)
     * 当它订阅原来的Observable时，
     * buffer(bufferClosingSelector)开始将数据收集到一个List，
     * 然后它调用bufferClosingSelector生成第二个Observable，
     * 当第二个Observable发射一个TClosing时，buffer发射当前的List，
     * 然后重复这个过程：开始组装一个新的List，
     * 然后调用bufferClosingSelector创建一个新的Observable并监视它。
     * 它会一直这样做直到原来的Observable执行完成
     */
    fun opeBufferClosing() {
        Observable.interval(200, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(30)
                .buffer(Observable.interval(500, TimeUnit.MILLISECONDS, Schedulers.newThread()))
                .subscribe {
                    Printer.printJavaInfo("ReceiveValue->$it")
                }
    }

    /**
     * buffer(boundary)
     * 监视一个名叫boundary的Observable，
     * 每当这个Observable发射了一个值，
     * 它就创建一个新的List开始收集来自原始Observable的数据并发射原来的List。，
     */
    fun opeBufferBoundary() {
        Observable.interval(200, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(30)
                .buffer(Observable.timer(2000, TimeUnit.MILLISECONDS, Schedulers.newThread()))
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * buffer(bufferOpenings, bufferClosingSelector)
     * 原始Obseravble开始发送数据之后，
     * buffer会监视bufferOpenings这个Observable，
     * 每当bufferOpenings发送出一个数据后,
     * 会创建出一个新的List开始存放原始的Obseravble发出的数据，
     * 相当于Open标记。
     * bufferOpenings发出的数据叫bufferClosingSelector的Func1会接收到，
     * 当bufferClosingSelector接收到这个信号后，
     * 根据需求做相应的处理。Func1会返回一个新的Observable，
     * 当buffer监测到这个新的Observable时，就会关闭List，然后将List发送出去
     */
    fun opeBufferOpeningClosing() {
        Observable.interval(200, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(30)
                .buffer(ObservableSource<Int> {
                    it.onNext(1)
                }, Function<Int, Observable<Long>> {
                    Observable.timer(3000, TimeUnit.MILLISECONDS, Schedulers.newThread())
                })
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * buffer(timespan, unit[, scheduler])
     * 定期以List的形式发射新的数据，
     * 每个时间段，收集来自原始Observable的数据
     * 从前面一个数据包裹之后，或者如果是第一个数据包裹，
     * 从有观察者订阅原来的Observale之后开始）。
     * 还有另一个版本的buffer接受一个Scheduler参数，
     * 默认情况下会使用computation调度器。
     */
    fun opeBufferTimespan() {
        Observable.interval(200, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(30)
                .buffer(1000, TimeUnit.MILLISECONDS)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * buffer(timespan, unit, count[, scheduler])
     * 每当收到来自原始Observable的count项数据，
     * 或者每过了一段指定的时间后，
     * buffer(timespan, unit, count)就以List的形式发射这期间的数据，
     * 即使数据项少于count项。
     * 还有另一个版本的buffer接受一个Scheduler参数，
     * 默认情况下会使用computation调度器。
     */
    fun opeBufferTimespanAndCount() {
        Observable.interval(200, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .take(30)
                .buffer(1000, TimeUnit.MILLISECONDS, 5)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }


    companion object {
        val instance = lazy { RxOperatorTransform() }.value
    }
}