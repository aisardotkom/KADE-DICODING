package inn.mroyek.submission5kade.presentation.ui.searchMatch

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import inn.mroyek.submission5kade.R
import inn.mroyek.submission5kade.common.parsingDate
import inn.mroyek.submission5kade.presentation.model.Search
import kotlinx.android.synthetic.main.item_match_adapter.view.*

class SearchMatchAdapter(private val itemMatch: Search?,
                         private val onItemClick: () -> Unit) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val view = viewHolder.itemView
        view.tv_date_time_match.text = itemMatch?.dateEvent?.parsingDate("yyyy-MM-dd")
        view.tv_home_team_match.text = itemMatch?.homeTeamStr
        view.tv_home_score_match.text = itemMatch?.homeScoreInt
        view.tv_away_team_match.text = itemMatch?.awayTeamStr
        view.tv_away_score_match.text = itemMatch?.awayScoreInt

        view.setOnClickListener {
            onItemClick()
        }
    }

    override fun getLayout(): Int = R.layout.item_match_adapter

}