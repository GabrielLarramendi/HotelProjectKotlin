package dominando.android.hotelproject.presenters

import dominando.android.hotelproject.interfaces.HotelDetailsView
import dominando.android.hotelproject.repositories.HotelRepository

class   HotelDetailsPresenter(
    private val view: HotelDetailsView,
    private val repository: HotelRepository
) {
    fun loadHotelDetails(id:Long) {
        repository.hotelById(id) { hotel ->
            if (hotel != null) {
                view.showHotelDetails(hotel)
            } else {
                view.errorHotelNotFound()
            }
        }
    }
}