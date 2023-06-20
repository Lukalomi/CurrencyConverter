package com.example.currencyconverter.presentation.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.archlifecycle.LifecycleController
import com.evernote.android.state.StateSaver
import com.example.currencyconverter.presentation.base.interfaces.IIntent
import com.example.currencyconverter.presentation.base.interfaces.IState
import com.example.currencyconverter.presentation.base.interfaces.IView
import com.example.currencyconverter.presentation.base.utils.ControllerAttachedEvent
import com.example.currencyconverter.presentation.base.utils.ViewModelUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseController<S : IState, I : IIntent, M : BaseViewModel<S, I>> :
    LifecycleController, CoroutineScope {

    @Inject
    lateinit var viewModel: M
    @Inject
    lateinit var eventBus: EventBus

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var job: Job

    protected lateinit var contentView: IView<S>

    constructor(args: Bundle) : super(args)

    constructor() : super()

    abstract fun inject()
    abstract fun onCreateControllerView(inflater: LayoutInflater): IView<S>

    private fun observeChanges() {
        viewModel.state.onEach {
            contentView.render(it)
        }.launchIn(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        inject()
        addLogs()
        job = SupervisorJob()
        viewModel = viewModelProvider(ViewModelUtils.createFor(viewModel))[viewModel::class.java]
        observeChanges()
        return onCreateControllerView(inflater).also {
            contentView = it
        } as View
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        addLogs()
        onAttach()

        try {
            eventBus.register(this)
        } catch (exception: Exception) {
        }
        eventBus.post(ControllerAttachedEvent(this))
    }

    open fun onAttach() {}

    override fun onDetach(view: View) {
        super.onDetach(view)
        addLogs()

        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this)
        }
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        job.cancel()
        addLogs()
    }

    override fun onDestroy() {
        super.onDestroy()
        addLogs()
    }

    override fun onActivityPaused(activity: Activity) {
        super.onActivityPaused(activity)
        addLogs()
    }

    override fun onActivityResumed(activity: Activity) {
        super.onActivityResumed(activity)
        addLogs()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        addLogs()
    }

    override fun onActivityStarted(activity: Activity) {
        super.onActivityStarted(activity)
        addLogs()
    }

    override fun onActivityStopped(activity: Activity) {
        super.onActivityStopped(activity)
        addLogs()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        StateSaver.saveInstanceState(this, outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        StateSaver.restoreInstanceState(this, savedInstanceState)
    }

    protected fun getString(id: Int): String {
        return activity?.getString(id).toString()
    }

    protected fun getSavedViewState(): Bundle? =
        viewState?.getBundle(Controller.KEY_VIEW_STATE_BUNDLE)

    private fun addLogs() {
        val methodName = Thread.currentThread().stackTrace[3].methodName
//        Sentry.addBreadcrumb(javaClass.simpleName, methodName)
//        FirebaseCrashlytics.getInstance().log(this.javaClass.simpleName + ": " + methodName)
    }
}



