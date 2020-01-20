package inn.mroyek.submission5kade.search

import inn.mroyek.submission5kade.model.pojo.Search
import inn.mroyek.submission5kade.network.ApiRepository
import inn.mroyek.submission5kade.ui.search.SearchContract
import inn.mroyek.submission5kade.ui.search.SearchMatchPresenter
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SearchPresenterTest {
    @Mock
    lateinit var repository: ApiRepository
    @Mock
    lateinit var callBack: SearchContract
    private lateinit var presenter: SearchMatchPresenter

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        val testScheduler = TestScheduler()
        presenter = SearchMatchPresenter(repository, testScheduler, testScheduler)
    }

    @Test
    fun `fetch valid data`(){
        val listSearch = mutableListOf<Search>()
        val query = ""

        presenter.bindCallBack(callBack)
        Mockito.`when`(repository.getSearchMatch(query))
            .thenReturn(Single.just(listSearch))

        presenter.getSearchMatch(query)

        verify(callBack, never()).showMatch(listSearch)
    }
}