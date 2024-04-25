package dominando.android.hotelproject.interfaces

import dominando.android.hotelproject.model.Hotel

interface HotelFormView {
    fun showHotel(hotel: Hotel)
    fun errorInvalidHotel()
    fun errorSaveHotel()
}