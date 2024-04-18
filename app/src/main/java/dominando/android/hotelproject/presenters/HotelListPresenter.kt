package dominando.android.hotelproject.presenters

import dominando.android.hotelproject.interfaces.HotelListView
import dominando.android.hotelproject.model.Hotel
import dominando.android.hotelproject.repositories.HotelRepository

class HotelListPresenter(
    private val view: HotelListView,
    private val repository: HotelRepository
) {

    fun searchHotels(term:String) {
        repository.search(term) {hotels -> view.showHotels(hotels)}
    }

    fun showHotelDetails(hotel: Hotel) {
        view.showHotelDetails(hotel)
    }
}