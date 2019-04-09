package rxjava

import io.reactivex.Observable
import io.reactivex.ObservableSource
import util.Printer

class RxOperatorError {


    fun startTest() {
//        errReturn()
//        errNext()
//        errReturnItem()
        errRetry()
    }

    fun errReturn() {
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onError(Throwable("ErrorTest"))
        }.onErrorReturn { 100 }
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    fun errNext() {
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onError(Throwable("ErrorTest"))
        }.onErrorResumeNext(ObservableSource { it.onNext(100) })
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    fun errReturnItem() {
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onError(Throwable("ErrorTest"))
        }.onErrorReturnItem(100)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

    /**
     * Retry操作符
     * 如遇错误多次尝试,仍不成功则错误
     */
    fun errRetry() {
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onError(Throwable("ErrorTest"))
        }
                .retry(2)
                .onErrorReturnItem(100)
                .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }


    companion object {
        val instance = lazy { RxOperatorError() }.value
    }
}
