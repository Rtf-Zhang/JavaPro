package rxjava

import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import util.Printer

class RxObserver {

    /**
     * 获取一个普通的观察者
     * String类型
     */
    fun getStandardObserverString(): Observer<String> {

        return object : Observer<String> {

            override fun onComplete() {
                Printer.printJavaInfo("Observer-->onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                Printer.printJavaInfo("Observer-->onSubscribe")
            }

            override fun onNext(t: String) {
                Printer.printJavaInfo("Observer-->receive-->$t")
            }

            override fun onError(e: Throwable) {
                Printer.printJavaInfo("Observer-->onError-->$e")
            }
        }
    }

    /**
     * 获取一个普通的观察者
     * Int类型
     */
    fun getStandardObserverInt(): Observer<Int> {
        return object : Observer<Int> {

            override fun onComplete() {
                Printer.printJavaInfo("Observer-->onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                Printer.printJavaInfo("Observer-->onSubscribe")
            }

            override fun onNext(t: Int) {
                Printer.printJavaInfo("Observer-->receive-->$t")
            }

            override fun onError(e: Throwable) {
                Printer.printJavaInfo("Observer-->onError-->$e")
            }
        }
    }

    /**
     * 获取一个普通的观察者
     * Long类型
     */
    fun getStandardObserverLong(): Observer<Long> {
        return object : Observer<Long> {

            override fun onComplete() {
                Printer.printJavaInfo("Observer-->onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                Printer.printJavaInfo("Observer-->onSubscribe")
            }

            override fun onNext(t: Long) {
                Printer.printJavaInfo("Observer-->receive-->$t")
            }

            override fun onError(e: Throwable) {
                Printer.printJavaInfo("Observer-->onError-->$e")
            }
        }
    }

    /**
     * Single消费者
     * String 类型
     */
    fun getSingleObserverString(): SingleObserver<String> {
        return object : SingleObserver<String> {

            override fun onSubscribe(d: Disposable) {
                Printer.printJavaInfo("SingleObserver:onSubscribe")
            }

            override fun onError(e: Throwable) {
                Printer.printJavaInfo("SingleObserver:onError")
            }

            override fun onSuccess(t: String) {
                Printer.printJavaInfo("SingleObserver:onSuccess:$t")
            }

        }
    }

    /**
     * Single消费者
     * Int 类型
     */
    fun getSingleObserverInt(): SingleObserver<Int> {
        return object : SingleObserver<Int> {

            override fun onSubscribe(d: Disposable) {
                Printer.printJavaInfo("SingleObserver:onSubscribe")
            }

            override fun onError(e: Throwable) {
                Printer.printJavaInfo("SingleObserver:onError")
            }

            override fun onSuccess(t: Int) {
                Printer.printJavaInfo("SingleObserver:onSuccess:$t")
            }

        }
    }

    /**
     * Single消费者
     * Boolean 类型
     */
    fun getSingleObserverBoolean(): SingleObserver<Boolean> {
        return object : SingleObserver<Boolean> {

            override fun onSubscribe(d: Disposable) {
                Printer.printJavaInfo("SingleObserver:onSubscribe")
            }

            override fun onError(e: Throwable) {
                Printer.printJavaInfo("SingleObserver:onError")
            }

            override fun onSuccess(t: Boolean) {
                Printer.printJavaInfo("SingleObserver:onSuccess:$t")
            }

        }
    }


    companion object {
        val instance = lazy { RxObserver() }.value
    }
}