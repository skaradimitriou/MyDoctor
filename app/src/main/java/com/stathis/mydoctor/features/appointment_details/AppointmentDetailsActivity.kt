package com.stathis.mydoctor.features.appointment_details


import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.models.Appointment
import kotlinx.android.synthetic.main.activity_appointment_details.*
import kotlinx.android.synthetic.main.appointment_card_item.*
import kotlinx.android.synthetic.main.appointment_card_item.view.*

class AppointmentDetailsActivity : AbstractActivity(R.layout.activity_appointment_details) {

    private lateinit var viewModel : AppointmentDetailsViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(AppointmentDetailsViewModel::class.java)
    }

    override fun running() {
        /*
        Logic bellow:

        - View my appointment details in a clean way
        - Be able to cancel my appointment or call my doctor

         */

        /*
        FIXME:
        - Type -> Video Consultation (with img vector at end)
        - Date & Time -> XX/XX/XXXX, XX:XX (calendar img vector)
        - Duration -> 1h (clock svg vector)
        - Estimated cost -> 25$ (card img)
        - Location (?) -> Athens (location icon)
         */

        val appointment = intent.getStringExtra("APPOINTMENT")

        appointment?.let{
            val model = Gson().fromJson(appointment, Appointment::class.java)
            bindAppointmentData(model)
        }
    }

    private fun bindAppointmentData(data : Appointment) {
        Glide.with(this).load(data.doctorImg).into(appointment_img)
        doctor_name.text = data.doctorName

        appointment_date.apply {
            this.item_header.text = "Date & Time"
            this.item_desc.text = "${data.date} | ${data.startHour} - ${data.endHour}"
            this.item_img.setImageResource(R.drawable.ic_monthly_calendar)
        }

        appointment_location.apply {
            this.item_header.text = "Location"
            this.item_desc.text = data.location
            this.item_img.setImageResource(R.drawable.ic_location)
        }

        appointment_reason.apply {
            this.item_header.text = "Type of visit"
            this.item_desc.text = data.description
            this.item_img.setImageResource(R.drawable.ic_visit_reason)
        }

        appointment_price.apply {
            this.item_header.text = "Estimated Cost"
            this.item_desc.text = "25$"
            this.item_img.setImageResource(R.drawable.ic_euro)
        }
    }

    override fun stopped() = viewModel.release(this)
}