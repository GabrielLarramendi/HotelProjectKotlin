package dominando.android.hotelproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import dominando.android.hotelproject.R
import dominando.android.hotelproject.interfaces.HotelDetailsView
import dominando.android.hotelproject.model.Hotel
import dominando.android.hotelproject.presenters.HotelDetailsPresenter
import dominando.android.hotelproject.repositories.MemoryRepository

class HotelDetailsFragment:Fragment(), HotelDetailsView {
    private val presenter = HotelDetailsPresenter(this, MemoryRepository)
    private var hotel: Hotel? = null

    private var textName: TextView? = null
    private var textAddress: TextView? = null
    private var rtbRating: RatingBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hotel_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textName = view.findViewById(R.id.txtName)
        textAddress = view.findViewById(R.id.txtAddress)
        rtbRating = view.findViewById(R.id.rtbRating)

        presenter.loadHotelDetails(arguments?.getLong(EXTRA_HOTEL_ID, -1)?:-1)
    }

    override fun showHotelDetails(hotel: Hotel?) {
        this.hotel = hotel
        textName?.text = hotel?.name
        textAddress?.text = hotel?.address
        if(hotel != null) {
            rtbRating?.rating = hotel.rating
        }
    }

    override fun errorHotelNotFound() {
        textName?.text = getString(R.string.error_hotel_not_found)
        textAddress?.visibility = View.GONE
        rtbRating?.visibility = View.GONE
    }

    companion object {
        const val TAG_DETAILS = "tagDetalhe"
        private const val EXTRA_HOTEL_ID = "hotelId"

        fun newInstance(id: Long) = HotelDetailsFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_HOTEL_ID, id)
            }
        }
    }
}