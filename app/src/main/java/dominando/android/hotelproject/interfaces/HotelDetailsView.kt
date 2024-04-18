package dominando.android.hotelproject.interfaces

import dominando.android.hotelproject.model.Hotel

interface HotelDetailsView {
    fun showHotelDetails(hotel: Hotel?)
    fun errorHotelNotFound()
}