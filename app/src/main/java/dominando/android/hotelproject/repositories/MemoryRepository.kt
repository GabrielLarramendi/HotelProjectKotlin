package dominando.android.hotelproject.repositories

import dominando.android.hotelproject.model.Hotel

object MemoryRepository:HotelRepository {
    private var nextId = 1L
    private var hotelList = mutableListOf<Hotel>()

    init {
        save(Hotel(0, "Mar Hotel", "Praia de PV", 4.0f))
        save(Hotel(0, "Ponta verde", "Praia de PV", 3.0f))
        save(Hotel(0, "Ponta verde Frances", "Praia do frances", 4.7f))
        save(Hotel(0, "Hotel Jatiuca", "Praia de Jatiuca", 3.9f))
        save(Hotel(0, "Ciribai", "Praia de Jatiuca", 4.9f))
        save(Hotel(0, "Ritz", "Praia de Cruz das Almas", 5.0f))
        save(Hotel(0, "Meridiano", "Praia de Pajucara", 3.9f))
        save(Hotel(0, "Ibis Budget", "Praia de Pajucara", 4.2f))
    }

    override fun save(hotel: Hotel) {
        if(hotel.id == 0L) {
            hotel.id = nextId++
            hotelList.add(hotel)
        } else {
            val index = hotelList.indexOfFirst { it.id == hotel.id }
            if(index > -1) {
                hotelList[index] = hotel
            } else {
                hotelList.add(hotel)
            }
        }
    }

    override fun remove(vararg hotel: Hotel) {
        hotelList.removeAll(hotel.toSet())
    }

    override fun hotelById(id: Long, callback: (Hotel?) -> Unit) {
        callback(hotelList.find { it.id == id })
    }

    override fun search(term: String, callback: (List<Hotel>) -> Unit) {
        callback(
            if(term.isEmpty()) hotelList
            else hotelList.filter {
                it.name.uppercase().contains(term.uppercase())
            }
        )
    }
}