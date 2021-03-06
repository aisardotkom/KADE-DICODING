package inn.mroyek.submission5kade.presentation.ui.standings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.android.support.DaggerFragment
import inn.mroyek.submission5kade.R
import inn.mroyek.submission5kade.data.remote.model.Table
import inn.mroyek.submission5kade.presentation.model.Leagues
import kotlinx.android.synthetic.main.fragment_standing.view.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class StandingFragment(bundle: Bundle?) : DaggerFragment(), StandingContract {
//    private val presenter = StandingPresenter(ApiRepository())
    @Inject
    lateinit var presenter : StandingPresenter

    private val adapterStanding = GroupAdapter<GroupieViewHolder>()
    private var league: Leagues? = null
    private val id = bundle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter.bind(this)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_standing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        league = id?.getParcelable("id")
        view.rv_standing.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterStanding
        }
        presenter.getStanding(id = league?.id.toString())
    }


    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

    override fun showStanding(standing: List<Table?>) {
        adapterStanding.clear()
        standing.forEach {
            adapterStanding.add(StandingAdapter(it))
        }
    }
}
