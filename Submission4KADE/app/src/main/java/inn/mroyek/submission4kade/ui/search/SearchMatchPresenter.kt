package inn.mroyek.submission4kade.ui.search

import inn.mroyek.submission4kade.base.BasePresenter
import inn.mroyek.submission4kade.network.ApiRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchMatchPresenter(
    private val repository: ApiRepository,
    private val backgroundScheduler: Scheduler = Schedulers.io(),
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread()
) : BasePresenter<SearchContract>() {
    fun getSearchMatch(query: String) {
        disposable.add(
            repository.getSearchMatch(query)
                ?.subscribeOn(backgroundScheduler)
                ?.observeOn(mainScheduler)
                ?.subscribe({
                    callback?.showMatch(listMacth = it)
                }, Throwable::printStackTrace)!!

        )
    }
}