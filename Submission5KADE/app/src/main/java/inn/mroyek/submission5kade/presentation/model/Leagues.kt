package inn.mroyek.submission5kade.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Leagues(
    var id: String = "",
    var title: String = "",
    var img: String = ""
) : Parcelable