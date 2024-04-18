package dominando.android.hotelproject.repositories

import dominando.android.hotelproject.model.Hotel

interface HotelRepository {
    fun save(hotel: Hotel)
    fun remove(vararg hotel: Hotel)
    fun hotelById(id: Long, callback: (Hotel?) -> Unit)
    fun search(term:String, callback: (List<Hotel>) -> Unit)
}