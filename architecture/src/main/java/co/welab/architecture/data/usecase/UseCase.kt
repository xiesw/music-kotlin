package co.welab.architecture.data.usecase

/**
 * Created by pain.xie on 2020/6/5
 */
abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValue> {

    lateinit var mRequestValues: Q;

    lateinit var mUseCaseCallback: UseCaseCallback<P>

    fun run() {
        executeUseCase(mRequestValues)
    }

    protected abstract fun executeUseCase(requestValue: Q)

    interface RequestValues {
    }

    interface ResponseValue {

    }

    interface UseCaseCallback<R> {
        fun onSuccess(response: R)

        fun onError()
    }
}