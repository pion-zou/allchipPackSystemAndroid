package com.example.allchip.model

import androidx.lifecycle.ViewModel
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

open class AutoDisposeRepository : IRepository,
        LifecycleScopeProvider<AutoDisposeRepository.ModelEvent> {


    // Subject backing the auto disposing of subscriptions.
    private val lifecycleEvents = BehaviorSubject.createDefault(ModelEvent.CREATED)

    /**
     * The events that represent the lifecycle of a [ViewModel].
     *
     * The [ViewModel] lifecycle is very simple. It is created
     * and then allows you to clean up any resources in the
     * [ViewModel.onCleared] method before it is destroyed.
     */
    enum class ModelEvent {
        CREATED, CLEARED
    }

    /**
     * The observable representing the lifecycle of the [ViewModel].
     *
     * @return [Observable] modelling the [ViewModel] lifecycle.
     */
    override fun lifecycle(): Observable<ModelEvent> {
        return lifecycleEvents.hide()
    }

    /**
     * Returns a [CorrespondingEventsFunction] that maps the
     * current event -> target disposal event.
     *
     * @return function mapping the current event to terminal event.
     */
    override fun correspondingEvents(): CorrespondingEventsFunction<ModelEvent> {
        return CORRESPONDING_EVENTS
    }

    override fun peekLifecycle(): ModelEvent? {
        return lifecycleEvents.value
    }

    /**
     * Emit the [ModelEvent.CLEARED] event to
     * dispose off any subscriptions in the ViewModel.
     */
    override fun onCleared() {
        lifecycleEvents.onNext(ModelEvent.CLEARED)
    }

    companion object {
        /**
         * Function of current event -> target disposal event. ViewModel has a very simple lifecycle.
         * It is created and then later on cleared. So we only have two events and all subscriptions
         * will only be disposed at [ModelEvent.CLEARED].
         */
        private val CORRESPONDING_EVENTS = CorrespondingEventsFunction<ModelEvent> { event ->
            when (event) {
                ModelEvent.CREATED -> ModelEvent.CLEARED
                else -> throw LifecycleEndedException(
                        "Cannot bind to ViewModel lifecycle after onCleared.")
            }
        }
    }
}