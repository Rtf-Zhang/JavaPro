package rxjava

import io.reactivex.Single
import io.reactivex.SingleSource

class RxSingleObservable {

    fun startTest() {
        getSingleObservable1().subscribe(RxObserver.instance.getSingleObserverString())
    }

    /**
     * 通过Create创建一个Single生产者
     */
    private fun getSingleObservableCreate(): Single<String> {
        return Single.create<String> {
            //OnSuccess 和 OnError 同一时间只能调用一个且一次
            it.onSuccess("value1")
//            it.onError(Throwable("This is single exception"))
        }
    }

    /**
     * 通过Error创建一个Single生产者
     */
    private fun getSingleObservableError(): Single<String> {
        return Single.error { Throwable("SingleObservableError!") }
    }

    /**
     * 通过转换Observable为一个Single生产者
     */
    private fun getSingleObservableCompose(): Single<String> {
        return Single.fromObservable {
            it.onNext("Single-Observable-value1")
            //只能转换发送单条数据的Observable.发送第二条会报空指针
//            it.onNext("Single-Observable-value2")
            it.onComplete()
        }
    }

    /**
     * 将多个SingleSource合并成一个Single生产者，且只发送队列中最先发送数据的生产者,其余丢弃
     */
    private fun getSingleObservableAmb(): Single<String> {
        val singleSources = mutableListOf<SingleSource<String>>()
        singleSources.add(SingleSource {
            it.onSuccess("SingleSource1:Emit:Value1")
        })
        singleSources.add(SingleSource {
            it.onSuccess("SingleSource2:Emit:Value2")
        })
        singleSources.add(SingleSource {
            it.onSuccess("SingleSource3:Emit:Value3")
        })
        return Single.amb(singleSources)
    }


    private fun getSingleObservable1(): Single<String> {
        return Single.defer {
            getSingleObservableCreate()
        }
    }

    companion object {
        val instance = lazy { RxSingleObservable() }.value
    }
}