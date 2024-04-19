package dominando.android.hotelproject.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import dominando.android.hotelproject.interfaces.HotelListView
import dominando.android.hotelproject.model.Hotel
import dominando.android.hotelproject.presenters.HotelListPresenter
import dominando.android.hotelproject.repositories.MemoryRepository

class HotelListFragment:ListFragment(), HotelListView {
    private val presenter = HotelListPresenter(this, MemoryRepository)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.searchHotels("")
    }

    override fun showHotels(hotels: List<Hotel>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, hotels)
        listAdapter = adapter
    }

    override fun showHotelDetails(hotel: Hotel) {
        if(activity is OnHotelClickListener) {
            val listener = activity as OnHotelClickListener
            listener.onHotelClick(hotel)
        }
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val hotel = l.getItemAtPosition(position) as Hotel
        presenter.showHotelDetails(hotel)
    }

    interface OnHotelClickListener {
        fun onHotelClick(hotel: Hotel)
    }

    fun search(text: String) {
        presenter.searchHotels(text)
    }

    fun clearSearch() {
        presenter.searchHotels("")
    }
}