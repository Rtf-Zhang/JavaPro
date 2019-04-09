package rxjava

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import util.Printer
import java.lang.System.exit
import java.util.concurrent.TimeUnit


object RxFlatMap {

    @JvmStatic
    fun main(args: Array<String>) {
        //普通生产者消费者
//        RxOperatorCreate.instance.startTest()

        //Single生产者消费者
//        RxSingleObservable.instance.startTest()

        //Subject
//        RxSubject.instance.startTest()

        //Transform Operator
//        RxOperatorTransform.instance.startTest()

        //Filter operator
//        RxOperatorFilter.instance.startTest()

        //Combine operator
//        RxOperatorCombine.instance.startTest()

        //Error operator
//        RxOperatorError.instance.startTest()

        //Auxiliary operator
//        RxOperatorAuxiliary.instance.startTest()

        //Condition operator
//        RxOperatorCondition.instance.startTest()

        //Connect operator
//        RxOperatorConnect.in
//        stance.startTest()

//        Observable.just(1, 2, 3)
//            .subscribe { Printer.printJavaInfo("ReceiveValue->$it") }
    }

}
