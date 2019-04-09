package rxjava

import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

class RxSubject {

    fun startTest() {
        val subject = getReplaySubject()
        subject.onNext("value1 After original subscribe")
        subject.onNext("value2 After original subscribe")
        subject.subscribe(RxObserver.instance.getStandardObserverString())
        subject.onNext("value1 After new subscribe")
        subject.onComplete()
        subject.onNext("value2 After new subscribe")

    }

    /**
     * AsyncSubject
     * 一个AsyncSubject只在原始Observable完成后，
     * 发射来自原始Observable的最后一个值
     */
    fun getAsyncSubject(): AsyncSubject<String> {
        return AsyncSubject.create()
    }

    /**
     * BehaviorSubject
     * 一个BehaviorSubject只在原始Observable完成后，
     * 发射来自原始Observable的最后一个值
     * 然后继续发送后续数据
     */
    fun getBehaviorSubject(): BehaviorSubject<String> {
        return BehaviorSubject.create()
    }

    /**
     * PublishSubject
     * PublishSubject只会把在订阅发生的时间点之后,
     * 来自原始Observable的数据发射给观察者
     * 然后继续发送后续数据
     */
    fun getPublishSubject(): PublishSubject<String> {
        return PublishSubject.create()
    }

    /**
     * ReplaySubject
     * ReplaySubject会发射所有来自原始Observable的数据给观察者，
     * 无论它们是何时订阅的
     */
    fun getReplaySubject(): ReplaySubject<String> {
        return ReplaySubject.create()
    }

    companion object {
        val instance = lazy { RxSubject() }.value
    }
}