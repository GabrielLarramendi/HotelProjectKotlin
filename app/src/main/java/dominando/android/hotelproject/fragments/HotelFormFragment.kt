package dominando.android.hotelproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText
import dominando.android.hotelproject.R
import dominando.android.hotelproject.interfaces.HotelFormView
import dominando.android.hotelproject.model.Hotel
import dominando.android.hotelproject.presenters.HotelFormPresenter
import dominando.android.hotelproject.repositories.MemoryRepository

class HotelFormFragment: DialogFragment(), HotelFormView {

    private var edtName:TextInputEditText? = null
    private var edtAddress:TextInputEditText? = null
    private lateinit var rtbRating:RatingBar

    private val presenter = HotelFormPresenter(this, MemoryRepository)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_hotel_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        edtName = view.findViewById(R.id.edtName)
        edtAddress = view.findViewById(R.id.edtAddress)
        rtbRating = view.findViewById(R.id.rtbRating)

        super.onViewCreated(view, savedInstanceState)

        val hotelId = arguments?.getLong(EXTRA_HOTEL_ID, 0) ?: 0
        presenter.loadHotel(hotelId)

        val edtTxtAddress:TextInputEditText = view.findViewById(R.id.txtAddress)
        edtTxtAddress.setOnEditorActionListener {_, i, _ ->
            handleKeyBoardEvent(i)
        }

        dialog?.setTitle(R.string.action_new_hotel)

        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)


    }

    override fun showHotel(hotel: Hotel) {
        edtName?.setText(hotel.name)
        edtAddress?.setText(hotel.address)
        rtbRating.rating = hotel.rating
    }

    override fun errorInvalidHotel() {
        Toast.makeText(requireContext(), R.string.error_invalid_hotel, Toast.LENGTH_SHORT).show()
    }

    override fun errorSaveHotel() {
        Toast.makeText(requireContext(), R.string.error_hotel_not_found, Toast.LENGTH_SHORT).show()
    }

    private fun handleKeyBoardEvent(actionId:Int):Boolean {
        if(EditorInfo.IME_ACTION_DONE == actionId) {
            val hotel = saveHotel()
            if (hotel != null)
                if(activity is OnHotelSavedListener) {
                    val listener = activity as OnHotelSavedListener
                    listener.onHotelSaved(hotel)
                }
            dialog?.dismiss()
            return true
        }
        return false
    }

    private fun saveHotel(): Hotel? {
        val hotel = Hotel()
        val hotelId = arguments?.getLong(EXTRA_HOTEL_ID, 0) ?: 0
        hotel.id = hotelId
        hotel.name = edtName?.text.toString()
        hotel.address = edtAddress?.text.toString()
        hotel.rating = rtbRating.rating

        return if(presenter.saveHotel(hotel)) {
            hotel
        } else {
            null
        }
    }

    fun open(fm: FragmentManager) {
        if(fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG)
        }
    }

    interface OnHotelSavedListener {
        fun onHotelSaved(hotel: Hotel)
    }

    companion object {
        private const val DIALOG_TAG = "editDialog"
        private const val EXTRA_HOTEL_ID = "hotel_id"

        fun newInstance(hotelId:Long = 0) = HotelFormFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_HOTEL_ID, hotelId)
            }
        }
    }
}