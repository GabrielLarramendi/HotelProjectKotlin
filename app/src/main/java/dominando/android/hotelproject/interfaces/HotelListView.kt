package dominando.android.hotelproject.interfaces

import dominando.android.hotelproject.model.Hotel

interface HotelListView {
    fun showHotels(hotels: List<Hotel>)
    fun showHotelDetails(hotel: Hotel)
}