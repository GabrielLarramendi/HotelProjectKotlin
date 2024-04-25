package dominando.android.hotelproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView

import dominando.android.hotelproject.R
import dominando.android.hotelproject.fragments.AboutDialogFragment
import dominando.android.hotelproject.fragments.HotelDetailsFragment
import dominando.android.hotelproject.fragments.HotelFormFragment
import dominando.android.hotelproject.fragments.HotelListFragment
import dominando.android.hotelproject.model.Hotel

class MainActivity : AppCompatActivity(),
    HotelListFragment.OnHotelClickListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener,
    HotelFormFragment.OnHotelSavedListener
{

    private var lastSearchTerm: String = ""
    private var searchView: SearchView? = null

    private val listFragment: HotelListFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentList) as HotelListFragment }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_SEARCH_TERM, lastSearchTerm)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lastSearchTerm = savedInstanceState.getString(EXTRA_SEARCH_TERM) ?: ""
    }

    override fun onHotelClick(hotel: Hotel) {
        if(isTablet()) {
            showDetailsFragment(hotel.id)
        }
        if (isSmartphone()) {
            showDetailsActivity(hotel.id)
        }
    }

    private fun showDetailsActivity(hotelId: Long) {
        searchView?.setOnQueryTextListener(null)
        HotelDetailsActivity.open(this, hotelId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hotel, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(this)
        searchView = searchItem?.actionView as SearchView
        searchView?.queryHint = getString(R.string.hint_search)
        searchView?.setOnQueryTextListener(this)

        if(lastSearchTerm.isNotEmpty()) {
            Handler().post {
                val query = lastSearchTerm
                searchItem.expandActionView()
                searchView?.setQuery(query, true)
                searchView?.clearFocus()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_info -> AboutDialogFragment().show(supportFragmentManager, "sobre")
            R.id.action_new -> HotelFormFragment.newInstance().open(supportFragmentManager)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?) = true

    override fun onQueryTextChange(newText: String?): Boolean {
        lastSearchTerm = newText ?:""
        listFragment.search(lastSearchTerm)
        return true
    }

    override fun onMenuItemActionExpand(item: MenuItem) = true

    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        lastSearchTerm = ""
        listFragment.clearSearch()
        return true
    }

    override fun onHotelSaved(hotel: Hotel) {
        listFragment.search(lastSearchTerm)
    }


    private fun isTablet() = resources.getBoolean(R.bool.tablet)
    private fun isSmartphone() = resources.getBoolean(R.bool.smartphone)

    private fun showDetailsFragment(hotelId: Long) {
        val fragment = HotelDetailsFragment.newInstance(hotelId)

        searchView?.setOnQueryTextListener(null)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.details, fragment, HotelDetailsFragment.TAG_DETAILS)
            .commit()
    }

    companion object {
        const val EXTRA_SEARCH_TERM = "lastSearch"
    }
}