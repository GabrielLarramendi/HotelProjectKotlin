package dominando.android.hotelproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import dominando.android.hotelproject.R
import dominando.android.hotelproject.fragments.HotelDetailsFragment
import dominando.android.hotelproject.fragments.HotelListFragment
import dominando.android.hotelproject.model.Hotel

class MainActivity : AppCompatActivity(), HotelListFragment.OnHotelClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onHotelClick(hotel: Hotel) {
        if(isTablet()) {
            showDetailsFragment(hotel.id)
        }
        if (isSmartphone()) {
            showDetailsActivity(hotel.id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    private fun showDetailsActivity(hotelId: Long) {
        HotelDetailsActivity.open(this, hotelId)
    }

    private fun isTablet() = resources.getBoolean(R.bool.tablet)
    private fun isSmartphone() = resources.getBoolean(R.bool.smartphone)

    private fun showDetailsFragment(hotelId: Long) {
        val fragment = HotelDetailsFragment.newInstance(hotelId)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.details, fragment, HotelDetailsFragment.TAG_DETAILS)
            .commit()
    }
}